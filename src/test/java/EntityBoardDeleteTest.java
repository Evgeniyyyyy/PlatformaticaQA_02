import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.UUID;
import static utils.ProjectUtils.*;
import static utils.TestUtils.*;

public class EntityBoardDeleteTest extends BaseTest {

    private static final String TEXT_VALUE = UUID.randomUUID().toString();
    private static final String INTEGER_VALUE = "" + (int) Math.random();
    private static final String DECIMAL_VALUE = "" + Math.random();
    private static final String BOARD_BUTTON = "//p[normalize-space() = 'Board']";
    private static final String EMPTY_RECYCLER_BIN = "Good job with housekeeping! Recycle bin is currently empty!";
    private static final By DELETE_RECORD = By.xpath("//*[@id=\"pa-all-entities-table\"]/tbody/tr/td[10]/div/ul/li[3]/a");

    public void recordCreateDelete() {

        start(getDriver());

        scrollClick(getDriver(), findElement(By.xpath(BOARD_BUTTON)));

        findElement(By.xpath("//i[contains(text(), 'create_new_folder')]")).click();

        findElement(By.xpath("//textarea[@name = 'entity_form_data[text]']")).sendKeys(TEXT_VALUE);
        findElement(By.xpath("//input[@name = 'entity_form_data[int]']")).sendKeys(INTEGER_VALUE);
        findElement(By.xpath("//input[@name = 'entity_form_data[decimal]']")).sendKeys(DECIMAL_VALUE);

        findElement(By.xpath("//input[@id = 'datetime']")).click();

        WebElement saveButtonClick = findElement(By.xpath("//button[@id = 'pa-entity-form-save-btn']"));
        scrollClick(getDriver(), saveButtonClick);

        findElement(By.xpath("//a[@href = 'index.php?action=action_list&list_type=table&entity_id=31']")).click();
        findElement(By.xpath("//button[@class = 'btn btn-round btn-sm btn-primary dropdown-toggle']")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(DELETE_RECORD));
        findElement(DELETE_RECORD).click();

        findElement(By.xpath("//a[@href = 'index.php?action=recycle_bin']/i")).click();
    }

    @Ignore
    @Test
    public void testRestoreDeleted () {

        recordCreateDelete();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(), 'restore as draft')]")))
                .click();

        scrollClick(getDriver(), findElement(By.xpath(BOARD_BUTTON)));

        WebElement recordText = findElement(By.xpath("//div[@class= 'kanban-item']/div[2]"));
        Assert.assertTrue(recordText.getText().contains(TEXT_VALUE));
    }

    @Ignore
    @Test
    public void testDeletePermanently () {

        recordCreateDelete();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(), 'delete permanently')]")))
                .click();

        WebElement recyclerBin = getDriver().findElement(By.xpath("//div[@class = 'card-body']"));
        Assert.assertTrue(recyclerBin.getText().contains(EMPTY_RECYCLER_BIN));
    }
}
