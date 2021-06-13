import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityVisibilityEventsTest extends BaseTest {

    private void clickVisabilityEventsMenu(){
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text() = ' Visibility events ']")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private void clickCreateNewFolderButton() {
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//span[@class ='toggle']"))));
    }

    private void clickTriggerFieldButton(){
        getDriver().findElement(By.xpath("//span[@class ='toggle']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("test_field")));
    }
    private void inputTextValue(String text) {
        getDriver().findElement(By.id("test_field")).sendKeys(text);
    }

    private void clickSaveButton(){
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[text() = 'create_new_folder']")));
        }

    private String infoText(){
       return getDriver().findElement(By.xpath("//span[@class='pagination-info']")).getText();
    }

    private String getAttributeClass(){
        return getDriver().findElement(By.xpath("//tbody/tr/td[2]/i")).getAttribute("class");
    }

    private String getValue(){
        return getDriver().findElement(By.xpath("//td/a")).getText();
    }

    @Test
    public void testCreateNewRecord(){

        final String testField = "My first test 2021";

        start(getDriver());
        clickVisabilityEventsMenu();
        clickCreateNewFolderButton();
        clickTriggerFieldButton();
        inputTextValue(testField);
        clickSaveButton();

        Assert.assertEquals(infoText(),"Showing 1 to 1 of 1 rows");
        Assert.assertEquals(getAttributeClass(),"fa fa-check-square-o");
        Assert.assertEquals(getValue(),"My first test 2021");

    }
}
