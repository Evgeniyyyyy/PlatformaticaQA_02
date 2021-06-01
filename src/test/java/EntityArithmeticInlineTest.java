import base.BaseTest;
import constants.EntityAssignConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import constants.EntityArithmeticInlineConstants;

import java.util.List;

public class EntityArithmeticInlineTest extends BaseTest {

    private void fillForm(String inputField1, String inputField2) {
        findElement(By.xpath("//input[@id='f1']")).sendKeys(inputField1);
        findElement(By.xpath("//input[@id='f2']")).sendKeys(inputField2);
        getWait().until(ExpectedConditions.attributeToBe(
                By.xpath("//input[@id='div']"),
                "value",
                EntityArithmeticInlineConstants.DIV.toString()));
        findElement(EntityArithmeticInlineConstants.ARITHMETIC_BUTTON_SAVE).click();
    }

    private void createRecord() {
        findElement(EntityArithmeticInlineConstants.ARITHMETIC_ADD_CARD).click();
        fillForm(EntityArithmeticInlineConstants.F1.toString(), EntityArithmeticInlineConstants.F2.toString());
    }

    private void viewAction() {
        WebElement button_action = findElement(EntityArithmeticInlineConstants.ARITHMETIC_ACTION_BUTTON);
        button_action.click();
        WebElement view_action = findElement(EntityArithmeticInlineConstants.ARITHMETIC_ACTION_VIEW);
        view_action.click();
    }

    private void viewActionClose() {
        WebElement button_close_window = findElement(EntityArithmeticInlineConstants.ARITHMETIC_VIEW_WINDOW_CLOSE);
        button_close_window.click();
    }

    @Test
    public void testViewRecord() {

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(),
                findElement(EntityArithmeticInlineConstants.LINK_ARITHMETIC_ENTITY));

        createRecord();
        viewAction();

        getWait().until(ExpectedConditions.presenceOfElementLocated(EntityArithmeticInlineConstants.ARITHMETIC_ACTION_VIEW_TITLE));
        List<WebElement> actualList = findElements(EntityArithmeticInlineConstants.ARITHMETIC_RESULT_LIST);
        List<Integer> expectedList = EntityArithmeticInlineConstants.expectedList;

        Assert.assertEquals(actualList.size(), expectedList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(actualList.get(i).getText(), expectedList.get(i).toString());
        }

        viewActionClose();
    }
}