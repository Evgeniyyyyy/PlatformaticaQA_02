import base.BaseTest;
import model.ArithmeticFunctionPage;
import model.MainPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EntityArithmeticFunctionTest extends BaseTest {

    private static final String PENCIL_ICON = "fa fa-pencil";
    private static final String CHECK_ICON = "fa-check-square-o";

    @Test
    public void testCreateRecord(){

        final Integer F1 = 20;
        final Integer F2 = 5;
        final List<Integer> expectedValues = List.of(F1, F2, F1 + F2, F1 - F2, F1 * F2, F1 / F2);

        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickCreateRecordButton()
                .fillForm(F1, F2)
                .clickSave();

        Assert.assertEquals(arithmeticFunctionPage.getRowCount(), 1);
        Assert.assertTrue(arithmeticFunctionPage.iconCheck(CHECK_ICON));
        Assert.assertEquals(arithmeticFunctionPage.getRow(0), arithmeticFunctionPage.wrapValues(expectedValues));
    }

    @Test
    public void testRecordSaveDraft(){
        final Integer F1 = 10;
        final Integer F2 = 2;
        final List<Integer> expectedValues = List.of(F1, F2, F1 + F2, F1 - F2, F1 * F2, F1 / F2);

        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickCreateRecordButton()
                .fillForm(F1, F2)
                .clickSaveDraft();

        Assert.assertEquals(arithmeticFunctionPage.getRowCount(), 1);
        Assert.assertTrue(arithmeticFunctionPage.iconCheck(PENCIL_ICON));
        Assert.assertEquals(arithmeticFunctionPage.getRow(0), arithmeticFunctionPage.wrapValues(expectedValues));
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testEditRecord() {
        final Integer F1 = 40;
        final Integer F2 = 10;
        final List<Integer> expectedValues = List.of(F1, F2, F1 + F2, F1 - F2, F1 * F2, F1 / F2);

        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickEditButton()
                .fillForm(F1, F2)
                .clickSave();

        Assert.assertEquals(arithmeticFunctionPage.getRowCount(), 1);
        Assert.assertTrue(arithmeticFunctionPage.iconCheck(CHECK_ICON));
        Assert.assertEquals(arithmeticFunctionPage.getRow(0), arithmeticFunctionPage.wrapValues(expectedValues));
    }
}
