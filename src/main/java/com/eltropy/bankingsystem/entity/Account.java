package com.eltropy.bankingsystem.entity;

import lombok.*;
import javax.persistence.*;

import com.google.gson.annotations.SerializedName;
import java.util.Date;


@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private Long customerId;
    private Double interestRate;
    private Type type;
    private Status status;
    private Double amount;
    
    public enum Type {
    	SAVING, SALARY, LOAN, CURRUNT_ACCOUNT
    }
    
    public enum Status {
    	ACTIVE, BLOCKED, INACTIVE;
    }

}
