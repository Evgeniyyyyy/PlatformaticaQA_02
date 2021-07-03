package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.*;

@Ignore
public class EntityImportValuesTest extends BaseTest {
    By importValuesMenu = By.xpath("//div[@id='menu-list-parent']/ul[@class='nav']//p[.=' Import values ']");
    By createNewFolderButton = By.xpath("//i[@class = 'material-icons' and text() = 'create_new_folder']");
    By stringField = By.id("string");
    By textField = By.id("text");
    By intField = By.id("int");
    By decimalField = By.id("decimal");
    By dateField = By.id("date");
    By dateTimeField = By.id("datetime");
    By myUser = By.xpath("//span[contains (text(),'tester32@tester.test')]");
    By saveButton = By.id("pa-entity-form-save-btn");
    By actionsButton = By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']/i");
    By deleteButton = By.linkText("delete");
    By deletedNotificationSign = By.xpath("//li/a[@class = 'nav-link']/span[@class='notification']/b");
    By rowsElement = By.xpath(" //div[@class='fixed-table-body']//table[@id='pa-all-entities-table']/tbody/tr");
    By recycleBinIcon = By.xpath("//li[@class='nav-item']/a[@href='index.php?action=recycle_bin']/i");
    By rowsElementRecycleBin = By.xpath("//td[@class='pa-recycle-col']/a/span");
    By restoreAsDraft = By.linkText("restore as draft");

    private static List<String> getRowValues(List <WebElement> cellsActual) {
        List<String> actual= new ArrayList<>();
        for (WebElement element: cellsActual ) {
            actual.add(element.getText());
        }
        return actual;
    }
    @Ignore
    @Test
    public void testRestoreDeletedRecord() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        final List<String> expectedValues = List.of
                ("String: String", "Text: Text Test", "Int: 89", "Decimal: 9.08", "Date: 2021-05-28",
                        "Datetime: 2021-05-28 12:22:25", "User: 34");

        start(getDriver());
        scrollClick(getDriver(),getDriver().findElement(importValuesMenu));
        getDriver().findElement(createNewFolderButton).click();
        getDriver().findElement(stringField).sendKeys("String");
        getDriver().findElement(textField).sendKeys("Text Test");
        getDriver().findElement(intField).sendKeys("89");
        getDriver().findElement(decimalField).sendKeys("9.08");
        getDriver().findElement(dateField).click();
        getDriver().findElement(dateField).clear();
        getDriver().findElement(dateField).sendKeys("28/05/2021");
        getDriver().findElement(dateTimeField).click();
        getDriver().findElement(dateTimeField).clear();
        getDriver().findElement(dateTimeField).sendKeys("28/05/2021 12:22:25");
        getDriver().findElement(By.xpath("//div[@class ='filter-option-inner-inner']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(myUser));
        scrollClick(getDriver(), getDriver()
                .findElement(myUser));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        jsClick(getDriver(), getDriver().findElement(saveButton));
        getDriver().findElement(actionsButton).click();
        getDriver().findElement(deleteButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(deletedNotificationSign));
        Boolean notificationDisplay = getDriver().findElement(deletedNotificationSign).isDisplayed();
        Assert.assertTrue(notificationDisplay);

        getDriver().findElement(recycleBinIcon).click();
        List<WebElement> rows2 = getDriver().findElements(rowsElementRecycleBin);

        Assert.assertEquals(getRowValues(rows2), expectedValues );

        getDriver().findElement(restoreAsDraft).click();
        scrollClick(getDriver(),getDriver().findElement(importValuesMenu));
        List<WebElement> rows3 = getDriver().findElements(rowsElement);

        Assert.assertEquals(rows3.size(), 1);

        }
    }

