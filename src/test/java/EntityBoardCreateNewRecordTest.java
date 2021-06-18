import base.BaseTest;
import base.LoginUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import static utils.TestUtils.scrollClick;

public class EntityBoardCreateNewRecordTest extends BaseTest {

     private void createFillNewRecordPositive(String BoardString) { //draft for 3 type of record
        //***Test Data***
        Random random=new Random();
        Date date=new Date();
        int minValue=1;//error bug report is filled
        List <String> expectedResult= List.of(BoardString,
                            "Create "+BoardString+" record",
                            String.valueOf(random.nextInt(100)+minValue),
                            String.format("%.2f", random.nextFloat()+minValue),
                            new SimpleDateFormat ("dd/MM/yyyy").format(date),
                            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date),
                            "",
                            LoginUtils.getUserName());

        findElement(By.xpath("//*[contains(text(),'create_new_folder')]")).click();//Create new record

        //choose item from dropbox accordingly with first item of test data
        /**/findElement(By.xpath("//button[@class='dropdown-toggle btn btn-link']")).click();
        /**/getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dropdown-menu show']")));
        /**/findElement(By
                .xpath("//div[@class='dropdown-menu show']//*[text()='"+BoardString+"']")).click();
        //filling form with rest test data
        WebElement Date = findElement(By.id("date"));
        scrollClick(getDriver(), Date);
        Date.clear();
        Date.sendKeys(expectedResult.get(4));
        WebElement dateTime = findElement(By.id("datetime"));
        scrollClick(getDriver(), dateTime);
        dateTime.clear();
        dateTime.sendKeys(expectedResult.get(5));
        findElement(By.id("int")).sendKeys(expectedResult.get(2));
        findElement(By.id("text")).sendKeys(expectedResult.get(1));
        findElement(By.id("decimal")).sendKeys(expectedResult.get(3));
        //choose user from User dropbox
        /**/findElement(By.xpath("//button[@data-id='user']")).click();
        /**/getWait().until(ExpectedConditions.presenceOfElementLocated(
                                              By.xpath("//div[@class='dropdown-menu show']")));
        /**/findElement(By.xpath("//div[@class='dropdown-menu show']//*[text()='"+
                                               expectedResult.get(7)+"']")).click();
        scrollClick(getDriver(), findElement(By.id("pa-entity-form-save-btn")));//save

        //Verify record in the Kanban view (Board view)
        boolean isKanban=findElement(By.xpath("//*[contains(@class,'pa-nav-pills-small')]/li[1]"))
                         .isSelected();
        if(!isKanban)findElement(By.xpath("//*[contains(@class,'pa-nav-pills-small')]/li[1]")).click();
        List <WebElement> ListOfEntities=findElements(By.xpath("//*[@id='kanban']//div[@class='kanban-board']"))
              .stream().filter(el->el.getAttribute("data-id").equals(BoardString)).collect(Collectors.toList());
        Assert.assertEquals(ListOfEntities.size(),1);//check if there is only one created record

        String realResultString=ListOfEntities.get(0).findElement(By.xpath(".//div[2]")).getText().trim();
        String expectedResultString=expectedResult.toString().replace("[", "")
                   .replace("]", "").replaceAll(",","").replaceAll("  "," ");
        Assert.assertEquals(realResultString,expectedResultString);

        //Verify record in the List view (Board view)
        findElement(By.xpath("//*[contains(@class,'pa-nav-pills-small')]/li[2]")).click();
        ListOfEntities = findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr"));
        Assert.assertEquals(ListOfEntities.size(), 1);//check if there is only one created record

        List <String> realResult = findElements(By.xpath("//td[@class='pa-list-table-th']"))
                .stream().map(el->el.getText()).collect(Collectors.toList());//get all list of data for created record
        Assert.assertEquals(realResult,expectedResult);
        Assert.assertTrue(findElement(By.xpath("//table[@id='pa-all-entities-table']//i"))
                    .getAttribute("class").contains("fa fa-check-square-o"));//verify left icon

    }

    private void setUp(){
        ProjectUtils.getEntity(getDriver(),"Board");
        Assert.assertEquals(findElement(By.xpath("//b[contains(text(),'Board')]")).getText(),
                "Board");
   }

    @Test()
    public void testCreateNewPendingRecordPositive()
    {
        /*Create new Pending record with all valid parameters
        Validation on Kanban and List view and delete new record*/
        setUp();
        createFillNewRecordPositive("Pending");
    }

    @Test()
    public void testCreateNewOnTrackRecordPositive()
    {
        /*Create new OnTrack record with all valid parameters
        Validation on Kanban and List view and delete new record*/
        setUp();
        createFillNewRecordPositive("On track");
    }

    @Test()
    public void testCreateNewDoneRecordPositive()
    {
        /*Create new Done record with all valid parameters
        Validation on Kanban and List view and delete new record*/
        setUp();
        createFillNewRecordPositive("Done");
    }
}
