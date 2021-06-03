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
}