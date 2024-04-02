package com.newDemom.BudgetApplication.Domain.Dto;

import com.newDemom.BudgetApplication.Domain.Transaction;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GroupDto {
    @NotEmpty
    @Size(min = 2)
    private String name;

    @NotEmpty
    private String icon;

    private Set<Transaction> transactions = new HashSet<>();
}
