import com.applitools.eyes.Eyes;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppliToolTest {
    WebDriver driver;
    Eyes eyes;
    String testName;
    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            testName = description.getDisplayName();
        }
    };

    @Before
    public void setUp() {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + "/driver/" + "chromedriver");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        driver = eyes.open(new ChromeDriver(), "Facebook", testName);
    }

    @After
    public void tearDown() {
        driver.quit();
        eyes.close();
    }

    @Test
    public void fBLoginBeforeTest() {
        driver.get("https://facebook.com");
        eyes.checkWindow("Facebook Before Page");
        driver.findElement(By.id("email")).sendKeys("testemail@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("SuperSecretPassword!");
    }

    @Test
    public void fBLoginAfterTest() {
        driver.get("https://facebook.com");
        driver.findElement(By.id("email")).sendKeys("testemail@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("SuperSecretPassword!");
        eyes.checkWindow("Facebook After Page");
    }
}