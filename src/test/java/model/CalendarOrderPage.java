package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CalendarOrderPage extends MainPage{

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//a[contains(@href, 'index.php?action=action_list&list_type=calendar&entity_id=3')]")
    private WebElement calendarView;

    public CalendarPage calendarView (){
        TestUtils.jsClick(getDriver(), calendarView);

        return new CalendarPage(getDriver());
    }

    public CalendarOrderPage(WebDriver driver) {
        super(driver);
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

}
