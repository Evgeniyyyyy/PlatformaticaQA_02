package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ArithmeticInlinePage extends MainPage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(className = "card-body")
    private static WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private static List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr/td/i")
    private static WebElement icon;

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement actionsViewButton;

    public ArithmeticInlinePage(WebDriver driver) {
        super(driver);
    }

    public ArithmeticInlineEditPage clickNewButton() {
        newButton.click();

        return new ArithmeticInlineEditPage(getDriver());
    }

    public ArithmeticFunctionEditPage clickEditButton(int row) {
        getDriver().findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[text()='menu']")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='edit']")))).click();

        return new ArithmeticFunctionEditPage(getDriver());
    }

    public ArithmeticInlinePage clickActions() {
        actionsButton.click();

        return new ArithmeticInlinePage(getDriver());
    }

    public ArithmeticInlineViewPage clickActionsView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();

        return new ArithmeticInlineViewPage(getDriver());
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

    public static boolean iconCheck(String cssClass) {
        return icon.getAttribute("class").contains(cssClass);
    }

    public List<String> wrapValues(List<Integer> expectedValues) {
        return expectedValues.stream().map(String::valueOf).collect(Collectors.toList());
    }
}
