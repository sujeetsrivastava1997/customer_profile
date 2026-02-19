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

public class CustomerSearchUITest {

    private static WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        // ⚠️ Assumes chromedriver is installed and in PATH
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.setBinary("/usr/bin/google-chrome"); // path to Chrome binary on CI/local

        driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void shouldDisplayCustomerSearchResults() throws InterruptedException {
        driver.get("http://localhost:8080");
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
