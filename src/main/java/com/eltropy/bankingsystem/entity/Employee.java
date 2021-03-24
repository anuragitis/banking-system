package com.eltropy.bankingsystem.entity;

import lombok.*;
import javax.persistence.*;
import com.google.gson.annotations.SerializedName;
import java.util.Date;


@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
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
    
    @SerializedName("date_of_joining")
    @Column(name = "date_of_joining")
    private Date dateOfJoining;
    
    @SerializedName("status")
    @Column(name = "status")
    private EmployeeStatus status;
    
    @SerializedName("address")
    @Column(name = "address")
    private String address;
    
    public enum EmployeeStatus {
    	ACTIVE, BLOCKED, INACTIVE;
    }

}
