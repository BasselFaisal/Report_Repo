package com.example.stimulsoftdemo.servlet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class SeleniumExample {

    @GetMapping("/hello")

    public static ResponseEntity<?> main(String[] args) {
        String chromePath = "D:\\Bassel_Work\\..Ultimate\\Report And Stimulsoft\\stimulsoft\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromePath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
//                "--headless",
                "--disable-gpu",
                "--window-size=1920,1200",
                "--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/helloo");
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.elementToBeClickable(By.className("stiJsViewerPagee")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        String html = driver.getPageSource();

        System.out.println(html);

        return new ResponseEntity<>(html, null, 200);
    }

    @GetMapping("/helloe")
    public ResponseEntity<?> mains(String[] args) throws InterruptedException {
        String chromePath = "D:\\Bassel_Work\\..Ultimate\\Report And Stimulsoft\\stimulsoft\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromePath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
//                "--headless",
                "--disable-gpu",
                "--window-size=1920,1200",
                "--ignore-certificate-errors");

        WebDriver driver = new ChromeDriver(options);
//        driver.get("http://localhost:8080/helloo");

        AtomicReference<CompletableFuture<Void>> newConnectionThread = new AtomicReference<>(null);
        AtomicReference<HashMap<String, Object>> htmlGenerating = new AtomicReference<>(null);

        newConnectionThread.set(getHtmlAsync(newConnectionThread, htmlGenerating, driver));
        newConnectionThread.get().join();

//        driver.wait(10000);
        String html = driver.getPageSource();

        System.out.println(html);

//        driver.quit();

        return ResponseEntity.ok(html);
    }


    private CompletableFuture<Void> getHtmlAsync(AtomicReference<CompletableFuture<Void>> newConnectionThread,
                                                          AtomicReference<HashMap<String, Object>> htmlGenerating,  WebDriver driver) {
        return CompletableFuture.supplyAsync(() -> {
            driver.get("http://localhost:8080/helloo");
            newConnectionThread.get().complete(null);
            return null;
        });
    }

}
