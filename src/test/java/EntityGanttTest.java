import base.BaseTest;
import model.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static utils.ProjectUtils.*;

public class EntityGanttTest extends BaseTest {
    private static final By CHECK_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By ACTUAL_RESULT = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");

    private static final String STRING_INPUT_VALUE = "Test";
    private static final String TEXT_INPUT_VALUE = "Text";
    private static final String INT_INPUT_VALUE = "100";
    private static final String DECIMAL_INPUT_VALUE = "0.10";
    private static final String EMPTY_FIELD = "";
    private static final String USER_NAME = "tester100@tester.test";
    private static final String DATA_VALUE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    private static final String DATA_TIME_VALUE = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    private static final String STRING_INPUT_VALUE2 = "Test2";
    private static final String TEXT_INPUT_VALUE2 = "Text2";
    private static final String INT_INPUT_VALUE2 = "200";
    private static final String DECIMAL_INPUT_VALUE2 = "0.20";

    private static final List<Object> NEW_EXPECTED_RESULT = Arrays
            .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
                    DATA_VALUE, DATA_TIME_VALUE, EMPTY_FIELD, USER_NAME);

    private static final List<Object> NEW_EXPECTED_RESULT2 = Arrays
            .asList(STRING_INPUT_VALUE2, TEXT_INPUT_VALUE2, INT_INPUT_VALUE2, DECIMAL_INPUT_VALUE2,
                    DATA_VALUE, DATA_TIME_VALUE, EMPTY_FIELD, USER_NAME);

    private void clickListButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=35\"]"))))
                .click();
    }

    @Test
    public void testCreateRecord() {
        GanttListPage ganttListPage = new MainPage(getDriver())
                .clickGanttMenu()
                .clickCreateRecordButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATA_VALUE)
                .fillDateTime(DATA_TIME_VALUE)
                .findUser(USER_NAME)
                .clickSave()
                .clickListButton();

        Assert.assertEquals(ganttListPage.getRowCount(), 1);
        Assert.assertEquals(ganttListPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(ganttListPage.getIcon(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {
        GanttViewPage ganttViewPage = new MainPage(getDriver())
                .clickGanttMenu()
                .clickListButton()
                .clickActions()
                .clickActionsView();

        Assert.assertEquals(ganttViewPage.getRecordInViewMode(), NEW_EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditRecord() {
        GanttListPage ganttListPage = new MainPage(getDriver())
                .clickGanttMenu()
                .clickListButton()
                .clickActions()
                .clickActionsEdit()
                .clearFields()
                .fillString(STRING_INPUT_VALUE2)
                .fillText(TEXT_INPUT_VALUE2)
                .fillInt(INT_INPUT_VALUE2)
                .fillDecimal(DECIMAL_INPUT_VALUE2)
                .clickSave()
                .clickListButton();

        Assert.assertEquals(ganttListPage.getRowCount(), 1);
        Assert.assertEquals(ganttListPage.getRow(0), NEW_EXPECTED_RESULT2);
        Assert.assertEquals(ganttListPage.getIcon(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {
        getEntity(getDriver(), "Gantt");
        clickListButton();
        clickActionsDelete(getWait(), getDriver());

        String textCardBodyAfterDelete = getDriver().findElement(By.xpath("//div[@class = 'card-body ']")).getText();
        Assert.assertTrue(textCardBodyAfterDelete.isEmpty());

        clickRecycleBin(getDriver());

        findElement(By.xpath("//tbody/tr/td/a")).click();

        List<Object> expectedRecord2 = Arrays.asList(STRING_INPUT_VALUE2, TEXT_INPUT_VALUE2, INT_INPUT_VALUE2,
                DECIMAL_INPUT_VALUE2, DATA_VALUE, DATA_TIME_VALUE);

        Assert.assertEquals(getActualValues(findElements(By.cssSelector("span.pa-view-field"))), expectedRecord2);
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreRecord(){

        getEntity(getDriver(), "Gantt");
        clickRecycleBin(getDriver());
        findElement(By.linkText("restore as draft")).click();

        Assert.assertEquals(findElement(By.className("card-body")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");

        getEntity(getDriver(), "Gantt");
        clickListButton();

        WebElement icon2 = findElement(CHECK_ICON);
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), NEW_EXPECTED_RESULT2);
    }

    @Test
    public void testCreateDraftRecord() {

        GanttListPage ganttListPage = new MainPage(getDriver())
                .clickGanttMenu()
                .clickCreateRecordButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATA_VALUE)
                .fillDateTime(DATA_TIME_VALUE)
                .findUser(USER_NAME)
                .clickSaveDraft()
                .clickListButton();

        Assert.assertEquals(ganttListPage.getRowCount(), 1);
        Assert.assertEquals(ganttListPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(ganttListPage.getIcon(), "fa fa-pencil");
    }
}
