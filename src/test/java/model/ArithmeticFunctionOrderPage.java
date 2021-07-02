package model;

import model.base.BaseOrderPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ArithmeticFunctionOrderPage extends BaseOrderPage<ArithmeticFunctionPage> {

   @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
   private WebElement toggle;

    public ArithmeticFunctionOrderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ArithmeticFunctionViewPage createViewPage() {
        return new ArithmeticFunctionViewPage(getDriver());
    }

    @Override
    protected ArithmeticFunctionEditPage createEditPage() {
        return new ArithmeticFunctionEditPage(getDriver());
    }

    @Override
    protected ArithmeticFunctionPage createMasterPage() {
        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionOrderPage clickToggleOrder(){
        toggle.click();
        return new ArithmeticFunctionOrderPage(getDriver());
    }
}
