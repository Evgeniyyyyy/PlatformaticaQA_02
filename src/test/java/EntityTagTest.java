import base.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static utils.ProjectUtils.*;

public class EntityTagTest extends BaseTest{

    private static final By ICON = By.xpath("//tbody/tr/td/i");
    private static final By ACTUAL_RESULT = By.xpath("//td[@class='pa-list-table-th']");
    private static final By FIELD_STRING = By.id("string");
    private static final By FIELD_TEXT = By.id("text");
    private static final By FIELD_INT = By.id("int");
    private static final By FIELD_DECIMAL = By.id("decimal");
    private static final By FIELD_DATE = By.id("date");
    private static final By FIELD_DATE_TIME = By.id("datetime");

    private static final List<By> ELEMENTS = List.of(
            FIELD_STRING, FIELD_TEXT, FIELD_INT, FIELD_DECIMAL, FIELD_DATE, FIELD_DATE_TIME);

    Random random = new Random();
    Date date = new Date();

    List <String> EXPECTED_RESULT = List.of(
            RandomStringUtils.randomAlphabetic(3),
            RandomStringUtils.randomAlphabetic(30),
            String.valueOf(random.nextInt(10000)),
            String.format("%.2f", random.nextFloat()),
            new SimpleDateFormat("dd/MM/yyyy").format(date),
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date),
            "", "tester26@tester.test");

    List <String> EDIT_RESULT = List.of(
            RandomStringUtils.randomAlphabetic(20),
            RandomStringUtils.randomAlphabetic(15),
            String.valueOf(random.nextInt(10000)),
            String.format("%.2f", random.nextFloat()),
            "15/11/0078", "17/06/0902 12:31:44",
            "", "tester27@tester.test");

    private void fillForm() {

        getEntity(getDriver(),"Tag");
        clickCreateRecord(getDriver());

        for (int i = 0; i < ELEMENTS.size(); i++) {
            findElement(ELEMENTS.get(i)).click();
            findElement(ELEMENTS.get(i)).clear();
            findElement(ELEMENTS.get(i)).sendKeys(EXPECTED_RESULT.get(i));
        }

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//div[@class='filter-option-inner-inner']")));
        TestUtils.jsClick(getDriver(), findElement(By.xpath("//span[text()='tester26@tester.test']")));
    }

    private void editForms() {

        for (int i = 0; i < ELEMENTS.size(); i++) {
            findElement(ELEMENTS.get(i)).clear();
            findElement(ELEMENTS.get(i)).sendKeys(EDIT_RESULT.get(i));
        }

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//div[@class='filter-option-inner-inner']")));
        TestUtils.jsClick(getDriver(), findElement(By.xpath("//span[text()='tester27@tester.test']")));

        clickSave(getDriver());
    }

    @Test
    public void testCancelRecord() {

        fillForm();
        clickCancel(getDriver());

        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));
    }

    @Test
    public void testCreateRecord() {

        fillForm();
        clickSave(getDriver());

        WebElement icon = findElement(ICON);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test
    public void testCreateDraftRecord() {

        fillForm();
        clickSaveDraft(getDriver());

        WebElement icon = findElement(ICON);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testEditRecord() {

        getEntity(getDriver(), "Tag");
        clickActionsEdit(getWait(), getDriver());
        editForms();

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EDIT_RESULT);
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testDeleteRecord() {

        getEntity(getDriver(), "Tag");
        clickActionsDelete(getWait(), getDriver());
        clickRecycleBin(getDriver());
        findElement(By.xpath("//tbody/tr/td/a")).click();

        List<WebElement> deleteRow = getDriver().findElements(By.cssSelector("span.pa-view-field"));
        for (int i = 0; i < deleteRow.size(); i++) {
            Assert.assertEquals(deleteRow.get(i).getText(), EXPECTED_RESULT.get(i));
        }
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord(){

        getEntity(getDriver(), "Tag");
        clickActionsView(getWait(),getDriver());

        List<WebElement> viewField = getDriver().findElements(By.xpath("//span[@class='pa-view-field']"));
        for (int i = 0; i < viewField.size(); i++) {
            Assert.assertEquals(viewField.get(i).getText(), EXPECTED_RESULT.get(i));
        }
    }
}
