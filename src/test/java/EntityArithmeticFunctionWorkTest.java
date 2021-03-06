import base.BaseTest;
import model.ArithmeticFunctionPage;
import model.ArithmeticFunctionViewPage;
import model.MainPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


public class EntityArithmeticFunctionWorkTest extends BaseTest {
    private static final Integer F1 = 10;
    private static final Integer F2 = 2;
    private static final Integer SUM = F1 + F2;
    private static final Integer SUB = F1 - F2;
    private static final Integer MUL = F1 * F2;
    private static final Integer DIV = F1 / F2;

    List<Integer> CREATE_DATA = Arrays.asList(F1, F2, SUM, SUB, MUL, DIV);
    String textEmptyBin = "Good job with housekeeping! Recycle bin is currently empty!";
    String iconDraft = "fa fa-pencil";
    String iconSave = "fa fa-check-square-o";

    @Test
    public void testCreateNewRecord (){

        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickCreateRecordButton()
                .fillForm(F1, F2)
                .clickSave();

        Assert.assertEquals(arithmeticFunctionPage.getRowCount(),1);
        Assert.assertTrue(arithmeticFunctionPage.iconCheck(iconSave));
        Assert.assertEquals(arithmeticFunctionPage.getRow(0), arithmeticFunctionPage.wrapValues(CREATE_DATA));
    }

    @Test(dependsOnMethods = "testSaveDraftNewRecord")
    public void testVerifyFunctionalWork () {
        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu();

        Assert.assertEquals(arithmeticFunctionPage.getRow(0).get(2), SUM.toString());
        Assert.assertEquals(arithmeticFunctionPage.getRow(0).get(3), SUB.toString());
        Assert.assertEquals(arithmeticFunctionPage.getRow(0).get(4), MUL.toString());
        Assert.assertEquals(arithmeticFunctionPage.getRow(0).get(5), DIV.toString());
    }

    @Test
    public void testSaveDraftNewRecord (){
        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickCreateRecordButton()
                .fillForm(F1, F2)
                .clickSaveDraft();

        Assert.assertEquals(arithmeticFunctionPage.getRowCount(),1);
        Assert.assertEquals(arithmeticFunctionPage.getRow(0), arithmeticFunctionPage.wrapValues(CREATE_DATA));
        Assert.assertTrue(arithmeticFunctionPage.iconCheck(iconDraft));
    }

    @Test
    public void testCancelNewRecord (){
        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickCreateRecordButton()
                .fillForm(F1, F2)
                .clickCancel();

        Assert.assertEquals(arithmeticFunctionPage.getRowCount(),0);
    }

    @Test(dependsOnMethods = "testCreateNewRecord")
    public void testDeleteRecord() {
        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickDeleteButton();


        Assert.assertEquals(arithmeticFunctionPage.getRowCount(), 0);
        Assert.assertEquals(arithmeticFunctionPage.getNoticeRecycleBin(), "1");
    }

    @Test(dependsOnMethods = "testSaveDraftNewRecord")
    public void testViewRecord() {
        ArithmeticFunctionViewPage arithmeticFunctionViewPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickViewButton(0);

        Assert.assertEquals(arithmeticFunctionViewPage.getRecordInViewMode(), arithmeticFunctionViewPage.wrapValues(CREATE_DATA));
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testDeleteRecycleBinRecord() {
        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickRecycleBinIcon()
                .clickDeletePermanently();


        Assert.assertEquals(arithmeticFunctionPage.getTextEmptyBin(), textEmptyBin);
    }
}
