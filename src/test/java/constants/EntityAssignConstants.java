package constants;

import org.openqa.selenium.By;

public class EntityAssignConstants {

    public static final By LINK_ASSIGN_ENTITY = By.xpath("//a[@href=\"index.php?action=action_list&entity_id=37&mod=2\"]");
    public static final By LINK_ASSIGN_ICON = By.xpath("//a[@href=\"index.php?action=action_list&entity_id=37&mod=2\"]/i");
    public static final String URL = "https://ref2.eteam.work/index.php?action=login";
    public static final String URL_ASSIGN = "https://ref2.eteam.work/index.php?action=action_list&entity_id=37&mod=2";

    public static final By ASSIGN_ADD_CARD = By.xpath("//div[@class=\"card-icon\"]");
    public static final By ASSIGN_STRING_FIELD = By.id("string");
    public static final By ASSIGN_TEXT_FIELD = By.id("text");
    public static final By ASSIGN_DATE_FIELD = By.id("date");
    public static final By ASSIGN_INT_FIELD = By.id("int");
    public static final By ASSIGN_DECIMAL_FIELD = By.id("decimal");
    public static final By ASSIGN_BUTTON_SAVE = By.id("pa-entity-form-save-btn");
    public static final By ASSIGN_BUTTON_SAVE_DRAFT = By.id("pa-entity-form-draft-btn");

    public static final By ASSIGN_TOGGLE_LIST_ACTION = By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=37\"]");
    public static final By ASSIGN_TOGGLE_ORDER_ACTION = By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=37&draggable=1\"]");

    public static final By ASSIGN_ROW_SAVE_RECORD = By.id("customId_0");
    public static final By ASSIGN_GET_ICON = By.xpath("//tbody/tr[1]/td[1]/i");
    public static final By ASSIGN_GET_LIST_ROW = By.xpath("//tbody/tr");
    public static final By ASSIGN_GET_SEARCH_FIELD = By.xpath("//input[contains(@class, \"search-input\")]");
    public static final By ASSIGN_GET_TEXT_MESSAGE = By.xpath(("//span[@class=\"pagination-info\"]"));

    public static final String CLASS_ITEM_SAVE_DRAFT = "fa fa-pencil";
    public static final String CLASS_ITEM_SAVE = "fa fa-check-square-o";
    public static final String TEXT_MESSAGE_ONE = "Showing 1 to 1 of 1 rows";
    public static final String TEXT_MESSAGE_TWO = "Showing 1 to 2 of 2 rows";
}
