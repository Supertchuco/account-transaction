package com.transactions.accounttransaction.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientIdAlreadyExistOnDatabaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClientIdAlreadyExistOnDatabaseException(final String message) {
        super(message);
    }
}
