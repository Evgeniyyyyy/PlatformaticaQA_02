package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class FieldsPage extends MainPage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr")
    private WebElement row;

    @FindBy(xpath = "//div[@class='dropdown pull-left']")
    private WebElement actionMenu;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement edit;

    @FindBy(xpath = "//i[text()='format_line_spacing']")
    private WebElement order;

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    @FindBy(id = "customId_0")
    private WebElement card;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement input;

    public FieldsPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage clickNewButton() {
        newButton.click();

        return new FieldsEditPage(getDriver());
    }

    public boolean isTableEmpty() {
        return Strings.isStringEmpty(table.getText());
    }

    public List<String> getRow(int number) {
        return rows.get(number).findElements(By.className("pa-list-table-th"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public int getRowCount() {
        if (isTableEmpty()) {
            return 0;
        } else {
            return rows.size();
        }
    }

    public FieldsEditPage clickEdit() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), edit);

        return new FieldsEditPage(getDriver());
    }

    public FieldsPage clickOrder() {
        order.click();

        return new FieldsPage(getDriver());
    }

    Actions actions = new Actions(getDriver());

    public FieldsPage getReorder() {
        actions.moveToElement(row).clickAndHold(row).dragAndDropBy(row, 0, 100);
        Action swapRow = actions.build();
        swapRow.perform();

        return new FieldsPage(getDriver());
    }

    public FieldsPage clickToggle() {
        toggle.click();

        return this;
    }

    public FieldsPage getNewReorder() {
        actions.moveToElement(card).clickAndHold(card).dragAndDropBy(card, 0, 140);
        Action swapCard = actions.build();
        swapCard.perform();

        return this;
    }

    public List<String> getRows(int number) {
        return rows.get(number).findElements(By.className("card-view-value"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public FieldsPage searchInput(String value) {
        input.sendKeys(value);

        return this;
    }
}
