package base;

import org.testng.annotations.*;

public abstract class DriverPerGroupBaseTest extends BaseTest {

    @BeforeGroups
    protected void beforeGroup() {
        System.out.println("beforeGroup");
        initDriver();
    }

    @AfterGroups
    protected void afterGroup() {
        System.out.println("afterGroup");
        stopDriver();
    }

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        System.out.println("empty beforeMethod");
    }

    @AfterMethod
    @Override
    protected void afterMethod() {
        System.out.println("empty afterMethod");
    }

}
