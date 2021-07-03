package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;

import java.util.Arrays;
import java.util.List;

import static utils.TestUtils.*;

@Ignore
public class EntityPlaceholderEditTest extends BaseTest{

    private static final By BUTTON_ACTION = By.cssSelector(".btn-round");
    private static final By MENU_ACTION_EDIT = By.xpath("//a[contains(text(),'edit')]");
    private static final By FIELD_PLACEHOLDER_STRING = By.id("string");
    private static final By FIELD_PLACEHOLDER_TEXT = By.id("text");
    private static final By FIELD_PLACEHOLDER_INT = By.id("int");
    private static final By FIELD_PLACEHOLDER_DECIMAL = By.id("decimal");
    private static final By FIELD_PLACEHOLDER_DATE = By.id("date");
    private static final By FIELD_PLACEHOLDER_DATETIME = By.id("datetime");
    private static final By FIELD_PLACEHOLDER_USER = By.name("entity_form_data[user]");
    private static final By BUTTON_SAVE = By.xpath("//button[@id='pa-entity-form-save-btn']");
    private static final By TABLE_FIELD_STRING = By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[2]/a");
    private static final By TABLE_FIELD_TEXT = By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[3]/a");
    private static final By TABLE_FIELD_INT = By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[4]/a");
    private static final By TABLE_FIELD_DECIMAL = By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[5]/a");
    private static final By TABLE_FIELD_DATE = By.cssSelector(".pa-list-table-th:nth-child(6) > a");
    private static final By TABLE_FIELD_DATETIME = By.cssSelector(".pa-list-table-th:nth-child(7) > a");
    private static final By TABLE_FIELD_USER = By.xpath("//td[contains(text(),'tester33@tester.test')]");

    private static final List<String> EDIT_PLACEHOLDER = Arrays.asList("Manager", "Merchandiser", "7", "15.69", "13/03/1999\n", "13/03/1999 12:12:11\n");

    private void createNewEntity(){
        ProjectUtils.start(getDriver());
        WebElement placeholder = getDriver().findElement(By.xpath("//p[contains(.,'Placeholder')]"));
        scrollClick(getDriver(), placeholder);
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
        getDriver().findElement(By.id("string")).sendKeys("Text");
        getDriver().findElement(By.id("text")).sendKeys("Text");
        getDriver().findElement(By.id("int")).sendKeys("34");
        getDriver().findElement(By.id("decimal")).sendKeys("5.8");
        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(By.xpath("//h4[contains(text(),'Placeholder')]")).click();
        scroll(getDriver(), findElement(FIELD_PLACEHOLDER_USER));

        WebElement user = findElement(By.xpath("//select/option[text() ='tester31@tester.test']"));
        user.click();
        jsClick(getDriver(), getDriver().findElement(BUTTON_SAVE));
    }

    @Test
    public void testEditRecord() {

        createNewEntity();
        String string =getDriver().findElement(TABLE_FIELD_STRING).getText();
        String text =getDriver().findElement(TABLE_FIELD_TEXT).getText();
        String _int =getDriver().findElement(TABLE_FIELD_INT).getText();
        String decimal =getDriver().findElement(TABLE_FIELD_DECIMAL).getText();
        String date = getDriver().findElement(TABLE_FIELD_DATE).getText();
        String datetime = getDriver().findElement(TABLE_FIELD_DATETIME).getText();

        findElement(BUTTON_ACTION).click();
        jsClick(getDriver(), getDriver().findElement(MENU_ACTION_EDIT));
        findElement(FIELD_PLACEHOLDER_STRING).clear();
        findElement(FIELD_PLACEHOLDER_STRING).sendKeys(EDIT_PLACEHOLDER.get(0));
        findElement(FIELD_PLACEHOLDER_TEXT).clear();
        findElement(FIELD_PLACEHOLDER_TEXT).sendKeys(EDIT_PLACEHOLDER.get(1));
        findElement(FIELD_PLACEHOLDER_INT).clear();
        findElement(FIELD_PLACEHOLDER_INT).sendKeys(EDIT_PLACEHOLDER.get(2));
        findElement(FIELD_PLACEHOLDER_DECIMAL).clear();
        findElement(FIELD_PLACEHOLDER_DECIMAL).sendKeys(EDIT_PLACEHOLDER.get(3));
        findElement(FIELD_PLACEHOLDER_DATE).click();
        findElement(FIELD_PLACEHOLDER_DATE).clear();
        findElement(FIELD_PLACEHOLDER_DATE).sendKeys(EDIT_PLACEHOLDER.get(4));
        findElement(FIELD_PLACEHOLDER_DATE).click();
        findElement(FIELD_PLACEHOLDER_DATETIME).clear();
        findElement(FIELD_PLACEHOLDER_DATETIME).sendKeys(EDIT_PLACEHOLDER.get(5));
        findElement(By.xpath("//h4[contains(text(),'Placeholder')]")).click();
        scroll(getDriver(), findElement(By.name("entity_form_data[user]")));
        WebElement user = findElement(By.xpath("//select/option[text() ='tester33@tester.test']"));
        user.click();

        jsClick(getDriver(), getDriver().findElement(BUTTON_SAVE));


        Assert.assertNotEquals(getDriver().findElement(TABLE_FIELD_STRING).getText(), string);
        Assert.assertNotEquals(getDriver().findElement(TABLE_FIELD_TEXT).getText(), text);
        Assert.assertNotEquals(getDriver().findElement(TABLE_FIELD_INT).getText(), _int);
        Assert.assertNotEquals(getDriver().findElement(TABLE_FIELD_DECIMAL).getText(), decimal);
        Assert.assertNotEquals(getDriver().findElement(TABLE_FIELD_DATE).getText(), date);
        Assert.assertNotEquals(getDriver().findElement(TABLE_FIELD_DATETIME).getText(), datetime);
        Assert.assertTrue(getDriver().findElement(TABLE_FIELD_USER).isDisplayed());
    }
}
