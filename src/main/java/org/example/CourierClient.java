package org.example;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    public Response create(String login, String password, String firstName) {
        CourierCreate addCourier = new CourierCreate(login, password, firstName);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(addCourier)
                .when()
                .post("/api/v1/courier");
    }

    public Response login(String login, String password) {
        CourierLogin addLogin = new CourierLogin(login, password);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(addLogin)
                .when()
                .post("/api/v1/courier/login");
    }

    public void delete(String id) {
        given()
                .header("Content-type", "application/json")
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}");

    }
}
