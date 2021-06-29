import base.BaseTest;
import model.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EntityReadonlyTest extends BaseTest {

    private static final String RECORD_ICON = "fa fa-check-square-o";
    private static final String DRAFT_RECORD_ICON = "fa fa-pencil";

    private final List<String> EXPECTED_VALUES = List.of("", "", "0", "0.00", "", "");

    private String getAttributeClass() {
        return getDriver().findElement(By.xpath("//tbody/tr/td[1]/i")).getAttribute("class");
    }

    private String getRecordInBin(){
        return findElement(By.xpath("//tbody/tr//span")).getText();
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

        Assert.assertEquals(getAttributeClass(), RECORD_ICON);
        Assert.assertEquals(readonlyPage.getRow(0), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testEditRecord() {

        ReadonlyPage readonlyPage = new MainPage(getDriver())
                .clickReadonlyMenu()
                .clickActionEdit()
                .fillString("EditString")
                .fillText("New Text")
                .fillInt("555")
                .fillDecimal("55.55")
                .clickSave();

        Assert.assertEquals(getAttributeClass(), RECORD_ICON);
        Assert.assertEquals(readonlyPage.getRow(0), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {

        ReadonlyPage readonlyPage = new ReadonlyPage(getDriver())
                .clickReadonlyMenu()
                .clickActionDelete();

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin();

        Assert.assertEquals(recycleBinPage.getTextNotificationRowCount(), "1");
        Assert.assertEquals(recycleBinPage.getRowCount(), 1);
        Assert.assertEquals(getRecordInBin(), "Decimal: 0.00");
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreRecord() {

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin()
                .clickDeletedRestoreAsDraft();

        Assert.assertEquals(recycleBinPage.getTextCardBody(),
                "Good job with housekeeping! Recycle bin is currently empty!");

        ReadonlyPage readonlyPage = new ReadonlyPage(getDriver())
                .clickReadonlyMenu();

        Assert.assertEquals(getAttributeClass(), DRAFT_RECORD_ICON);
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

        Assert.assertEquals(getAttributeClass(), DRAFT_RECORD_ICON);
        Assert.assertEquals(readonlyPage.getRow(0), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testViewDraftRecord() {

        ReadonlyPage readonlyPage = new MainPage(getDriver())
                .clickReadonlyMenu()
                .clickActionView();

        Assert.assertEquals(readonlyPage.getRecordInViewMode(), EXPECTED_VALUES);
    }
}
