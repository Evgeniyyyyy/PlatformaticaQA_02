import base.BaseTest;
import constants.EntityBoardConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import static utils.ProjectUtils.start;

public class InputDataAndSaveTest extends BaseTest {

    private void openMenuAndClick() {

        WebElement boardElement = getDriver().findElement((EntityBoardConstants.LINK_BOARD_ENTITY));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(boardElement).click(boardElement).build().perform();
    }

    private void addCard() {
        WebElement card = getDriver().findElement((EntityBoardConstants.BOARD_ADD_CARD));
        card.click();
    }

    @Test
    public void testInputDataAndSave()    {
        start(getDriver());
        openMenuAndClick();
        addCard();

        WebElement textField = getDriver().findElement((By.id("text")));
        textField.sendKeys("any Text");
        WebElement intField = getDriver().findElement((By.id("int")));
        intField.sendKeys("222");
        WebElement decimalField = getDriver().findElement((By.id("decimal")));
        decimalField.sendKeys("0.2");
        WebElement dateField = getDriver().findElement((By.id("date")));
        dateField.click();
        WebElement dateTime = getDriver().findElement((By.id("datetime")));
        dateTime.click();
        WebElement userField = getDriver().findElement(By.xpath("//*[@id='_field_container-user']/div[1]"));
        userField.click();

        List<WebElement> allProductNameElement = getDriver().findElements(By.cssSelector(("[class='dropdown-item']")));
        String userEmail = "apptester3@tester.test";
        for (WebElement ele : allProductNameElement) {
            String name = ele.getText();
            if (name.equalsIgnoreCase(userEmail)) {
                ele.click();
            } else {
                System.out.println("userEmail couldn't find");
            }
        }
        WebElement clickSaveButton = getDriver().findElement(By.id("pa-entity-form-save-btn"));
        clickSaveButton.click();
        WebElement pending = getDriver().findElement(By.cssSelector(" main > div > div:nth-child(2)"));
        Assert.assertTrue(pending.getText().toLowerCase().contains(userEmail.toLowerCase()), "New pending task was created");
    }
}
