import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class EntityReferenceValues1Test extends BaseTest {

    private static final String LABEL = "Label";
    private static final String FILTER_1 = "Area";
    private static final String FILTER_2 = "Season";

    private static final List<String> EXPECTED_VALUES = List.of(LABEL, FILTER_1, FILTER_2);

    @Test
    public void testReferenceValues() {
        
        TestUtils.scrollClick(getDriver(), By.xpath("// p[contains (text(), 'Reference values')] "));

        getDriver().findElement(By.xpath("// i[contains (text(), 'create_new_folder')]")).click();
        getDriver().findElement(By.id("label")).sendKeys(LABEL);
        getDriver().findElement(By.id("filter_1")).sendKeys(FILTER_1);
        getDriver().findElement(By.id("filter_2")).sendKeys(FILTER_2);
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();

        List<WebElement> cells = getDriver().findElements(By.xpath("//td [@class = 'pa-list-table-th']"));
        WebElement firstColumn = getDriver().findElement(By.xpath("// td/i"));

        Assert.assertEquals(cells.stream().map(WebElement::getText).collect(Collectors.toList()), EXPECTED_VALUES);
        Assert.assertEquals(firstColumn.getAttribute("class"), "fa fa-check-square-o");
    }
}
