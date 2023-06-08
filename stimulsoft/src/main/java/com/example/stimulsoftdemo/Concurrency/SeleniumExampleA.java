package com.example.stimulsoftdemo.Concurrency;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class SeleniumExampleA {

    // Configuration of chrome driver
    private static WebDriver configChromeDriver() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // Ensure this option is set to run Chrome in headless mode

        // Set the path of the chromedriver executable. You would need to download this separately.
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        return new ChromeDriver(options);
    }

    // Getting HTML content from the provided URL
    private static String getHtml(WebDriver driver, String url) {
        driver.get(url);
        return driver.getPageSource();
    }

    private static void getHtmlAsync(AtomicReference<CompletableFuture<String>> futureRef, AtomicReference<WebDriver> driverRef, String url) {
        WebDriver driver = configChromeDriver();
        driverRef.set(driver);

        futureRef.set(CompletableFuture.supplyAsync(() -> getHtml(driver, url)));
    }

    @GetMapping("/helloa")
    public ResponseEntity<?>  main(String[] args) throws Exception {
        AtomicReference<CompletableFuture<String>> futureHtmlRef = new AtomicReference<>();
        AtomicReference<WebDriver> driverRef = new AtomicReference<>();

        getHtmlAsync(futureHtmlRef, driverRef, "http://localhost:8080/helloo");

        // do some other work...

        // When you need the HTML, you can call `join` to get the result
        // `join` will block until the CompletableFuture is completed
         futureHtmlRef.get().join();


        // Make sure to quit the driver when you're done
        String html = driverRef.get().getPageSource();
        System.out.println(html);
        return ResponseEntity.ok(html);

    }
}

