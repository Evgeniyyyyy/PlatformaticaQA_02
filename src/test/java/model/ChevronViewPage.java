package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;


public class ChevronViewPage  extends BaseModel{

    @FindBy(xpath = "//span [@class = 'pa-view-field']")
    private List<WebElement> viewModeRecord;

    @FindBy(xpath = "//div[@class = 'form-group']/p")
    private WebElement user;

    public ChevronViewPage(WebDriver driver) {
        super(driver);
    }

    public ChevronPage.ChevronPageDto getData() {

        List<String> viewModeValues = new ArrayList<>();
        for (WebElement element : viewModeRecord) {
            viewModeValues.add(element.getText());
        }

        ChevronPage.ChevronPageDto row = new ChevronPage.ChevronPageDto();

        row.stringDropDownValue = viewModeValues.get(0);
        row.formTextValue = viewModeValues.get(1);
        row.formIntValue = viewModeValues.get(2);
        row.formDecimalValue = viewModeValues.get(3);
        row.dateValue = viewModeValues.get(4);
        row.dateTimeValue = viewModeValues.get(5);
        row.userValue = getDriver().findElement(By.xpath("//div[@class = 'form-group']/p")).getText();

        return row;
    }
}
