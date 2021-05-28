import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

import java.util.List;

public class EntityArithmeticFunctionTest extends BaseTest {

    @Test
    public void testRecordSaveDraft(){

        final Integer F1 = 10;
        final Integer F2 = 2;
        final Integer DIV = F1 / F2;
        final List<Integer> expectedList = List.of(F1, F2, F1 + F2, F1 - F2, F1 * F2, DIV);

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(),
                findElement(By.xpath("//p[contains(text(),'Arithmetic Function')]")));

        findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();

        findElement(By.xpath("//input[@id='f1']")).sendKeys(F1.toString());
        findElement(By.xpath("//input[@id='f2']")).sendKeys(F2.toString());

        getWait().until(ExpectedConditions.attributeToBe(
                By.xpath("//input[@id='div']"), "value", DIV.toString()));

        findElement(By.xpath("//button[@id='pa-entity-form-draft-btn']")).click();

        WebElement icon = findElement(By.xpath("//tbody/tr/td[1]/i"));
        List<WebElement> columnList = findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));

        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(columnList.size(), expectedList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedList.get(i).toString());
        }
    }

    private void fillForm(String inputField1, String inputField2) {
        WebElement f1 = findElement(By.xpath("//input[@data-field_name='f1']"));
        WebElement f2 = findElement(By.xpath("//input[@data-field_name='f2']"));
        f1.clear();
        f1.sendKeys(inputField1);
        f2.clear();
        f2.sendKeys(inputField2);

        Integer DIV = Integer.parseInt(inputField1) / Integer.parseInt(inputField2);
        getWait().until(ExpectedConditions.attributeToBe(
                By.xpath("//input[@id='div']"), "value", DIV.toString()));

        findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();
    }

    private void createRecord() {
        TestUtils.scrollClick(getDriver(),
                By.xpath("//p[text()=' Arithmetic Function ']/parent::a/parent::li"));
        findElement(By.xpath("//i[text()='create_new_folder']")).click();

        fillForm("20", "10");
    }
    @Ignore
    @Test
    public void testEditRecord() {
        final Integer F1 = 40;
        final Integer F2 = 10;
        final List<Integer> expectedList = List.of(F1, F2, F1 + F2, F1 - F2, F1 * F2, F1 / F2);

        ProjectUtils.start(getDriver());

        createRecord();

        findElement(By.xpath("//div[@class='dropdown pull-left']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='edit']"))).click();
        fillForm(F1.toString(), F2.toString());

        List<WebElement> columnList = findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));

        Assert.assertEquals(columnList.size(), expectedList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedList.get(i).toString());
        }
    }
}
