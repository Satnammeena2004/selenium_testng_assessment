package qa.utils;

import qa.base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenShotUtils {

    /*
     * Takes a screenshot and saves to screenshots/ folder.
     * Returns the file path so the report can embed it.
     *
     * File name example:
     *   testValidLogin_FAIL_14-30-01.png
     */
    public static String capture(String testName, String status) {

        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH-mm-ss"));

        String fileName = testName + "_" + status + "_" + time + ".png";
        String filePath = "screenshots/" + fileName;

        try {
            // Cast driver to TakesScreenshot interface
            TakesScreenshot camera =
                    (TakesScreenshot) BaseTest.getDriver();

            // Selenium captures browser viewport → temp file
            File tempFile = camera.getScreenshotAs(OutputType.FILE);

            // Make sure folder exists
            new File("screenshots").mkdirs();

            // Copy temp file to our folder with our name
            FileUtils.copyFile(tempFile, new File(filePath));

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }

        return filePath;
    }

    // Returns screenshot as Base64 — for embedding in HTML report
    public static String captureBase64() {
        try {
            return ((TakesScreenshot) BaseTest.getDriver())
                    .getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            return "";
        }
    }
}