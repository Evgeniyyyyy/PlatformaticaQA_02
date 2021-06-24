package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static utils.TestUtils.scrollClick;

public class RecycleBinPage extends MainPage{

    @FindBy(xpath = "//td[@class='pa-recycle-col']/a")
    private WebElement deletedRecord;

    @FindBy(linkText = "delete permanently")
    private WebElement deletedRecordPermanently;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//a[contains(text(), 'restore as draft')]")
    private WebElement restoreAsDraft;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(className = "pagination-info")
    private static WebElement paginationInfo;

    @FindBy(xpath = "//tr[@data-index='0']/td[2]")
    private static WebElement entityName;

    @FindBy(xpath = "//a[@href='index.php?action=recycle_bin']")
    private WebElement notificationRecycleBinIcon;

    @FindBy(xpath = "//a/span[@class='notification']")
    private WebElement notificationRowCount;

    @FindBy (xpath="//tr//span[contains(text(),'Label')]/b")
    private List<WebElement> rowsValueByLabelOnly;

    @FindBy (xpath="//td[@class='pa-recycle-col']//span[contains(text(),'Label')]/b")
    private static List<WebElement> tableColumns;

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

    public String getTextCardBody() {
        return table.getText();
    }

    public static String getTextPaginationInfo() {

        return paginationInfo.getText();
    }

    public static String getEntityName() {
        return entityName.getText();
    }

    public <ViewPageType extends BaseViewPage> RecycleBinPage clickDeletedRecord(
            Function<WebDriver, ViewPageType> constructor,
            Consumer<ViewPageType> consumer)
    {
        getWait().until(ExpectedConditions.elementToBeClickable(deletedRecord));
        deletedRecord.click();

        ViewPageType viewPageType = constructor.apply(getDriver());
        consumer.accept(viewPageType);
        viewPageType.closeViewWindow();

        return this;
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
        if(isTableAvailable())
            return rows.size();
        else return 0;
    }

    public String getTextNotificationRecycleBin(){

        return notificationRecycleBinIcon.getText();
    }

    public String getTextNotificationRowCount(){

        return notificationRowCount.getText();
    }
    
    public int getRowsValuesWithFilter(String filterWord)
    {
        if(isTableAvailable())
            return rowsValueByLabelOnly.stream().map(el -> el.getText()).filter(el -> el.contains(filterWord))
                    .collect(Collectors.toList()).size();
        else return 0;
    }

    public RecycleBinPage deleteRecord(String NameOfRecord) {
        scrollClick(getDriver(), By.xpath("//tr[contains(.,'Label') and contains(.,'" +
                NameOfRecord + "')]//a[text()='delete permanently']"));
        return new RecycleBinPage(getDriver());
    }

    public List<String> getRowsValueByLabelOnly()
    {
        if(isTableAvailable())
            return rowsValueByLabelOnly.stream().map(el -> el.getText()).collect(Collectors.toList());
        else return null;
    }
}
