package constants;

import org.openqa.selenium.By;

import java.util.List;

public class EntityArithmeticInlineConstants {
    public static final Integer F1 = 10;
    public static final Integer F2 = 2;
    public static final Integer SUM = F1 + F2;
    public static final Integer SUB = F1 - F2;
    public static final Integer MUL = F1 * F2;
    public static final Integer DIV = F1 / F2;
    public static final List<Integer> expectedList = List.of(F1, F2, SUM, SUB, MUL, DIV);

    public static final By LINK_ARITHMETIC_ENTITY = By.xpath("//p[contains(text(),'Arithmetic Inline')]");
    public static final By LINK_ARITHMETIC_ICON = By.xpath("//a[@href=\"index.php?action=action_list&entity_id=54&mod=2\"]/i");
    public static final String URL = "https://ref2.eteam.work/index.php?action=login";
    public static final String URL_ARITHMETIC = "https://ref2.eteam.work/index.php?action=action_list&entity_id=54&mod=2";

    public static final By ARITHMETIC_ADD_CARD = By.xpath("//div[@class=\"card-icon\"]");
    public static final By ARITHMETIC_BUTTON_SAVE = By.id("pa-entity-form-save-btn");
    public static final By ARITHMETIC_BUTTON_SAVE_DRAFT = By.id("pa-entity-form-draft-btn");

    public static final By ARITHMETIC_ACTION_BUTTON = By.className("btn-primary");
    public static final By ARITHMETIC_ACTION_VIEW = By.xpath("//div[@class=\"dropdown pull-left show\"]/ul/li[1]/a");
    public static final By ARITHMETIC_RESULT_LIST = By.xpath("//div[@style=\"background-color:white\"]");
    public static final By ARITHMETIC_VIEW_WINDOW_CLOSE = By.cssSelector("button.pa-btn-close-form");
}
