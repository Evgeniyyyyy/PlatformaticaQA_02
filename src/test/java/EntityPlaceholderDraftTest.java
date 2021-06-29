import base.BaseTest;
import model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.*;
import java.util.stream.Collectors;
import static utils.ProjectUtils.*;
import static utils.ProjectUtils.getRandom;

public class EntityPlaceholderDraftTest extends BaseTest {

    private static final String STRING_VALUE = getTextRandom(17);
    private static final String STRING_NEW_VALUE = getTextRandom(17);
    private static final String TEXT_VALUE = getTextRandom(20);
    private static final String TEXT_NEW_VALUE = getTextRandom(20);
    private static final String INT_VALUE = getRandomIntValue();
    private static final String INT_NEW_VALUE = getRandomIntValue();
    private static final String DECIMAL_VALUE = getRandomDecimalValue();
    private static final String DATE_VALUE = getDate(getRandom(2555000));
    private static final String DATETIME_VALUE = getDateTime(getRandom(2555000));
    private static final String USER_NAME = getUser();
    private static final String PENCIL_ICON_CLASS = "fa fa-pencil";
    private static final String EMPTY_RECYCLE_BIN_MESSAGE = "Good job with housekeeping! Recycle bin is currently empty!";

    private static final List<String> EXPECTED_RESULT_EDIT = Arrays.asList(STRING_NEW_VALUE, TEXT_NEW_VALUE, INT_NEW_VALUE, DECIMAL_VALUE, DATE_VALUE, DATETIME_VALUE, "", "", USER_NAME);
    private static final List<String> EXPECTED_RESULT_VIEW_PAGE = List
            .of(STRING_NEW_VALUE, TEXT_NEW_VALUE, INT_NEW_VALUE, DECIMAL_VALUE, DATE_VALUE, DATETIME_VALUE);
    private static final List<String> EXPECTED_RESULTS_CREATE = List
        .of(STRING_VALUE, TEXT_VALUE, INT_VALUE, DECIMAL_VALUE, DATE_VALUE, DATETIME_VALUE, "", "", USER_NAME);

    private static String getRandomDecimalValue() {
        return RandomUtils.nextInt(0, 10000) + "." + RandomStringUtils.randomNumeric(2);
    }

    private static String getRandomIntValue() {
        return String.valueOf(RandomUtils.nextInt(0, 100000));
    }

    private List<String> getListOfValues(List<WebElement> columnList) {
        return columnList.stream().map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Test
    public void testCreateNewPlaceholderDraftRecord() {

        PlaceholderDifPage placeholderDifPage = new MainPage(getDriver())
                .clickPlaceholderDifMenu()
                .clickNewButton()
                .fillString(STRING_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .fillDate(DATE_VALUE)
                .fillDateTime(DATETIME_VALUE)
                .fillUserField(USER_NAME)
                .clickSaveDraft();

        Assert.assertEquals(placeholderDifPage.getRowCount(), 1);
        Assert.assertEquals(placeholderDifPage.getRow(0), EXPECTED_RESULTS_CREATE);
        Assert.assertEquals(placeholderDifPage.getClassIcon(), PENCIL_ICON_CLASS);

        List<WebElement> columnList = placeholderDifPage.getPlaceholderTableColumns();
        Assert.assertEquals(columnList.size(), EXPECTED_RESULTS_CREATE.size());
        Assert.assertEquals(getListOfValues(columnList), EXPECTED_RESULTS_CREATE);
    }

    @Test(dependsOnMethods = "testCreateNewPlaceholderDraftRecord")
    public void testEditPlaceholderDraftRecord() {

        PlaceholderDifPage placeholderDifPage = new MainPage(getDriver())
                .clickPlaceholderDifMenu()
                .clickButtonList()
                .clickActionsMenu()
                .clickButtonEdit()
                .fillNewString(STRING_NEW_VALUE)
                .fillNewText(TEXT_NEW_VALUE)
                .fillNewInt(INT_NEW_VALUE)
                .clickSaveDraft();

        List<WebElement> columnEditList = placeholderDifPage.getPlaceholderTableColumns();
        Assert.assertEquals(columnEditList.size(), EXPECTED_RESULT_EDIT.size());
        Assert.assertEquals(getListOfValues(columnEditList), EXPECTED_RESULT_EDIT);
    }

    @Test(dependsOnMethods = "testEditPlaceholderDraftRecord")
    public void testViewDraftRecord() {

        PlaceholderDifViewPage placeholderDifViewPage = new MainPage(getDriver())
                .clickPlaceholderDifMenu()
                .clickButtonList()
                .clickActionsMenu()
                .clickButtonView();

        WebElement userName = placeholderDifViewPage.getUserName();
        Assert.assertEquals(userName.getText(), USER_NAME);

        List<WebElement> resultListView = placeholderDifViewPage.getViewResultList();
        Assert.assertEquals(getListOfValues(resultListView), EXPECTED_RESULT_VIEW_PAGE);
        Assert.assertEquals(placeholderDifViewPage.getUserName().getText(), USER_NAME);
    }

    @Test(dependsOnMethods = "testViewDraftRecord")
    public void testDeletePlaceholderDraftRecord() {

        PlaceholderDifPage placeholderDifPage = new PlaceholderDifPage(getDriver())
                .deletePlaceholderRecord();

        Assert.assertEquals(placeholderDifPage.getRowCount(), 0);
        Assert.assertEquals(placeholderDifPage.getRecycleBinCountNotification(), "1");
    }

    @Test(dependsOnMethods = "testDeletePlaceholderDraftRecord")
    public void testRestorePlaceholderDraftRecord() throws InterruptedException {

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin();
        Assert.assertEquals(recycleBinPage.getTextPaginationInfo(), "Showing 1 to 1 of 1 rows");

        recycleBinPage.clickDeletedRestoreAsDraft();
        Assert.assertEquals(recycleBinPage.getTextCardBody(), EMPTY_RECYCLE_BIN_MESSAGE);

        PlaceholderDifPage placeholderDifPage = new PlaceholderDifPage(getDriver())
                .clickPlaceholderDifMenu();

        Assert.assertEquals(placeholderDifPage.getClassIcon(), PENCIL_ICON_CLASS);

        List<WebElement> columnList = placeholderDifPage.getPlaceholderTableColumns();
        Assert.assertEquals(columnList.size(), EXPECTED_RESULT_EDIT.size());
        Assert.assertEquals(getListOfValues(columnList), EXPECTED_RESULT_EDIT);
    }

    @Test(dependsOnMethods = "testRestorePlaceholderDraftRecord")
    public void testDeleteRecycleBinDraftRecord() {

        new PlaceholderDifPage(getDriver())
                .deletePlaceholderRecord();

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickPlaceholderDifMenu()
                .clickIconRecycleBin();

        Assert.assertEquals(recycleBinPage.getTextPaginationInfo(), "Showing 1 to 1 of 1 rows");
        Assert.assertEquals(recycleBinPage.getRowCount(), 1);

        recycleBinPage.clickDeletedRecordPermanently();
        Assert.assertEquals(recycleBinPage.getTextCardBody(), EMPTY_RECYCLE_BIN_MESSAGE);
    }

    @Test
    public void testCancelCreatePlaceholderAction() {

        PlaceholderDifEditPage placeholderDifEditPage = new MainPage(getDriver())
                .clickPlaceholderDifMenu()
                .clickNewButton()
                .fillString(STRING_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .fillDate(DATE_VALUE)
                .fillDateTime(DATETIME_VALUE)
                .fillUserField(USER_NAME)
                .clickCancel();

        PlaceholderDifPage placeholderDifPage = new PlaceholderDifPage(getDriver());
        Assert.assertTrue(placeholderDifPage.isTableEmpty());
    }
}

