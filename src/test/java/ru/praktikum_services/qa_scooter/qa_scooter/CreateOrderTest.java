package ru.praktikum_services.qa_scooter.qa_scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.order.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    Order orderBlank = new Order();

    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public CreateOrderTest(String firstName, String lastName, String address, int metroStation, String phone,
                           int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                { "Ivan", "Ivanov", "ul. Lenina, 39, 2", 4, "+7 800 355 35 35", 5, "2024-10-30",
                        "Please, be quiet", new String[] {"BLACK"}},
                { "Petr", "Petrov", "ul. Lenina, 40, 3", 2, "+7 900 466 21 22", 3, "2024-10-28",
                        "No", new String[] {"GREY"}},
                { "John", "Doe", "ul. Karla-Marksa, 41, 5", 6, "89214668990", 2 , "2024-10-26",
                        "Ask me anything", new String[] {"BLACK", "GREY"}},
                { "Karl", "Gustav", "ul. Nelsona, d. 32, f. 5", 1, "+3 655 332 08 43", 1, "2024-11-02",
                        "Pray for the king", new String[] {}}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders")
    public void createOrder() {
        fillInOrderInfo();
        sendCreateOrder(orderBlank).then().statusCode(201);
    }

    @Test
    @DisplayName("Check that response of /api/v1/orders return track number")
    public void checkOrderTrackNumber() {
        fillInOrderInfo();
        sendCreateOrder(orderBlank).then().assertThat().body("track", notNullValue());
    }

    @Step("Fill in orders info")
    public void fillInOrderInfo() {
        orderBlank.setFirstName(firstName);
        orderBlank.setLastName(lastName);
        orderBlank.setAddress(address);
        orderBlank.setMetroStation(metroStation);
        orderBlank.setPhone(phone);
        orderBlank.setRentTime(rentTime);
        orderBlank.setDeliveryDate(deliveryDate);
        orderBlank.setComment(comment);
        orderBlank.setColor(color);
    }

    @Step("Send POST request to /api/v1/orders")
    public Response sendCreateOrder(Order order) {
        Response response = given().header("Content-type", "application/json")
                .and().body(order).post("/api/v1/orders");
        return response;
    }

}
