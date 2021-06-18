import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.stream.Collectors;

public class EntityArithmeticFunctionTest extends BaseTest {

    private static final By F1_FIELD = By.id("f1");
    private static final By F2_FIELD = By.id("f2");
    private static final By DIV_FIELD = By.id("div");

    private void sendKeysSlow(WebElement element, String text) {
        if (!element.getAttribute("value").isEmpty()) {
            element.clear();
        }
        String newValue = "";

        for (char ch: text.toCharArray()) {
            element.sendKeys(String.valueOf(ch));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            newValue += ch;
            getWait().until(ExpectedConditions.attributeToBe(element, "value", newValue));
        }
    }

    private void fillForm(Integer inputField1, Integer inputField2) {
        WebElement f1 = findElement(F1_FIELD);
        WebElement f2 = findElement(F2_FIELD);
        String expectedValueDIV = String.valueOf(inputField1/inputField2);

        sendKeysSlow(f1, inputField1.toString());
        sendKeysSlow(f2, inputField2.toString());

        getWait().until(ExpectedConditions.attributeToBe(DIV_FIELD, "value", expectedValueDIV));
    }

    @Test
    public void testCreateRecord(){
        final Integer F1 = 20;
        final Integer F2 = 5;
        final List<Integer> expectedValues = List.of(F1, F2, F1 + F2, F1 - F2, F1 * F2, F1 / F2);

        ProjectUtils.getEntity(getDriver(),"Arithmetic Function");
        ProjectUtils.clickCreateRecord(getDriver());

        fillForm(F1, F2);
        ProjectUtils.clickSave(getDriver());

        List<WebElement> actualValues = findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));

        Assert.assertTrue(getDriver().findElement(By.xpath("//tbody/tr/td[1]/i")).getAttribute("class").contains("fa-check-square-o"));
        Assert.assertEquals(
                actualValues.stream().map(WebElement::getText).collect(Collectors.toList()),
                expectedValues.stream().map(String::valueOf).collect(Collectors.toList()));
    }

    @Test
    public void testRecordSaveDraft(){
        final Integer F1 = 10;
        final Integer F2 = 2;
        final Integer DIV = F1 / F2;
        final List<Integer> expectedValues = List.of(F1, F2, F1 + F2, F1 - F2, F1 * F2, DIV);

        ProjectUtils.getEntity(getDriver(),"Arithmetic Function");
        ProjectUtils.clickCreateRecord(getDriver());

        fillForm(F1, F2);
        ProjectUtils.clickSaveDraft(getDriver());

        List<WebElement> actualValues = findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));

        Assert.assertTrue(getDriver().findElement(By.xpath("//tbody/tr/td[1]/i")).getAttribute("class").contains("fa fa-pencil"));
        Assert.assertEquals(
                actualValues.stream().map(WebElement::getText).collect(Collectors.toList()),
                expectedValues.stream().map(String::valueOf).collect(Collectors.toList()));
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testEditRecord() {
        final Integer F1 = 40;
        final Integer F2 = 10;
        final List<Integer> expectedValues = List.of(F1, F2, F1 + F2, F1 - F2, F1 * F2, F1 / F2);

        ProjectUtils.getEntity(getDriver(),"Arithmetic Function");
        ProjectUtils.clickActionsEdit(getWait(), getDriver());

        Assert.assertNotNull(findElement(F1_FIELD).getAttribute("value"));
        Assert.assertNotNull(findElement(F2_FIELD).getAttribute("value"));

        fillForm(F1, F2);
        ProjectUtils.clickSave(getDriver());

        List<WebElement> actualValues = findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));

        Assert.assertEquals(
                actualValues.stream().map(WebElement::getText).collect(Collectors.toList()),
                expectedValues.stream().map(String::valueOf).collect(Collectors.toList()));
    }
}
