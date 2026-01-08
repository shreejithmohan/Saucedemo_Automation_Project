package utilities;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class ExtentManager 
{

	private static Logger logger = LogManager.getLogger(ExtentManager.class);
	private static ExtentReports extentReports;
	private static ExtentSparkReporter sparkReporter;
	
	/**
	 * Initialize Extent Report
	 * @return ExtentReports instance
	 */
	public static ExtentReports initializeExtentReport() {
		try {
			String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
			String reportPath = System.getProperty("user.dir") + File.separator + "test-output" 
					+ File.separator + "ExtentReport_" + timestamp + ".html";
			
			// Create directories if not exist
			new File(reportPath).getParentFile().mkdirs();
			
			sparkReporter = new ExtentSparkReporter(reportPath);
			sparkReporter.config().setTheme(Theme.DARK);
			sparkReporter.config().setDocumentTitle("SauceDemo Login Test Report");
			sparkReporter.config().setReportName("Automation Test Report");
			sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
			
			extentReports = new ExtentReports();
			extentReports.attachReporter(sparkReporter);
			
			// Add system information
			extentReports.setSystemInfo("Application Name", "SauceDemo");
			extentReports.setSystemInfo("Test Environment", "Production");
			extentReports.setSystemInfo("Browser", "Chrome");
			extentReports.setSystemInfo("OS", System.getProperty("os.name"));
			extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
			extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
			
			logger.info("Extent Report initialized: " + reportPath);
			return extentReports;
		} catch (Exception e) {
			logger.error("Error initializing Extent Report: " + e.getMessage());
			throw new RuntimeException("Failed to initialize Extent Report: " + e.getMessage());
		}
	}
	
	/**
	 * Get Extent Report instance
	 * @return ExtentReports instance
	 */
	public static ExtentReports getExtentReports() {
		if (extentReports == null) {
			return initializeExtentReport();
		}
		return extentReports;
	}
	
	/**
	 * Create test in Extent Report
	 * @param testName - Test name
	 * @param testDescription - Test description
	 * @return ExtentTest instance
	 */
	public static ExtentTest createTest(String testName, String testDescription) {
		logger.info("Creating test: " + testName);
		return getExtentReports().createTest(testName, testDescription);
	}
	
	/**
	 * Flush (finalize) Extent Report
	 */
	public static void flushExtentReport() {
		if (extentReports != null) {
			extentReports.flush();
			logger.info("Extent Report finalized and flushed");
		}
	}
	
	/**
	 * Add screenshot to test report
	 * @param test - ExtentTest instance
	 * @param screenshotPath - Path to screenshot
	 * @param message - Message to display
	 */
	public static void addScreenshot(ExtentTest test, String screenshotPath, String message) {
		try {
			test.addScreenCaptureFromPath(screenshotPath, message);
			logger.info("Screenshot added to report: " + screenshotPath);
		} catch (Exception e) {
			logger.error("Error adding screenshot to report: " + e.getMessage());
		}
	}
	
	/**
	 * Add screenshot as Base64 to test report
	 * @param test - ExtentTest instance
	 * @param base64Screenshot - Base64 string of screenshot
	 * @param message - Message to display
	 */
	public static void addBase64Screenshot(ExtentTest test, String base64Screenshot, String message) {
		try {
			test.addScreenCaptureFromBase64String(base64Screenshot, message);
			logger.info("Base64 screenshot added to report");
		} catch (Exception e) {
			logger.error("Error adding Base64 screenshot to report: " + e.getMessage());
		}
	}
}
