package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ParentPage extends MainPage{
    @FindBy(xpath = "//i[text()='create_new_folder']")
    private static WebElement newButton;

    @FindBy(className = "card-body")
    private static WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private static List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr/td/i")
    private static WebElement icon;

    public ParentPage(WebDriver driver) {
        super(driver);
    }

    public ParentEditPage clickNewButton() {
        newButton.click();

        return new ParentEditPage(getDriver());
    }

    public static boolean isTableEmpty() {
        return Strings.isStringEmpty(table.getText());
    }

    public static List<String> getRow(int number) {
        return rows.get(number).findElements(By.className("pa-list-table-th"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public static int getRowCount() {
        if (isTableEmpty()) {
            return 0;
        } else {
            return rows.size();
        }
    }

    public static String getClassIcon() {
        return icon.getAttribute("class");
    }
}
