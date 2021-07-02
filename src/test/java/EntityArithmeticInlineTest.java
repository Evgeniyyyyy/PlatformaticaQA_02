import base.BaseTest;
import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

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

    @Test
    public void testCreateRecord() {
        ArithmeticInlinePage arithmeticInlinePage = new MainPage(getDriver())
                .clickArithmeticInlineMenu()
                .clickNewButton()
                .fillForm(F1, F2)
                .clickSave();

        Assert.assertEquals(arithmeticInlinePage.getRowCount(), 1);
        Assert.assertEquals(arithmeticInlinePage.getRow(0), EXPECTED_LIST);
        Assert.assertEquals(arithmeticInlinePage.getClassIcon(), CLASS_ICON_SAVE);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        ArithmeticInlineViewPage arithmeticInlineViewPage = new MainPage(getDriver())
                .clickArithmeticInlineMenu()
                .clickViewButton(0);

        List<String> ACTUAL_LIST = arithmeticInlineViewPage.getRecordInViewMode();

        Assert.assertEquals(ACTUAL_LIST.size(), EXPECTED_LIST.size());
        for (int i = 0; i < EXPECTED_LIST.size(); i++) {
            Assert.assertEquals(ACTUAL_LIST.get(i), EXPECTED_LIST.get(i));
        }
    }
}
