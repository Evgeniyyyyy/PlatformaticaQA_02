import base.BaseTest;

import model.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class EntityBoardDraftRecordTest extends BaseTest {

    private static final String STRING_INPUT_PENDING = "Pending";
    private static final String STRING_INPUT_ONTRACK = "On track";
    private static final String USER_NAME = "tester" + new Random().nextInt(299) + "@tester.test";
    private static final String DRAFT_ICON_CLASS_NAME = "fa fa-pencil";
    private static final String MESSAGE_EMPTY_RECYCLE_BIN = "Good job with housekeeping! Recycle bin is currently empty!";

    private static final String TEXT_VALUE_PENDING = getRandomTextValue();
    private static final String INT_VALUE_PENDING = getRandomIntValue();
    private static final String DECIMAL_VALUE_PENDING = getRandomDecimalValue();
    private static final String EDIT_TEXT_VALUE = "QWERTY";
    private static final String EDIT_INT_VALUE = "12345";
    private static final String EDIT_DECIMAL_VALUE = "33.55";

    private static final List<String> EXPECTED_CREATED_PENDING_RECORD = List.of(
            STRING_INPUT_PENDING,
            TEXT_VALUE_PENDING,
            INT_VALUE_PENDING,
            DECIMAL_VALUE_PENDING,
            "", "", "", USER_NAME);

    private static final List<String> EXPECTED_VIEW_PENDING_RECORD = List.of(
            STRING_INPUT_PENDING,
            TEXT_VALUE_PENDING,
            INT_VALUE_PENDING,
            DECIMAL_VALUE_PENDING,
            "", "");

    private static final List<String> EXPECTED_EDITED_RECORD = List.of(
            STRING_INPUT_ONTRACK,
            EDIT_TEXT_VALUE,
            EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE,
            "", "", "", USER_NAME);

    private static final List<String> EXPECTED_EDITED_VIEWMODE_RECORD = List.of(
            STRING_INPUT_ONTRACK,
            EDIT_TEXT_VALUE,
            EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE,
            "", "");

    private static String getRandomTextValue() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    private static String getRandomIntValue() {
        return String.valueOf(RandomUtils.nextInt(0, 100000));
    }

    private static String getRandomDecimalValue() {
        return RandomUtils.nextInt(0, 10000) + "." + RandomStringUtils.randomNumeric(2);
    }

    @Test
    public void testCreateDraftRecord() {

        BoardListPage boardPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickNewButton()
                .fillString(STRING_INPUT_PENDING)
                .fillText(TEXT_VALUE_PENDING)
                .fillInt(INT_VALUE_PENDING)
                .fillDecimal(DECIMAL_VALUE_PENDING)
                .findUser(USER_NAME)
                .clickSaveDraft()
                .clickListButton();

        Assert.assertEquals(boardPage.getRowCount(), 1);
        Assert.assertEquals(boardPage.getIcon(), DRAFT_ICON_CLASS_NAME);
        Assert.assertEquals(boardPage.getRow(0), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testViewDraftRecord() {

        BoardViewPage boardPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickActions()
                .clickActionsView();

        Assert.assertEquals(boardPage.getActualRecordInViewMode(), EXPECTED_VIEW_PENDING_RECORD);
        Assert.assertEquals(boardPage.getActualUserName(), USER_NAME);

    }

    @Test(dependsOnMethods = "testViewDraftRecord")
    public void testEditDraftRecord() {

        BoardListPage boardPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickActions()
                .clickActionsEdit()
                .fillString(STRING_INPUT_ONTRACK)
                .clearText()
                .fillText(EDIT_TEXT_VALUE)
                .clearInt()
                .fillInt(EDIT_INT_VALUE)
                .clearDecimal()
                .fillDecimal(EDIT_DECIMAL_VALUE)
                .clickSaveDraft()
                .clickListButton();

        Assert.assertEquals(boardPage.getRowCount(), 1);
        Assert.assertEquals(boardPage.getRow(0), EXPECTED_EDITED_RECORD);
    }

    @Test(dependsOnMethods = "testEditDraftRecord")
    public void testDeleteDraftRecord() {

        BoardListPage boardPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickActions()
                .clickActionsDelete();

        Assert.assertTrue(boardPage.isTableEmpty());
        Assert.assertEquals(boardPage.getTextNotificationRecycleBin(), "1");

        RecycleBinPage boardRecycleBinPage = new MainPage(getDriver())
                .clickRecycleBin();

        Assert.assertEquals(boardRecycleBinPage.getRowCount(), 1);
    }

    @Test(dependsOnMethods = "testDeleteDraftRecord")
    public void testDeleteRecordFromRecycleBin() {

        BoardViewPage boardPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickRecycleBin()
                .clickDeletedRecord();

        Assert.assertEquals(boardPage.getActualRecordInViewMode(), EXPECTED_EDITED_VIEWMODE_RECORD);
        Assert.assertEquals(boardPage.getActualUserName(), USER_NAME);

        boardPage.closeViewWindow().clickDeletedRecordPermanently();

        Assert.assertEquals(BoardPage.getTextCardBody(), MESSAGE_EMPTY_RECYCLE_BIN);
    }

    @Test
    public void testCancelRecord() {

        BoardPage boardPage = new MainPage(getDriver())
                .clickBoardMenu();

        String textCardBodyBeforeCancel = BoardPage.getTextCardBody();

        boardPage.clickNewButton()
                .fillString(STRING_INPUT_PENDING)
                .fillText(TEXT_VALUE_PENDING)
                .fillInt(INT_VALUE_PENDING)
                .fillDecimal(DECIMAL_VALUE_PENDING)
                .findUser(USER_NAME)
                .clickCancel();

        String textCardBodyAfterCancel = BoardPage.getTextCardBody();
        Assert.assertEquals(textCardBodyAfterCancel, textCardBodyBeforeCancel);
    }
}
