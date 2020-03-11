package com.demo.scripts.ui;

import com.demo.config.BasicTestConfig;
import com.demo.objects.PageObjects;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.demo.config.ReporterConfig.startTestReport;
import static com.demo.config.ReporterConfig.test;
import static com.demo.utilities.user_interface.Assertions.checkPageTitle;
import static com.demo.utilities.user_interface.Assertions.getCurrentURL;
import static com.demo.utilities.user_interface.ImageCompare.imageCompare;

public class OpenHomePage extends BasicTestConfig {

    private static PageObjects takeawayHomePage = PageFactory.initElements(driver, PageObjects.class);

    //  Private method used for report
    private static void report() throws Exception {
        String testName        = "<b>[WEB] Open Home Page</b>";
        String testCategory    = "Frontend";
        String testDescription = "The purpose of this test is to verify that the user can open home page successfully."              +
                                 "<br><br><b>*** STEPS DESCRIPTION ***</b><br><br>"                                                      +
                                 "[1] Open home page.<br>"                     +
                                 "[2] Wait until search field is displayed.<br>" +
                                 "[3] Check that page title contains <i>Thuisbezorgd</i><br>" +
                                 "[4] Take a screenshot of the whole page.<br>" +
                                 "[5] Compare actual home page screenshot with image from data base.";

        startTestReport(testName, testDescription, testCategory);
    }


    public static void openHomePage() throws Exception {
        report();
        wait = new WebDriverWait(driver, 10);

        String url = "https://www.thuisbezorgd.nl/en/";
        driver.get(url);

        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.home_page_search_field));
        checkPageTitle("Thuisbezorgd");

        takeScreenshot(driver, "Takeaway_HomePage_Actual");
        imageCompare("Takeaway_HomePage_Actual", "Takeaway_HomePage_Expected");

        test.pass("<pre><b>[STEP 1] URL <u>" + getCurrentURL() + "</u> was opened successfully.</b><br>" +
                "[x] Check that current page title contains expected keyword<br>" +
                "[x] Check similarity percentage of the actual screenshot and image from test data");
        }
    }
