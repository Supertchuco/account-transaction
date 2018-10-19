package com.transactions.accounttransaction.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SaveAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SaveAccountException(final String message) {
        super(message);
    }
}
