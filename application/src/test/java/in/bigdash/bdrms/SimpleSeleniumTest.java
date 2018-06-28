package in.bigdash.bdrms;

import java.net.URL;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
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
    public void newPickupRequestWithFileStorageType() throws Exception {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/pickuprequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(2)")).click();
        driver.findElement(By.id("pickupDateTime")).sendKeys("2018-06-30 22:00 pm");
        driver.findElement(By.id("numberFiles")).sendKeys("100");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
    }

    @Test
    public void newPickupRequestWithBoxStorageType() throws Exception {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/pickuprequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(1)")).click();
        driver.findElement(By.id("pickupDateTime")).sendKeys("2018-06-30 22:00 pm");
        driver.findElement(By.id("numberFiles")).sendKeys("100");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
    }

    @Test
    public void newPickupRequestWithDocumentStorageType() throws Exception {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/pickuprequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        Thread.sleep(250);
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(3)")).click();
        driver.findElement(By.id("pickupDateTime")).sendKeys("2018-06-30 22:00 pm");
        driver.findElement(By.id("numberFiles")).sendKeys("100");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        String id = getId();

        driver.get("http://localhost:8080/login");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/pickuprequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(2)")).click();
        driver.findElement(By.id("pickupDateTime")).sendKeys("2018-06-30 22:00 pm");
        driver.findElement(By.id("numberFiles")).sendKeys("100");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        driver.findElement(By.id("Request_workflow")).click();
        driver.findElement(By.id("select2-userAssigned-container")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(1)")).click();
        driver.findElement(By.id("save-button")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();
        driver.findElement(By.id("Request_workflow")).click();
        driver.findElement(By.id("startProcessRequestButton")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("addBoxButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("b1"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.cssSelector("a[data-barcode='B1"+id+"']")).click();
        driver.findElement(By.id("addFileButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("f1"+id);
        driver.findElement(By.id("ref1")).clear();
        driver.findElement(By.id("ref1")).sendKeys("f1"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.id("addFileButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("f2"+id);
        driver.findElement(By.id("ref1")).clear();
        driver.findElement(By.id("ref1")).sendKeys("f2"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.id("addFileButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("f3"+id);
        driver.findElement(By.id("ref1")).clear();
        driver.findElement(By.id("ref1")).sendKeys("f3"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.id("showBoxesButton")).click();
        driver.findElement(By.id("addBoxButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("b2"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.cssSelector("a[data-barcode='B2"+id+"']")).click();
        driver.findElement(By.id("addFileButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("f4"+id);
        driver.findElement(By.id("ref1")).clear();
        driver.findElement(By.id("ref1")).sendKeys("f4"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.id("addFileButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("f5"+id);
        driver.findElement(By.id("ref1")).clear();
        driver.findElement(By.id("ref1")).sendKeys("f5"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.id("addFileButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("f6"+id);
        driver.findElement(By.id("ref1")).clear();
        driver.findElement(By.id("ref1")).sendKeys("f6"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.id("showBoxesButton")).click();
        driver.findElement(By.id("addBoxButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("b3"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.cssSelector("a[data-barcode='B3"+id+"']")).click();
        driver.findElement(By.id("addFileButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("f7"+id);
        driver.findElement(By.id("ref1")).clear();
        driver.findElement(By.id("ref1")).sendKeys("f7"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.id("addFileButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("f8"+id);
        driver.findElement(By.id("ref1")).clear();
        driver.findElement(By.id("ref1")).sendKeys("f8"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.id("addFileButton")).click();
        driver.findElement(By.id("barcode")).clear();
        driver.findElement(By.id("barcode")).sendKeys("f9"+id);
        driver.findElement(By.id("ref1")).clear();
        driver.findElement(By.id("ref1")).sendKeys("f9"+id);
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.id("showBoxesButton")).click();
        driver.findElement(By.id("saveRecordsButton")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();
        driver.findElement(By.id("startTransitButton")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();
        driver.findElement(By.id("Request_workflow")).click();
        driver.findElement(By.id("startProcessRequestButton")).click();
        // ERROR: Caught exception [ERROR: Unsupported command [setText | id=incomingBoxBarcode | B1]]
        // ERROR: Caught exception [ERROR: Unsupported command [setText | id=incomingBoxBarcode | B2]]
        // ERROR: Caught exception [ERROR: Unsupported command [setText | id=incomingBoxBarcode | B3]]
        Thread.sleep(1000);
        driver.findElement(By.id("incomingBoxBarcode")).sendKeys("B1"+id+Keys.ENTER);
        driver.findElement(By.id("incomingBoxBarcode")).sendKeys("B2"+id+Keys.ENTER);
        driver.findElement(By.id("incomingBoxBarcode")).sendKeys("B3"+id+Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();
        driver.findElement(By.id("Request_workflow")).click();
    }

    private String getId(){
        //return DigestUtils.sha1Hex(UUID.randomUUID().toString());
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,7).toUpperCase();
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
