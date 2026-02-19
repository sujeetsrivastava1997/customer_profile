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
    int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCreateCustomer() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"email\":\"john.doe@example.com\"}")
                .when()
                .post("/api/customers")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201)))
                .body("id", notNullValue())
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"))
                .body("email", equalTo("john.doe@example.com"));
    }

    @Test
    void shouldGetAllCustomers() {
        // Create a customer first
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\":\"Alice\", \"lastName\":\"Smith\", \"email\":\"alice.smith@example.com\"}")
                .when()
                .post("/api/customers")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201)));

        // Get all customers
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/customers")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].firstName", notNullValue())
                .body("[0].lastName", notNullValue())
                .body("[0].email", notNullValue());
    }

    @Test
    void shouldSearchCustomerByKeyword() {
        // Create a customer first
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\":\"Bob\", \"lastName\":\"Marley\", \"email\":\"bob.marley@example.com\"}")
                .when()
                .post("/api/customers")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201)));

        // Search customer
        RestAssured.given()
                .accept(ContentType.JSON)
                .queryParam("keyword", "Bob")
                .when()
                .get("/api/customers/search")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].firstName", equalTo("Bob"));
    }
}
