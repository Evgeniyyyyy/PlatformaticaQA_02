package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import static utils.TestUtils.scrollClick;

public class ReferenceValuesViewPage extends BasePage {

    public ReferenceValuesViewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy (xpath="//i[@class='material-icons' and text()='clear']")
    private static WebElement closeButton;

    @FindBy (xpath="//div[@class='card-body']//*[self:: p or self::span[@class='pa-view-field']]")
    private List<WebElement> values;

    public ReferenceValuesPage clickClose()
    {
        scrollClick(getDriver(),closeButton) ;

        return new ReferenceValuesPage(getDriver());
    }

    public List<String> getValues()
    {
        if(isTableAvailable()) {
            return values.stream().map(WebElement::getText).collect(Collectors.toList());
        }
        return null;
    }
}