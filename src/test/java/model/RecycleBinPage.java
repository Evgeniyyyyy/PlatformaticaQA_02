package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class RecycleBinPage extends MainPage{

    @FindBy(xpath = "//td[@class='pa-recycle-col']/a")
    private WebElement deletedRecord;

    @FindBy(linkText = "delete permanently")
    private WebElement deletedRecordPermanently;

    @FindBy(className = "card-body")
    private static WebElement table;

    @FindBy(xpath = "//a[contains(text(), 'restore as draft')]")
    private WebElement restoreAsDraft;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(className = "pagination-info")
    private static WebElement paginationInfo;

    @FindBy(xpath = "//tr[@data-index='0']/td[2]")
    private static WebElement entityName;

    public RecycleBinPage(WebDriver driver) {
        super(driver);
    }

    public RecycleBinPage clickDeletedRecordPermanently(){
        getWait().until(ExpectedConditions.elementToBeClickable(deletedRecordPermanently));
        deletedRecordPermanently.click();

        return new RecycleBinPage(getDriver());
    }

    public RecycleBinPage clickDeletedRestoreAsDraft(){
        getWait().until(ExpectedConditions.elementToBeClickable(restoreAsDraft));
        restoreAsDraft.click();

        return new RecycleBinPage(getDriver());
    }

    public static String getTextCardBody() {

        return table.getText();
    }

    public static String getTextPaginationInfo() {

        return paginationInfo.getText();
    }

    public static String getEntityName() {
        return entityName.getText();
    }

    public BoardViewPage clickDeletedRecord(){
        getWait().until(ExpectedConditions.elementToBeClickable(deletedRecord));
        deletedRecord.click();

        return new BoardViewPage(getDriver());
    }

    public ParentViewPage clickDeletedRecordForParent(){
        getWait().until(ExpectedConditions.elementToBeClickable(deletedRecord));
        deletedRecord.click();

        return new ParentViewPage(getDriver());
    }

    public ParentPage clickDeletedRecordPermanentlyForParent(){
        getWait().until(ExpectedConditions.elementToBeClickable(deletedRecordPermanently));
        deletedRecordPermanently.click();

        return new ParentPage(getDriver());
    }

    public int getRowCount() {
        return rows.size();
    }

}
