package in.bigdash.bdrms;

import java.net.URL;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SimpleSeleniumTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        URL local = new URL("http://localhost:9515");

        System.setProperty("webdriver.chrome.driver","/Users/joppu/lib/chromedriver");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("disable-infobars");
        //options.addArguments("--start-fullscreen");

        driver = new ChromeDriver(options);
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        driver.get("http://localhost:8080/login");
        Thread.sleep(5000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/pickuprequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(2)")).click();
        driver.findElement(By.id("pickupDateTime")).sendKeys("2018-06-30 22:00 pm");
        driver.findElement(By.id("numberFiles")).sendKeys("100");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        // ERROR: Caught exception [unknown command []]
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(5000);
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
