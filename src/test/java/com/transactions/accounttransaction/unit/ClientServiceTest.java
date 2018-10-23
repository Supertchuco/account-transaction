package com.transactions.accounttransaction.unit;

import com.transactions.accounttransaction.entity.Client;
import com.transactions.accounttransaction.exception.ClientIdAlreadyExistOnDatabaseException;
import com.transactions.accounttransaction.exception.SaveClientException;
import com.transactions.accounttransaction.repository.ClientRepository;
import com.transactions.accounttransaction.service.ClientService;
import com.transactions.accounttransaction.vo.CreateClientVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client;

    private CreateClientVO createClientVO;

    @Before
    public void setUp() {
        createClientVO = new CreateClientVO(123, "test@test.com");

        client = new Client(123, "test@test.com");
    }

    @Test
    public void CreateClientHappyScenario() {
        doReturn(client).when(clientRepository).save(Mockito.any());
        assertEquals(client.getId(), clientService.createClient(createClientVO).getId());
        assertEquals(client.getEmail(), clientService.createClient(createClientVO).getEmail());
    }

    @Test(expected = ClientIdAlreadyExistOnDatabaseException.class)
    public void CreateClientWhenClientIdExistOnDatabase() {
        doReturn(client).when(clientRepository).findById(123);
        clientService.createClient(createClientVO);
    }

    @Test(expected = SaveClientException.class)
    public void CreateClientWhenSomeExceptionOccurredInSaveMethodInClientRepository() {
        doThrow(IllegalArgumentException.class).when(clientRepository).save(Mockito.any());
        clientService.createClient(createClientVO);
    }

}
