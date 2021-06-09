package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Listeners(TestOrder.class)
public abstract class BaseTest {

    public static final String HUB_URL = "http://localhost:4444/wd/hub";

    private static boolean remoteWebDriver = false;

    static {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(HUB_URL + "/status").openConnection();
            try {
                con.setRequestMethod("GET");
                remoteWebDriver = con.getResponseCode() == HttpURLConnection.HTTP_OK;
            } finally {
                con.disconnect();
            }
        } catch (IOException ignore) {}

        if (!remoteWebDriver) {
            WebDriverManager.chromedriver().setup();
        }
    }

    public static boolean isRemoteWebDriver() {
        return remoteWebDriver;
    }

    private WebDriver driver;
    private WebDriverWait wait;

    private final List<String> methodList = new ArrayList<>();

    @BeforeClass
    protected void beforeClass() {
        for (Method method : this.getClass().getMethods()) {
            Test test = method.getAnnotation(Test.class);
            if (test != null) {
                methodList.addAll(List.of(test.dependsOnMethods()));
            }
        }
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        if (method.getAnnotation(Test.class).dependsOnMethods().length == 0) {
            initDriver();

            ProjectLogin.start(getDriver());
        } else {
            ProjectLogin.get(getDriver());
        }
    }

    protected void initDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1920,1080");

        if (isRemoteWebDriver()) {

            chromeOptions.setHeadless(true);
            chromeOptions.addArguments("--disable-gpu");

            try {
                driver = new RemoteWebDriver(new URL(HUB_URL), chromeOptions);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            driver = new ChromeDriver(chromeOptions);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    protected void afterMethod(Method method) {
        if (!methodList.contains(method.getName())) {
            stopDriver();
        }
    }

    @AfterClass
    protected void afterClass() {
        if (!driver.toString().contains("null")) {
            stopDriver();
        }
    }

    protected void stopDriver() {
        driver.quit();
        wait = null;
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver, 10);
        }
        return wait;
    }

    protected WebElement findElement(By by) {
        return getDriver().findElement(by);
    }

    protected List<WebElement> findElements(By by) {
        return getDriver().findElements(by);
    }
}
