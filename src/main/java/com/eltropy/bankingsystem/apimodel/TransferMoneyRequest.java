package com.eltropy.bankingsystem.apimodel;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyRequest {

    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
}
