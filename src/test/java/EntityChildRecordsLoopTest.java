import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import utils.ProjectUtils;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.TestUtils.scrollClick;

public class EntityChildRecordsLoopTest extends BaseTest {

    private static final String URL = "https://ref2.eteam.work/";

    private final double startBalanceValue = 100;
    private final double cardAmountValue = 200;
    private final double expectedEndBalance = startBalanceValue + cardAmountValue;
    private final String cardItemValue = "book";

    private void createChildRecordsLoopCard() {
        WebElement childRecordsLoop = findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        scrollClick(getDriver(), childRecordsLoop);

        WebElement newChildRecLoopFolder = findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        newChildRecLoopFolder.click();

        WebElement startBalance = findElement(By.xpath("//input[@id='start_balance']"));
        startBalance.sendKeys(String.valueOf(startBalanceValue));

        WebElement newChildRecLoopRecord = findElement(By.xpath("//tbody/tr[@id='add-row-68']/td[1]/button[1]"));
        newChildRecLoopRecord.click();

        WebElement amount = findElement(By.xpath("//textarea[@id='t-68-r-1-amount']"));
        amount.clear();
        amount.sendKeys(String.valueOf(cardAmountValue));

        WebElement item = findElement(By.xpath("//textarea[@id='t-68-r-1-item']"));
        item.sendKeys(cardItemValue);

        getWait().until(ExpectedConditions.attributeToBe(By.xpath("//input[@id='end_balance']"),
                "value", String.valueOf((int) expectedEndBalance)));

        WebElement saveButton = findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveButton.click();
    }

    @Test
    public void testCreateChildRecordsLoopCard() {
        getDriver().get(URL);

        ProjectUtils.login(getDriver());

        createChildRecordsLoopCard();

        List<WebElement> columnList = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        assertTrue(columnList.size() > 0);

        double startBalanceAmount = Double.parseDouble(columnList.get(0).findElement(By.tagName("a")).getText());
        double endBalanceAmount = Double.parseDouble(columnList.get(1).findElement(By.tagName("a")).getText());

        assertEquals(startBalanceAmount, startBalanceValue);
        assertEquals(endBalanceAmount, expectedEndBalance);

    }

    @Test
    public void testViewChildRecordsLoopCard() {
        getDriver().get(URL);

        ProjectUtils.login(getDriver());

        createChildRecordsLoopCard();

        WebElement childRecordsLoop = findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        scrollClick(getDriver(), childRecordsLoop);

        List<WebElement> columnList = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        assertTrue(columnList.size() > 0);

        int numberOfCards = columnList.size() / 2;

        WebElement targetRowDiv = findElement(By.xpath("//tbody/tr[" + numberOfCards + "]/td[4]/div[1]"));

        WebElement lastCardDropdownMenu = targetRowDiv.findElement(By.xpath("button[1]"));
        lastCardDropdownMenu.click();

        WebElement viewEntity = targetRowDiv.findElement(By.xpath("ul/li/a[text() = 'view']"));
        getWait().until(ExpectedConditions.visibilityOf(viewEntity));
        viewEntity.click();

        List<WebElement> balances = findElements(By.className("pa-view-field"));
        assertEquals(balances.get(0).getText(), String.format("%.2f", startBalanceValue));
        assertEquals(balances.get(1).getText(), String.format("%.2f", expectedEndBalance));
        assertEquals(findElement(By.xpath("//tbody/tr[1]/td[2]")).getText().trim(),
                String.format("%.2f", cardAmountValue));
        assertEquals(findElement(By.xpath("//tbody/tr[1]/td[3]")).getText().trim(), cardItemValue);
    }
}
