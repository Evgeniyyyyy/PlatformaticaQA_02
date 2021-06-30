package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;

public class AssignViewPage extends BaseViewPage<AssignPage> {

        public AssignViewPage(WebDriver driver) {
            super(driver);
        }

        @Override
        protected AssignPage createMasterPage() {
            return new AssignPage(getDriver());
        }
}
