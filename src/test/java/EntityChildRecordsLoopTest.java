import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.List;

public class EntityChildRecordsLoopTest extends BaseTest {

    private final double startBalanceValue = 100;
    private final double cardAmountValue = 200;
    private final double expectedEndBalance = startBalanceValue + cardAmountValue;
    private final String cardItemValue = "book";
    private final double editCardAmountValue = 500;
    private final double expectedEditEndBalanceValue = startBalanceValue + editCardAmountValue;

    private void scrollToChildRecordsLoopCard() {
        WebElement childRecordsLoop = findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        TestUtils.scrollClick(getDriver(), childRecordsLoop);
    }

    private void createChildRecordsLoopCard() {
        scrollToChildRecordsLoopCard();

        WebElement newChildRecLoopFolder = findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        newChildRecLoopFolder.click();

        WebElement startBalance = findElement(By.xpath("//input[@id='start_balance']"));
        startBalance.sendKeys(String.valueOf(startBalanceValue));

        WebElement newChildRecLoopRecord = findElement(By.xpath("//tbody/tr[@id='add-row-68']/td[1]/button[1]"));
        newChildRecLoopRecord.click();

        WebElement amount = findElement(By.xpath("//textarea[@id='t-68-r-1-amount']"));
        getWait().until(ExpectedConditions.visibilityOf(amount));
        amount.clear();
        sendKeysOneByOne(amount, String.valueOf(cardAmountValue));

        WebElement item = findElement(By.xpath("//textarea[@id='t-68-r-1-item']"));
        item.sendKeys(cardItemValue);

        getWait().until(ExpectedConditions.attributeToBe(By.xpath("//input[@id='end_balance']"),
                "value", String.valueOf((int) expectedEndBalance)));

        WebElement saveButton = findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveButton.click();
    }

    private void sendKeysOneByOne(WebElement element, String input) {
        char[] editKeys = input.toCharArray();
        for (char c : editKeys) {
            element.sendKeys(String.valueOf(c));
        }
    }

    @Test
    public void testCreateChildRecordsLoopCard() {
        ProjectUtils.start(getDriver());

        createChildRecordsLoopCard();

        List<WebElement> columnList = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        Assert.assertTrue(columnList.size() > 0);

        double startBalanceAmount = Double.parseDouble(columnList.get(columnList.size() - 2).findElement(By.tagName("a")).getText());
        double endBalanceAmount = Double.parseDouble(columnList.get(columnList.size() - 1).findElement(By.tagName("a")).getText());

        Assert.assertEquals(startBalanceAmount, startBalanceValue);
        Assert.assertEquals(endBalanceAmount, expectedEndBalance);

    }

    @Test
    public void testViewChildRecordsLoopCard() {
        ProjectUtils.start(getDriver());

        createChildRecordsLoopCard();

        WebElement childRecordsLoop = findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        TestUtils.scrollClick(getDriver(), childRecordsLoop);

        List<WebElement> columnList = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        Assert.assertTrue(columnList.size() > 0);

        int numberOfCards = columnList.size() / 2;

        WebElement targetRowDiv = findElement(By.xpath("//tbody/tr[" + numberOfCards + "]/td[4]/div[1]"));

        WebElement lastCardDropdownMenu = targetRowDiv.findElement(By.xpath("button[1]"));
        getWait().until(ExpectedConditions.visibilityOf(lastCardDropdownMenu));
        lastCardDropdownMenu.click();

        WebElement viewEntity = targetRowDiv.findElement(By.xpath("ul/li/a[text() = 'view']"));
        getWait().until(ExpectedConditions.visibilityOf(viewEntity));
        viewEntity.click();

        List<WebElement> balances = findElements(By.className("pa-view-field"));
        Assert.assertEquals(balances.get(0).getText(), String.format("%.2f", startBalanceValue));
        Assert.assertEquals(balances.get(1).getText(), String.format("%.2f", expectedEndBalance));
        Assert.assertEquals(findElement(By.xpath("//tbody/tr[1]/td[2]")).getText().trim(),
                String.format("%.2f", cardAmountValue));
        Assert.assertEquals(findElement(By.xpath("//tbody/tr[1]/td[3]")).getText().trim(), cardItemValue);
    }

    @Test
    public void testEditChildRecordsLoopCard() {
        ProjectUtils.start(getDriver());

        createChildRecordsLoopCard();

        WebElement childRecordsLoop = findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        TestUtils.scrollClick(getDriver(), childRecordsLoop);

        List<WebElement> columnList = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        Assert.assertTrue(columnList.size() > 0);

        int numberOfCards = columnList.size() / 2;

        WebElement targetRowDiv = findElement(By.xpath("//tbody/tr[" + numberOfCards + "]/td[4]/div[1]"));

        WebElement lastCardDropdownMenu = targetRowDiv.findElement(By.xpath("button[1]"));
        getWait().until(ExpectedConditions.visibilityOf(lastCardDropdownMenu));
        lastCardDropdownMenu.click();

        WebElement editEntity = targetRowDiv.findElement(By.xpath("ul/li/a[text() = 'edit']"));
        getWait().until(ExpectedConditions.elementToBeClickable(editEntity));
        TestUtils.jsClick(getDriver(), editEntity);

        WebElement amount = findElement(By.xpath("//textarea[@id='t-68-r-1-amount']"));
        getWait().until(ExpectedConditions.visibilityOf(amount));
        amount.clear();
        sendKeysOneByOne(amount, String.valueOf(editCardAmountValue));

        getWait().until(ExpectedConditions.attributeToBe(By.xpath("//input[@id='end_balance']"),
                "value", String.valueOf((int) expectedEditEndBalanceValue)));

        WebElement saveButton = findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveButton.click();

        columnList = findElements(By.className("pa-list-table-th"));
        getWait().until(ExpectedConditions.visibilityOf(columnList.get(columnList.size() - 1)));
        Assert.assertTrue(columnList.size() > 0);

        double startBalanceAmount = Double.parseDouble(columnList.get(columnList.size() - 2).findElement(By.tagName("a")).getText());
        double endBalanceAmount = Double.parseDouble(columnList.get(columnList.size() - 1).findElement(By.tagName("a")).getText());

        Assert.assertEquals(startBalanceAmount, startBalanceValue);
        Assert.assertEquals(endBalanceAmount, expectedEditEndBalanceValue);
    }

    @Test
    public void testDeleteChildRecordsLoopCard() {
        ProjectUtils.start(getDriver());

        createChildRecordsLoopCard();

        WebElement childRecordsLoop = findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        TestUtils.scrollClick(getDriver(), childRecordsLoop);

        List<WebElement> columnList = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        Assert.assertTrue(columnList.size() > 0);

        int numberOfCards = columnList.size() / 2;

        double startBalanceToBeDeleted = Double.parseDouble(columnList.get(columnList.size() - 2).findElement(By.tagName("a")).getText());
        double endBalanceToBeDeleted = Double.parseDouble(columnList.get(columnList.size() - 1).findElement(By.tagName("a")).getText());

        Assert.assertEquals(startBalanceValue, startBalanceToBeDeleted);
        Assert.assertEquals(expectedEndBalance, endBalanceToBeDeleted);

        WebElement targetRowDiv = findElement(By.xpath("//tbody/tr[" + numberOfCards + "]/td[4]/div[1]"));

        WebElement lastCardDropdownMenu = targetRowDiv.findElement(By.xpath("button[1]"));
        lastCardDropdownMenu.click();

        WebElement deleteEntity = targetRowDiv.findElement(By.xpath("ul/li/a[text() = 'delete']"));
        getWait().until(ExpectedConditions.elementToBeClickable(deleteEntity));
        TestUtils.jsClick(getDriver(), deleteEntity);

        getWait().until(ExpectedConditions.visibilityOf(findElement(By.className("notification"))));

        WebElement recycleBinIcon = findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        recycleBinIcon.click();

        WebElement deletedContentLink = findElement(By.className("pa-recycle-col")).findElement(By.tagName("a"));
        getWait().until(ExpectedConditions.elementToBeClickable(deletedContentLink));
        TestUtils.jsClick(getDriver(), deletedContentLink);

        String startBalanceXpath = String.format("//span[text() =  %.2f]", startBalanceValue);
        WebElement deletedStartBalance = findElement(By.xpath(startBalanceXpath));
        String endBalanceXpath = String.format("//span[text() = %.2f]", expectedEndBalance);
        WebElement deletedEndBalance = findElement(By.xpath(endBalanceXpath));

        Assert.assertTrue(deletedStartBalance.isDisplayed());
        Assert.assertTrue(deletedEndBalance.isDisplayed());
    }

    @Test
    public void testRestoreChildRecordsLoopCard(){
        ProjectUtils.start(getDriver());

        createChildRecordsLoopCard();

        WebElement childRecordsLoop = findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        TestUtils.scrollClick(getDriver(), childRecordsLoop);

        List<WebElement> columnList = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        Assert.assertTrue(columnList.size() > 0);

        int numberOfCards = columnList.size() / 2;

        double startBalanceToBeDeleted = Double.parseDouble(columnList.get(columnList.size() - 2).findElement(By.tagName("a")).getText());
        double endBalanceToBeDeleted = Double.parseDouble(columnList.get(columnList.size() - 1).findElement(By.tagName("a")).getText());

        Assert.assertEquals(startBalanceValue, startBalanceToBeDeleted);
        Assert.assertEquals(expectedEndBalance, endBalanceToBeDeleted);

        WebElement targetRowDiv = findElement(By.xpath("//tbody/tr[" + numberOfCards + "]/td[4]/div[1]"));

        WebElement lastCardDropdownMenu = targetRowDiv.findElement(By.xpath("button[1]"));
        lastCardDropdownMenu.click();

        WebElement deleteEntity = targetRowDiv.findElement(By.xpath("ul/li/a[text() = 'delete']"));
        getWait().until(ExpectedConditions.elementToBeClickable(deleteEntity));
        TestUtils.jsClick(getDriver(), deleteEntity);

        getWait().until(ExpectedConditions.visibilityOf(findElement(By.className("notification"))));

        WebElement recycleBinIcon = findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        recycleBinIcon.click();

        WebElement restoreAsADraftEntity = findElement(By.xpath("//a[contains(text(),'restore as draft')]"));
        TestUtils.jsClick(getDriver(), restoreAsADraftEntity);

        WebElement recycleBinMessage = findElement(By.xpath(" //div[contains(text(),'Good job with housekeeping! Recycle bin is current')]"));
        recycleBinMessage.isDisplayed();

        scrollToChildRecordsLoopCard();

        WebElement checkIcon = findElement(By.xpath("//tbody/tr/td/i[@class = 'fa fa-pencil']"));
        Assert.assertTrue(checkIcon.isDisplayed());
    }
}
