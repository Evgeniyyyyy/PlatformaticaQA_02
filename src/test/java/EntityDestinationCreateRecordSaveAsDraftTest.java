import base.BaseTest;
import model.ExportDestinationViewPage;
import model.MainPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class EntityDestinationCreateRecordSaveAsDraftTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = "Test";
    private static final String TEXT_INPUT_VALUE = "Text";
    private static final String INT_INPUT_VALUE = "100";
    private static final String DECIMAL_INPUT_VALUE = "0.10";
    private static final String CURRENT_USER = "tester33@tester.test";
    private static final String DATE_INPUT_VALUE = "30/03/1986";
    private static final String DATETIME_INPUT_VALUE = "30/03/1986 00:00:01";

    public static final By TESTER33_USER = By.cssSelector("li:nth-child(237) a:nth-child(1) span:nth-child(2)");

    public static final List<String> EXPECTED_LIST = List.of(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE,
            DECIMAL_INPUT_VALUE, DATE_INPUT_VALUE, DATETIME_INPUT_VALUE, "", CURRENT_USER);

    @Test
    public void testCreateAsDraftRecord() {
        ExportDestinationViewPage exportDestinationViewPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickNewButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATE_INPUT_VALUE)
                .fillDateTime(DATETIME_INPUT_VALUE)
                .fillUser(TESTER33_USER)
                .clickSaveDraft()
                .clickActions()
                .clickActionsView();

        Assert.assertEquals(EXPECTED_LIST, exportDestinationViewPage.getRecordInViewMode());
    }
}