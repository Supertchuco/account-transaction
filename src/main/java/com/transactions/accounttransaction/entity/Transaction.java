package com.transactions.accounttransaction.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "Transaction")
@Table(name = "Transaction")
public class Transaction implements Serializable {

    @Id
    @Column
    @GeneratedValue
    private int transactionId;

    @Column
    private BigDecimal transactionValue;

    @Column
    private String operation;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "accountId", nullable = false)
    private Account originAccount;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "accountId", nullable = false)
    private Account targetAccount;

    @Column
    private Date createDate;

    public Transaction(final BigDecimal transactionValue, final String operation, final Account originAccount, final Account targetAccount, final Date createDate) {
        this.transactionValue = transactionValue;
        this.operation = operation;
        this.originAccount = originAccount;
        this.targetAccount = targetAccount;
        this.createDate = createDate;
    }

}
