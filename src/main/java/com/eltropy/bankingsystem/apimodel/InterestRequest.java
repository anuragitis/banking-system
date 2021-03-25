package com.eltropy.bankingsystem.apimodel;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterestRequest {

    private Long accountId;
    private Integer timeInYears;
}
