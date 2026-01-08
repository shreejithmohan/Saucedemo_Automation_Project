package utilities;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot_Utils {
    public static String takeScreenshot(WebDriver driver, String name) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String dir = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "screenshots";
            Path outDir = Paths.get(dir);
            if (!Files.exists(outDir)) {
                Files.createDirectories(outDir);
            }
            String path = dir + File.separator + name + "_" + timestamp + ".png";
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(path);
            Files.copy(src.toPath(), dest.toPath());
            return dest.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
