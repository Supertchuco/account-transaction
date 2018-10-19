package com.transactions.accounttransaction.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClientNotFoundException(final String message) {
        super(message);
    }
}
