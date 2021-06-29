import base.BaseTest;
import model.ArithmeticFunctionPage;
import model.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.List;
import static utils.ProjectUtils.*;
import static utils.TestUtils.scrollClick;

public class EntityArithmeticFunction1Test extends BaseTest {

    private static final Integer F1 = 22;
    private static final Integer F2 = 2;
    private static final Integer SUM = F1 + F2;
    private static final Integer SUB = F1 - F2;
    private static final Integer MUL = F1 * F2;
    private static final Integer DIV = F1 / F2;
    private static final By DIV_FIELD = By.id("div");
    private static final List<Integer> expectedList = List.of(F1, F2, SUM, SUB, MUL, DIV);

    public void CreateArithmeticFunction() {
        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickNewButton()
                .fillForm(F1, F2)
                .clickSave();
    }

    @Test
        public void testCreateArithmeticFunctions() {
        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                    .clickArithmeticFunctionMenu();
         for (int i = 0; i < 12; i++) {
                CreateArithmeticFunction();
         }
            Assert.assertEquals(arithmeticFunctionPage.getCells().size(), 10);
            List<WebElement> elementsWeb = getDriver().findElements(By.xpath("//td[@class = 'pa-list-table-th']"));
            for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(elementsWeb.get(i).getText(), expectedList.get(i).toString());
         }
    }

    @Test(dependsOnMethods = "testCreateArithmeticFunctions")
        public void testArithmeticFunctionRecordsListPagination() {
        ArithmeticFunctionPage arithmeticFunctionPage = new MainPage(getDriver())
                .clickArithmeticFunctionMenu()
                .clickPaginationButton(2);

        Assert.assertEquals(arithmeticFunctionPage.getCells().size(), 2);
        arithmeticFunctionPage.clickPaginationButton(1);
        arithmeticFunctionPage.clickSizeButton();
        arithmeticFunctionPage.clickSize25Button();
        Assert.assertEquals(arithmeticFunctionPage.getCells().size(), 12);
    }
}
