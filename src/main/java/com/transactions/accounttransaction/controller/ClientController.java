package com.transactions.accounttransaction.controller;

import com.transactions.accounttransaction.entity.Client;
import com.transactions.accounttransaction.service.ClientService;
import com.transactions.accounttransaction.vo.CreateClientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/createClient", method = RequestMethod.POST)
    public Client createClient(final @RequestBody CreateClientVO createClientVO) {
        return clientService.createClient(createClientVO);
    }
}
