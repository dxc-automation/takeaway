package com.demo.config;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.demo.config.ConsoleRunner.xmlFile;
import static com.demo.config.ReporterConfig.extent;
import static com.demo.properties.FilePaths.*;
import static com.demo.properties.TestData.*;
import static org.apache.commons.io.FileUtils.cleanDirectory;


/**
 *                          This class contains all methods for taking screenshots,
 *                          browser initialization and generateReport generation.
 *          List:
 *   [1]    takeScreenshot  Capture screenshot and save the file with PNG extension.
 *                          Example:            takeScreenshot(driver, "FileName");
 *   [2]    browserConfig           Launch web browser. Value must be setted in testng.xml
 *   [3]    generateReport          Describes the result of a scripts and print result values.
 *   [4]    finishReport    Writes scripts information from the started reporters to
 *                          their output view.
 *   [5]    tearDown        Stop web driver and close the browser.
 */
public class BasicTestConfig {

    public static WebDriver     driver;
    public static WebDriverWait wait;
    public static File          screenshotFile;


    public static final Logger LOG = LogManager.getLogger(BasicTestConfig.class);

    /**
     * Used for screenshot generating
     * @param driver, name
     * @throws Exception
     */
        public static void takeScreenshot (WebDriver driver, String name){
            screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(screenshotFile, new File(screenshots_actual_folder + name + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    /**
     * Delete all reporting files from previous tests
     */
        @BeforeSuite
        public void cleanReportData() {
            File reportJsonDir    = new File(report_json_folder);
            File reportFailedDir  = new File(screenshots_failed_folder);
            File reportActual     = new File(screenshots_actual_folder);
            File reportBuffer     = new File(screenshots_buffer_folder);
            File reportVideos     = new File(video_files);

            try {
                if (!reportJsonDir.exists() && reportFailedDir.exists()) {
                    reportJsonDir.mkdir();
                    reportFailedDir.mkdir();
                } else {
                    cleanDirectory(new File(report_json_folder));
                    cleanDirectory(new File(screenshots_failed_folder));
                } if (!reportActual.exists() && reportBuffer.exists()) {
                    reportBuffer.mkdir();
                    reportActual.mkdir();
                } else {
                    cleanDirectory(new File(screenshots_actual_folder));
                    cleanDirectory(new File(screenshots_buffer_folder));
                } if (!reportVideos.exists()) {
                    reportVideos.mkdir();
                } else {
                    cleanDirectory(new File(video_files));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    @BeforeTest
    public void readPropertiesFile() throws IOException {
        InputStream inputStream = new FileInputStream(config_properties_file);
        Properties properties = new Properties();
        properties.load(inputStream);

        // Get value of the property
        USER = properties.getProperty("account");
        USER_ADDRESS = properties.getProperty("address");
        USER_CITY = properties.getProperty("city");
        USER_EMAIL = properties.getProperty("email");
        USER_PHONE_NUMBER = properties.getProperty("phone");
        USER_POST_CODE = properties.getProperty("postcode");
        inputStream.close();
    }


    @Parameters({"environment"})
    @BeforeTest
    public void setEnvironment(String environment) {
        if (environment.equalsIgnoreCase("alpha")) {
            search_environment = "/en/order-takeaway-8888-alpha?search";
        } else if (environment.equalsIgnoreCase("beta")) {
            search_environment = "/en/order-takeaway-8888-beta?search";
        }
    }



    /**
     * Used for browser configuration
     * @param browser
     * @throws Exception
     */
        @Parameters({"browser"})
        @BeforeSuite
        public void browserConfig(String browser) throws Exception {
            DesiredCapabilities capability = new DesiredCapabilities();

            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", chrome_driver_file);

                ChromeOptions options = new ChromeOptions();
              //  options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                options.addArguments("scripts-type");
                options.addArguments("start-maximized");
                options.addArguments("--disable-search-geolocation-disclosure");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-notifications");
                options.addArguments("--incognito");
                driver = new ChromeDriver(options);
                LOG.info("| Chrome browser launched successfully |");

            } else if (browser.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", firefox_driver_file);
                FirefoxProfile profile = new FirefoxProfile();
                profile.setAcceptUntrustedCertificates(true);
                profile.setAssumeUntrustedCertificateIssuer(true);

                FirefoxOptions options = new FirefoxOptions();
                options.setLogLevel(FirefoxDriverLogLevel.TRACE);
                driver = new FirefoxDriver();
                LOG.info("| Firefox browser launched successfully |");

            } else if (browser.equalsIgnoreCase("safari")) {
                capability.setCapability("browserstack.safari.driver", "3.141.59");
                capability.setCapability("browserstack.safari.enablePopups", false);
                capability.setCapability("browserstack.debug", true);
                capability.setCapability("browserstack.console", "debug");
                capability.setCapability("browserstack.networkLogs", true);

                SafariOptions sOptions = new SafariOptions();
                sOptions.setUseTechnologyPreview(true);
                SafariOptions.fromCapabilities(capability);
                capability.setCapability(SafariOptions.CAPABILITY, sOptions);
                driver = new SafariDriver();
                LOG.info("| Safari browser launched successfully |");

            } else if (browser.equalsIgnoreCase("none")) {
                System.setProperty("webdriver.chrome.driver", chrome_driver_file);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("scripts-type");
                driver = new ChromeDriver(options);

                Point position = new Point(-2000, 0);
                driver.manage().window().setPosition(position);
                LOG.info("| Chrome browser minimized successfully |");
            }
        }



        @AfterSuite(alwaysRun = true)
        public void flushReportData() throws Exception{
            try {
                if (xmlFile.exists()) {
                    xmlFile.delete();
                } else {
                    System.out.println("\n testng.xml file does not exist");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            extent.flush();
            driver.close();
            driver.quit();
        }
}

