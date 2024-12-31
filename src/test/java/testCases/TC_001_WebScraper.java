package testCases;

import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.OpinionPage;
import utility.BaseTest;

@Test
public class TC_001_WebScraper extends BaseTest{
	public void scrapingArticle() {
		HomePage homepage = new HomePage(driver);
		OpinionPage opinionPage = new OpinionPage(driver);
		
		homepage.openURL();
		homepage.handleCookiesPopup();
		homepage.verifySelectedLanguage();
		homepage.navigateToOpinionSection();
		
		opinionPage.scrapeArticles(5);
	}
}
