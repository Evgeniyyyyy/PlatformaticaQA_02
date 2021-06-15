import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;


public class EntityTagViewTest extends BaseTest {

    private static final String STRING = "String";
    private static final String TEXT = "Text";
    private static final String INT = "23";
    private static final String DECIMAL = "23.23";
    private static final List<String> EXPECTED_RESULT = List.of(STRING, TEXT, INT, DECIMAL, "", "");
    private static final By fieldDate = By.xpath("//input[@id='date']");
    private static final By fieldDateTime = By.xpath("//input[@id='datetime']");

    private void fillField(By field){
        getDriver().findElement(field).click();
        getDriver().findElement(field).clear();
        getDriver().findElement(field).sendKeys("");
    }


    private void createNewEntity(){
        getEntity(getDriver(), "Tag");
        clickCreateRecord(getDriver());
        getDriver().findElement(By.id("string")).sendKeys(STRING);
        getDriver().findElement(By.id("text")).sendKeys(TEXT);
        getDriver().findElement(By.id("int")).sendKeys(INT);
        getDriver().findElement(By.id("decimal")).sendKeys(DECIMAL);
        fillField(fieldDate);
        fillField(fieldDateTime);
        clickSave(getDriver());
        getDriver().findElement(By.xpath("//p[contains(.,'Home')]")).click();
    }

    @Test
    public void testViewRecord(){

        createNewEntity();
        getEntity(getDriver(), "Tag");
        clickActionsView(getWait(),getDriver(),0);
        List<WebElement> viewField =getDriver().findElements(By.xpath("//span[@class='pa-view-field']"));

        Assert.assertEquals(getActualValues(viewField), EXPECTED_RESULT);
    }
}
