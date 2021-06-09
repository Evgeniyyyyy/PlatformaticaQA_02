import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.ProjectUtils.*;
import static utils.TestUtils.*;

public class EntityTagAssignTest extends BaseTest {

    private static final String NEW_TAG = "new tag";
    private static final By ADDITIONAL_TAG = By.xpath("//div[@class = 'd-flex pa-list-tags']/div[2]");

    public void createRecord(){

        start(getDriver());

        jsClick(getDriver(), findElement(By.xpath("//p[text()=' Tag ']")));

        clickCreateRecord(getDriver());

        findElement(By.id("string")).sendKeys("Hello world");
        findElement(By.id("text")).sendKeys("Be healthy");
        findElement(By.id("int")).sendKeys("123");
        findElement(By.id("decimal")).sendKeys("456.98");

        clickSave(getDriver());
    }

    public void createTag(){
        findElement(By.xpath("//li[@class = 'nav-item'][3]")).click();
        findElement(By.xpath("//input[@name = 'new_tag']")).sendKeys(NEW_TAG);
        findElement(By.xpath("//button[contains(text(), 'New tag')]")).click();
    }

    public void addTagToRecord(){
        findElement(By.xpath("//div/div/label")).click();
        findElement(By.xpath("//tbody/tr/td[1]")).click();
    }

    public void clickOnAssignTags(){
        findElement(By.xpath("//button[contains(text(), 'Assign tags')]")).click();
    }

    @Test
    public void testTagAssignToRecord(){

        createRecord();

        createTag();
        Assert.assertEquals(
                findElement(By.xpath("//div[@class = 'd-flex pa-list-tags']/div[1]"))
                        .getText()
                        , NEW_TAG);

        addTagToRecord();
        clickOnAssignTags();
        Assert.assertEquals(
                findElement(By.xpath("//td[@class = 'pa-tag-td']"))
                        .getText()
                        , NEW_TAG);

        createTag();
        Assert.assertEquals(findElement(ADDITIONAL_TAG).getText(), NEW_TAG);

        addTagToRecord();
        findElement(ADDITIONAL_TAG).click();
        clickOnAssignTags();
        Assert.assertEquals(findElement(
                By.xpath("//td[@class = 'pa-tag-td']/span[2]"))
                .getText()
                , NEW_TAG);
    }
}