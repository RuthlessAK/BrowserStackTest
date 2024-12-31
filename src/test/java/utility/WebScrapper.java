package utility;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.net.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import com.google.cloud.translate.*;

public class WebScrapper {
    public static void main(String[] args) throws Exception {
        // Setup Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10) );

        // Base URL
        String baseUrl = "https://elpais.com";
        driver.get(baseUrl);

        // Ensure site is in Spanish
//        if (!driver.getTitle().contains("Español")) {
//            System.out.println("Website is not in Spanish. Exiting.");
//            driver.quit();
//            return;
//        }

        // Navigate to Opinion section
        driver.get(baseUrl + "/opinion/");

        // Fetch first five articles
        List<WebElement> articles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("article a"))).subList(0, 5);
        List<Map<String, String>> articleData = new ArrayList<>();
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        for (WebElement article : articles) {
            try {
                // Click and open each article
                String link = article.getAttribute("href");
                driver.get(link);

                // Extract title and content
                String title = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))).getText();
                List<WebElement> paragraphs = driver.findElements(By.tagName("p"));
                StringBuilder content = new StringBuilder();
                for (WebElement p : paragraphs) {
                    content.append(p.getText()).append("\n");
                }

                // Download cover image if available
                try {
                    WebElement image = driver.findElement(By.tagName("img"));
                    String imgUrl = image.getAttribute("src");
                    byte[] imgData = new URL(imgUrl).openStream().readAllBytes();
                    String imgName = title.replace(" ", "_").replace("/", "_") + ".jpg";
                    Files.write(Paths.get(imgName), imgData);
                } catch (Exception e) {
                    System.out.println("No image found for " + title);
                }

                // Translate title to English
                Translation translation = translate.translate(title, Translate.TranslateOption.sourceLanguage("es"), Translate.TranslateOption.targetLanguage("en"));
                String translatedTitle = translation.getTranslatedText();

                // Store data
                Map<String, String> articleInfo = new HashMap<>();
                articleInfo.put("original_title", title);
                articleInfo.put("translated_title", translatedTitle);
                articleInfo.put("content", content.toString());
                articleData.add(articleInfo);

                // Print details
                System.out.println("Original Title: " + title);
                System.out.println("Translated Title: " + translatedTitle);
                System.out.println("Content: " + content.substring(0, Math.min(content.length(), 200)) + "...\n");
            } catch (Exception e) {
                System.out.println("Error processing article: " + e.getMessage());
            }
        }

        // Analyze Translated Headers
        Map<String, Integer> wordCounts = new HashMap<>();
        for (Map<String, String> article : articleData) {
            String[] words = article.get("translated_title").split(" ");
            for (String word : words) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("Repeated Words in Headers:");
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }

        // Close the driver
        driver.quit();

        // Cross-Browser Testing with BrowserStack can be integrated similarly.
    }
}
