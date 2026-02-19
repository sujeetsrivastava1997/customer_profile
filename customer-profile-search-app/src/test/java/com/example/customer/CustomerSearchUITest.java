package com.example.customer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerSearchUITest {

    @LocalServerPort
    int port; // Injects the random port

    private static WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void shouldDisplayCustomerSearchResults() throws InterruptedException {
        driver.get("http://localhost:" + port); // Use injected port
        WebElement searchBox = driver.findElement(By.id("keyword"));
        WebElement searchButton = driver.findElement(By.tagName("button"));
        searchBox.sendKeys("John");
        searchButton.click();

        Thread.sleep(2000);

        WebElement resultsDiv = driver.findElement(By.id("results"));
        String resultsText = resultsDiv.getText();
        assertTrue(resultsText.contains("John"));
    }
}

