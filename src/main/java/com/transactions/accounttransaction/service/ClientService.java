package com.transactions.accounttransaction.service;

import com.transactions.accounttransaction.entity.Client;
import com.transactions.accounttransaction.exception.ClientIdAlreadyExistOnDatabaseException;
import com.transactions.accounttransaction.exception.SaveClientException;
import com.transactions.accounttransaction.repository.ClientRepository;
import com.transactions.accounttransaction.vo.CreateClientVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Client createClient(final CreateClientVO createClientVO) {
        log.info("Create new client");
        Client dbClient = clientRepository.findById(createClientVO.getClientId());
        if (!Objects.isNull(dbClient)) {
            log.error("This client id: {} already exist in our database", createClientVO.getClientId());
            throw new ClientIdAlreadyExistOnDatabaseException(String.format("This client id: %d already exist in our database",
                    createClientVO.getClientId()));
        }
        try {
            log.info("Save client on database");
            return clientRepository.save(new Client(createClientVO.getClientId(), createClientVO.getEmail()));
        } catch (Exception e) {
            log.error("Error to persist client with client id: {} on database", createClientVO.getClientId(), e);
            throw new SaveClientException(String.format("Error to persist client with client id: %d on database",
                    createClientVO.getClientId()));
        }
    }

    public Client findClientByClientId(final int clientId) {
        return clientRepository.findById(clientId);
    }

    public Client saveClient(final Client client) {
        return clientRepository.save(client);
    }

}
