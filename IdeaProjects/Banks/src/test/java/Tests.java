import com.anttoinettae.entities.Bank;
import com.anttoinettae.entities.Client;
import com.anttoinettae.exceptions.*;
import com.anttoinettae.models.Account;
import com.anttoinettae.models.Transaction;
import com.anttoinettae.services.CentralBank;
import com.anttoinettae.tools.ClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class Tests {
    public CentralBank cb = new CentralBank();
    public BigDecimal cc = BigDecimal.valueOf(50.0);
    public Integer di = 3;
    public Integer sdi = 4;
    public Integer mdi = 5;
    public Integer ldi = 6;
    public Bank tink;
    public Bank alf;
    public Client anta;
    public  Client vitya;
    public Account antAc;
    public Account vitAc;

    // fields
    @BeforeEach
    public void SetUp() throws InvalidQuantityException, NullNameException, NullIdException, NullClientException, AbsentBankException, SameClientException, NullSurnameException, NullAccountException, AbsentClientException, AbsentAccountException, SameAccountException, NotReliableException, InvalidPassportException, NullAddressException {
        tink = cb.addBank("tinkoff", cc, di, sdi, mdi, ldi);
        alf = cb.addBank("alfa", cc, di, sdi, mdi, ldi);
        anta = new ClientBuilder().withName("anta").withSurname("chernova").withPassport("4218 409837").withAddress("street").build();
        vitya = new ClientBuilder().withName("vitya").withSurname("ovch").withPassport("4219 409837").withAddress("street").build();
        cb.addClient(tink, anta);
        cb.addClient(alf, vitya);
        antAc = cb.addCreditAccountToClient(tink, anta);
        vitAc = cb.addDepositAccountToClient(alf, vitya);
        cb.changeBalance(antAc, BigDecimal.valueOf(2000));
        cb.changeBalance(vitAc, BigDecimal.valueOf(3000));
    }

    @Test
    public void TimePassed_DepositBalanceIncreasedButCreditDidNot(){
        cb.timeManager.addYears(2);
        Assertions.assertTrue(vitAc.getBalance().compareTo(BigDecimal.valueOf(3000)) > 0);
        Assertions.assertEquals(0, antAc.getBalance().compareTo(BigDecimal.valueOf(2000)));
    }

    @Test
    public void TransferredMoney_BalancesChanged() throws NullAccountException, InvalidQuantityException, NotReliableException {
        cb.transferMoney(antAc, vitAc, BigDecimal.valueOf(1000));
        Assertions.assertEquals(1000, antAc.getBalance().intValue());
        Assertions.assertEquals(4000, vitAc.getBalance().intValue());
    }

    @Test
    public void TransferredThenRevert_BalancesStayed() throws NullAccountException, InvalidQuantityException, NotReliableException, NullTransactionException, AbsentTransactionException, NullBankException, AbsentAccountException, IsCanceledException {
        Transaction tr = cb.transferMoney(antAc, vitAc, BigDecimal.valueOf(1000));
        cb.revert(tink, antAc, tr);
        Assertions.assertEquals(2000, antAc.getBalance().intValue());
        Assertions.assertEquals(3000, vitAc.getBalance().intValue());
    }
}
