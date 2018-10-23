package com.transactions.accounttransaction.controller;

import com.transactions.accounttransaction.entity.Client;
import com.transactions.accounttransaction.service.AccountService;
import com.transactions.accounttransaction.vo.CreateAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public Client createAccount(final @RequestBody CreateAccountVO createAccountVO) {
        return accountService.createAccount(createAccountVO);
    }
}
