import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;

import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;


public class EntityTagViewTest extends BaseTest {

    public boolean isElementPresent(By element) {
        try {
            getDriver().findElement(element);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void createNewEntity(){
        ProjectUtils.start(getDriver());
        WebElement tag = getDriver().findElement(By.xpath("//p[contains(.,'Tag')]"));
        scrollClick(getDriver(), tag);
        getDriver().findElement(By.xpath("//i[contains(.,'create_new_folder')]")).click();
        getDriver().findElement(By.id("string")).sendKeys("String");
        getDriver().findElement(By.id("text")).sendKeys("Text");
        getDriver().findElement(By.id("int")).sendKeys("23");
        getDriver().findElement(By.id("decimal")).sendKeys("23.23");
        getDriver().findElement(By.id("date")).click();
        jsClick(getDriver(), getDriver().findElement(By.cssSelector(".today")));
        getDriver().findElement(By.id("datetime")).click();
        jsClick(getDriver(), getDriver().findElement(By.cssSelector(".today")));
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
        getDriver().findElement(By.xpath("//p[contains(.,'Home')]")).click();
    }

    @Test
    public void testViewRecord(){
        By MENU_ENTITY_TAG = By.xpath("//p[contains(.,'Tag')]");
        By BUTTON_ACTION = By.cssSelector(".btn-round");
        By MENU_ACTION_VIEW = By.xpath("//a[contains(text(),'view')]");
        By VIEW_LABEL_STRING = By.xpath("//label[contains(.,'String')]");
        By VIEW_LABEL_TEXT = By.xpath("//label[contains(.,'Text')]");
        By VIEW_LABEL_INT = By.xpath("//label[contains(.,'Int')]");
        By VIEW_LABEL_DECIMAL = By.xpath("//label[contains(.,'Decimal')]");
        By VIEW_LABEL_DATE = By.xpath("//label[contains(.,'Date')]");
        By VIEW_LABEL_DATETIME = By.xpath("//label[contains(.,'Datetime')]");
        By VIEW_LABEL_USER = By.xpath("//label[contains(.,'User')]");
        By VIEW_TITLE_TAG = By.xpath("//h4[contains(.,'Tag')]");
        By VIEW_BUTTON_CLOSE = By.xpath("//button[contains(.,'clear')]");

        createNewEntity();
        WebElement tag = getDriver().findElement(MENU_ENTITY_TAG);
        scrollClick(getDriver(), tag);
        getDriver().findElement(BUTTON_ACTION).click();
        jsClick(getDriver(), getDriver().findElement(MENU_ACTION_VIEW));

        Assert.assertTrue(isElementPresent(VIEW_LABEL_STRING));
        Assert.assertTrue(isElementPresent(VIEW_LABEL_TEXT));
        Assert.assertTrue(isElementPresent(VIEW_LABEL_INT));
        Assert.assertTrue(isElementPresent(VIEW_LABEL_DECIMAL));
        Assert.assertTrue(isElementPresent(VIEW_LABEL_DATE));
        Assert.assertTrue(isElementPresent(VIEW_LABEL_DATETIME));
        Assert.assertTrue(isElementPresent(VIEW_LABEL_USER));
        Assert.assertTrue(isElementPresent(VIEW_TITLE_TAG));
        Assert.assertTrue(isElementPresent(VIEW_BUTTON_CLOSE));
    }
}
