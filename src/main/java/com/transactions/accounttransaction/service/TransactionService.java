package com.transactions.accounttransaction.service;

import com.transactions.accounttransaction.entity.Account;
import com.transactions.accounttransaction.entity.Transaction;
import com.transactions.accounttransaction.exception.OriginAccountBalanceIsNotEnoughException;
import com.transactions.accounttransaction.exception.OriginAccountNotFoundException;
import com.transactions.accounttransaction.exception.PerformMoneyAccountTransferTransactionException;
import com.transactions.accounttransaction.exception.TargetAccountNotFoundException;
import com.transactions.accounttransaction.repository.TransactionRepository;
import com.transactions.accounttransaction.vo.PerformMoneyTransferVO;
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

    public Transaction performMoneyTransferTransaction(final PerformMoneyTransferVO performMoneyTransferVO) {
        log.info("perform money account transaction between account origin account id: {} and target account id: {}",
                performMoneyTransferVO.getOriginAccountId(), performMoneyTransferVO.getTargetAccountId());

        Account originAccount = accountService.findAccountByAccountId(performMoneyTransferVO.getOriginAccountId());
        if (Objects.isNull(originAccount)) {
            log.error("Origin account not found with this account id: {}", performMoneyTransferVO.getOriginAccountId());
            throw new OriginAccountNotFoundException(String.format("Origin account not found with this account id: %d", performMoneyTransferVO.getOriginAccountId()));
        }

        Account targetAccount = accountService.findAccountByAccountId(performMoneyTransferVO.getTargetAccountId());
        if (Objects.isNull(targetAccount)) {
            log.error("Target account not found with this account id: {}", performMoneyTransferVO.getTargetAccountId());
            throw new TargetAccountNotFoundException(String.format("Target account not found with this account id: %d", performMoneyTransferVO.getTargetAccountId()));
        }

        originAccountHasBalanceToAccountTransfer(originAccount, performMoneyTransferVO.getTransactionValue());
        return doMoneyTransfer(originAccount, targetAccount, performMoneyTransferVO.getTransactionValue());
    }

    @Transactional
    private Transaction doMoneyTransfer(final Account originAccount, final Account targetAccount, final BigDecimal transactionValue) {
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
            throw new PerformMoneyAccountTransferTransactionException("Error to update accounts balance");
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
            log.error("Origin Client account balance: {} is not enough to transfer this value: {}", originAccount.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP), valueToTransfer.setScale(2, BigDecimal.ROUND_HALF_UP));
            throw new OriginAccountBalanceIsNotEnoughException(String.format("Origin Client account balance: %.2f is not enough to transfer this value: %.2f", originAccount.getAccountBalance(), valueToTransfer));
        }
    }

}
