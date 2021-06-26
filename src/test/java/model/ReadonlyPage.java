package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadonlyPage extends MainPage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement actionsViewButton;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement actionsEditButton;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//td[@class = 'pa-list-table-th'][3]/a")
    private WebElement record;

    @FindBy(xpath = "//span [@class = 'pa-view-field']")
    private List<WebElement> viewModeRecord;

    public ReadonlyPage(WebDriver driver) {
        super(driver);
    }

    public ReadonlyEditPage clickNewButton() {
        TestUtils.jsClick(getDriver(), newButton);

        return new ReadonlyEditPage(getDriver());
    }

    public ReadonlyPage clickActionButton() {
        actionsButton.click();

        return new ReadonlyPage(getDriver());
    }

    public ReadonlyPage clickActionView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();

        return new ReadonlyPage(getDriver());
    }

    public ReadonlyEditPage clickActionEdit() {
        getWait().until(TestUtils.movingIsFinished(actionsEditButton));
        actionsEditButton.click();

        return new ReadonlyEditPage(getDriver());
    }

    public ReadonlyPage clickRecord() {
        TestUtils.jsClick(getDriver(), record);

        return new ReadonlyPage(getDriver());
    }

    public List<String> getRecordInViewMode(){
        List<String> listValues = new ArrayList<>();
        for (WebElement element : viewModeRecord) {
            listValues.add(element.getText());
        }
        return listValues;
    }

    public boolean isTableEmpty() {
        return Strings.isStringEmpty(table.getText());
    }

    public List<String> getRow(int number) {
        return rows.get(number).findElements(By.xpath("//td[@class = 'pa-list-table-th']/a"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public int getRowCount() {
        if (isTableEmpty()) {
            return 0;
        } else {
            return rows.size();
        }
    }
}
