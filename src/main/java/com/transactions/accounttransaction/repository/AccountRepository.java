package com.transactions.accounttransaction.repository;

import com.transactions.accounttransaction.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByAccountId(final int accountId);
}
