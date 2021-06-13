import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import static utils.ProjectUtils.*;
import utils.TestUtils;
import constants.EntityArithmeticInlineConstants;
import utils.ProjectUtils;
import java.util.List;

public class EntityArithmeticInlineTest extends BaseTest {

    public static final Integer F1 = 10;
    public static final Integer F2 = 2;
    public static final Integer SUM = F1 + F2;
    public static final Integer SUB = F1 - F2;
    public static final Integer MUL = F1 * F2;
    public static final Integer DIV = F1 / F2;
    public static final List<Integer> expectedList = List.of(F1, F2, SUM, SUB, MUL, DIV);

    private void fillForm(String inputField1, String inputField2) {
        findElement(By.xpath("//input[@id='f1']")).sendKeys(inputField1);
        findElement(By.xpath("//input[@id='f2']")).sendKeys(inputField2);
        getWait().until(ExpectedConditions.attributeToBe(
                By.xpath("//input[@id='div']"),
                "value",
                DIV.toString()));
        clickSave(getDriver());
    }

    private void createRecord() {
        findElement(EntityArithmeticInlineConstants.ADD_CARD).click();
        fillForm(F1.toString(), F2.toString());
    }

    private void viewAction() {
        WebElement button_action = findElement(EntityArithmeticInlineConstants.ACTION_BUTTON);
        button_action.click();
        WebElement view_action = findElement(EntityArithmeticInlineConstants.ACTION_VIEW);
        view_action.click();
    }

    private void viewActionClose() {
        WebElement button_close_window = findElement(EntityArithmeticInlineConstants.VIEW_WINDOW_CLOSE);
        button_close_window.click();
    }
    @Ignore
    @Test
    public void testViewRecord() {

        start(getDriver());

        TestUtils.scrollClick(getDriver(),
                findElement(EntityArithmeticInlineConstants.LINK_ENTITY));

        clickCreateRecord(getDriver());
        fillForm(F1.toString(), F2.toString());
        viewAction();

        getWait().until(ExpectedConditions.presenceOfElementLocated(EntityArithmeticInlineConstants.ACTION_VIEW_TITLE));
        List<WebElement> actualList = findElements(EntityArithmeticInlineConstants.RESULT_LIST);

        Assert.assertEquals(actualList.size(), expectedList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(actualList.get(i).getText(), expectedList.get(i).toString());
        }

        viewActionClose();
    }

    @Test //test by PRoman-86
    public void testCreateAndSaveNewRecord() throws InterruptedException {

        ProjectUtils.start(getDriver());

        TestUtils.jsClick(getDriver(), findElement(EntityArithmeticInlineConstants.LINK_ENTITY));
        TestUtils.jsClick(getDriver(), findElement(EntityArithmeticInlineConstants.ADD_CARD));
        findElement(By.xpath("//input[@id='f1']")).sendKeys(Integer.toString(F1));
        findElement(By.xpath("//input[@id='f2']")).sendKeys(Integer.toString(F2));

        getWait().until(ExpectedConditions.textToBePresentInElementValue(By.xpath("//input[@id='div']"), String.valueOf(DIV)));
        getWait().until(ExpectedConditions.textToBePresentInElementValue(By.xpath("//input[@id='mul']"), String.valueOf(MUL)));
        getWait().until(ExpectedConditions.textToBePresentInElementValue(By.xpath("//input[@id='sub']"), String.valueOf(SUB)));
        getWait().until(ExpectedConditions.textToBePresentInElementValue(By.xpath("//input[@id='sum']"), String.valueOf(SUM)));

        TestUtils.scrollClick(getDriver(), findElement(EntityArithmeticInlineConstants.BUTTON_SAVE));
        TestUtils.jsClick(getDriver(), findElement(EntityArithmeticInlineConstants.ACTION_BUTTON));
        TestUtils.jsClick(getDriver(), findElement(EntityArithmeticInlineConstants.ACTION_VIEW));

        Assert.assertEquals(findElement(By.xpath("//div[3]//div[1]")).getText(), String.valueOf(SUM));
        Assert.assertEquals(findElement(By.xpath("//div[4]//div[1]")).getText(), String.valueOf(SUB));
        Assert.assertEquals(findElement(By.xpath("//div[5]//div[1]")).getText(), String.valueOf(MUL));
        Assert.assertEquals(findElement(By.xpath("//div[6]//div[1]")).getText(), String.valueOf(DIV));
    }
}