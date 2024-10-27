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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateCourierTest {

    Courier courier;
    Courier courierBlank;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courier = new Courier("PJFry", "123321", "Fry");
        courierBlank = new Courier();
    }

    @Test
    @DisplayName("Check that courier can be created with valid login and password")
    public void canCreateCourier() {
        sendCreateCourier(courier);
        assertThat(getCourierId(courier), is(not(0)));
        System.out.println("id:" + getCourierId(courier));
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier with valid request")
    public void createCourierStatusCode() {
        sendCreateCourier(courier).then().statusCode(201);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier with existing login")
    public void cantCreateExistingCourier(){
        sendCreateCourier(courier);
        sendCreateCourier(courier).then().statusCode(409);
    }

    @Test
    @DisplayName("Check error message of /api/v1/courier with existing login")
    public void createExistingCourierErrorMessage(){
        sendCreateCourier(courier);
        sendCreateCourier(courier).then().assertThat().body("message", equalTo
                ("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Check response body of /api/v1/courier with valid request")
    public void checkCreateNewValidCourierResponse() {
        sendCreateCourier(courier).then().assertThat().body("ok", equalTo(true));
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

    @Step("Send DELETE request to /api/v1/courier")
    public void sendDeleteCourier(String id) {
        given().header("Content-type", "application/json").delete(String.format("/api/v1/courier/%s", id));
        //System.out.println(response.statusCode());
    }

    @After
    public void deleteCourier() {
            sendDeleteCourier(getCourierId(courier));
    }
}
