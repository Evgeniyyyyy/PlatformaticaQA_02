package model;

import model.base.BaseViewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChildRecordsLoopViewPage extends BaseViewPage<ChildRecordsLoopPage> {

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    public ChildRecordsLoopViewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ChildRecordsLoopPage createMasterPage() {
        return new ChildRecordsLoopPage(getDriver());
    }

    public List<List<String>> getRows() {
        List<List<String>>  listTable = rows.stream().map(row -> row.findElements(By.tagName("td")))
                .map(list -> list.stream().map(WebElement::getText).collect(Collectors.toList())).collect(Collectors.toList());
        listTable.remove(listTable.size() - 1);

        return listTable;
    }

}
