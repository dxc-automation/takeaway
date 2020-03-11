package com.demo.scripts.ui;

import com.demo.config.BasicTestConfig;
import com.demo.objects.PageObjects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static com.demo.config.ReporterConfig.startTestReport;
import static com.demo.config.ReporterConfig.test;
import static com.demo.properties.TestData.*;
import static com.demo.utilities.user_interface.Assertions.checkCurrentUrl;



public class PlaceOrder extends BasicTestConfig {

    private static PageObjects takeawayHomePage = PageFactory.initElements(driver, PageObjects.class);

    //  Private method used for report
    private static void report() throws Exception {
        String testName = "<b>[WEB] Place Order</b>";
        String testCategory = "Frontend";
        String testDescription = "The purpose of this test is to verify that the user can select item from the menu and place a order." +
                "[PRECONDITIONS]<br>" +
                "<i>Restaurant menu must be opened.</i><br><br>" +
                "[STEPS]<br>" +
                "[1] Wait until restaurant menu is opened.<br>" +
                "[2] Open product details form.<br>" +
                "[3] Add product to the basket.<br>" +
                "[4] C";

        startTestReport(testName, testDescription, testCategory);
    }


    public static void order() throws Exception {
        report();
        wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.popular_products_table));
        test.pass("<b>[STEP 1] Restaurant popular products page is opened.</b>");

        takeawayHomePage.popular_product.click();
        test.pass("<b>[STEP 2] Product order details form is opened.</b>");

        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.add_to_basket_btn));
        takeawayHomePage.add_to_basket_btn.click();
        test.pass("<b>[STEP 3] Product is added to cart.</b>");

        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.basket_order_btn));
        takeawayHomePage.basket_order_btn.click();
        test.pass("<b>[STEP 4] Order button is clicked.</b>");


        wait.until(ExpectedConditions.visibilityOf(takeawayHomePage.address_page_address));
        takeawayHomePage.address_page_address.sendKeys(USER_ADDRESS);
        takeawayHomePage.address_page_postcode.sendKeys("AA");
        takeawayHomePage.address_page_city.sendKeys(USER_CITY);
        takeawayHomePage.address_page_username.sendKeys(USER);
        takeawayHomePage.address_page_email.sendKeys(USER_EMAIL);
        takeawayHomePage.address_page_phone.sendKeys(USER_PHONE_NUMBER);
        test.pass("<b>[STEP 5] Address details are filled.</b>");

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("scrollBy(0, 350)");
        takeawayHomePage.address_page_payment_cash_option.click();
        test.pass("<b>[STEP 6] Payment method is selected.</b>");

        String deliveryTime = takeawayHomePage.address_page_delivery_time_dropdown.getText();


        takeawayHomePage.address_page_order_btn.click();
        Assert.assertNotNull(checkCurrentUrl("ordered"));
        test.pass("<b>[STEP 7] Order is placed successfully.</b><br><br>" +
                "<i>" + takeawayHomePage.order_reference.getText() + "</i>");
        }
    }


