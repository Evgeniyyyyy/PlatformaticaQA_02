import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;
import java.util.Arrays;
import java.util.List;

import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;

public class EntityPlaceholderTest extends BaseTest {
    By checkBox = By.xpath("//tbody/tr/td[1]/i");
    By checkMail = By.xpath("//tbody/tr/td[contains(text(),'tester99@tester.test')]");
    By checkRow = By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr");
    //find DIV without children elements
    By checkEmptyRow = By.xpath("//div[@class=\"card pa-container-table\"]/div[count(*)=0]");
    By createNewFolder = By.xpath("//div/i[text()='create_new_folder']");
    By userField = By.xpath("//div[@class = \"filter-option-inner-inner\"]");
    By findMail = By.xpath("//span[contains (text(),  \"tester99@tester.test\")]");
    By saveButton = By.id("pa-entity-form-save-btn");
    By actionsButton = By.xpath("//i[contains(text(), 'menu')]");
    By binTableRows = By.xpath("//tbody/tr");
    By exitButton = By.xpath("//i[contains(text(),'clear')]");

    private final String STRING_VALUE = "Test 01";
    private final String TEXT_VALUE = "first test";
    private final String INT_VALUE = "1";
    private final String DECIMAL_VALUE = "1.11";
    private final String DATE_VALUE = "01/01/2021";
    private final String DATETIME_VALUE = "01/01/2021 10:10:10";
    private final String MAIL_VALUE = "tester99@tester.test";
    private final String emptyBinPlaceholder = "Good job with housekeeping! Recycle bin is currently empty!";
    private final List<String> expectedResult = List.of(
            STRING_VALUE, TEXT_VALUE, INT_VALUE, DECIMAL_VALUE, DATE_VALUE, DATETIME_VALUE);

    private void clickPlaceholderMenu() {
        scrollClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Placeholder')]")));
    }

    private void createNewRecord(){
        clickPlaceholderMenu();

        findElement(createNewFolder).click();
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
        findElement(userField).click();
        scrollClick(getDriver(), getDriver().findElement(findMail));
        jsClick(getDriver(), getDriver().findElement(saveButton));
    }

    private void clickActionButton(String menuName){
        jsClick(getDriver(), findElement(actionsButton));
        getWait().until(TestUtils.movingIsFinished(getDriver()
                .findElement(By.xpath(String.format("//li/a[contains (text(), \"%s\")]", menuName))))).click();
    }

    private void clickNotification() {
        getDriver().findElement(By.xpath("//span[@class = \"notification\"]")).click();
    }

    private WebElement icon() {
        return findElement(checkBox);
    }

    private WebElement result() {
        return findElement(checkMail);
    }

    private List<WebElement> record() {
        return findElements(checkRow);
    }

    private List<WebElement> emptyRow() {
        return findElements(checkEmptyRow);
    }

    @Test
    public void testCreateNewRecord() {
        createNewRecord();

        Assert.assertEquals(record().size(), 1);
        Assert.assertEquals(icon().getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(result().getText(), MAIL_VALUE);
    }

    @Test(dependsOnMethods = "testCreateNewRecord")
    public void testViewRecord() {
        clickPlaceholderMenu();
        clickActionButton("view");

        List<WebElement> resultList = findElements(By.xpath("//span[@class='pa-view-field']"));
        Assert.assertEquals(resultList.size(), expectedResult.size());
        for (int i = 0; i < resultList.size(); i++) {
            Assert.assertNotNull(resultList.get(i));
            Assert.assertEquals(resultList.get(i).getText(), expectedResult.get(i));
            Assert.assertEquals(findElement(By.xpath("//div[@class = 'form-group']/p")).getText(), MAIL_VALUE);
        }
        findElement(exitButton).click();
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditRecord() {
        final List<String> editResult =
                Arrays.asList("Test 02", "Second test", "2", "2.22", "02/02/2020\n", "02/02/2020 02:02:02\n");

        clickPlaceholderMenu();
        clickActionButton("edit");

        findElement(By.id("string")).clear();
        findElement(By.id("string")).sendKeys(editResult.get(0));
        findElement(By.id("text")).clear();
        findElement(By.id("text")).sendKeys(editResult.get(1));
        findElement(By.id("int")).clear();
        findElement(By.id("int")).sendKeys(editResult.get(2));
        findElement(By.id("decimal")).clear();
        findElement(By.id("decimal")).sendKeys(editResult.get(3));
        findElement(By.id("date")).click();
        findElement(By.id("date")).clear();
        findElement(By.id("date")).sendKeys(editResult.get(4));
        findElement(By.id("datetime")).click();
        findElement(By.id("datetime")).clear();
        findElement(By.id("datetime")).sendKeys(editResult.get(5));
        TestUtils.jsClick(getDriver(), getDriver().findElement(saveButton));

        List<WebElement> editedList = findElements(By.xpath("//td[@class = \"pa-list-table-th\"]/a[count(*)=0]"));
        Assert.assertEquals(editedList.size(), expectedResult.size());
        for (int i = 0; i < editedList.size(); i++) {
            Assert.assertNotNull(editedList.get(i));
            Assert.assertNotEquals(editedList.get(i).getText(), expectedResult.get(i));
        }
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {
        clickPlaceholderMenu();
        clickActionButton("delete");

        Assert.assertEquals(emptyRow().size(), 1);
        clickNotification();
        Assert.assertEquals(findElement(By.xpath("//span[@class='pagination-info']")).getText(), "Showing 1 to 1 of 1 rows");
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreDeletedRecord() {
        clickPlaceholderMenu();
        clickNotification();

        List<WebElement> rows = findElements(binTableRows);
        Assert.assertEquals(rows.size(), 1);
        getDriver().findElement(By.linkText("restore as draft")).click();
        Assert.assertEquals(findElement(By.className("card-body")).getText(), emptyBinPlaceholder);

        clickPlaceholderMenu();

        Assert.assertEquals(record().size(), 1);
        Assert.assertEquals(icon().getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(result().getText(), MAIL_VALUE);
    }

    @Test(dependsOnMethods = "testRestoreDeletedRecord")
    public void testDeleteExistingRecordPermanently() {
        clickPlaceholderMenu();
        clickActionButton("delete");
        clickNotification();

        List<WebElement> rows = findElements(binTableRows);
        Assert.assertEquals(rows.size(), 1);
        getDriver().findElement(By.linkText("delete permanently")).click();
        Assert.assertEquals(findElement(By.className("card-body")).getText(), emptyBinPlaceholder);

        clickPlaceholderMenu();

        Assert.assertEquals(emptyRow().size(), 1);
    }
}