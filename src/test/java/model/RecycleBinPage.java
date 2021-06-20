package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RecycleBinPage extends MainPage{

    @FindBy(xpath = "//td[@class='pa-recycle-col']/a")
    private WebElement deletedRecord;

    @FindBy(linkText = "delete permanently")
    private WebElement deletedRecordPermanently;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//a[contains(text(), 'restore as draft')]")
    private WebElement restoreAsDraft;

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

    public String getTextCardBody(){

        return table.getText();
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

    public ParentPage clickRestoreAsDraftForParent(){
        getWait().until(ExpectedConditions.elementToBeClickable(restoreAsDraft));
        restoreAsDraft.click();

        return new ParentPage(getDriver());
    }

}
