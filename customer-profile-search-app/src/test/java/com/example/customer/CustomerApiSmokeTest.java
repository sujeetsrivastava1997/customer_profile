package com.example.customer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CustomerApiSmokeTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void healthEndpointShouldReturnUp() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/actuator/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }

    @Test
    void getCustomersEndpointShouldReturn200() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/customers")
                .then()
                .statusCode(200);
    }
}
