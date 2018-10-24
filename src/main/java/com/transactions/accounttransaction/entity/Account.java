package com.transactions.accounttransaction.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "Account")
@Table(name = "Account")
@AllArgsConstructor
public class Account implements Serializable {

    @Id
    @Column
    private int accountId;

    @Column
    private BigDecimal accountBalance;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;

    @Column
    private Date createDate;

    public Account(final int accountId, final Client client) {
        this.accountId = accountId;
        this.client = client;
        this.accountBalance = new BigDecimal("00.00");
        this.createDate = new Date();
    }

    public Account(final int accountId, final Client client, final BigDecimal accountBalance) {
        this.accountId = accountId;
        this.client = client;
        this.accountBalance = accountBalance;
        this.createDate = new Date();
    }

}
