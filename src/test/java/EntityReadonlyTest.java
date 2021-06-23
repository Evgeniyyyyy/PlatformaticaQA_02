import base.BaseTest;
import model.MainPage;
import model.ReadonlyPage;
import model.ReadonlyViewPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EntityReadonlyTest extends BaseTest {

    private final List<String> EXPECTED_VALUES = List.of("", "", "0", "0.00", "", "");

    private String getAttributeClass() {
        return getDriver().findElement(By.xpath("//tbody/tr/td[1]/i")).getAttribute("class");
    }

    @Test
    public void testCancelRecord() {

        ReadonlyPage readonlyPage = new MainPage(getDriver())
                .clickReadonlyMenu()
                .clickNewButton()
                .clickCancel();

        Assert.assertTrue(readonlyPage.isTableEmpty());
    }
  
    @Test
    public void testCreateRecord() {

        ReadonlyPage readonlyPage = new MainPage(getDriver())
                .clickReadonlyMenu()
                .clickNewButton()
                .fillString("String")
                .fillText("Text")
                .fillInt("1000")
                .fillDecimal("20.55")
                .clickSave();

        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
        Assert.assertEquals(readonlyPage.getRow(0), EXPECTED_VALUES);
    }

    @Test
    public void testCreateDraftRecord() {

        ReadonlyPage readonlyPage = new MainPage(getDriver())
                .clickReadonlyMenu()
                .clickNewButton()
                .fillString("")
                .fillText("")
                .fillInt("")
                .fillDecimal("")
                .clickSaveDraft();

        Assert.assertEquals(getAttributeClass(), "fa fa-pencil");
        Assert.assertEquals(readonlyPage.getRow(0), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testViewDraftRecord() {

        ReadonlyViewPage readonlyViewPage = new MainPage(getDriver())
                .clickReadonlyMenu()
                .clickRecord();

        Assert.assertEquals(readonlyViewPage.getRecordInViewMode(), EXPECTED_VALUES);
    }
}
