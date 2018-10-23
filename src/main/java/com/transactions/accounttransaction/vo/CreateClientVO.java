package com.transactions.accounttransaction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientVO implements Serializable {

    @NotNull
    private int clientId;

    @NotNull
    private String email;

}
