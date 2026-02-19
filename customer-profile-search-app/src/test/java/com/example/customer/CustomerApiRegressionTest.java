package com.example.customer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class CustomerApiRegressionTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void shouldCreateAndRetrieveCustomer() {
        // Create a customer
        int id = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"email\":\"john.doe@example.com\"}")
                .when()
                .post("/api/customers")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .extract()
                .path("id");

        // Retrieve the customer
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/customers/" + id)
                .then()
                .statusCode(200)
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"))
                .body("email", equalTo("john.doe@example.com"));
    }

    @Test
    void shouldUpdateCustomer() {
        int id = 1; // Assume a customer exists with id=1
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\":\"Jane\"}")
                .when()
                .patch("/api/customers/" + id)
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));

        // Optional: verify update
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/customers/" + id)
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Jane"));
    }
}
