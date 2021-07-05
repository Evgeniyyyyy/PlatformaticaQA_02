import base.BaseTest;

import model.ChildRecordsLoopPage;
import model.MainPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static utils.ProjectUtils.getRandom;
import static utils.ProjectUtils.getTextRandom;

public class EntityChildRecordsLoopTest extends BaseTest {

    private static final String START_BALANCE_VALUE = "1";
    private static final int VALUE_AMOUNT_FIELD1 = getRandom(20);
    private static final int VALUE_AMOUNT_FIELD2 = getRandom(20);
    private static final int VALUE_AMOUNT_FIELD3 = getRandom(20);
    private static final String VALUE_ITEM_FIELD1 = getTextRandom(5);
    private static final String VALUE_ITEM_FIELD2 = getTextRandom(5);
    private static final String VALUE_ITEM_FIELD3 = getTextRandom(5);

    private static final String PAGINATION_INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";

    private static final List<Integer> LIST_VALUES_AMOUNT_FIELD = Arrays.asList(
            VALUE_AMOUNT_FIELD1, VALUE_AMOUNT_FIELD2, VALUE_AMOUNT_FIELD3);
    private static final List<String> LIST_VALUES_ITEM_FIELD = Arrays.asList(
            VALUE_ITEM_FIELD1, VALUE_ITEM_FIELD2, VALUE_ITEM_FIELD3);

    @Test
    public void testCreateRecordsChRecLoop(){

        double sumOfAmountFieldValues = Double.parseDouble(START_BALANCE_VALUE);

        for (int i = 0; i < 3; i++) {

            sumOfAmountFieldValues = sumOfAmountFieldValues + LIST_VALUES_AMOUNT_FIELD.get(i);
        }

        final List<String> expectedRecord = Arrays.asList(
                String.format(Locale.ROOT,"%.2f", Double.parseDouble(START_BALANCE_VALUE)),
                String.format(Locale.ROOT,"%.2f", sumOfAmountFieldValues));

        ChildRecordsLoopPage childRecordsLoopPage = new MainPage(getDriver())
                .clickChildRecordsLoopMenu()
                .clickNewButton()
                .fillStartBalance(START_BALANCE_VALUE)
                .clickAddRowBtn()
                .fillAmount(1, String.valueOf(VALUE_AMOUNT_FIELD1))
                .fillItem(1, VALUE_ITEM_FIELD1)
                .clickAddRowBtn()
                .fillAmount(2, String.valueOf(VALUE_AMOUNT_FIELD2))
                .fillItem(2, VALUE_ITEM_FIELD2)
                .clickAddRowBtn()
                .fillAmount(3, String.valueOf(VALUE_AMOUNT_FIELD3))
                .fillItem(3, VALUE_ITEM_FIELD3)
                .clickSave()
                .getTextPaginationInfo(PAGINATION_INFO_STR_1_OF_1);

        Assert.assertEquals(childRecordsLoopPage.getRow(0), expectedRecord);
    }
}
