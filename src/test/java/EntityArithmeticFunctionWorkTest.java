import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.valueOf;
import static utils.ProjectUtils.*;


public class EntityArithmeticFunctionWorkTest extends BaseTest {

    private static final By F1_FIELD = By.xpath("//input[@id='f1']");
    private static final By F2_FIELD = By.xpath("//input[@id='f2']");
    private static final By DIV_FIELD = By.id("div");

    List<Integer> CREATE_DATA = Arrays.asList(10, 2, 12, 8, 20, 5);

    private static List<String> convertIntToString (List<Integer> actualElements) {
        List<String> listValues = new ArrayList<>();
        for (Integer element : actualElements) {
            listValues.add(element.toString());
        }
        return listValues;
    }

    @Test
     public void testCreateNewEntity () {
        getEntity(getDriver(),"Arithmetic Function");
        clickCreateRecord(getDriver());
        getDriver().findElement(F1_FIELD).sendKeys(CREATE_DATA.get(0).toString());
        getDriver().findElement(F2_FIELD).sendKeys(CREATE_DATA.get(1).toString());
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElement(DIV_FIELD), "value"));
        clickSave(getDriver());

        List<WebElement> viewFields =getDriver().findElements(By.xpath("//tr[@data-index='0']/td[@class='pa-list-table-th']"));
        Assert.assertEquals(getActualValues(viewFields), convertIntToString(CREATE_DATA));
    }

    @Test(dependsOnMethods = "testCreateNewEntity")
    public void testVerifyFunctionalWork () {
        getEntity(getDriver(),"Arithmetic Function");

        String sum = valueOf(CREATE_DATA.get(0) + CREATE_DATA.get(1));
        String sub = valueOf(CREATE_DATA.get(0) - CREATE_DATA.get(1));
        String mul = valueOf(CREATE_DATA.get(0) * CREATE_DATA.get(1));
        String div = valueOf(CREATE_DATA.get(0) / CREATE_DATA.get(1));

        Assert.assertEquals(getDriver().findElement(By.xpath("//td[4]/a[1]")).getText(), sum);
        Assert.assertEquals(getDriver().findElement(By.xpath("//td[5]/a[1]")).getText(), sub);
        Assert.assertEquals(getDriver().findElement(By.xpath("//td[6]/a[1]")).getText(), mul);
        Assert.assertEquals(getDriver().findElement(By.xpath("//td[7]/a[1]")).getText(), div);
    }

    @Test(dependsOnMethods = "testCreateNewEntity")
    public void testViewArithmeticFunction(){
        getEntity(getDriver(),"Arithmetic Function");
        getDriver().findElement(By.xpath("//a[@class='nav-link active']/i[contains(text(), 'list')]")).click();
        clickActionsView(getWait(),getDriver(),0);
        List<WebElement> viewFields =getDriver().findElements(By.xpath("//span[@class='pa-view-field']"));

        Assert.assertEquals(getActualValues(viewFields), convertIntToString(CREATE_DATA));
    }
}
