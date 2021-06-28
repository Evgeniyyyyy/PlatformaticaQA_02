import base.BaseTest;
import model.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;
import constants.EntityArithmeticInlineConstants;
import utils.ProjectUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityArithmeticInlineTest extends BaseTest {

    public static final Integer F1 = 10;
    public static final Integer F2 = 2;
    public static final Integer SUM = F1 + F2;
    public static final Integer SUB = F1 - F2;
    public static final Integer MUL = F1 * F2;
    public static final Integer DIV = F1 / F2;
    public static final List<String> EXPECTED_LIST = List.of(
            F1.toString(),
            F2.toString(),
            SUM.toString(),
            SUB.toString(),
            MUL.toString(),
            DIV.toString());
    private static final String CLASS_ICON_SAVE = "fa fa-check-square-o";
    private static final String CLASS_ICON_SAVE_DRAFT = "fa fa-pencil";
    private static List<String> ACTUAL_LIST = new ArrayList<>();

    @Test
    public void testViewRecord() {
        ArithmeticInlinePage arithmeticInlinePage = new MainPage(getDriver())
                .clickArithmeticInlineMenu()
                .clickNewButton()
                .fillForm(F1, F2)
                .clickSave();

        Assert.assertEquals(ArithmeticInlinePage.getRowCount(), 1);
        Assert.assertEquals(ArithmeticInlinePage.getRow(0), EXPECTED_LIST);
        Assert.assertTrue(ArithmeticInlinePage.iconCheck(CLASS_ICON_SAVE));

        ArithmeticInlineViewPage arithmeticInlineViewPage = new MainPage(getDriver())
                .clickArithmeticInlineMenu()
                .clickActions()
                .clickActionsView();

        ACTUAL_LIST = arithmeticInlineViewPage.getRecordInViewMode();

        Assert.assertEquals(ACTUAL_LIST.size(), EXPECTED_LIST.size());
        for (int i = 0; i < EXPECTED_LIST.size(); i++) {
            Assert.assertEquals(ACTUAL_LIST.get(i), EXPECTED_LIST.get(i).toString());
        }
    }

    @Ignore
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