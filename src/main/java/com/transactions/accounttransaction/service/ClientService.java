package com.transactions.accounttransaction.service;

import com.transactions.accounttransaction.entity.Client;
import com.transactions.accounttransaction.exception.ClientIdAlreadyExistOnDatabaseException;
import com.transactions.accounttransaction.exception.SaveClientException;
import com.transactions.accounttransaction.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Client createClient(final Client client) {
        log.info("Create new client");
        if (existClientIdOnDatabase(client.getClientId())) {
            log.error("This client id: {} already exist in our database", client.getClientId());
            throw new ClientIdAlreadyExistOnDatabaseException();
        }

        try {
            log.info("Save client on database");
            return clientRepository.save(client);
        } catch (Exception e) {
            log.error("Error to persist client with client id: {} on database", client.getClientId(), e);
            throw new SaveClientException();
        }
    }

    public boolean existClientIdOnDatabase(final int clientId) {
        log.info("Check if exist a client with this client id: {}", clientId);
        Client client = clientRepository.findClientId(clientId);
        if (!Objects.isNull(client)) {
            return true;
        }
        return false;
    }

}
