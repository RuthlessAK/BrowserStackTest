package utility;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

	public WebDriver driver;
	
	@BeforeMethod
	public void setUpDriver() {
		 System.setProperty("webdriver.chrome.driver","./Drivers/chromedriver.exe");
		 ChromeOptions options = new ChromeOptions();
		 options.addArguments("--disable-extensions");
         options.addArguments("--disable-gpu");
         options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}
	
	@AfterMethod()
    public void tearDown(){
        driver.quit();
    }
	
	
}
