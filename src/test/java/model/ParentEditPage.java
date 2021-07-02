package model;

import model.base.BaseEditPage;
import model.base.BaseListMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class ParentEditPage extends BaseEditPage<ParentPage> {

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
    private WebElement fieldDateTime;

    @FindBy(xpath = "//div[contains(text(),'apptester10@tester.test')]")
    private WebElement chooseUser;

    @FindBy(className = "filter-option-inner-inner")
    private WebElement fieldUserName;

    public ParentEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ParentPage createMasterPage() {
        return new ParentPage(getDriver());
    }

    public ParentEditPage fillString(String value) {
        fieldString.clear();
        fieldString.sendKeys(value);

        return this;
    }

    public ParentEditPage fillText(String value) {
        fieldText.clear();
        fieldText.sendKeys(value);

        return this;
    }

    public ParentEditPage fillInt(String value) {
        fieldInt.clear();
        fieldInt.sendKeys(value);

        return this;
    }

    public ParentEditPage fillDecimal(String value) {
        fieldDecimal.clear();
        fieldDecimal.sendKeys(value);

        return this;
    }

    public ParentEditPage fillDate(String value) {

        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public ParentEditPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return this;
    }

    public ParentEditPage findUser(String value) {
        TestUtils.jsClick(getDriver(), fieldUserName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

        return this;
    }

    public ParentEditPage clearElement(WebElement element) {
        element.clear();

        return this;
    }

    public ParentEditPage clearElement(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        element.clear();

        return this;
    }
}
