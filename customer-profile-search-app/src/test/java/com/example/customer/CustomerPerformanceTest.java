package com.example.customer;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerPerformanceTest {

    private static final int NUM_THREADS = 20; // number of concurrent users
    private static final int REQUESTS_PER_THREAD = 10;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testCustomerApiPerformance() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < REQUESTS_PER_THREAD; j++) {
                    Response response = RestAssured.get("/api/customers");
                    assertEquals(200, response.getStatusCode(), "Customer API failed");
                }
            });
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES);
        if (!finished) {
            throw new RuntimeException("Performance test did not finish in time");
        }
    }

    @Test
    public void testCustomerSearchPerformance() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < REQUESTS_PER_THREAD; j++) {
                    Response response = RestAssured.get("/api/customers/search?keyword=John");
                    assertEquals(200, response.getStatusCode(), "Search API failed");
                }
            });
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES);
        if (!finished) {
            throw new RuntimeException("Search performance test did not finish in time");
        }
    }
}
