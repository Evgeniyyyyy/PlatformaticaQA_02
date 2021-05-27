import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.scrollClick;

public class EntityEventsChain1Test extends BaseTest {
    By eventsChain1Menu = By.xpath("//p[contains(.,'Events Chain 1')]");
    By createNewFolderButton = By.xpath("//i[text() = 'create_new_folder']");
    By f1Field = By.id("f1");
    By f10Field = By.id("f10");
    By saveButton = By.id("pa-entity-form-save-btn");
    By rows = By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr");
    By cells = By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr/td/a");
    By checkBox = By.xpath("//i[@class = 'fa fa-check-square-o']");

    public List<String> getRowValues(List<WebElement> cellsActual) {
        List<String> actualValues = new ArrayList<>();

        for(WebElement cell : cellsActual) {
            actualValues.add(cell.getText());
        }
        return actualValues;
    }

    @Test
    public void testCreateNewRecord() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);

        String f1InputValue = "1";
        List<String> expectedValues = Arrays.asList("1", "2", "4", "8", "16", "32", "64", "128", "256", "512");

        start(getDriver());
        scrollClick(getDriver(), getDriver().findElement(eventsChain1Menu));
        wait.until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(createNewFolderButton)))
                .click();
        getDriver().findElement(f1Field)
                .sendKeys(f1InputValue);
        wait.until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElement(f10Field), "value"));
        getDriver().findElement(saveButton)
                .click();

        List<WebElement> records = getDriver().findElements(rows);
        List<WebElement> cellsActual = getDriver().findElements(cells);

        Assert.assertEquals(records.size(), 1);
        Assert.assertEquals(getRowValues(cellsActual), expectedValues);
        Assert.assertTrue(getDriver().findElement(checkBox).isDisplayed());
    }
}
