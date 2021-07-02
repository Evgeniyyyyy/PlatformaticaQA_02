import base.BaseTest;
import model.MainPage;
import model.ReferenceValuesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EntityReferenceValuesViewExistingRecordTest extends BaseTest {

    private static final String LABEL = "Hello";
    private static final String FIELD1 = "Everyone";
    private static final String FIELD2 = "Test";

    private static final List<String> EXPECTED_RESULT = List.of(LABEL, FIELD1, FIELD2);

    @Test
    public void testCreateRecord() {

        ReferenceValuesPage referenceValuesPage = new MainPage(getDriver())
                .clickReferenceValueMenu()
                .clickCreateButton()
                .fillLabel(LABEL)
                .fillFilter_1(FIELD1)
                .fillFilter_2(FIELD2)
                .clickSaveButton();

        Assert.assertEquals(referenceValuesPage.getRowsValue(), EXPECTED_RESULT);
        Assert.assertEquals(referenceValuesPage.getTableRowsCount(), 1);
    }
}
