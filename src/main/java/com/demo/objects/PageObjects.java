package com.demo.objects;

import com.demo.config.BasicTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PageObjects extends BasicTestConfig {

    public static final Logger LOG = LogManager.getLogger(PageObjects.class);
    public PageObjects(WebDriver driver) {
        this.driver = driver;
    }
    final WebDriver driver;


    //  Home Page
    @FindBy(how = How.CSS, using = "input[id='imysearchstring']")
    public WebElement home_page_search_field;

    @FindBy(how = How.CSS, using = "div#iautoCompleteDropDownContent")
    public WebElement home_page_search_results_form;

    @FindBy(how = How.CSS, using = "a[data-reference=postcode]")
    public WebElement home_page_search_sugngestion;

    @FindBy(how = How.CSS, using = "[data-href='/en/order-takeaway-8888-alpha?search'] span")
    public WebElement home_page_search_result_1;




    //  Restaurants Search Page
    @FindBy(how = How.CSS, using = "button.topbar__title.js-header-location-update.show-location")
    public WebElement catalog_page_header_update_location_btn;

    @FindBy(how = How.CSS, using = "input#irestaurantsearchstring-middle")
    public WebElement catalog_page_search_field;

    @FindBy(how = How.CSS, using = ".utility-middle-bar .btn.btn-map .btn-icon")
    public WebElement catalog_page_user_localization_icon;

    @FindBy(how = How.XPATH, using = "//div[@class='clustered-pins']//div[contains(text(),'3')]")
    public WebElement catalog_page_user_localization_round_icon;

    @FindBy(how = How.XPATH, using = "//div[@title='Happy Italy Test']/img")
    public WebElement catalog_page_user_localization_gps_icon;

    @FindBy(how = How.XPATH, using = "//div[@class='restaurant-infopanel delivery-card']//div[@class='logowrapper']")
    public WebElement catalog_page_user_localization_near_rest;



    //  Basket
    @FindBy(how = How.ID, using = "popularOQ53N3Q3R")
    public WebElement popular_product;

    @FindBy(how = How.CSS, using = "div#menu-tab-content")
    public WebElement popular_products_table;

    @FindBy(how = How.CSS, using = "span.button_add_value")
    public WebElement add_to_basket_btn;

    @FindBy(how = How.CSS, using = "div#ibasket")
    public WebElement basket_form;

    @FindBy(how = How.CSS, using = ".basket__order-button.cartbutton-button")
    public WebElement basket_order_btn;




    //  Address Page
    @FindBy(how = How.CSS, using = "input#iaddress")
    public WebElement address_page_address;

    @FindBy(how = How.CSS, using = "input#ipostcode")
    public WebElement address_page_postcode;

    @FindBy(how = How.CSS, using = "input#itown")
    public WebElement address_page_city;

    @FindBy(how = How.CSS, using = "input#isurname")
    public WebElement address_page_username;

    @FindBy(how = How.CSS, using = "input#iemail")
    public WebElement address_page_email;

    @FindBy(how = How.CSS, using = "input#iphonenumber")
    public WebElement address_page_phone;

    @FindBy(how = How.CSS, using = "select#ideliverytime")
    public WebElement address_page_delivery_time_dropdown;

    @FindBy(how = How.XPATH, using = "//div[@id='ipaymentmethods']/div[1]")
    public WebElement address_page_payment_cash_option;

    @FindBy(how = How.CSS, using = "input[value='Order and pay']")
    public WebElement address_page_order_btn;

    @FindBy(how = How.CSS, using = ".order-reference")
    public WebElement order_reference;



    //  Information Form
    @FindBy(how = How.CSS, using = "button.info.info-icon")
    public WebElement restaurant_info_icon;

    @FindBy(how = How.CSS, using = ".restaurant-tab-content")
    public WebElement restaurant_info_map;

    @FindBy(how = How.CSS, using = "button.tabs-header__modal-close")
    public WebElement restaurant_info_close_btn;

    @FindBy(how = How.CSS, using = ".restaurant-tab-content .card-body")
    public WebElement restaurant_address;
}






