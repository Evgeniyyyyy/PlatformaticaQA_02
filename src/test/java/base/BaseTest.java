package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Listeners(OrderUtils.class)
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

    private List<List<Method>> methodList;

    @BeforeClass
    protected void beforeClass() {
        methodList = OrderUtils.orderMethods(
                Arrays.stream(this.getClass().getMethods())
                        .filter(m -> m.getAnnotation(Test.class) != null && m.getAnnotation(Ignore.class) == null)
                        .collect(Collectors.toList()),
                m -> m.getName(),
                m -> m.getAnnotation(Test.class).dependsOnMethods());
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        System.out.printf("Run %s.%s\n", this.getClass().getName(), method.getName());

        if (method.getAnnotation(Test.class).dependsOnMethods().length == 0) {
            System.out.println("Browser open, get web page and login");
            startDriver();
            loginWeb();
        } else {
            System.out.println("Get web page");
            getWeb();
        }
    }

    protected void loginWeb() {
        LoginUtils.get(driver);
        LoginUtils.login(driver);
        LoginUtils.reset(driver);
    }

    protected void getWeb() {
        LoginUtils.get(getDriver());
    }

    protected void startDriver() {
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
    protected void afterMethod(Method method, ITestResult testResult) {
        List<Method> list = OrderUtils.find(methodList, method).orElse(null);
        if (!testResult.isSuccess() || list == null || (list.remove(method) && list.isEmpty())) {
            stopDriver();
            System.out.println("Browser closed");
        }

        System.out.printf("Execution time is %o\n\n", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000);
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
