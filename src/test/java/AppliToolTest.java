import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;
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

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppliToolTest {
    WebDriver driver;
    String TEST_NAME;
    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            TEST_NAME = description.getDisplayName();
        }
    };
    String APPLITOOLS_API_KEY = System.getenv("APPLITOOLS_API_KEY");
    String APPLICATION_NAME = "Facebook";
    RectangleSize EYE_SIZE = new RectangleSize(1200, 669);
    String BASE_URL = "https://facebook.com";
    Eyes eyes;

    @Before
    public void setUp() {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + "/driver/chromedriver");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(BASE_URL);

        eyes = new Eyes();
        eyes.setApiKey(APPLITOOLS_API_KEY);
        eyes.open(driver, APPLICATION_NAME, TEST_NAME, EYE_SIZE);
    }

    @After
    public void tearDown() {
        driver.quit();
        eyes.close();
        eyes.abortIfNotClosed();
    }

    @Test
    public void fBLoginBeforeTest() {
        eyes.checkWindow("Facebook Before Page");
        driver.findElement(By.id("email")).sendKeys("testemail@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("SuperSecretPassword!");
    }

    @Test
    public void fBLoginAfterTest() {
        driver.findElement(By.id("email")).sendKeys("testemail@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("SuperSecretPassword!");
        eyes.checkWindow("Facebook After Page");
    }
}