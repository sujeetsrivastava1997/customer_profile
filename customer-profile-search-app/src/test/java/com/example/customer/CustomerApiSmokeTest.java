package com.example.customer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerApiSmokeTest {

    @LocalServerPort
    int port; // Injects the random port

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port; // dynamically use the injected port
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
