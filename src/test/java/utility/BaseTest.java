package utility;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

	public WebDriver driver;
	
	@BeforeMethod
	public void setUpDriver() throws MalformedURLException {
		 System.setProperty("webdriver.chrome.driver","./Drivers/chromedriver.exe");
//		 ChromeOptions options = new ChromeOptions();
//		 options.addArguments("--disable-extensions");
//         options.addArguments("--disable-gpu");
//         options.addArguments("--remote-allow-origins=*");
//		driver = new ChromeDriver(options);
		String username = "ankitkothiyal_mg9F5n";
        String accessKey = "rFTMyoUdxxwyaz1CvJaU";
		
		DesiredCapabilities caps = new DesiredCapabilities();


        Map<String, Object> browserStackOptions = new HashMap<>();
//        browserStackOptions.put("os", "Windows");
//        browserStackOptions.put("osVersion", "10");
//        browserStackOptions.put("browserName", "Firefox");
//        browserStackOptions.put("browserVersion", "133.0");
        
//        browserStackOptions.put("os", "OS X");
//        browserStackOptions.put("osVersion", "Monterey");
//        browserStackOptions.put("browserName", "Safari");
//        browserStackOptions.put("browserVersion", "15.6");
        
//        browserStackOptions.put("deviceName", "Samsung Galaxy S23 Ultra");
//      browserStackOptions.put("osVersion", "13.0");
//      browserStackOptions.put("browserName", "chrome");
//      browserStackOptions.put("deviceOrientation", "portrait");
      
        
        browserStackOptions.put("buildName", "BrowserStackTest");
        browserStackOptions.put("sessionName", "WebScraper");
        caps.setCapability("bstack:options", browserStackOptions);
        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"), caps);

		driver.manage().window().maximize();
	}
	
	@AfterMethod()
    public void tearDown(){
        driver.quit();
    }
	
	
}
