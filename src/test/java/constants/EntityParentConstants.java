package constants;

import org.openqa.selenium.By;

public class EntityParentConstants {

    public static final By GET_PARENT_TITLE = By.xpath("//h3[contains(text(), 'Parent')]");

    public static final By STRING_FIELD = By.id("string");
    public static final By TEXT_FIELD = By.id("text");
    public static final By INT_FIELD = By.id("int");
    public static final By DECIMAL_FIELD = By.id("decimal");
    public static final By DATE_FIELD = By.id("date");
    public static final By TESTER_NAME_FIELD = By.xpath("//div[@class='filter-option-inner-inner']");
    public static final By TESTER_NAME = By.xpath("//span[contains(text(), 'apptester10@tester.test')]");

    public static final By PARENT_GET_CONTANER = By.className("card-body");
    public static final By PARENT_GET_LIST_ROW = By.xpath("//tbody/tr");
    public static final By PARENT_GET_ICON = By.xpath("//tbody/tr[1]/td[1]/i");
    public static final String CLASS_ITEM_SAVE_DRAFT = "fa fa-pencil";

    public static final By PARENT_ACTION_BUTTON = By.className("btn-primary");;
    public static final By PARENT_ACTION_DELETE = By.xpath("//ul[@class='dropdown-menu dropdown-menu-right show']/li/a[contains(text(),\"delete\")]");
    public static final By PARENT_RECYCLING_BIN_ICON_NOTICE = By.xpath("//span[@class='notification']");
    public static final By PARENT_GET_TEXT_MESSAGE = By.xpath(("//span[@class='pagination-info']"));

    public static final String TEXT_MESSAGE_TWO = "Showing 1 to 2 of 2 rows";
}
