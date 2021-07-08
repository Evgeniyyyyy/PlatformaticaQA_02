package temp;

import base.BaseTest;
import model.BoardBaseEditPage;
import model.BoardListPage;
import model.BoardPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParamsEntityBoardTest extends BaseTest {

    @DataProvider(name = "testData")
    public static Object[][] getData() {

        return new Object[][] {
                {BoardBaseEditPage.FieldString.Pending, BoardBaseEditPage.FieldString.OnTrack},
                {BoardBaseEditPage.FieldString.Done, BoardBaseEditPage.FieldString.Pending}
        };
    }

    @Test(dataProvider = "testData")
    public void testEntityBoard(BoardBaseEditPage.FieldString from, BoardBaseEditPage.FieldString to) {

        getDriver().get("https://ref2.eteam.work/index.php?action=action_list&entity_id=31&mod=2");

        BoardPage boardPage = new BoardPage(getDriver())
                .clickCreateRecordButton()
                .fillString(from)
                .fillText("1")
                .clickSave();

        WebElement pendingRow = getDriver().findElement(By.xpath(String.format("//div[@data-id='%s']/main/div", from.getValue())));
        WebElement onTrackBox = getDriver().findElement(By.xpath(String.format("//div[@data-id='%s']/main", to.getValue())));
        Actions actions = new Actions(getDriver());

        actions
                .dragAndDrop(pendingRow, onTrackBox)
                .build()
                .perform();

        BoardListPage boardListPage = boardPage.clickListButton();

        Assert.assertEquals(boardListPage.getRow(0).get(0), to.getValue());
    }



}
