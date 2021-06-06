import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.TestUtils.scrollClick;

public class EntityBoardEditRecordTest extends BaseTest {

    private void getTextField() {
        WebElement textField = getWait().until(ExpectedConditions.elementToBeClickable(TEXT_INPUT_AREA));
        textField.click();
        textField.clear();
    }

    private static final By ENTITY_BOARD_MENU = By.xpath("//div[@id='menu-list-parent']//li[10]/a/i");
    private static final By CREATE_NEW_RECORD = By.xpath("//i[contains (text(), 'create_new_folder')]");
    private static final By TEXT_INPUT_AREA = By.id("text");
    private static final By INT_INPUT_AREA = By.id("int");
    private static final By SAVE_BUTTON = By.xpath("//button[@id='pa-entity-form-save-btn']");
    private static final By LIST_BUTTON = By.xpath("//li//a[@class='nav-link ']/i[@class='material-icons']");
    private static final By ACTIONS_BUTTON = By.xpath("//tbody/tr//td//button[@type='button']");
    private static final By EDIT_BUTTON = By.xpath("//tbody/tr//td//li[2]/a");
    private static final By CELLS = By.xpath("//tbody//td[@class='pa-list-table-th']");
    private static final By ICON = By.xpath("//tbody/tr/td[1]/i");

    private List<WebElement> getCells() {
        return getDriver().findElements(CELLS);
    }

    private List<String> getRowValues() {
        List<String> actualValues = new ArrayList<>();

        for (WebElement cell : getCells()) {
            actualValues.add(cell.getText());
        }
        return actualValues;
    }

    private String getAttributeClass() {
        return getDriver().findElement(ICON).getAttribute("class");
    }

    private void createRecord() {

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(), getDriver().findElement(ENTITY_BOARD_MENU));
        getDriver().findElement(CREATE_NEW_RECORD).click();
        getTextField();
        getDriver().findElement(TEXT_INPUT_AREA).sendKeys("Some text here...");
        getDriver().findElement(INT_INPUT_AREA).sendKeys("1234");
        WebElement findSaveButton = getDriver().findElement(SAVE_BUTTON);
        TestUtils.scrollClick(getDriver(), findSaveButton);
    }

    @Test
    public void testEditRecord() {

        String NEW_TEXT = "any text";
        String NEW_INT = "2222";
        final List<String> expectedValues = Arrays.asList(
                "Pending", NEW_TEXT, NEW_INT, "0.00", "", "", "", "apptester1@tester.test");

        createRecord();

        scrollClick(getDriver(), getDriver().findElement(ENTITY_BOARD_MENU));
        getDriver().findElement(LIST_BUTTON).click();
        getDriver().findElement(ACTIONS_BUTTON).click();

        getWait().until(TestUtils.movingIsFinished(EDIT_BUTTON)).click();
        getTextField();
        getDriver().findElement(TEXT_INPUT_AREA).sendKeys(NEW_TEXT);

        WebElement intField = getDriver().findElement(INT_INPUT_AREA);
        intField.clear();
        intField.sendKeys(NEW_INT);
        TestUtils.scrollClick(getDriver(), getDriver().findElement(SAVE_BUTTON));

        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
        Assert.assertEquals(getCells().size(), expectedValues.size());
        Assert.assertEquals(getRowValues(), expectedValues);
    }
}
