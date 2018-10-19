package com.transactions.accounttransaction.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OriginAccountBalanceIsNotEnoughException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OriginAccountBalanceIsNotEnoughException(final String message) {
        super(message);
    }
}
