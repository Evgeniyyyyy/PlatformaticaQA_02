import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ProjectUtils;

import java.util.List;

import static utils.TestUtils.scrollClick;

 public class EntityChildRecordsLoopTest extends BaseTest {

     final static String URL = "https://ref2.eteam.work/";

     @Test
    public void testCreateChildRecordsLoopCard()  {
        final double startBalanceValue = 100;
        final double cardAmountValue = 200;
        final double expectedEndBalance = startBalanceValue + cardAmountValue;
        final String cardItemValue = "book";

        getDriver().get(URL);

        ProjectUtils.login(getDriver());

        WebElement childRecordsLoop = getDriver().findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        scrollClick(getDriver(), childRecordsLoop);

        WebElement newChildRecLoopFolder = getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        newChildRecLoopFolder.click();

        WebElement startBalance = getDriver().findElement(By.xpath("//input[@id='start_balance']"));
        startBalance.sendKeys(String.valueOf(startBalanceValue));

        WebElement newChildRecLoopRecord = getDriver().findElement(By.xpath("//tbody/tr[@id='add-row-68']/td[1]/button[1]"));
        newChildRecLoopRecord.click();

        WebElement amount = getDriver().findElement(By.xpath("//textarea[@id='t-68-r-1-amount']"));
        amount.clear();
        amount.sendKeys(String.valueOf(cardAmountValue));

        WebElement item = getDriver().findElement(By.xpath("//textarea[@id='t-68-r-1-item']"));
        item.sendKeys(cardItemValue);

        getWait().until(ExpectedConditions.attributeToBe(By.xpath("//input[@id='end_balance']"),
                "value", String.valueOf((int) expectedEndBalance)));

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveButton.click();

        List<WebElement> columnList = getDriver().findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        Assert.assertTrue(columnList.size() > 0);

        double startBalanceAmount = Double.parseDouble(columnList.get(0).findElement(By.tagName("a")).getText());
        double endBalanceAmount = Double.parseDouble(columnList.get(1).findElement(By.tagName("a")).getText());

        Assert.assertEquals(startBalanceAmount, startBalanceValue);
        Assert.assertEquals(endBalanceAmount, expectedEndBalance);

    }

    @Test
    public void testViewChildRecordsLoopCard() {
        final double startBalanceValue = 100;
        final double cardAmountValue = 200;
        final double expectedEndBalance = startBalanceValue + cardAmountValue;
        final String cardItemValue = "book";

        getDriver().get(URL);

        ProjectUtils.login(getDriver());

        WebElement childRecordsLoop = getDriver().findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        scrollClick(getDriver(), childRecordsLoop);

        List<WebElement> columnList = getDriver().findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        Assert.assertTrue(columnList.size() > 0);

        int numberOfCards = columnList.size() / 2;

        WebElement targetRowDiv = getDriver().findElement(By.xpath("//tbody/tr[" + numberOfCards + "]/td[4]/div[1]"));

        WebElement lastCardDropdownMenu = targetRowDiv.findElement(By.xpath("button[1]"));
        lastCardDropdownMenu.click();

        WebElement viewEntity = targetRowDiv.findElement(By.xpath("ul/li/a[text() = 'view']"));
        getWait().until(ExpectedConditions.visibilityOf(viewEntity));
        viewEntity.click();

        List<WebElement> balances = getDriver().findElements(By.className("pa-view-field"));
        Assert.assertEquals(
                balances.get(0).getText(),
                String.format("%.2f", startBalanceValue) // "100.00"
        );
        Assert.assertEquals(
                balances.get(1).getText(),
                String.format("%.2f", expectedEndBalance) // "300.00"
        );
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//tbody/tr[1]/td[2]")).getText().trim(),
                String.format("%.2f", cardAmountValue) // "200.00"
        );
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//tbody/tr[1]/td[3]")).getText().trim(),
                cardItemValue // "book"
        );
    }
}
