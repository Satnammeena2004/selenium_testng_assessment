package qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/*
 * ExtentReportManager controls the HTML report.
 *
 * ExtentReports  = the report itself (one instance for whole suite)
 * ExtentTest     = one row/node in the report (one per test method)
 * ThreadLocal    = each parallel test has its own ExtentTest node
 */
public class ExtentReportManager {

    // One report for entire test run
    private static ExtentReports extent;

    // Each thread (test) gets its own node in the report
    private static final ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();
//    private static ExtentTest testNode ;

    // Call once when suite starts
    public static void initReport() {

        // SparkReporter = the HTML file generator
        ExtentSparkReporter spark =
                new ExtentSparkReporter("reports/TestReport.html");

        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("QA Assessment Report");
        spark.config().setReportName("Selenium Test Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        // System info shown at top of report
        extent.setSystemInfo("Tester", "STNAM");
        extent.setSystemInfo("Project", "SeleniumAssessment");
        extent.setSystemInfo("Site", "tutorialsninja.com/demo");
        extent.setSystemInfo("Browser", ConfigReader.get("browser"));
    }

    // Call at start of each test
    public static void startTest(String testName, String description) {
        ExtentTest node = extent.createTest(testName, description);
        testNode.set(node);
    }

    // Get current test's node (to log steps)
    public static ExtentTest getTest() {
        return testNode.get();
    }

    // Call once when suite ends — writes HTML file to disk
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}