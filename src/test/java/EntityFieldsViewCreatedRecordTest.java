import base.BaseTest;
import model.FieldsPage;
import model.MainPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.ProjectUtils.*;

public class EntityFieldsViewCreatedRecordTest extends BaseTest {

    @Test
    public void testViewCreatedRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickCreateRecordButton()
                .fillTitle(getTextRandom(10))
                .clickSave()
                .clickFieldsMenu()
                .clickCreateRecordButton()
                .fillTitle(getTextRandom(10))
                .clickSave()
                .clickFieldsMenu()
                .clickCreateRecordButton()
                .fillTitle("besttextever")
                .clickSave()
                .clickFieldsMenu()
                .clickCreateRecordButton()
                .fillTitle(getTextRandom(10))
                .clickSave()
                .clickFieldsMenu()
                .clickCreateRecordButton()
                .fillTitle(getTextRandom(10))
                .clickSave()
                .clickExactViewRecord();

        boolean res = getDriver().findElements(By.xpath(
                "//*[contains(text(), 'besttextever')]")).size() > 0
                && getDriver().findElements(By.xpath("//*[contains(text(), 'Showing')]")).size() == 0;

        Assert.assertTrue(res);

    }
}