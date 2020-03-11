package com.demo.utilities.user_interface;

import com.demo.config.BasicTestConfig;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import static com.demo.config.ReporterConfig.test;


public class Assertions extends BasicTestConfig {

    private static boolean checkTitle;
    private static boolean checkUrl;


    //  Verify that element exists
    public static boolean elementExistsAssertion(WebElement element) {
        if (element.getSize() != null) {
            return true;
        } else {
            return false;
        }
    }


    //  Verify that tha page is loaded
    public static boolean isLoaded() throws Exception {
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        });
        return false;
    }


    //  Return page title as string
    public static String getPageTitle() {
        String title = driver.getTitle();
        return title;
    }


    //  Return URL address as string
    public static String getCurrentURL() {
        String url = driver.getCurrentUrl();
        return url;
    }


    //  Verify that current page title contains expected keyword
    public static boolean checkCurrentUrl(String expectedURL) {
        try {
            String url = driver.getCurrentUrl();
            checkUrl = url.contains(expectedURL);
        } catch (Exception e) {
            test.fail(e.getMessage());
            e.printStackTrace();
        }
        return checkUrl;
    }


    //  Verify that current page title contains expected keyword
    public static boolean checkPageTitle(String expectedPageTitle) {
        try {
            String title = driver.getTitle();
            checkTitle = title.contains(expectedPageTitle);
        } catch (Exception e) {
            test.fail(e.getMessage());
            e.printStackTrace();
        }
        return checkTitle;
    }
}
