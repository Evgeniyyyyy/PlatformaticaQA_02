import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityCalendarDeleteDraftRecordPermanentlyTest extends BaseTest {

    String newEventTitle = "Calendar Event";
    String newEventDescription = "Event description.";
    String intNumbers = "28";
    String decimalNumbers = "20.21";

    private void navigateToCalendarPage() {
        TestUtils.scrollClick(getDriver(), By.xpath("//p[contains (text(), 'Calendar')]"));

    }

    private void navigateToRecycleBin() {
        WebElement recycleBin = findElement(By.xpath("//a/i[contains (text(), 'delete_outline')]"));
        recycleBin.click();
    }

    private void createDraftCalendarRecord() {
        WebElement inputFieldString = findElement(By.xpath("//input[@id='string']"));
        inputFieldString.clear();
        inputFieldString.sendKeys(newEventTitle);

        WebElement inputFieldText = findElement(By.xpath("//textarea[@id='text']"));
        inputFieldText.clear();
        inputFieldText.sendKeys(newEventDescription);

        findElement(By.xpath("//input[@id='int']")).sendKeys(intNumbers);
        findElement(By.xpath("//input[@id='decimal']")).sendKeys(decimalNumbers);

        WebElement inputFieldDate = findElement(By.xpath("//input[@id='date']"));
        inputFieldDate.click();

        WebElement inputFieldDateTime = findElement(By.xpath("//input[@id='datetime']"));
        inputFieldDateTime.click();

        By fieldUser = By.xpath("//button[@data-id = 'user']");
        getDriver().findElement(fieldUser).click();

        Actions actions = new Actions(getDriver());
        WebElement userchoice = getDriver().findElement(By.xpath("//div[@class= \"inner show\"]"));
        actions.moveToElement(userchoice);

        TestUtils.scroll(getDriver(), getDriver().findElement(
                By.xpath("//ul [@class = 'dropdown-menu inner show']/li/a/span[ text() = 'tester18@tester.test']")));

        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul [@class = 'dropdown-menu inner show']/li/a/span[ text() = 'tester18@tester.test']")))
                .click();

        WebElement saveDraftButton = findElement(By.id("pa-entity-form-draft-btn"));
        saveDraftButton.click();
    }

    private void deleteDraftCalendarRecord() {

        WebElement buttonActions = findElement(By.xpath("//button[@class = \"btn btn-round btn-sm btn-primary dropdown-toggle\"]"));
        buttonActions.click();

        WebElement buttonDraftToBeDeleted = getWait().until(ExpectedConditions.elementToBeClickable(findElement(By.xpath("//a[contains (text(), \"delete\")]"))));
        buttonDraftToBeDeleted.click();
    }

    @Ignore
    @Test
    public void EntityCalendarDeleteDraftTest() {

        ProjectUtils.start(getDriver());

        navigateToCalendarPage();
        findElement(By.xpath("//div[@class='card-icon']")).click();

        createDraftCalendarRecord();

        findElement(By.xpath("//a [ @href = \"index.php?action=action_list&list_type=table&entity_id=32\"]")).click();

        deleteDraftCalendarRecord();

        navigateToRecycleBin();

        WebElement draftToBeDeletedFromRecycleBin = getWait().until(ExpectedConditions
                .elementToBeClickable(findElement(By.xpath("//a[contains (text(), \"delete permanently\")]"))));

        draftToBeDeletedFromRecycleBin.click();

        WebElement contentRecycleBinCard = findElement(By.xpath("//div[@class = 'card-body']"));
        String actualTextAfterDraftDeleted  = contentRecycleBinCard.getText();

        Assert.assertEquals(actualTextAfterDraftDeleted, "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
