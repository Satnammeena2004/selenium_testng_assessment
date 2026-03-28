package qa.listeners;

import qa.utils.ExtentReportManager;
import qa.utils.ScreenShotUtils;
import org.testng.*;


public class TestListeners implements ITestListener, ISuiteListener {



    @Override
    public void onStart(ISuite suite) {
        System.out.println("====== SUITE STARTED: " + suite.getName() + " ======");
        ExtentReportManager.initReport();
    }

    @Override
    public void onFinish(ISuite suite) {
        ExtentReportManager.flushReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n▶ STARTED: " + result.getName());

        // Create a new node in Extent Report for this test
        ExtentReportManager.startTest(
                result.getName(),
                result.getMethod().getDescription()
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ PASSED: " + result.getName());

        ExtentReportManager.getTest().pass("Test passed successfully");

        String path = ScreenShotUtils.capture(result.getName(), "PASS");
        ExtentReportManager.getTest()
                .addScreenCaptureFromPath(path, "Pass Screenshot");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ FAILED: " + result.getName());
        System.out.println("   Reason: " + result.getThrowable().getMessage());

        ExtentReportManager.getTest().fail(result.getThrowable());

        String path = ScreenShotUtils.capture(result.getName(), "FAIL");
        ExtentReportManager.getTest()
                .addScreenCaptureFromPath(path, "Failure Screenshot");

        ExtentReportManager.getTest()
                .addScreenCaptureFromBase64String(
                        ScreenShotUtils.captureBase64(),
                        "Failure (Embedded)"
                );
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip("Test was skipped");
    }
}