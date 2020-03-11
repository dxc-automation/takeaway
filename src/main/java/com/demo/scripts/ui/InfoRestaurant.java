package com.demo.scripts.ui;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.demo.config.BasicTestConfig;
import com.demo.objects.PageObjects;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.demo.config.ReporterConfig.startTestReport;
import static com.demo.config.ReporterConfig.test;
import static com.demo.properties.FilePaths.screenshots_actual_folder;
import static com.demo.utilities.user_interface.ElementScreenshot.elementScreenshot;


public class InfoRestaurant extends BasicTestConfig {

    private static PageObjects takeawayHomePage = PageFactory.initElements(driver, PageObjects.class);



    //  Private method used for report
    private static void report() throws Exception {
        String testName        = "<b>[WEB] Restaurant Info</b>";
        String testCategory    = "Frontend";
        String testDescription = "The purpose of this test is to verify that the restaurant information form can be opened and displayed properly."              +
                "<br><br><b>*** STEPS & PRECONDITIONS ***</b><br><br>" +
                "[PRECONDITIONS]<br>" +
                "<i>Restaurant menu must be opened.</i><br><br>" +
                "[STEPS]<br>"                                                       +
                "[1] Wait until information icon is displayed.<br>"                      +
                "[2] Click on the information icon.<br>" +
                "[3] Wait until location map is displayed.<br>" +
                "[4] Take a screenshot of the location map.<br>" +
                "[5] Get restaurant address from information form.";

        startTestReport(testName, testDescription, testCategory);
    }



    //  Test method that is called with test value
    public static void getInfo() throws Exception {
        report();
        wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.restaurant_info_icon));
        takeawayHomePage.restaurant_info_icon.click();
        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.restaurant_info_map));

        Thread.sleep(500);
        elementScreenshot(takeawayHomePage.restaurant_info_map, "Restaurant_Info_Map");
        test.pass("<pre><b>INFO MAP</b><br>", MediaEntityBuilder.createScreenCaptureFromPath(screenshots_actual_folder + "Restaurant_Info_Map.png").build());

        String info = takeawayHomePage.restaurant_address.getText();
        test.info("<pre><b>Restaurant Address</b><br>" + info);

        takeawayHomePage.restaurant_info_close_btn.click();
    }
}
