package ru.praktikum_services.qa_scooter.qa_scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.order.Order;
import ru.praktikum_services.qa_scooter.order_list.ListOfOrders;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListOfOrderFromListTest {

    Order order;
    ListOfOrders orders;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        order = new Order("Ivan", "Ivanov", "ul. Lenina, 39, 2", 4, "+7 800 355 35 35", 5, "2024-10-30", "Please, be quiet",
                new String[] {"BLACK"});
        sendCreateOrder(order);
    }

    @Test
    @DisplayName("Check response of /api/v1/orders contains list of orders")
    public void getOrderList (){
        orders = sendGetOrdersList().body().as(ListOfOrders.class);
        assertThat(orders.getOrders(), is(notNullValue()));
    }

    @Step("Send POST request to /api/v1/orders")
    public void sendCreateOrder(Order order) {
        given().header("Content-type", "application/json").and().body(order).post("/api/v1/orders");
    }

    @Step("Send GET request to /api/v1/orders")
    public Response sendGetOrdersList() {
        return given().header("Content-type", "application/json").get("/api/v1/orders");
    }

}
