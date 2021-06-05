package base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public abstract class DriverPerClassBaseTest extends BaseTest {

    @BeforeClass
    protected void beforeClass() {
        System.out.println("beforeClass");
        initDriver();
    }

    @AfterClass
    protected void afterClass() {
        System.out.println("afterClass");
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
