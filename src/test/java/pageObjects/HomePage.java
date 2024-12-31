
package pageObjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage {
	private WebDriver driver;
	private WebDriverWait wait;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	/**
	 * Function to read URL from config and Navigate to it
	 */
	public void openURL() {
		driver.get("https://elpais.com/");
	}

	/**
	 * Function to handle cookies Popup
	 */
	public void handleCookiesPopup() {
		try {
			WebElement elem = driver.findElement(By.xpath("//div[@id='didomi-notice']"));
			System.out.println();
			WebElement btn_Accept = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(elem.findElement(By.xpath("//button[@id='didomi-notice-agree-button']"))));
			btn_Accept.click();
			System.out.println("Clicked on Accept Cookies Button");
		} catch (Exception e) {
			System.out.println("Cookies Popup not displayed.");
		}
	}

	/**
	 * Function to verify that page is in Spanish Language
	 */
	public void verifySelectedLanguage() {
		WebElement lang = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='edition_head']//span")));
		Assert.assertTrue(lang.getText().equalsIgnoreCase("España"), "Page text is not in spanish");
	}

	/**
	 * Navigate to Opinion Section
	 */
	public void navigateToOpinionSection() {
		WebElement btn_Opinion = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Opinión")));
		btn_Opinion.click();
		System.out.println("Clicked on Opinion Button");
	}
}

