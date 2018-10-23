package com.transactions.accounttransaction.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TargetAccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TargetAccountNotFoundException(final String message) {
        super(message);
    }
}
