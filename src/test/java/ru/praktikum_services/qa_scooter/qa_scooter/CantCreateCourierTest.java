package ru.praktikum_services.qa_scooter.qa_scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.courier.Courier;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CantCreateCourierTest {

    Courier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courier = new Courier("PJFry", "123321", "Fry");
    }

    @Test
    @DisplayName("Check status code and error message of /api/v1/courier without filled login")
    public void cantCrateCourierWithoutFilledLogin(){
        courier.setLogin("");
        sendCreateCourier(courier).then().statusCode(400).and().assertThat().body("message", equalTo
                ("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check status code and error message of /api/v1/courier without filled password")
    public void cantCrateCourierWithoutFilledPassword(){
        courier.setPassword("");
        sendCreateCourier(courier).then().statusCode(400).and().assertThat().body("message", equalTo
                ("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check status code and error message of /api/v1/courier without password field")
    public void cantCreateCourierWithoutRequiredFieldPassword(){
        courier.setPassword(null);
        sendCreateCourier(courier).then().statusCode(400).and().assertThat().body("message", equalTo
                ("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check status code and error message of /api/v1/courier without login field")
    public void cantCreateCourierWithoutRequiredFieldLogin(){
        courier.setLogin(null);
        sendCreateCourier(courier).then().statusCode(400).and().assertThat().body("message", equalTo
                ("Недостаточно данных для создания учетной записи"));
    }

    @Step("Send POST request to /api/v1/courier")
    public Response sendCreateCourier(Courier courier) {
        return given().header("Content-type", "application/json").and().body(courier).post("/api/v1/courier");
    }
}
