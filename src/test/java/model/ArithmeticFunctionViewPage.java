package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ArithmeticFunctionViewPage extends BaseViewPage<ArithmeticFunctionPage> {

    public ArithmeticFunctionViewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[@class='pa-view-field']")
    private List<WebElement> viewModeRecord;

    @FindBy(xpath = "//div[@class='form-group']/p")
    private WebElement user;

    @Override
    public List<String> getRecordInViewMode(){
        return viewModeRecord.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> wrapValues(List<Integer> expectedValues) {
        return expectedValues.stream().map(String::valueOf).collect(Collectors.toList());
    }

    @Override
    protected ArithmeticFunctionPage createMasterPage() {
        return new ArithmeticFunctionPage(getDriver());
    }
}
