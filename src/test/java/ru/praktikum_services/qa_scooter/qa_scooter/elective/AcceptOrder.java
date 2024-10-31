package ru.praktikum_services.qa_scooter.qa_scooter.elective;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.courier.Courier;
import ru.praktikum_services.qa_scooter.courier.CourierId;
import ru.praktikum_services.qa_scooter.order.CreateOrderResponse;
import ru.praktikum_services.qa_scooter.order.Order;
import ru.praktikum_services.qa_scooter.track.OrderObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AcceptOrder {

    Courier courier;
    Courier courierBlank;
    Order order;
    String courierId;
    String orderId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courier = new Courier("PJFry", "123321", "Fry");
        courierBlank = new Courier();
        sendCreateCourier(courier);
        courierId = getCourierId(courier);
        order = new Order("Russ", "Leman", "ul. Lenina, 39, 2", 4, "+7 800 355 35 55", 5, "2024-10-30", "Please, be quiet",
                new String[] {"BLACK"});
        sendCreateOrder(order);
        orderId = "" + sendGetOrderByTrackNumber(sendCreateOrder(order)
                .body().as(CreateOrderResponse.class).getTrack())
                .body().as(OrderObject.class).getOrder().getId();
    }

    @Test
    @DisplayName("Check response body of /api/v1/orders/accept/:id with valid request")
    public void acceptOrder() {
        sendAcceptOrder(orderId, courierId)
                .then().assertThat()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check error message of /api/v1/orders/accept/:id without courier id")
    public void errorMessageAcceptOrderWithoutCourierId() {
        sendAcceptOrder(orderId, "")
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Check error message of /api/v1/orders/accept/:id with nonexistent courier id")
    public void errorMessageAcceptOrderWithNonexistentCourierId() {
        sendAcceptOrder(orderId, "0001")
                .then().assertThat()
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Test
    @DisplayName("Check error message of /api/v1/orders/accept/:id without id")
    public void errorMessageAcceptOrderWithoutId() {
        sendAcceptOrder("", courierId)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Check error message of /api/v1/orders/accept/:id with nonexistent id")
    public void errorMessageAcceptOrderWithNonexistentId() {
        sendAcceptOrder("00000000", courierId).then().assertThat().body("message", equalTo("Заказа с таким id не существует"));
    }

    @Step("Send POST request to /api/v1/courier")
    public Response sendCreateCourier(Courier courier) {
        return given().header("Content-type", "application/json").and().body(courier).post("/api/v1/courier");
    }

    @Step("Send POST request to /api/v1/courier/login")
    public String getCourierId(Courier courier) {
        courierBlank.setLogin(courier.getLogin());
        courierBlank.setPassword(courier.getPassword());
        CourierId courierId = given().header("Content-type", "application/json").and().body(courierBlank).post("/api/v1/courier/login").body().as(CourierId.class);
        return courierId.getId();
    }

    @Step("Send DELETE request to /api/v1/courier/:id")
    public Response sendDeleteCourier(String id) {
        Response response = given().header("Content-type", "application/json").delete("/api/v1/courier/" + id);
        return response;
    }

    @Step("Send POST request to /api/v1/orders")
    public Response sendCreateOrder(Order order) {
        Response response = given().header("Content-type", "application/json").and().body(order).post("/api/v1/orders");
        return response;
    }

    @Step("Send GET request to /api/v1/orders/track")
    public Response sendGetOrderByTrackNumber(String id) {
        return given().header("Content-type", "application/json").get("/api/v1/orders/track" + "?t=" + id);
    }

    @Step("Send PUT request to /api/v1/orders/accept/:id")
    public Response sendAcceptOrder(String id, String courierId) {
        Response response = given().header("Content-type","application/json")
                .queryParam("courierId", courierId)
                .put("/api/v1/orders/accept/" + id);
        return response;
    }

    @After
    public void deleteCourier() {
        sendDeleteCourier(getCourierId(courier));
    }

}
