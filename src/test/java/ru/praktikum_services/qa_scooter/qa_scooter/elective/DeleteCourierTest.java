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

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class DeleteCourierTest {

    Courier courier;
    Courier courierBlank;
    String courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courier = new Courier("PJFry", "123321", "Fry");
        courierBlank = new Courier();
        sendCreateCourier(courier);
        courierId = getCourierId(courier);
    }

    @Test
    @DisplayName("Check error message of DELETE request to /api/v1/courier/ without id")
    public void errorMessageDeleteCourierWithoutId() {
       sendDeleteCourierNoId().then().assertThat().body("message", equalTo
                ("Недостаточно данных для удаления курьера"));
    }

    @Test
    @DisplayName("Check error message of DELETE request to /api/v1/courier/ with nonexistent id")
    public void errorMessageDeleteCourierWithNonexistentId(){
        sendDeleteCourier("0").then().assertThat().body("message", equalTo
                ("Курьера с таким id нет"));
    }

    @Test
    @DisplayName("Check status code of DELETE request to /api/v1/courier/ with valid id")
    public void deleteCourierById(){
        sendDeleteCourier(courierId).then().statusCode(200);
    }

    @Test
    @DisplayName("Check response body of DELETE request to /api/v1/courier/ with valid id")
    public void deleteCourierBody(){
        sendDeleteCourier(courierId).then().body("ok", equalTo(true));
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

    @Step("Send DELETE request to /api/v1/courier/")
    public Response sendDeleteCourierNoId() {
        Response response = given().header("Content-type", "application/json").delete("/api/v1/courier/");
        return response;
    }

    @After
    public void deleteCourier() {
            sendDeleteCourier(getCourierId(courier));
    }
}
