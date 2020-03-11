package com.demo.scripts.ui;

import com.demo.config.BasicTestConfig;
import com.demo.objects.PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.demo.config.ReporterConfig.startTestReport;
import static com.demo.config.ReporterConfig.test;
import static com.demo.properties.TestData.search_environment;
import static com.demo.utilities.user_interface.Assertions.checkCurrentUrl;


public class SearchRestaurant extends BasicTestConfig {

    private static PageObjects takeawayHomePage = PageFactory.initElements(driver, PageObjects.class);



    //  Private method used for report
    private static void report() throws Exception {
        String testName        = "<b>[WEB] Search For Restaurant</b>";
        String testCategory    = "Frontend";
        String testDescription = "The purpose of this test is to verify that the restaurant search functionality is working as expected."              +
                "<br><br><b>*** STEPS DESCRIPTION ***</b><br><br>" +
                "[1] Open home page.<br>" +
                "[2] Type <i>8888</i> in search field.<br>" +
                "[3] Verify search functionality is redirecting to the correct URL address.<br>" +
                "[4] Wait until restaurants search page is loaded.<br>" +
                "[5] Search for restaurant with specific name.<br>" +
                "[6] Open restaurant details page.";

        startTestReport(testName, testDescription, testCategory);
    }



    //  Test method that is called with test value
    public static void searchRestaurant(String restaurantName) throws Exception {
        report();
        wait = new WebDriverWait(driver, 10);

        //  Load web page
        String url = "https://www.thuisbezorgd.nl" + search_environment;
        driver.get(url);
        checkCurrentUrl(url);
        test.pass("<pre><b>[STEP 1] Restaurants search page is opened.</b>");


        //  Search for restaurant
        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.catalog_page_search_field));
        takeawayHomePage.catalog_page_search_field.sendKeys(restaurantName);

        WebElement restaurant = driver.findElement(By.cssSelector("[data-id='OQNQ11N1']"));
        wait.until(ExpectedConditions.visibilityOf(restaurant));
        restaurant.click();
        test.pass("<pre><b>[STEP 2] Restaurant menu is opened.</b>");
    }
}
