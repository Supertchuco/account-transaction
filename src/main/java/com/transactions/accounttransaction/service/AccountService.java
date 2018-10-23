package com.transactions.accounttransaction.service;

import com.transactions.accounttransaction.entity.Account;
import com.transactions.accounttransaction.entity.Client;
import com.transactions.accounttransaction.exception.AccountdAlreadyExistOnDatabaseException;
import com.transactions.accounttransaction.exception.ClientNotFoundException;
import com.transactions.accounttransaction.exception.SaveAccountException;
import com.transactions.accounttransaction.repository.AccountRepository;
import com.transactions.accounttransaction.repository.ClientRepository;
import com.transactions.accounttransaction.vo.CreateAccountVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientService clientService;

    public Client createAccount(final CreateAccountVO createAccountVO) {
        log.info("Create account for this client id {} with account id {}", createAccountVO.getClientId(), createAccountVO.getAccountId());

        Account dbAccount = accountRepository.findByAccountId(createAccountVO.getAccountId());
        if (!Objects.isNull(dbAccount)) {
            log.error("This account id: {} already exist in our database", createAccountVO.getAccountId());
            throw new AccountdAlreadyExistOnDatabaseException(String.format("This account id: %d already exist in our database", createAccountVO.getAccountId()));
        }

        Client client = clientService.findClientByClientId(createAccountVO.getClientId());
        if (Objects.isNull(client)) {
            log.error("Client not found with this client id: {}", createAccountVO.getClientId());
            throw new ClientNotFoundException((String.format("Client not found with this client id: %d", createAccountVO.getClientId())));
        }

        if (CollectionUtils.isEmpty(client.getAccounts())) {
            client.setAccounts(new ArrayList<>());
        }

        try {
            client.getAccounts().add(new Account(createAccountVO.getAccountId(), (Objects.isNull(createAccountVO.getAccountBalance())) ? new BigDecimal("0.00") : createAccountVO.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP), client, new Date()));
            return clientService.saveClient(client);
        } catch (Exception e) {
            log.error("Error to save account", e);
            throw new SaveAccountException();
        }
    }

    public Account findAccountByAccountId(final int accountId) {
        log.info("Find account by account id: {}", accountId);
        return accountRepository.findByAccountId(accountId);
    }

    public Account saveAccount(final Account account) {
        log.info("save account with account id: {}", account.getAccountId());
        return accountRepository.save(account);
    }
}
