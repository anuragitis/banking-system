package com.eltropy.bankingsystem.entity;

import lombok.*;
import javax.persistence.*;

import com.eltropy.bankingsystem.entity.Employee.EmployeeStatus;
import com.google.gson.annotations.SerializedName;
import java.util.Date;


@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @NonNull
    @SerializedName("first_name")
    @Column(name = "first_name")
    private String firstName;
    
    @SerializedName("middle_name")
    @Column(name = "middle_name")
    private String middleName;

    @NonNull
    @SerializedName("last_name")
    @Column(name = "last_name")
    private String lastName;

    @SerializedName("email")
    @Column(name = "email", unique = true)
    private String email;

    @SerializedName("phone")
    @Column(name = "phone", unique = true)
    private String phone;
    
    @SerializedName("address")
    @Column(name = "address")
    private String address;
    
    private String kyc;
    
    @SerializedName("status")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
    
    public enum CustomerStatus {
    	ACTIVE, BLOCKED, INACTIVE;
    }

}
