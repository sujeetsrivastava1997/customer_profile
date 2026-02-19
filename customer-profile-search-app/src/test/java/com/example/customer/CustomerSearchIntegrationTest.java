package com.example.customer;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerSearchIntegrationTest {

    @LocalServerPort
    int port;

    @Test
    void shouldSearchCustomer() {
        RestAssured.port = port;
        given().contentType("application/json").body("{\"name\":\"John\",\"email\":\"john@test.com\"}")
        .when().post("/api/customers")
        .then().statusCode(200);
        given()
        .when().get("/api/customers/search?keyword=john")
        .then().statusCode(200).body("size()", greaterThan(0));
    }
}
