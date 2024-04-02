package com.newDemom.BudgetApplication.Domain.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionDto {
    private String name;

    private long amount;

}
