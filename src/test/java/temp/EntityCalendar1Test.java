package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityCalendar1Test extends BaseTest {
    private static final String STRING = "String";
    private static final String TEXT = "Text";
    private static final String INT = "52";
    private static final String DECIMAL = "25.52";
    private static final String DATE = "27/05/2021";
    private static final String DATETIME = "27/05/2021 10:40:45";
    private static final String FILE = "";
    private static final String USER = "tester29@tester.test";
    private static final List<String> EXPECTED_VALUES = List.of(STRING, TEXT, INT, DECIMAL, DATE, DATETIME, FILE, USER);

    private List<String> getTdsTexts (List<WebElement> trsActual) {
        List<String> actualValues = new ArrayList<>();

        for(WebElement tr: trsActual) {
            actualValues.add(tr.getText());
        }
        return actualValues;
    }

    @Test
    public void testCreateDraftRecord(){

        TestUtils.scrollClick(getDriver(), getDriver().findElement(
                By.xpath("//div[@id = 'menu-list-parent']/ul/li/a/p[text() = ' Calendar ']")));

        By createNewRecord =By.xpath("//i[text() = 'create_new_folder']");
        getDriver().findElement(createNewRecord).click();

        By fieldString = By.id("string");
        getDriver().findElement(fieldString).sendKeys(STRING);

        By fieldText = By.id("text");
        getDriver().findElement(fieldText).sendKeys(TEXT);

        By fieldInt = By.id("int");
        getDriver().findElement(fieldInt).sendKeys(INT);

        By fieldDecimal = By.id("decimal");
        getDriver().findElement(fieldDecimal).sendKeys(DECIMAL);

        WebElement fieldDate = getDriver().findElement(By.id("date"));
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(DATE);

        WebElement fieldDateTime = getDriver().findElement(By.id("datetime"));
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(DATETIME);

        findElement(By.xpath("//button[@data-id = 'user']")).click();

        Select selectUser = new Select(findElement(By.id("user")));
        selectUser.selectByVisibleText(USER);

        By buttonSaveDraft = By.id("pa-entity-form-draft-btn");
        getDriver().findElement(buttonSaveDraft).click();

        By menuList = By.xpath("//a[contains(., 'List')]");
        getDriver().findElement(menuList).click();

        List<WebElement> trs = getDriver().findElements(By.xpath("//div[@ class = 'card-body ']//table/tbody/tr"));
        WebElement iconDraft = getDriver().findElement(By.xpath("//i[@class = 'fa fa-pencil']"));
        List<WebElement> tds = getDriver().findElements(By.xpath(
                "//table[@id = 'pa-all-entities-table']/tbody/tr/td[@class = 'pa-list-table-th']"));

        Assert.assertEquals(trs.size(),1);
        Assert.assertTrue(iconDraft.isDisplayed());
        Assert.assertEquals(getTdsTexts(tds),EXPECTED_VALUES);
    }
}
