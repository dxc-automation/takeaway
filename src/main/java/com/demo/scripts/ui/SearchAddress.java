package com.demo.scripts.ui;

import com.demo.config.BasicTestConfig;
import com.demo.objects.PageObjects;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static com.demo.config.ReporterConfig.startTestReport;
import static com.demo.config.ReporterConfig.test;
import static com.demo.utilities.user_interface.ElementScreenshot.elementScreenshot;
import static com.demo.utilities.user_interface.ImageCompare.imageCompare;



public class SearchAddress extends BasicTestConfig {

    private static PageObjects takeawayHomePage = PageFactory.initElements(driver, PageObjects.class);

    //  Private method used for report
    private static void report() throws Exception {
        String testName        = "<b>[WEB] Search For Address</b>";
        String testCategory    = "Frontend";
        String testDescription = "The purpose of this test is to verify that the address search functionality is working as expected. "              +
                "<br><br><b>*** STEPS DESCRIPTION ***</b><br><br>" +
                "[1] Open home page.<br>" +
                "[2] Wait until search field is displayed.<br>" +
                "[3] Write <i>location post code</i> in search field.<br>" +
                "[4] Check that search suggestions contains specific post code.<br>" +
                "[5] Take a screenshot of the suggestions web element form.<br>" +
                "[6] Compare actual screenshot with image from data base.";

        startTestReport(testName, testDescription, testCategory);
    }



    public static void searchLocation(String location) throws Exception {
        report();
        wait = new WebDriverWait(driver, 10);

        String url = "https://www.thuisbezorgd.nl/en/";
        driver.get(url);

        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.home_page_search_field));
        takeawayHomePage.home_page_search_field.sendKeys(location);
        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.home_page_search_sugngestion));

        String suggestionText   = takeawayHomePage.home_page_search_sugngestion.getText();
        boolean checkSuggestion = suggestionText.equals(location);
        Assert.assertTrue(checkSuggestion);
        test.pass("<b>[STEP 1] Correct search suggestion appear.</b>");

        takeawayHomePage.home_page_search_sugngestion.click();
        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.home_page_search_results_form));
        test.pass("<b>[STEP 2] Search results web form appear.</b>");

        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.home_page_search_result_1));
        elementScreenshot(takeawayHomePage.home_page_search_results_form, "Search_Results_Actual");
        imageCompare("Search_Results_Actual", "Search_Results_Expected");
        takeawayHomePage.home_page_search_result_1.click();
    }
}
