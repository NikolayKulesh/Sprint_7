package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {
    @Step("Create order, POST request to /api/v1/orders")
    public Response createOrder(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] colour) {
        OrderCreate addOrder = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colour);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(addOrder)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Get orders, GET request to /api/v1/orders")
    public Response getOrders() {
        return given()
                .get("/api/v1/orders");
    }
}
