import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.TestUtils.jsClick;

public class EntityImportValues1Test extends BaseTest {
    private void createNewRecord(){
        getDriver().findElement(By.xpath("//div[@class='card-icon']/i[text()='create_new_folder']")).click();

        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("date")).clear();
        getDriver().findElement(By.id("date")).sendKeys("31/05/2021");

        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(By.id("datetime")).clear();
        getDriver().findElement(By.id("datetime")).sendKeys("31/05/2021 20:04:52");

        getDriver().findElement(By.xpath("//button[@data-id='user']")).click();

        Select selectUser = new Select(findElement(By.id("user")));
        selectUser.selectByVisibleText("tester14@tester.test");

        getDriver().findElement(By.id("string")).sendKeys("String");
        getDriver().findElement(By.id("text")).sendKeys("Text");
        getDriver().findElement(By.id("int")).sendKeys("2");
        getDriver().findElement(By.id("decimal")).sendKeys("2.20");

        jsClick(getDriver(),getDriver().findElement(By.id("pa-entity-form-save-btn")));
    }
    private List<String> getActualValues(List<WebElement> actualElements){
        List<String> list = new ArrayList<>();
        for (WebElement element : actualElements) {
            list.add(element.getText());
        }
        return list;
    }
    @Test
    public void testViewNewRecord(){
        final List<String> expectedValues = Arrays.asList(
                "String", "Text", "2", "2.20","31/05/2021", "31/05/2021 20:04:52");
        final String expectedUser = "tester14@tester.test";
        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), getDriver().findElement(
                By.xpath("//div[@id='menu-list-parent']/ul/li/a/p[text()=' Import values ']")));
        createNewRecord();
        getDriver().findElement(
                By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']")).click();
        getDriver().findElement(By.linkText("view")).click();
        List<WebElement> actualElements = getDriver().findElements(By.xpath("//span[@class='pa-view-field']"));
        WebElement actualUser = getDriver().findElement(By.xpath("//div[@class='form-group']/p"));

        Assert.assertEquals(actualElements.size(), expectedValues.size());
        Assert.assertEquals(getActualValues(actualElements), expectedValues);
        Assert.assertEquals(actualUser.getText(), expectedUser);
    }
}



