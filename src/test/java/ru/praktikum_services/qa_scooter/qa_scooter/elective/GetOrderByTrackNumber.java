package ru.praktikum_services.qa_scooter.qa_scooter.elective;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.order.CreateOrderResponse;
import ru.praktikum_services.qa_scooter.order.Order;
import ru.praktikum_services.qa_scooter.track.OrderObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetOrderByTrackNumber {

    Order order;
    String orderTrackNumber;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        order = new Order("Thor", "Ivanov", "ul. Lenina, 39, 2", 4, "+7 800 355 35 55", 5, "2024-10-30", "Please, be quiet",
                new String[] {"BLACK"});
        orderTrackNumber = sendCreateOrder(order).body().as(CreateOrderResponse.class).getTrack();
    }

    @Test
    @DisplayName("Check response of GET request to /api/v1/orders/track with valid track returns order object")
    public void getOrderByTrack() {
        OrderObject orderObject = sendGetOrderByTrackNumber(orderTrackNumber).body().as(OrderObject.class);
        assertThat(orderObject, is(notNullValue()));
    }

    @Test
    @DisplayName("Check error message of GET request to /api/v1/orders/track without track number")
    public void errorMessageGetOrderByTrackWithoutTrack() {
        sendGetOrderByTrackNumber("").then().assertThat().body("message", equalTo
                ("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Check error message of GET request to /api/v1/orders/track with nonexistent track number")
    public void errorMessageGetOrderByTrackWithNonexistentTrack() {
        sendGetOrderByTrackNumber("1").then().assertThat().body("message", equalTo
                ("Заказ не найден"));
    }

    @Step("Create order")
    public Response sendCreateOrder(Order order) {
        Response response = given().header("Content-type", "application/json").and().body(order).post("/api/v1/orders");
        return response;
    }

    @Step("Send GET request to /api/v1/orders/track")
    public Response sendGetOrderByTrackNumber(String id) {
        return given().header("Content-type", "application/json").get("/api/v1/orders/track" + "?t=" + id);
    }

}
