package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static utils.ProjectUtils.getEntity;
import static utils.TestUtils.*;

public class EntityPlaceholderViewAndCancelTest extends BaseTest {

    private static final String STRING_VALUE = "String";
    private static final String TEXT_VALUE = "Text";
    private static final String INT_VALUE = "456";
    private static final String DECIMAL_VALUE = "34.35";
    private static final String DATE_VALUE = "30/05/2021";
    private static final String DATETIME_VALUE = "02/06/2021 10:10:10";
    private static final List<String> expectedCreatedResult = List.of(
            STRING_VALUE, TEXT_VALUE, INT_VALUE, DECIMAL_VALUE, DATE_VALUE,
            DATETIME_VALUE, "", "", "tester68@tester.test");

    public void fillForm() {
        getEntity(getDriver(),"Placeholder");
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(text(),'create_new_folder')]"))).click();

        findElement(By.id("string")).sendKeys(STRING_VALUE);
        findElement(By.id("text")).sendKeys(TEXT_VALUE);
        findElement(By.id("int")).sendKeys(INT_VALUE);
        findElement(By.id("decimal")).sendKeys(DECIMAL_VALUE);
        findElement(By.id("date")).click();
        findElement(By.id("date")).clear();
        findElement(By.id("date")).sendKeys(DATE_VALUE);
        findElement(By.id("datetime")).click();
        findElement(By.id("datetime")).clear();
        findElement(By.id("datetime")).sendKeys(DATETIME_VALUE);
        findElement(By.xpath("//button[@data-id='user']")).click();
        jsClick(getDriver(), findElement(
                By.xpath("//span[text()='tester68@tester.test']")));

        getWait().until(ExpectedConditions.attributeToBe(findElement(By.id("datetime")), "value", DATETIME_VALUE));
    }

    @Test
    public void testCreatedAndCancel() {
        fillForm();
        scrollClick(getDriver(), findElement(By.xpath("//button[contains(text(), 'Cancel')]")));

        Assert.assertEquals(findElement(By.className("card-body")).getText(), "");
        Assert.assertNull(findElement(By.xpath("//a[@href='index.php?action=recycle_bin']")).getAttribute("span"));
    }

    @Test(dependsOnMethods = "testCreatedAndCancel")
    public void testCreateRecord() {
        fillForm();
        scrollClick(getDriver(), findElement(By.id("pa-entity-form-save-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//i[text()='menu']")));

        List<WebElement> resultList = findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));
        Assert.assertEquals(resultList.stream().map(WebElement::getText).collect(Collectors.toList()), expectedCreatedResult);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {
        final List<String> expectedResult = List
                .of(STRING_VALUE, TEXT_VALUE, INT_VALUE, DECIMAL_VALUE, DATE_VALUE, DATETIME_VALUE);

        getEntity(getDriver(), "Placeholder");
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//i[text()='menu']"))).click();
        getWait().until(movingIsFinished(By.xpath("//a[contains(., 'view')]"))).click();

        List<WebElement> resultList = findElements(By.xpath("//span[@class='pa-view-field']"));
        Assert.assertEquals(resultList.stream().map(WebElement::getText).collect(Collectors.toList()), expectedResult);
    }
}