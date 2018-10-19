package com.transactions.accounttransaction.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OriginAccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OriginAccountNotFoundException(final String message) {
        super(message);
    }
}
