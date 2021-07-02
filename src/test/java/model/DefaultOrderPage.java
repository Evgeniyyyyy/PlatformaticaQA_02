package model;

import model.base.BaseOrderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DefaultOrderPage extends BaseOrderPage<DefaultPage>{

        @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
        private WebElement toggle;

        public DefaultOrderPage(WebDriver driver) {
            super(driver);
        }

        @Override
        protected DefaultPage createMasterPage() {
            return new DefaultPage(getDriver());
        }

        public DefaultOrderPage clickToggleOrder() {
            toggle.click();

            return new DefaultOrderPage(getDriver());
        }
}
