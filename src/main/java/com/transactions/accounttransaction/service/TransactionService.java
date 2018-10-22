package com.transactions.accounttransaction.service;

import com.transactions.accounttransaction.entity.Account;
import com.transactions.accounttransaction.entity.Transaction;
import com.transactions.accounttransaction.exception.OriginAccountBalanceIsNotEnoughException;
import com.transactions.accounttransaction.exception.OriginAccountNotFoundException;
import com.transactions.accounttransaction.exception.PerformMoneyAccountTransferTransactionException;
import com.transactions.accounttransaction.repository.TransactionRepository;
import com.transactions.accounttransaction.vo.PerformMoneyAccountTransferTransactionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class TransactionService {

    final private static String TRANSFER_TRANSACTION = "TRANSFER_TRANSACTION";

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction performMoneyAccountTransferTransaction(final PerformMoneyAccountTransferTransactionVO performMoneyAccountTransferTransactionVO) {
        log.info("perform money account transaction between account origin account id: {} and target account id: {}",
                performMoneyAccountTransferTransactionVO.getOriginAccountId(), performMoneyAccountTransferTransactionVO.getTargetAccountId());

        Account originAccount = accountService.findAccountByAccountId(performMoneyAccountTransferTransactionVO.getOriginAccountId());
        if (Objects.isNull(originAccount)) {
            log.error("Origin account not found with this account id: {}", performMoneyAccountTransferTransactionVO.getOriginAccountId());
            throw new OriginAccountNotFoundException();
        }

        Account targetAccount = accountService.findAccountByAccountId(performMoneyAccountTransferTransactionVO.getTargetAccountId());
        if (Objects.isNull(targetAccount)) {
            log.error("Target account not found with this account id: {}", performMoneyAccountTransferTransactionVO.getTargetAccountId());
            throw new OriginAccountNotFoundException();
        }

        originAccountHasBalanceToAccountTransfer(originAccount, performMoneyAccountTransferTransactionVO.getTransactionValue());
        return doMoneyTransfer(originAccount, targetAccount, performMoneyAccountTransferTransactionVO.getTransactionValue());
    }

    @Transactional
    private Transaction doMoneyTransfer(Account originAccount, Account targetAccount, BigDecimal transactionValue) {
        log.info("Do money transfer");
        try {
            originAccount.setAccountBalance(debitValueIntoOriginAccountBalance(originAccount.getAccountBalance(), transactionValue));
            targetAccount.setAccountBalance(addValueIntoTargetAccountBalance(targetAccount.getAccountBalance(), transactionValue));
            log.info("Update origin account balance on database");
            accountService.saveAccount(originAccount);
            log.info("Update target account balance on database");
            accountService.saveAccount(targetAccount);
            log.info("Create transaction registry on database");
            return transactionRepository.
                    save(new Transaction(transactionValue, TRANSFER_TRANSACTION, originAccount, targetAccount, new Date()));
        } catch (Exception e) {
            log.error("Error to update accounts balance", e);
            throw new PerformMoneyAccountTransferTransactionException();
        }
    }

    private BigDecimal debitValueIntoOriginAccountBalance(final BigDecimal originAccountBalance, final BigDecimal transactionValue) {
        log.info("Debit value into origin account");
        return originAccountBalance.subtract(transactionValue).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal addValueIntoTargetAccountBalance(final BigDecimal targetAccountBalance, final BigDecimal transactionValue) {
        log.info("Add value into origin account");
        return targetAccountBalance.add(transactionValue).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private void originAccountHasBalanceToAccountTransfer(final Account originAccount, final BigDecimal valueToTransfer) {
        if (originAccount.getAccountBalance().compareTo(valueToTransfer) == -1) {
            log.error("Origin Client account balance: {} is not enough to transfer this value: {}", originAccount.getAccountBalance(), valueToTransfer);
            throw new OriginAccountBalanceIsNotEnoughException();
        }
    }

}
