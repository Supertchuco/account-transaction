package com.transactions.accounttransaction.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreateAccountVO implements Serializable {

    @NotNull
    private int accountId;

    @NotNull
    private int clientId;

    private BigDecimal accountBalance;

}
