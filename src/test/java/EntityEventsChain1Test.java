import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityEventsChain1Test extends BaseTest {

    private static final By EVENTS_CHAIN1_MENU = By.xpath("//p[contains(.,'Events Chain 1')]");
    private static final By CREATE_NEW_FOLDER_BUTTON = By.xpath("//i[text() = 'create_new_folder']");
    private static final By F1_FIELD = By.id("f1");
    private static final By F10_FIELD = By.id("f10");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By CELLS = By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr/td/a");
    private static final By ICON = By.xpath("//tbody/tr/td[1]/i");

    private void clickEventsChain1Menu(){
        scrollClick(getDriver(), getDriver().findElement(EVENTS_CHAIN1_MENU));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(CREATE_NEW_FOLDER_BUTTON)));
    }
    private void clickCreateNewFolderButton() {
        getDriver().findElement(CREATE_NEW_FOLDER_BUTTON).click();
    }

    private void inputF1Value(String f1Value){
        getDriver().findElement(F1_FIELD).sendKeys(f1Value);
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElement(F10_FIELD), "value"));
    }

    private void clickSaveButton(){
        getDriver().findElement(SAVE_BUTTON).click();
    }

    private List<WebElement> getCells(){
        return getDriver().findElements(CELLS);
    }

    private List<String> getRowValues() {
        List<String> actualValues = new ArrayList<>();

        for(WebElement cell : getCells()) {
            actualValues.add(cell.getText());
        }

        return actualValues;
    }

    private String getAttributeClass() {
        return getDriver().findElement(ICON).getAttribute("class");
    }

    @Test
    public void testCreateNewRecord() {
        final String f1InputValue = "1";
        final List<String> expectedValues = Arrays.asList("1", "2", "4", "8", "16", "32", "64", "128", "256", "512");

        start(getDriver());
        clickEventsChain1Menu();
        clickCreateNewFolderButton();
        inputF1Value(f1InputValue);
        clickSaveButton();

        Assert.assertEquals(getCells().size(), expectedValues.size());
        Assert.assertEquals(getRowValues(), expectedValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }
}
