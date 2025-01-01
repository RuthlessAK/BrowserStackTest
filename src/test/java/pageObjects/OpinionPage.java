package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import utility.DownloadUtils;
import utility.TranslationUtils;

public class OpinionPage {
	
	private WebDriver driver;
	private WebDriverWait wait;

	public OpinionPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}
	/**
	 * Function to get all Articles
	 * @return list of articles
	 */
	public List<WebElement> fetchArticles() {
		return driver.findElements(By.cssSelector("article"));
	}

	/**
	 * Function to get Title of Article
	 * @param article
	 * @return
	 */
	public String extractTitle(WebElement article) {
		return article.findElement(By.xpath("header/h2/a")).getText();
	}
	
	/**
	 * Function to get Content of Article
	 * @param article
	 * @return
	 */
	public String extractContent(WebElement article) {
		try {
			return article.findElement(By.xpath("p")).getText();
		}catch (StaleElementReferenceException e) {
			return article.findElement(By.xpath("p")).getText();
		}
		
	}
	
	public WebElement getArticleImage(WebElement article) {
		try {
			return article.findElement(By.tagName("img"));
		}catch (Exception e) {
			return null;
		}
        
    }
	
	
	/**
	 * Function to scrape given number of articles' title , content and image if present and then translate text
	 * @param numberOfArticles
	 */
	public void scrapeArticles(int numberOfArticles) {
		List<String> translatedHeaders = new ArrayList<>();
		List<WebElement> articles = fetchArticles();		
		if(articles.size()<numberOfArticles) {
			numberOfArticles= articles.size();
			//if number of actual articles are less than required articles, get all present articles 
		}

		for (int i = 0; i < numberOfArticles; i++) {
			String title;
			String content;
			try {
				title = extractTitle(articles.get(i));
			}catch (StaleElementReferenceException e) {
				articles = fetchArticles();
				title = extractTitle(articles.get(i));
			}
			System.out.println("Spanish Title for article " + ( i + 1) +" : "+ title);
			String translatedTitle = TranslationUtils.translateText(title);
			System.out.println("Translated English Title for article " + (i+1) +" : "+ translatedTitle);
			try {
				content = extractContent(articles.get(i));
			}catch (StaleElementReferenceException e) {
				articles = fetchArticles();
				content = extractContent(articles.get(i));
			}
			
//			String content = extractContent(articles.get(i));
			System.out.println("Spanish Content for article " + (i + 1) +" : "+ content);
			translatedHeaders.add(translatedTitle);
			WebElement img = getArticleImage(articles.get(i));
			if (img != null) {
				String imageUrl = img.getAttribute("src");
				DownloadUtils.downloadImage(imageUrl,(i+1));
			}
			
		}
		
		checkRepeatedWords(translatedHeaders);
		
	}
	
	public void checkRepeatedWords(List<String> headers) {
//		  String result = headers.stream().collect(Collectors.joining(" "));
		
		String text = String.join(" ", headers).replace("(", "").replace(")", "");
		System.out.println("Combined Headers : "+text);
		String[] words = text.toLowerCase().split("\\s+");
		
		Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) { // Filter words that are repeated more than twice
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        }
    }
}
