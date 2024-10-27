package ru.praktikum_services.qa_scooter.qa_scooter;

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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LogInTest {

    Courier courier = new Courier("PJFry", "123321", "Fry");
    Courier courierBlank = new Courier();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        sendCreateCourier(courier);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login with valid request")
    public void logInCourier() {
        courierBlank.setLogin(courier.getLogin());
        courierBlank.setPassword(courier.getPassword());
        sendLoginCourier(courier).then().statusCode(200);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login without login field")
    public void logInWithoutLoginField() {
        courierBlank.setPassword(courier.getPassword());
        sendLoginCourier(courierBlank).then().statusCode(400);
    }

    @Test
    @DisplayName("Check error message of /api/v1/courier/login without login field")
    public void errorMessageLogInWithoutLoginField() {
        courierBlank.setPassword(courier.getPassword());
        sendLoginCourier(courierBlank).then().assertThat().body("message", equalTo
                ("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login without password field")
    public void logInWithoutPasswordField() {
        courierBlank.setLogin(courier.getLogin());
        sendLoginCourier(courierBlank).then().statusCode(400);
    }

    @Test
    @DisplayName("Check error message of /api/v1/courier/login without password field")
    public void errorMessageLogInWithoutPasswordField() {
        courierBlank.setLogin(courier.getLogin());
        sendLoginCourier(courierBlank).then().assertThat().body("message", equalTo
                ("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check status code and error message of /api/v1/courier/login without fields")
    public void logInWithEmptyBody() {
        sendLoginCourier(courierBlank).then().statusCode(400).and().assertThat().body("message", equalTo
                ("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check error message of /api/v1/courier/login with wrong password")
    public void errorMessageWhenLogInWithWrongPassword() {
        courierBlank.setLogin(courier.getLogin());
        courierBlank.setPassword("132312");
        sendLoginCourier(courierBlank).then().assertThat().body("message", equalTo
                ("Учетная запись не найдена"));
    }


    @Test
    @DisplayName("Check error message of /api/v1/courier/login with wrong login")
    public void errorMessageWhenLogInWithNonexistingLogin() {
        courierBlank.setLogin("Fry");
        courierBlank.setPassword(courier.getPassword());
        sendLoginCourier(courierBlank).then().assertThat().body("message", equalTo
                ("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check id in response of /api/v1/courier/login with valid request")
    public void getIdWhenRequestSuccessful() {
        courierBlank.setLogin(courier.getLogin());
        courierBlank.setPassword(courier.getPassword());
        sendLoginCourier(courierBlank).then().assertThat().body("id", notNullValue());
    }

    @Step("Send POST request to /api/v1/courier")
    public Response sendCreateCourier(Courier courier) {
        Response response = given().header("Content-type", "application/json").and().body(courier).post("/api/v1/courier");
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response sendLoginCourier(Courier courier) {
        Response response = given().header("Content-type", "application/json").and().body(courier).post("/api/v1/courier/login");
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login")
    public String getCourierId(Courier courier) {
        courierBlank.setLogin(courier.getLogin());
        courierBlank.setPassword(courier.getPassword());
        CourierId courierId = given().header("Content-type", "application/json").and().body(courierBlank).post("/api/v1/courier/login").body().as(CourierId.class);
        return courierId.getId();
    }

    @Step("Send DELETE request to /api/v1/courier")
    public void sendDeleteCourier(String id) {
        Response response = given().header("Content-type", "application/json").delete(String.format("/api/v1/courier/%s", id));
        //System.out.println(response.statusCode());
    }

    @After
    public void deleteCourier() {
        sendDeleteCourier(getCourierId(courier));
    }

}
