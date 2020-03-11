package com.demo.config;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.demo.config.BasicTestConfig.driver;
import static com.demo.properties.FilePaths.report_json_folder;
import static com.demo.properties.FilePaths.screenshots_failed_folder;
import static com.demo.properties.TestData.fileName;
import static com.demo.utilities.FileUtility.readJsonResponseFile;
import static com.demo.utilities.web_services.HttpClientConfig.*;

public class TestNGListener extends ReporterConfig implements ITestListener {


    @Override
    public void onStart(ITestContext arg0) { System.out.println("Execute Test Suite ==>" + arg0.getName()); }


    @Override
    public void onTestStart(ITestResult arg0) {
        System.out.println("Test Started->"+arg0.getName());
    }


    @Override
    public void onTestSuccess(ITestResult arg0) {
        System.out.println("Test Pass->"+arg0.getName());
        Path file         = Paths.get(report_json_folder + fileName);

        if (Files.exists(file)) {
            //  Print into HTML generateReport file
            test.pass("<pre>"
                    + "<br/>"
                    + "<center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center>"
                    + "<br/>"
                    + "<br/>"
                    + "Response Code    : " + responseCode
                    + "<br/>"
                    + "Response Message : " + responseMsg
                    + "<br/>"
                    + "<br/>"
                    + responseHeaders
                    + "<br/>"
                    + "<br/>"
                    + "<br/>"
                    + readJsonResponseFile()
                    + "<br/>"
                    + "<br/>"
                    + "</pre>");
        } else if (Files.exists(file) == false){
            System.out.println("TEST PASSED");
        }
    }


    @Override
    public void onTestFailure(ITestResult result) {
        //    Throwable throwable = arg0.getThrowable();
        //     System.out.println("TEST FAILED->"+arg0.getName());
        //    System.out.println("<br>" + throwable.getMessage());
        //    test.fail(throwable);

        try {
            String method     = result.getMethod().getMethodName();
            String fileName   = method + ".json";
            Path file         = Paths.get(report_json_folder + fileName);
            String methodName = String.format("%s", result.getMethod().getRealClass().getSimpleName());
            Throwable throwable = result.getThrowable();
            System.out.println(throwable.getMessage());

            File jsonReportFile = new File(report_json_folder + fileName);

            if (jsonReportFile.exists()) {

                        // Print into HTML generateReport file
                        test.fail("<pre>"
                                + "<br/>"
                                + "<center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center>"
                                + "<br />"
                                + "<br />"
                                + "Response Code  : " + responseCode
                                + "<br />"
                                + "Error Message  : " + responseMsg
                                + "<br />"
                                + "<br />"
                                + responseHeaders
                                + "<br />"
                                + "<br />"
                                + "<br />"
                                + "<center><b>********  E X C E P T I O N  ********</b></center>"
                                + "<br />"
                                + throwable
                                + "<br />"
                                + "<br />"
                                + readJsonResponseFile()
                                + "<br />"
                                + "</pre>");

                    } else {
                        File fileFail;
                        fileFail = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                        FileUtils.copyFile(fileFail, new File(screenshots_failed_folder + methodName + ".png"));

                        test.fail("<pre><b>FAILED ON SCREEN</b><br>", MediaEntityBuilder.createScreenCaptureFromPath(screenshots_failed_folder + methodName + ".png").build());
                        test.fail(throwable.getStackTrace().toString());
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onTestSkipped(ITestResult arg0) {
        System.out.println("Test Skipped->"+arg0.getName());
    }


    @Override
    public void onFinish(ITestContext arg0) {
        System.out.println("END Of Execution(TEST)->"+arg0.getName());
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub

    }



}