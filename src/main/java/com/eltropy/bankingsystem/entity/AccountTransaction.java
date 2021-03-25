package com.eltropy.bankingsystem.entity;

import lombok.*;
import javax.persistence.*;

import com.google.gson.annotations.SerializedName;
import java.util.Date;


@Entity
@Table(name = "account_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_transaction_id")
    private Long id;

    private Long accountId;
    private Double openningBalance;
    private Double closingBalance;
    private Type type;
    private Double amount;
    private Date transactionDate;
    
    public enum Type {
    	DEBIT, CREDIT, INTEREST
    }

}
