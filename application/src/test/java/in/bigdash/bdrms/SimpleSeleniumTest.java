package in.bigdash.bdrms;

import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Ignore
public class SimpleSeleniumTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver","/Users/joppu/lib/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");



        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void newPickupRequestWithFileStorageType() throws Exception {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("user1");
        driver.findElement(By.name("password")).sendKeys("user1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/pickuprequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(2)")).click();
        driver.findElement(By.id("pickupDateTime")).sendKeys("2018-06-30 22:00 pm");
        driver.findElement(By.id("numberFiles")).sendKeys("100");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
    }

    @Test
    public void newPickupRequestWithBoxStorageType() throws Exception {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("user1");
        driver.findElement(By.name("password")).sendKeys("user1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/pickuprequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(1)")).click();
        driver.findElement(By.id("pickupDateTime")).sendKeys("2018-06-30 22:00 pm");
        driver.findElement(By.id("numberFiles")).sendKeys("100");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
    }

    @Test
    public void newPickupRequestWithDocumentStorageType() throws Exception {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("user1");
        driver.findElement(By.name("password")).sendKeys("user1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/pickuprequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(3)")).click();
        driver.findElement(By.id("pickupDateTime")).sendKeys("2018-06-30 22:00 pm");
        driver.findElement(By.id("numberFiles")).sendKeys("100");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
    }

    @Test
    public void newPickupRequest() throws Exception {
        String id = getId();
        doNewPickupRequest(id);
    }

    @Test
    public void newRetrievalRequest() throws Exception {
        String id = getId();
        doNewPickupRequest(id);
        doNewRetrievalRequest(id);
    }


    public void doNewPickupRequest(String id) throws Exception {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("user1");
        driver.findElement(By.name("password")).sendKeys("user1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/pickuprequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(2)")).click();
        driver.findElement(By.id("pickupDateTime")).sendKeys("2018-06-30 22:00 pm");
        driver.findElement(By.id("numberFiles")).sendKeys("100");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();

        String requestId = getRequestIdFromUrl(driver.findElement(By.id("PickupRequest_edit")).getAttribute("href"));
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("operator");
        driver.findElement(By.name("password")).sendKeys("operator");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/requests/" + requestId + "/workflow");


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

        Thread.sleep(1000);
        driver.findElement(By.id("incomingBoxBarcode")).sendKeys("B1"+id+Keys.ENTER);
        driver.findElement(By.id("incomingBoxBarcode")).sendKeys("B2"+id+Keys.ENTER);
        driver.findElement(By.id("incomingBoxBarcode")).sendKeys("B3"+id+Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();
        driver.findElement(By.id("Request_workflow")).click();

        driver.findElement(By.id("validatedBoxBarcode")).sendKeys("B1"+id+Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("shelf1"+Keys.ENTER);

        driver.findElement(By.id("validatedBoxBarcode")).sendKeys("B2"+id+Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("shelf1"+Keys.ENTER);

        driver.findElement(By.id("validatedBoxBarcode")).sendKeys("B3"+id+Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("shelf1"+Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();

        driver.findElement(By.id("Request_workflow")).click();
        driver.findElement(By.id("startProcessRequestButton")).click();
    }

    public void doNewRetrievalRequest(String id) throws Exception {
        String[] filePres = {"F1","F2","F3"};

        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("user1");
        driver.findElement(By.name("password")).sendKeys("user1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/retrievalrequests/create-form");
        driver.findElement(By.id("select2-storagetypesselect2-container")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(2)")).click();
        driver.findElement(By.id("addRecordsButton")).click();

        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@type='search']")).clear();
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(id);
        Thread.sleep(1000);

        for(String filePre : filePres){
            driver.findElement(By.cssSelector("tr[data-barcode='" + filePre + id + "']")).click();
            Thread.sleep(250);
        }

        driver.findElement(By.id("okModalButton")).click();
        Thread.sleep(500);
        driver.findElement(By.id("saveRequestButton")).click();

        String requestId = getRequestIdFromUrl(driver.findElement(By.id("RetrievalRequest_edit")).getAttribute("href"));
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("operator");
        driver.findElement(By.name("password")).sendKeys("operator");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("http://localhost:8080/requests/" + requestId + "/workflow");


        driver.findElement(By.id("select2-userAssigned-container")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("li.select2-results__option:nth-of-type(1)")).click();
        driver.findElement(By.id("save-button")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();
        driver.findElement(By.id("Request_workflow")).click();
        driver.findElement(By.id("startProcessRequestButton")).click();


        for(String filePre : filePres){
            driver.findElement(By.id("recordBarcode")).sendKeys(filePre + id + Keys.ENTER);
            Thread.sleep(500);
        }

        driver.findElement(By.cssSelector("button.confirm")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button.confirm")).click();

    }

    private String getId(){
        //return DigestUtils.sha1Hex(UUID.randomUUID().toString());
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,7).toUpperCase();
    }

    private String getRequestIdFromUrl(String url){
        int j = url.lastIndexOf("/");
        int i = url.lastIndexOf("/", j-1) + 1;
        return url.substring(i,j);
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
