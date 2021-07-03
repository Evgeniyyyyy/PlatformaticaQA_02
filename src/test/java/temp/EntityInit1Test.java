package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityInit1Test extends BaseTest {

    private static final List<String> EXPECTED_VALUES = List.of("New String", "New Text", "2", "3.14", "01/01/2020",
            "31/12/2020 23:59:59", "apptester1@tester.test", "", "Two");

    @Test
    public void testCreateRecord() {
        TestUtils.scrollClick(getDriver(), By.xpath("//a[@class='nav-link']/p[text()=' Init ']"));
        ProjectUtils.clickCreateRecord(getDriver());
        TestUtils.scrollClick(getDriver(), By.id("pa-entity-form-save-btn"));

        List<WebElement> values = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        List<String> actualValues = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            actualValues.add(values.get(i).getText());
        }

        Assert.assertEquals(actualValues, EXPECTED_VALUES);
    }
}
