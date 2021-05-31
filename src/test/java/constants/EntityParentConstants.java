package constants;

import org.openqa.selenium.By;

public class EntityParentConstants {

    public static final By LINK_PARENT_ENTITY = By.xpath("//p[contains(text(),'Parent')]");
    public static final By GET_PARENT_TITLE = By.tagName("h3");
    public static final String URL = "https://ref2.eteam.work/index.php?action=login";
    public static final String URL_PARENT = "https://ref2.eteam.work/index.php?action=action_list&entity_id=31&mod=2";

    public static final By PARENT_ADD_CARD = By.xpath("//div[@class=\"card-icon\"]");
    public static final By PARENT_BUTTON_SAVE = By.id("pa-entity-form-save-btn");
    public static final By PARENT_BUTTON_SAVE_DRAFT = By.id("pa-entity-form-draft-btn");
    public static final By PARENT_BUTTON_CANCEL = By.xpath("//button[contains(text(), \"Cancel\")]");

    public static final By STRING_FIELD = By.id("string");
    public static final By TEXT_FIELD = By.id("text");
    public static final By INT_FIELD = By.id("int");
    public static final By DECIMAL_FIELD = By.id("decimal");
    public static final By DATE_FIELD = By.id("date");
    public static final By TESTER_NAME_FIELD = By.xpath("//div[@class=\"filter-option-inner-inner\"]");
    public static final By TESTER_NAME = By.xpath("//span[contains(text(), \"apptester10@tester.test\")]");

    public static final By PARENT_GET_CONTANER = By.className("card-body");
    public static final By PARENT_GET_LIST_ROW = By.xpath("//tbody/tr");
    public static final By PARENT_GET_ICON = By.xpath("//tbody/tr[1]/td[1]/i");
    public static final String CLASS_ITEM_SAVE_DRAFT = "fa fa-pencil";

    public static final By PARENT_TOGGLE_LIST_ACTION = By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=57\"]");
    public static final By PARENT_ACTION_BUTTON = By.className("btn-primary");;
    public static final By PARENT_ACTION_DELETE = By.xpath("//ul[@class=\"dropdown-menu dropdown-menu-right show\"]/li/a[contains(text(),\"delete\")]");
    public static final By PARENT_RECYCLING_BIN_ICON = By.xpath("//a[@href=\"index.php?action=recycle_bin\"]");
    public static final By PARENT_RECYCLING_BIN_ICON_NOTICE = By.xpath("//span[@class=\"notification\"]");

}
