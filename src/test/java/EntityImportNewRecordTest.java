import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

public class EntityImportNewRecordTest extends BaseTest {

    private static final String STRING_FIRST = "ABC";
    private static final String TEXT_FIRST = "One\n" + "Two\n" + "Three";
    private static final String INTEGER_FIRST = "5";
    private static final String DECIMAL_FIRST = "5.50";
    private static final String USER_FIRST = "tester35@tester.test";

    private static final String STRING_SECOND = "String";
    private static final String TEXT_SECOND = "1\n" + "2\n" + "3";
    private static final String INTEGER_SECOND = "9";
    private static final String DECIMAL_SECOND = "9.30";
    private static final String USER_SECOND = "apptester1@tester.test";

    @Test
    public void testCreateNewRecord() {

        TestUtils.scrollClick(getDriver(), findElement(By.xpath("//li[7]/a/p[contains(text(), 'Import')]")));

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class ='card-icon']/i")));
        findElement(By.xpath("//div[@class ='card-icon']/i")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("string")));

        findElement(By.id("string")).sendKeys(STRING_FIRST);
        findElement(By.id("text")).sendKeys(TEXT_FIRST);
        findElement(By.id("int")).sendKeys(INTEGER_FIRST);
        findElement(By.id("decimal")).sendKeys(DECIMAL_FIRST);
        WebElement dateFirst = findElement(By.id("date"));
        dateFirst.click();
        final String DATE_FIRST = dateFirst.getAttribute("value");
        WebElement dateTimeFirst = findElement(By.id("datetime"));
        dateTimeFirst.click();
        final String DATETIME_FIRST = dateTimeFirst.getAttribute("value");
        TestUtils.scrollClick(getDriver(), By.xpath("//div[@class='filter-option-inner-inner']"));
        getWait().until(TestUtils.movingIsFinished(By.xpath("//li/a/span[contains(text(), 'tester35@tester.test')]"))).click();

        TestUtils.scrollClick(getDriver(), By.xpath("//button[@data-table_id='22']"));

        findElement(By.id("t-22-r-1-string")).sendKeys(STRING_SECOND);
        findElement(By.id("t-22-r-1-text")).sendKeys(TEXT_SECOND);
        findElement(By.id("t-22-r-1-int")).sendKeys(INTEGER_SECOND);
        findElement(By.id("t-22-r-1-decimal")).sendKeys(DECIMAL_SECOND);
        WebElement dateSecond = findElement(By.id("t-22-r-1-date"));
        dateSecond.click();
        final String DATE_SECOND = dateSecond.getAttribute("value");
        WebElement dateTimeSecond = findElement(By.id("t-22-r-1-datetime"));
        TestUtils.scrollClick(getDriver(), dateTimeSecond);
        final String DATETIME_SECOND = dateTimeSecond.getAttribute("value");
        TestUtils.scrollClick(getDriver(), By.id("t-22-r-1-user"));
        TestUtils.scrollClick(getDriver(), By
                .xpath(String.format("//select[@id='t-22-r-1-user']/option[contains (text(), '%s')]", USER_SECOND)));
        WebElement saveButton = findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        TestUtils.scroll(getDriver(), saveButton);
        TestUtils.jsClick(getDriver(), saveButton);

        WebElement menu = findElement(By.xpath("//button[contains(i, 'menu')]"));
        getWait().until(ExpectedConditions.elementToBeClickable(menu));
        menu.click();
        WebElement viewButton = findElement(By.xpath("//a[contains (text(), 'view')]"));
        getWait().until(TestUtils.movingIsFinished(viewButton)).click();

        Assert.assertEquals(findElement(By.xpath("//div[1]/div/span"))
                .getText(), STRING_FIRST);
        Assert.assertEquals(findElement(By.xpath("//div[2]/div/span"))
                .getText(), TEXT_FIRST);
        Assert.assertEquals(findElement(By.xpath("//div[3]/div/span"))
                .getText(), INTEGER_FIRST);
        Assert.assertEquals(findElement(By.xpath("//div[4]/div/span"))
                .getText(), DECIMAL_FIRST);
        Assert.assertEquals(findElement(By.xpath("//div[5]/div/span"))
                .getText(), DATE_FIRST);
        Assert.assertEquals(findElement(By.xpath("//div[6]/div/span"))
                .getText(), DATETIME_FIRST);
        Assert.assertEquals(findElement(By.xpath("//div[@class='form-group']/p"))
                .getText(), USER_FIRST);

        Assert.assertEquals(findElement(By.xpath("//tbody/tr[1]/td[2]"))
                .getAttribute("innerHTML").trim(), STRING_SECOND);
        Assert.assertEquals(findElement(By.xpath("//tbody/tr[1]/td[3]"))
                .getAttribute("innerHTML").trim(), TEXT_SECOND);
        Assert.assertEquals(findElement(By.xpath("//tbody/tr[1]/td[4]"))
                .getAttribute("innerHTML").trim(), INTEGER_SECOND);
        Assert.assertEquals(findElement(By.xpath("//tbody/tr[1]/td[5]"))
                .getAttribute("innerHTML").trim(), DECIMAL_SECOND);
        Assert.assertEquals(findElement(By.xpath("//tbody/tr[1]/td[6]"))
                .getAttribute("innerHTML").trim(), DATE_SECOND);
        Assert.assertEquals(findElement(By.xpath("//tbody/tr[1]/td[7]"))
                .getAttribute("innerHTML").trim(), DATETIME_SECOND);
        Assert.assertEquals(findElement(By.xpath("//tbody/tr[1]/td[9]"))
                .getAttribute("innerHTML").trim(), USER_SECOND);
    }
}
