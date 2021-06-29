package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ReferenceValuesPage1 extends MainPage {
    @FindBy(xpath = "//i[contains(text(),'create_new_folder')]")
    private WebElement newButton;

    @FindBy(className = "card-body")
    private static WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private static List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr")
    private WebElement row;

    @FindBy(xpath = "//tbody/tr/td/i")
    private static WebElement icon;

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement actionsViewButton;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement actionsEditButton;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement actionsDeleteButton;

    @FindBy(xpath = "//i[contains(.,'list')]")
    private WebElement listButton;

    @FindBy(xpath = "//a[contains(.,'Order')]")
    private WebElement orderButton;

    @FindBy(xpath = "//i[contains(@class,'fa fa-toggle')]")
    private WebElement toggle;

    @FindBy(id = "customId_0")
    private WebElement card;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//a/span[@class='notification']")
    private static WebElement notificationRecycleBinIcon;

    public ReferenceValuesPage1(WebDriver driver) {
        super(driver);
    }

    public ReferenceValuesEditPage1 clickNewButton() {
        newButton.click();

        return new ReferenceValuesEditPage1(getDriver());
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

    public static String getIcon() {
        return icon.getAttribute("class");
    }


    public RecycleBinPage clickRecycleBin() {
        recycleBinIcon.click();

        return new RecycleBinPage(getDriver());
    }

    public ReferenceValuesPage1 clickActions() {
        actionsButton.click();

        return new ReferenceValuesPage1(getDriver());
    }

    public ReferenceValuesViewPage1 clickActionsView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();


        return new ReferenceValuesViewPage1(getDriver());

    }

    public ReferenceValuesEditPage1 clickActionsEdit() {
        getWait().until(TestUtils.movingIsFinished(actionsDeleteButton));
        actionsEditButton.click();

        return new ReferenceValuesEditPage1(getDriver());
    }

    public ReferenceValuesPage1 clickActionsDelete() {
        getWait().until(TestUtils.movingIsFinished(actionsDeleteButton));
        actionsDeleteButton.click();

        return new ReferenceValuesPage1(getDriver());
    }

    public static int getTextnotificationRecycleBinIcon() {

        return Integer.parseInt(notificationRecycleBinIcon.getText());
    }


    public ReferenceValuesPage1 clickListButton() {
        listButton.click();
        return new ReferenceValuesPage1(getDriver());
    }

    public ReferenceValuesPage1 clickOrder() {
        orderButton.click();

        return new ReferenceValuesPage1(getDriver());
    }

    Actions actions = new Actions(getDriver());

    public ReferenceValuesPage1 getReorder() {
        actions.moveToElement(row)
                .clickAndHold(row)
                .dragAndDropBy(row, 0, 20)
                .build()
                .perform();
        return new ReferenceValuesPage1(getDriver());
    }

    public ReferenceValuesPage1 clickToggle() {
        toggle.click();

        return this;
    }

    public ReferenceValuesPage1 getNewReorder() {
        actions.moveToElement(card)
                .clickAndHold(card)
                .dragAndDropBy(card, 0, 100)
                .build()
                .perform();

        return this;
    }

    public static List<String> getRows(int number) {
        return rows.get(number).findElements(By.className("card-view-value"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

}

