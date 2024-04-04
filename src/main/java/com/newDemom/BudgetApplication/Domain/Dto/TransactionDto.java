package com.newDemom.BudgetApplication.Domain.Dto;

import com.newDemom.BudgetApplication.Domain.Group;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionDto {

    private long id;
    private String name;

    private long amount;

}
