package com.transactions.accounttransaction.unit;

import com.transactions.accounttransaction.entity.Account;
import com.transactions.accounttransaction.entity.Client;
import com.transactions.accounttransaction.repository.AccountRepository;
import com.transactions.accounttransaction.repository.ClientRepository;
import com.transactions.accounttransaction.service.AccountService;
import com.transactions.accounttransaction.service.ClientService;
import com.transactions.accounttransaction.vo.CreateAccountVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    ClientService clientService;

    private CreateAccountVO createAccountVO;

    private Client client;

    @Before
    public void setUp() {
        createAccountVO = new CreateAccountVO();
        createAccountVO.setAccountBalance(new BigDecimal("1"));
        createAccountVO.setClientId(567);
        client = new Client(910, "test@test.com");

    }

    @Test
    public void createAccountTestHappyScenario(){
        createAccountVO.setAccountId(567);
        doReturn(client).when(clientService).findClientByClientId(Mockito.anyInt());
       // client.setAccounts(Arrays.asList(new Account(123, client, new BigDecimal("1.00"))));
        Client clientReturned = accountService.createAccount(createAccountVO);
        assertEquals(1.00, clientReturned.getAccounts().get(0).getAccountBalance());
        assertEquals(123, clientReturned.getAccounts().get(0).getAccountId());
    }


}
