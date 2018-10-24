package com.transactions.accounttransaction.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PerformMoneyTransferVO implements Serializable {

    private BigDecimal transactionValue;

    private int originAccountId;

    private int targetAccountId;

}
