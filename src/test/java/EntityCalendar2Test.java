import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.List;

public class EntityCalendar2Test extends BaseTest {

    private void createCalendarDraftRecord(String str, String text, String in, String decimal) {
        TestUtils.scrollClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Calendar')]")));
        getDriver().findElement(By.xpath("//*[@class='card-icon']")).click();

        findElement(By.id("string")).sendKeys(str);
        findElement(By.id("text")).sendKeys(text);
        findElement(By.id("date")).click();
        findElement(By.id("datetime")).click();
        findElement(By.id("int")).sendKeys(in);
        findElement(By.id("decimal")).sendKeys(decimal);

        TestUtils.scrollClick(getDriver(), findElement(By.id("pa-entity-form-draft-btn")));
    }


    @Test
    public void testEditSaveDraftRecord() {

        final String newString = "NEW String";
        final String newTest = "This is a NEW text!";
        final String newData = "25/05/2021";
        final String newDatetime = "25/05/2021 17:11:53";
        final String newInt = "2021";
        final String newDecimal = "25.52";
        final String tester = "tester30@tester.test";

        final List<String> expextedResult = List.of(newString, newTest, newInt, newDecimal, newData,
                newDatetime, "", tester);

        ProjectUtils.start(getDriver());

        createCalendarDraftRecord("Java Learning", "This is a cool lessons.", "115", "65.35");

        findElement(By.xpath("//a[@class='nav-link ' and contains(.,'list')]")).click();
        findElement(By.xpath("//a[contains(.,'Java Learning')]/parent::td/parent::tr//button")).click();

        getWait().until(TestUtils.movingIsFinished(getDriver().findElement(By.xpath
                ("//a[contains(.,'Java Learning')]/parent::td/parent::tr//div//a[contains(.,'edit')]")))).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='string']"))).clear();
        findElement(By.id("string")).sendKeys(newString);

        findElement(By.id("text")).clear();
        findElement(By.id("text")).sendKeys(newTest);

        findElement(By.id("date")).clear();
        findElement(By.id("date")).sendKeys(newData);

        findElement(By.id("datetime")).clear();
        findElement(By.id("datetime")).sendKeys(newDatetime);

        findElement(By.id("int")).clear();
        findElement(By.id("int")).sendKeys(newInt);

        findElement(By.id("decimal")).clear();
        findElement(By.id("decimal")).sendKeys(newDecimal);

        findElement(By.xpath("//div[@class='filter-option-inner']")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dropdown-menu show']")));
        TestUtils.scrollClick(getDriver(), By.xpath("//li//span[@class='text' and text() = '" + tester + "']"));

        findElement(By.id("pa-entity-form-draft-btn")).click();

        List<WebElement> result = getDriver().findElements(
                By.xpath("//a[contains(.,'" + newString + "')]/parent::td/parent::tr/td[@class='pa-list-table-th']"));
        for (int i = 0; i < 8; i++) {
            Assert.assertEquals(result.get(i).getText(), expextedResult.get(i));
        }
    }
}
