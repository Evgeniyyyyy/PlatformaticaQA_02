import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityReferenceValues1Test extends BaseTest {
    @Test
    public void testReferenceValues() {

        List<String> expectedValues = List.of("Label", "Area", "Season");

        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), By.xpath("// p[contains (text(), 'Reference values')] "));
        getDriver().findElement(By.xpath("// i[contains (text(), 'create_new_folder')]")).click();
        getDriver().findElement(By.id("label")).sendKeys("Label");
        getDriver().findElement(By.id("filter_1")).sendKeys("Area");
        getDriver().findElement(By.id("filter_2")).sendKeys("Season");
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//span [@class = 'pagination-info']")).getText(), "Showing 1 to 1 of 1 rows");

        List<WebElement> cells = getDriver().findElements(By.xpath("//td [@class = 'pa-list-table-th']"));

        List<String> actualResult = new ArrayList<>();

        for (WebElement cell : cells) {
            actualResult.add(cell.getText());
        }

        Assert.assertEquals(actualResult, expectedValues);

        Assert.assertEquals((getDriver().findElement(
                By.xpath("// td/i")).getAttribute("class")), "fa fa-check-square-o");
    }
}
