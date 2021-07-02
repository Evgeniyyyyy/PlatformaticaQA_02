package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ArithmeticInlineViewPage extends BaseViewPage<ArithmeticInlinePage> {

    public ArithmeticInlineViewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ArithmeticInlinePage createMasterPage() {
        return new ArithmeticInlinePage(getDriver());
    }

    @FindBy(xpath = "//span[@class='pa-view-field']")
    private static List<WebElement> viewModeRecord;

    public List<String> getRecordInViewMode(){
        List<String> listValues = new ArrayList<>();
        for (WebElement element : viewModeRecord) {
            listValues.add(element.getText());
        }

        return listValues;
    }
}
