package model;

import model.base.BaseListMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ArithmeticInlinePage extends BaseListMasterPage<ArithmeticInlineEditPage, ArithmeticInlineViewPage> {

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement actionsViewButton;

    public ArithmeticInlinePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ArithmeticInlineViewPage createViewPage() {
        return new ArithmeticInlineViewPage(getDriver());
    }

    @Override
    protected ArithmeticInlineEditPage createEditPage() {
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

    public List<String> wrapValues(List<Integer> expectedValues) {
        return expectedValues.stream().map(String::valueOf).collect(Collectors.toList());
    }
}
