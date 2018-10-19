package com.transactions.accounttransaction.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SaveClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SaveClientException(final String message) {
        super(message);
    }
}
