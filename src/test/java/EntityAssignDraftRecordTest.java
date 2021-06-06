import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static utils.ProjectUtils.clickSaveDraft;
import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityAssignDraftRecordTest extends BaseTest {

    @Test
    public void testSaveDraftRecord() {

        final String stringInput = "Hello";
        final String textInput = "everyone";
        final String intInput = "555";
        final String decimalInput = "55.55";
        final String dataInput = "03/06/2021";
        final String dataTimeInput = "12/06/2021 09:09:09";
        final String empty = "";
        final String userDefault = "apptester1@tester.test";

        final List<String> expectedValues = Arrays.asList(
                stringInput, textInput, intInput, decimalInput, dataInput, dataTimeInput, empty, userDefault);

        start(getDriver());
        scrollClick(getDriver(), By.xpath("//p[contains(text(),' Assign ')]"));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("string")).sendKeys(stringInput);
        getDriver().findElement(By.id("text")).sendKeys(textInput);
        getDriver().findElement(By.id("int")).sendKeys(intInput);
        getDriver().findElement(By.id("decimal")).sendKeys(decimalInput);

        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("date")).clear();
        getDriver().findElement(By.id("date")).sendKeys(dataInput);

        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(By.id("datetime")).clear();
        getDriver().findElement(By.id("datetime")).sendKeys(dataTimeInput);

        clickSaveDraft(getDriver());

        List<WebElement> cells = getDriver().findElements(By.xpath("//td[@class= 'pa-list-table-th']"));
        List<String> actualValues = new ArrayList<>();

        for (WebElement cell : cells) {
            actualValues.add(cell.getText());
        }

        Assert.assertEquals(cells.size(), expectedValues.size());
        Assert.assertEquals(actualValues, expectedValues);
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//tbody/tr/td[1]/i")).getAttribute("class"), "fa fa-pencil");
    }
}
