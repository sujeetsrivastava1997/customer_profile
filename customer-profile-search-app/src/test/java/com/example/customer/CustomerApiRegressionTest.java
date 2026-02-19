package com.example.customer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerApiRegressionTest {

    @LocalServerPort
    int port; // Injected random port

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port; // dynamically use the injected port
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
        // First, create a customer to update
        int id = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\":\"Alice\", \"lastName\":\"Smith\", \"email\":\"alice.smith@example.com\"}")
                .when()
                .post("/api/customers")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update the customer
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\":\"Jane\"}")
                .when()
                .patch("/api/customers/" + id)
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));

        // Verify update
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/customers/" + id)
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Jane"));
    }
}
