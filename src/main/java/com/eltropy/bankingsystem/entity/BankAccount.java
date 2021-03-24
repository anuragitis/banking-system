package com.aditya.banking.system.demo.entity.dao;


import com.aditya.banking.system.demo.entity.constant.TableName;
import com.aditya.banking.system.demo.entity.constant.enums.BankAccountStatus;
import com.aditya.banking.system.demo.entity.constant.enums.BankAccountType;
import com.aditya.banking.system.demo.entity.dao.common.BaseEntity;
import lombok.*;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Entity
@Table(name = TableName.BANK_ACCOUNT)
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount extends BaseEntity {

    private static final long serialVersionUID = -6251308367342483048L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long id;

    @Column(unique = true)
    private Long number;

    private Double withdrawalAmount;
    private Double depositAmount;
    private Double closingBalance;
    private Double interestRate;
    private BankAccountType type;
    private BankAccountStatus status;
    private long bankBranchId;


    public Long getNumber() {
        return number;
    }

    public Double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(Double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public BankAccountType getType() {
        return type;
    }

    public BankAccountStatus getStatus() {
        return status;
    }

    public long getBankBranchId() {
        return bankBranchId;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public void setType(BankAccountType type) {
        this.type = type;
    }

    public void setStatus(BankAccountStatus status) {
        this.status = status;
    }

    public void setBankBranchId(long bankBranchId) {
        this.bankBranchId = bankBranchId;
    }
}
