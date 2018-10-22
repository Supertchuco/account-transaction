package com.transactions.accounttransaction.unit;

import com.transactions.accounttransaction.entity.Account;
import com.transactions.accounttransaction.entity.Client;
import com.transactions.accounttransaction.exception.OriginAccountBalanceIsNotEnoughException;
import com.transactions.accounttransaction.exception.PerformMoneyAccountTransferTransactionException;
import com.transactions.accounttransaction.repository.TransactionRepository;
import com.transactions.accounttransaction.service.AccountService;
import com.transactions.accounttransaction.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionRepository transactionRepository;

    private Client client;

    @Before
    public void setUp() {
        client = new Client(123, "test@test.com");
    }


    @Test
    public void originAccountHasBalanceToAccountTransferTestWhenOriginHasBalance() {
        Account originAccount = new Account(1234, client, new BigDecimal("600.00"));
        BigDecimal valueToTransfer = new BigDecimal("500.00");
        Object[] inputArray = {originAccount, valueToTransfer};
        ReflectionTestUtils.invokeMethod(transactionService, "originAccountHasBalanceToAccountTransfer", inputArray);
    }

    @Test
    public void originAccountHasBalanceToAccountTransferTestWhenOriginHasSomeValueThatValueToTransfer() {
        Account originAccount = new Account(1234, client, new BigDecimal("500.00"));
        BigDecimal valueToTransfer = new BigDecimal("500.00");
        Object[] inputArray = {originAccount, valueToTransfer};
        ReflectionTestUtils.invokeMethod(transactionService, "originAccountHasBalanceToAccountTransfer", inputArray);
    }

    @Test(expected = OriginAccountBalanceIsNotEnoughException.class)
    public void originAccountHasBalanceToAccountTransferTestWhenOriginNotHasBalance() {
        Account originAccount = new Account(1234, client, new BigDecimal("200.00"));
        BigDecimal valueToTransfer = new BigDecimal("500.00");
        Object[] inputArray = {originAccount, valueToTransfer};
        ReflectionTestUtils.invokeMethod(transactionService, "originAccountHasBalanceToAccountTransfer", inputArray);
    }

    @Test
    public void doMoneyTransferTestHappyScenario() {
        Account originAccount = new Account(1234, client);
        Account targetAccount = new Account(1234, client);
        originAccount.setAccountBalance(new BigDecimal("200.00"));
        BigDecimal valueToTransfer = new BigDecimal("100.00");
        Object[] inputArray = {originAccount, targetAccount, valueToTransfer};
        ReflectionTestUtils.invokeMethod(transactionService, "doMoneyTransfer", inputArray);
    }

    @Test(expected = PerformMoneyAccountTransferTransactionException.class)
    public void doMoneyTransferTestWhenSomeExceptionOccurred() {
        Account originAccount = new Account(1234, client);
        Account targetAccount = new Account(1234, client);
        originAccount.setAccountBalance(new BigDecimal("200.00"));
        BigDecimal valueToTransfer = new BigDecimal("100.00");
        Object[] inputArray = {originAccount, targetAccount, valueToTransfer};
        doThrow(IllegalArgumentException.class).when(transactionRepository).save(Mockito.any());
        ReflectionTestUtils.invokeMethod(transactionService, "doMoneyTransfer", inputArray);
    }

    @Test
    public void debitValueIntoOriginAccountBalanceTestHappyScenarios() {
        Object[] inputArray = {new BigDecimal("200.00"), new BigDecimal("200.00")};
        assertEquals(new BigDecimal("00.00"), ReflectionTestUtils.invokeMethod(transactionService, "debitValueIntoOriginAccountBalance", inputArray));

        inputArray[0] = new BigDecimal("100.00");
        inputArray[1] = new BigDecimal("5.20");
        assertEquals(new BigDecimal("94.80"), ReflectionTestUtils.invokeMethod(transactionService, "debitValueIntoOriginAccountBalance", inputArray));

        inputArray[0] = new BigDecimal("5.25");
        inputArray[1] = new BigDecimal("01.11");
        assertEquals(new BigDecimal("04.14"), ReflectionTestUtils.invokeMethod(transactionService, "debitValueIntoOriginAccountBalance", inputArray));
    }

    @Test
    public void addValueIntoTargetAccountBalanceTestHappyScenario() {
        Object[] inputArray = {new BigDecimal("0.00"), new BigDecimal("200.00")};
        assertEquals(new BigDecimal("200.00"), ReflectionTestUtils.invokeMethod(transactionService, "addValueIntoTargetAccountBalance", inputArray));

        inputArray[0] = new BigDecimal("100.00");
        inputArray[1] = new BigDecimal("5.20");
        assertEquals(new BigDecimal("105.20"), ReflectionTestUtils.invokeMethod(transactionService, "addValueIntoTargetAccountBalance", inputArray));

        inputArray[0] = new BigDecimal("5.25");
        inputArray[1] = new BigDecimal("01.11");
        assertEquals(new BigDecimal("06.36"), ReflectionTestUtils.invokeMethod(transactionService, "addValueIntoTargetAccountBalance", inputArray));

    }
}
