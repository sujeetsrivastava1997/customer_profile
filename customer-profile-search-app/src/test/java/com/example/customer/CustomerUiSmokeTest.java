package com.example.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerUiSmokeTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        // Configure ChromeDriver for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run without GUI
        options.addArguments("--disable-gpu"); // Recommended for headless
        options.addArguments("--window-size=1920,1080"); // Ensure full page renders
        driver = new ChromeDriver(options);

        driver.get("http://localhost:8080"); // URL of your UI page
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

        Thread.sleep(1000); // Wait for JS to finish (smoke test)

        WebElement resultsDiv = driver.findElement(By.id("results"));
        assertTrue(resultsDiv.getText().contains("No customers found."), "Should display no results message");
    }

    @Test
    void shouldDisplayCustomerTableHeaders() throws InterruptedException {
        // Assuming you have a customer "John Doe" in your backend already
        WebElement input = driver.findElement(By.id("keyword"));
        WebElement button = driver.findElement(By.tagName("button"));

        input.clear();
        input.sendKeys("John");
        button.click();

        Thread.sleep(1000); // Wait for JS to populate table

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
