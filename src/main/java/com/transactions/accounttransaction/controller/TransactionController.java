package com.transactions.accounttransaction.controller;

import com.transactions.accounttransaction.entity.Transaction;
import com.transactions.accounttransaction.service.TransactionService;
import com.transactions.accounttransaction.vo.PerformMoneyTransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/doTransferMoney", method = RequestMethod.POST)
    public Transaction doTransferMoney(final @RequestBody PerformMoneyTransferVO performMoneyTransferVO) {
        return transactionService.performMoneyTransferTransaction(performMoneyTransferVO);
    }

}
