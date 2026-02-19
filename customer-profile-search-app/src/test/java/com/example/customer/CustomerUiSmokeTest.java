package com.example.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerUiSmokeTest {

    @LocalServerPort
    int port; // Inject random port

    private WebDriver driver;
    private String baseUrl;

    @BeforeEach
    void setup() {
        // Set base URL dynamically based on random port
        baseUrl = "http://localhost:" + port;

        // Configure ChromeDriver for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        driver.get(baseUrl); // Use dynamic URL
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void pageShouldLoadAndDisplayElements() {
        WebElement input = driver.findElement(By.id("keyword"));
        WebElement button = driver.findElement(By.tagName("button"));

        assertTrue(input.isDisplayed(), "Keyword input should be visible");
        assertTrue(button.isDisplayed(), "Search button should be visible");
    }

    @Test
    void shouldShowNoCustomersMessageWhenNothingFound() throws InterruptedException {
        WebElement input = driver.findElement(By.id("keyword"));
        WebElement button = driver.findElement(By.tagName("button"));

        input.clear();
        input.sendKeys("NonExistentCustomer");
        button.click();

        Thread.sleep(1000); // wait for JS to update UI

        WebElement resultsDiv = driver.findElement(By.id("results"));
        assertTrue(resultsDiv.getText().contains("No customers found."), "Should display no results message");
    }

    @Test
    void shouldDisplayCustomerTableHeaders() throws InterruptedException {
        WebElement input = driver.findElement(By.id("keyword"));
        WebElement button = driver.findElement(By.tagName("button"));

        input.clear();
        input.sendKeys("John");
        button.click();

        Thread.sleep(1000); // wait for table to populate

        WebElement resultsDiv = driver.findElement(By.id("results"));
        WebElement table = resultsDiv.findElement(By.tagName("table"));
        List<WebElement> headers = table.findElements(By.tagName("th"));

        assertEquals(7, headers.size(), "Table should have 7 headers");

        String[] expectedHeaders = {"First Name", "Last Name", "Email", "Address", "Phone", "Preferred Language", "Employee"};
        for (int i = 0; i < headers.size(); i++) {
            assertEquals(expectedHeaders[i], headers.get(i).getText());
        }
    }
}
