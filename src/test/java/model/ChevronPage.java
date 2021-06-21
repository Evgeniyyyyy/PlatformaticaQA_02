package model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChevronPage extends MainPage {

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

    @FindBy(xpath = "//a[text()='view']")
    private WebElement view;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement edit;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement delete;

    @FindBy(xpath = "//a[text()='All']")
    private WebElement allButton;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//tbody/tr[1]/td[1]/i[1]")
    private WebElement icon;

    public ChevronPage(WebDriver driver) {
        super(driver);
    }

    public ChevronEditPage clickNewButton() {
        newButton.click();

        return new ChevronEditPage(getDriver());
    }

    public ChevronPage clickAllButton() {
        allButton.click();

        return this;
    }

    public RecycleBinPage clickRecycleBin(){
        recycleBinIcon.click();

        return new RecycleBinPage(getDriver());
    }


    public int getRowCount() {
        if (isTableEmpty()) {
            return 0;
        } else {
            return rows.size();
        }
    }

    public ChevronViewPage clickView() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), view);

        return new ChevronViewPage(getDriver());
    }

    public ChevronEditPage clickEdit() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), edit);

        return new ChevronEditPage(getDriver());
    }

    public ChevronPage clickDelete() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), delete);

        return this;
    }

    public Boolean isNotEmptyRecycleBin() {
        String expectedTextRecycleBinAfterDelete = "delete_outline\n" + "1";
        String textRecycleBinAfterDelete = getDriver().findElement(
                By.xpath("//a[@href='index.php?action=recycle_bin']")).getText();
        return textRecycleBinAfterDelete.equals(expectedTextRecycleBinAfterDelete);
    }

    public int chooseRecordNumberInTable(List<String> data) {

        for(int j = 1; j <= rows.size(); j ++){
            List<WebElement> cells = getDriver().findElements(
                    By.xpath("//tbody/tr["+ j +"]/td[@class = 'pa-list-table-th']"));

            if(cells.get(1).getText().equals(data.get(1))){
                return j - 1;
            }
        }
        return 0;
    }

    public boolean isTableEmpty() {
        List<WebElement> tableElements = getDriver().findElements(By.xpath("//div[@class='card-body ']/*"));
        return tableElements.size() == 1;
    }

    public String getIcon() {
        return icon.getAttribute("class");
    }

    public ChevronPageDto getRow(int number) {
        List<String> cellValues = rows.get(number).findElements(By.className("pa-list-table-th"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        ChevronPageDto row = new ChevronPageDto();

        row.stringDropDownValue = cellValues.get(0);
        row.formTextValue = cellValues.get(1);
        row.formIntValue = cellValues.get(2);
        row.formDecimalValue = cellValues.get(3);
        row.dateValue = cellValues.get(4);
        row.dateTimeValue = cellValues.get(5);
        row.userValue = cellValues.get(7);

        return row;
    }


    public static class ChevronPageDto {
        public String stringDropDownValue;
        public String formTextValue;
        public String formIntValue;
        public String formDecimalValue;
        public String dateValue;
        public String dateTimeValue;
        public String userValue;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChevronPageDto)) return false;
            ChevronPageDto that = (ChevronPageDto) o;
            return Objects.equals(stringDropDownValue, that.stringDropDownValue) && Objects.equals(formTextValue, that.formTextValue) && Objects.equals(formIntValue, that.formIntValue) && Objects.equals(formDecimalValue, that.formDecimalValue) && Objects.equals(dateValue, that.dateValue) && Objects.equals(dateTimeValue, that.dateTimeValue) && Objects.equals(userValue, that.userValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stringDropDownValue, formTextValue, formIntValue, formDecimalValue, dateValue, dateTimeValue, userValue);
        }

        @Override
        public String toString() {
            return "ChevronPageDto{" +
                    "stringDropDownValue='" + stringDropDownValue + '\'' +
                    ", formTextValue='" + formTextValue + '\'' +
                    ", formIntValue='" + formIntValue + '\'' +
                    ", formDecimalValue='" + formDecimalValue + '\'' +
                    ", dateValue='" + dateValue + '\'' +
                    ", dateTimeValue='" + dateTimeValue + '\'' +
                    ", userValue='" + userValue + '\'' +
                    '}';
        }
    }

    public static ChevronPageDto generateRandomData(String dropDownValue){
        ChevronPageDto data = new ChevronPageDto();
        data.stringDropDownValue = dropDownValue;
        data.formTextValue = RandomStringUtils.randomAlphabetic(10);
        data.formIntValue = "" + RandomUtils.nextInt(0, 1000000);
        data.formDecimalValue = RandomUtils.nextInt(0, 1000000) + "." + RandomStringUtils.randomNumeric(2);
        data.dateValue = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        data.dateTimeValue = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        data.userValue = "apptester1@tester.test";

        return data;
    }
}
