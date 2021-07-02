package model;

import model.base.BaseEditPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class TagEditPage extends BaseEditPage<TagPage> {

    @FindBy(id = "string")
    private WebElement fieldString;

    @FindBy(id = "text")
    private WebElement fieldText;

    @FindBy(id = "int")
    private WebElement fieldInt;

    @FindBy(id = "decimal")
    private WebElement fieldDecimal;

    @FindBy(id = "date")
    private WebElement fieldDate;

    @FindBy(id = "datetime")
    private WebElement fieldDatetime;

    @FindBy(className = "filter-option-inner-inner")
    private WebElement fieldUserName;

    public TagEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected TagPage createMasterPage() {
        return new TagPage(getDriver());
    }

    public TagEditPage fillString(String value) {
        fieldString.clear();
        fieldString.sendKeys(value);

        return this;
    }

    public TagEditPage fillText(String value) {
        fieldText.clear();
        fieldText.sendKeys(value);

        return this;
    }

    public TagEditPage fillInt(String value) {
        fieldInt.clear();
        fieldInt.sendKeys(value);

        return this;
    }

    public TagEditPage fillDecimal(String value) {
        fieldDecimal.clear();
        fieldDecimal.sendKeys(value);

        return this;
    }

    public TagEditPage fillDate(String value) {
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public TagEditPage fillDateTime(String value) {
        fieldDatetime.click();
        fieldDatetime.clear();
        fieldDatetime.sendKeys(value);

        return this;
    }

    public TagEditPage findUser(String value) {
        TestUtils.jsClick(getDriver(), fieldUserName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

        return this;
    }
}
