package com.eltropy.bankingsystem.entity;


import lombok.*;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    private String updatedBy;

}
