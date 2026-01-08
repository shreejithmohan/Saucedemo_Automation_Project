package helper;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import utilities.WebDriver_Utils;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest testReporter;
    protected Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void beforeSuite() {
        String out = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
        ExtentSparkReporter spark = new ExtentSparkReporter(out);
        extent = new ExtentReports();
        extent.attachReporter(spark);
        log.info("ExtentReports initialized at {}", out);
    }

    @AfterSuite
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
            log.info("ExtentReports flushed");
        }
    }

    @BeforeMethod
    public void setup(Method m) {
        driver = WebDriver_Utils.createDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
        log.info("Browser started and navigated to saucedemo");
        testReporter = extent.createTest(m.getName());
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed");
        }
    }
}

