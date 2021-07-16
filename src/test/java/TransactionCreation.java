import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Stories;
import baseClass.TransactionBase;
public class TransactionCreation {

    @Test
    @Stories("Create a complete flow for transaction")
    public void tc01_TransactionCreationTest() {
        TransactionBase tb = new TransactionBase();
        tb.createUser();
        tb.retrieveUser();
        tb.getCardProduct();
        tb.CreateCard();
        tb.CreateTransaction();

    }

}