package com.newDemom.BudgetApplication.Repository;

import com.newDemom.BudgetApplication.Domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
