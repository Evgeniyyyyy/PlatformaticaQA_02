package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.ProjectUtils.clickSave;
import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;

@Ignore
public class EntityChevronSentRecordViaListTest extends BaseTest {

    private static final String ENTERED_DATA = "firstExample";
    private static final By CHEVRON = By.xpath("//p[contains(text(),' Chevron ')]");
    private static final By CREATE_NEW_FOLDER = By.xpath("//i[text() = 'create_new_folder']");
    private static final By TEXT = By.id("text");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By TOGGLE = By.xpath("//i[@class ='fa fa-toggle-off']");

    private void createNewRecord() {
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(By.xpath("//i[contains(text(),'create_new_folder')]"))))
                .click();
        findElement(By.id("text")).sendKeys(ENTERED_DATA);
        clickSave(getDriver());
    }

    private void sentCreatedRecord() {
        findElement(By.xpath("//button[contains(text(),'Sent')]")).click();
    }

    private void clickSentMenu() {
        findElement(By.xpath("//a[contains(.,'Sent')]")).click();
    }

    private List<WebElement> getCellsValues(){
        return findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']/a"));
    }

    private List<String> getValues() {
        List<String> values = new ArrayList<>();
        for (int i = 1; i < getCellsValues().size(); i++) {
            values.add(getCellsValues().get(i).getText());
        }
        return values;
    }

    private List<WebElement> getCells(){
        return findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));
    }

    private List<String> getLastTwoCellsValues() {
        List<String> values = new ArrayList<>();
        for (int i = getCells().size() - 2; i < getCells().size(); i++) {
            values.add(getCells().get(i).getText());
        }
        return  values;
    }

    @Test
    public void testSentRecordViaList() {
        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), By.xpath("//p[contains(text(),'Chevron')]"));

        createNewRecord();

        List<String> oldValues = getValues();
        oldValues.addAll(getLastTwoCellsValues());

        sentCreatedRecord();
        clickSentMenu();

        Assert.assertEquals(getCellsValues().get(0).getText(), "Sent");

        List<String> newValues = getValues();
        newValues.addAll(getLastTwoCellsValues());

        Assert.assertEquals(newValues, oldValues);
    }

    @Test
    public void testSentRecordWithActivatedToggle() {
        clickChevronMenu();
        clickCreateNewFolderButton();
        inputF1Value("Mail");
        clickSaveButton();
        clickToggle();
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//button[text()='Sent']")));
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//a[contains(@href,'index.php?action=action_list&list_type=table&entity_id=36&stage=Sent')]")));


        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//span[@class='pagination-info']"),
                "Showing 1 to 1 of 1 rows"));
        Assert.assertEquals(findElement(
                By.xpath("//span[@class='pagination-info']")).getText(),
                "Showing 1 to 1 of 1 rows");

    }

    private void clickChevronMenu() {
        scrollClick(getDriver(), findElement(CHEVRON));
        getWait().until(ExpectedConditions.elementToBeClickable(findElement(CREATE_NEW_FOLDER)));
    }

    private void clickCreateNewFolderButton() {
        findElement(CREATE_NEW_FOLDER).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(TEXT));
    }

    private void inputF1Value(String mail) {
        findElement(TEXT).sendKeys(mail);
    }

    private void clickSaveButton() {
        findElement(SAVE_BUTTON).click();
        getWait().until(ExpectedConditions.elementToBeClickable(findElement(CREATE_NEW_FOLDER)));
    }

    private void clickToggle() {
        findElement(TOGGLE).click();
    }
}