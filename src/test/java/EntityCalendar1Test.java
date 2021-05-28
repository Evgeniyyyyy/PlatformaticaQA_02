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
import java.util.Arrays;
import java.util.List;
public class EntityCalendar1Test extends BaseTest {

    private List<String> getTdsTexts (List<WebElement> trsActual) {
        List<String> actualValues = new ArrayList<>();

        for(WebElement tr: trsActual) {
            actualValues.add(tr.getText());
        }
        return actualValues;
    }

    @Ignore
    @Test
    public void testCreateDraftRecord(){

        List<String> expectedValues = Arrays.asList(
                "String", "text", "52", "25.52", "27/05/2021", "27/05/2021 10:40:45", "", "tester29@tester.test");

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(), getDriver().findElement(
                By.xpath("//div[@ id = 'menu-list-parent']/ul/li/a/p[text() = ' Calendar ']")));

        By createNewRecord =By.xpath("//i[text() = 'create_new_folder']");
        getDriver().findElement(createNewRecord).click();

        By fieldString = By.id("string");
        getDriver().findElement(fieldString).sendKeys("String");

        By fieldText = By.id("text");
        getDriver().findElement(fieldText).sendKeys("text");

        By fieldInt = By.id("int");
        getDriver().findElement(fieldInt).sendKeys("52");

        By fieldDecimal = By.id("decimal");
        getDriver().findElement(fieldDecimal).sendKeys("25.52");

        WebElement fieldDate = getDriver().findElement(By.id("date"));
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys("27/05/2021");

        WebElement fieldDateTime = getDriver().findElement(By.id("datetime"));
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys("27/05/2021 10:40:45");

        By fieldUser = By.xpath("//button[@data-id = 'user']");
        getDriver().findElement(fieldUser).click();

        TestUtils.scroll(getDriver(), getDriver().findElement(
                By.xpath("//ul [@class = 'dropdown-menu inner show']/li/a/span[ text() = 'tester29@tester.test']")));

        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul [@class = 'dropdown-menu inner show']/li/a/span[ text() = 'tester29@tester.test']")))
                .click();

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
        Assert.assertEquals(getTdsTexts(tds),expectedValues);
    }
}
