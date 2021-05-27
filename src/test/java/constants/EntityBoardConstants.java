package constants;

import org.openqa.selenium.By;

public class EntityBoardConstants {

    public static final By LINK_BOARD_ENTITY = By.xpath("//a[@href=\"index.php?action=action_list&entity_id=31&mod=2\"]");
    public static final By LINK_BOARD_ICON = By.xpath("//a[@href=\"index.php?action=action_list&entity_id=31&mod=2\"]/i");
    public static final String URL = "https://ref2.eteam.work/index.php?action=login";
    public static final String URL_BOARD = "https://ref2.eteam.work/index.php?action=action_list&entity_id=31&mod=2";

    public static final By BOARD_ADD_CARD = By.xpath("//div[@class=\"card-icon\"]");
    public static final By BOARD_BUTTON_SAVE = By.id("pa-entity-form-save-btn");
    public static final By BOARD_TOGGLE_LIST_ACTION = By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=31\"]");
    public static final By BOARD_ACTION_BUTTON = By.cssSelector("button.btn-round.dropdown-toggle");
    public static final By BOARD_ACTION_DELETE = By.xpath("//ul[@class=\"dropdown-menu dropdown-menu-right show\"]/li/a[contains(text(),\"delete\")]");
    public static final By BOARD_RECYCLING_BIN_ICON = By.xpath("//a[@href=\"index.php?action=recycle_bin\"]");

    public static final By RECYCLING_BIN_DELETE_PERMANENTLY = By.xpath("//tr[@data-index=\"0\"]/td/a[contains(text(), \"delete permanently\")]");

}