package com.transactions.accounttransaction.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PerformMoneyAccountTransferTransactionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PerformMoneyAccountTransferTransactionException(final String message) {
        super(message);
    }
}
