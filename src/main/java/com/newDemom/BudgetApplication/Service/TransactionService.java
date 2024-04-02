package com.newDemom.BudgetApplication.Service;

import com.newDemom.BudgetApplication.Domain.Transaction;

import java.util.List;

public interface TransactionService {

    public List<Transaction> getGroupTransactions(long groupId);

    public Transaction createTransaction(long groupId, Transaction transaction);
}
