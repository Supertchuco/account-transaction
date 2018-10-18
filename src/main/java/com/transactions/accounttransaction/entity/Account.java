package com.transactions.accounttransaction.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity(name = "Account")
@Table(name = "Account")
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    @Id
    @Column
    @GeneratedValue
    private int accountId;

    @Column
    private BigDecimal accountBalance;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;
}