package testcases;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import helper.BaseTest;
import pom_Pages.LoginPage_POM;
import utilities.Excel_File_Reader;
import utilities.ScreenShot_Utils;

public class Login_Testcase extends BaseTest {
    private Logger log = LogManager.getLogger(Login_Testcase.class);

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        String path = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
        Excel_File_Reader reader = new Excel_File_Reader(path);
        return reader.getTestData();
    }

    @Test(dataProvider = "loginData")
    public void validLogin(String username, String password) {
        LoginPage_POM login = new LoginPage_POM(driver);
        log.info("Starting login test for user {}", username);
        testReporter.info("Attempting login for " + username);
        login.login(username, password);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // ignore
        }
        String url = driver.getCurrentUrl();
        boolean passed = url.contains("inventory.html");
        if (!passed) {
            String shot = ScreenShot_Utils.takeScreenshot(driver, "login_failure_" + username);
            testReporter.fail("Login failed - screenshot: " + shot);
        }
        Assert.assertTrue(passed, "Expected to be on inventory page after login");
        testReporter.pass("Login successful for " + username);
    }
}

