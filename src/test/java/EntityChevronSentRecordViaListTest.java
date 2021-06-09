import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.ProjectUtils.clickSave;

public class EntityChevronSentRecordViaListTest extends BaseTest {

    private static final String ENTERED_DATA = "firstExample";

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
}
