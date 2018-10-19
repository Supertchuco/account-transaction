package com.transactions.accounttransaction.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountdAlreadyExistOnDatabaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountdAlreadyExistOnDatabaseException(final String message) {
        super(message);
    }
}
