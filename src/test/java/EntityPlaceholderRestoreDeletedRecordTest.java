import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.scrollClick;

public class EntityPlaceholderRestoreDeletedRecordTest extends BaseTest {

    private static final String TR_XPATH = "//table/tbody/tr";
    private static final String STRING = "Task";
    private static final String TEXT = "English";
    private static final String INT = "25";
    private static final String DECIMAL = "25.13";
    private static final String DATE = "";
    private static final String DATETIME = "";
    private static final String FILE = "";
    private static final String FILE_IMAGE = "";
    private static final String USER_ID = "31";
    private static final String USER = "tester29@tester.test";

    private static final List<String> EXPECTED_RESULT = List.of(STRING, TEXT, INT, DECIMAL, USER_ID);
    private static final List<String> EXPECTED_RESULT_1 = List.of(STRING, TEXT, INT, DECIMAL, DATE, DATETIME, FILE, FILE_IMAGE, USER);

    private List<String> getActualResult(List<WebElement> testData) {
        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i <= testData.size() - 1; i++) {
            actualResult.add(testData.get(i).getText());
        }

        return actualResult;
    }

    @Test
    public void testDeleteRecord() {

        scrollClick(getDriver(), By.xpath("//p[contains (text(), 'Placeholder')]"));
        clickCreateRecord(getDriver());

        findElement(By.id("string")).sendKeys(STRING);
        findElement(By.id("text")).sendKeys(TEXT);
        findElement(By.id("int")).sendKeys(INT);
        findElement(By.id("decimal")).sendKeys(DECIMAL);
        findElement(
                By.xpath("//div [@ id = '_field_container-user']/div/select/option [text() = '" + USER + "']"))
                .click();

        clickSave(getDriver());
        clickActionsDelete(getWait(), getDriver());
        clickRecycleBin(getDriver());

        List<WebElement> rows = findElements(By.xpath(TR_XPATH));

        Assert.assertEquals(rows.size(), 1);

        List<WebElement> testData = findElements(
                By.xpath(TR_XPATH + "/td [@class = 'pa-recycle-col']/a/span/b"));

        Assert.assertEquals(getActualResult(testData), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreDeletedRecord() {

        clickRecycleBin(getDriver());

        List<WebElement> rows = findElements(By.xpath(TR_XPATH));

        Assert.assertEquals(rows.size(), 1);

        List<WebElement> testData = findElements(
                By.xpath(TR_XPATH + "/td [@class = 'pa-recycle-col']/a/span/b"));

        Assert.assertEquals(getActualResult(testData), EXPECTED_RESULT);

        findElement(
                By.xpath(TR_XPATH + "/td [@class = 'pa-recycle-control']/a[contains (text(), 'restore as draft')]"))
                .click();
        scrollClick(getDriver(), By.xpath("//p[contains (text(), 'Placeholder')]"));

        List<WebElement> testData1 = findElements(
                By.xpath(TR_XPATH + "/td [@class = 'pa-list-table-th']"));

        Assert.assertEquals(getActualResult(testData1), EXPECTED_RESULT_1);

        WebElement icon = findElement(By.xpath("//td [1]/i"));

        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");
    }
}
