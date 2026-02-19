package com.example.customer;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerPerformanceTest {

    @LocalServerPort
    int port;

    private static final int NUM_THREADS = 20;
    private static final int REQUESTS_PER_THREAD = 10;

    @Test
    public void testCustomerApiPerformance() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < REQUESTS_PER_THREAD; j++) {
                    Response response = RestAssured.get("http://localhost:" + port + "/api/customers");
                    assertEquals(200, response.getStatusCode());
                }
            });
        }

        executor.shutdown();
        if (!executor.awaitTermination(2, TimeUnit.MINUTES)) {
            throw new RuntimeException("Performance test did not finish in time");
        }
    }

    @Test
    public void testCustomerSearchPerformance() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < REQUESTS_PER_THREAD; j++) {
                    Response response = RestAssured.get("http://localhost:" + port + "/api/customers/search?keyword=John");
                    assertEquals(200, response.getStatusCode());
                }
            });
        }

        executor.shutdown();
        if (!executor.awaitTermination(2, TimeUnit.MINUTES)) {
            throw new RuntimeException("Search performance test did not finish in time");
        }
    }
}

