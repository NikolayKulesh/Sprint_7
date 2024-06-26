package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    @Step("Create courier, POST request to /api/v1/courier")
    public Response create(String login, String password, String firstName) {
        CourierCreate addCourier = new CourierCreate(login, password, firstName);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(addCourier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Get courier id, POST request to /api/v1/courier/login")
    public Response login(String login, String password) {
        CourierLogin addLogin = new CourierLogin(login, password);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(addLogin)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Delete courier, DELETE request to /api/v1/courier/{id}")
    public void delete(String id) {
        given()
                .header("Content-type", "application/json")
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}");

    }
}
