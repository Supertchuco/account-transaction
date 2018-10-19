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

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    public Client createAccount(CreateAccountVO createAccountVO) {
        log.info("Create account for this client id {} with account id {}", createAccountVO.getClientId(), createAccountVO.getAccountId());
        if (existAccountIdOnDatabase(createAccountVO.getAccountId())) {
            log.error("This account id: {} already exist in our database", createAccountVO.getAccountId());
            throw new AccountdAlreadyExistOnDatabaseException();
        }

        Client client = clientRepository.findClientId(createAccountVO.getClientId());
        if (Objects.isNull(client)) {
            log.error("Client not found with this client id: {}", createAccountVO.getClientId());
            throw new ClientNotFoundException();
        }

        if (CollectionUtils.isEmpty(client.getAccounts())) {
            client.setAccounts(new ArrayList<>());
        }

        try {
            client.getAccounts().add(new Account(createAccountVO.getAccountId(), createAccountVO.getAccountBalance(), client, new Date()));
            return clientRepository.save(client);
        } catch (Exception e) {
            log.error("Error to save account", e);
            throw new SaveAccountException();
        }
    }

    public boolean existAccountIdOnDatabase(final int accountId) {
        log.info("Check if exist a account with this account id: {}", accountId);
        Account account = accountRepository.findByAccountId(accountId);
        if (!Objects.isNull(account)) {
            return true;
        }
        return false;
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
