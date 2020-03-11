package com.demo.tests;

import com.demo.config.BasicTestConfig;
import com.demo.utilities.user_interface.VideoRecord;
import org.testng.annotations.Test;

import static com.demo.scripts.ui.InfoRestaurant.getInfo;
import static com.demo.scripts.ui.OpenHomePage.openHomePage;
import static com.demo.scripts.ui.PlaceOrder.order;
import static com.demo.scripts.ui.SearchAddress.searchLocation;
import static com.demo.scripts.ui.SearchRestaurant.searchRestaurant;
import static com.demo.utilities.user_interface.AlertHandling.acceptAlert;

public class TestCase_01 extends BasicTestConfig {
    VideoRecord videoReord = new VideoRecord();


    @Test(description = "WEB", enabled = true)
    public void openHome() throws Exception {
        //  start screen recorder
        videoReord.startRecording();
        openHomePage();
        acceptAlert();
        videoReord.stopRecording();
    }


    @Test(description = "WEB", enabled = true)
    public void search() throws Exception {
        videoReord.startRecording();
        searchLocation("8888");
        videoReord.stopRecording();
    }


    @Test(description = "WEB", enabled = true)
    public void selectRestaurant() throws Exception {
        videoReord.startRecording();
        searchRestaurant("Happy Italy Test");
        getInfo();
        videoReord.stopRecording();
    }


    @Test(description = "WEB", enabled = true)
    public void placeOrder() throws Exception {
        videoReord.startRecording();
        order();
        videoReord.stopRecording();
    }
}