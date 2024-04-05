package com.newDemom.BudgetApplication.Service;

import com.newDemom.BudgetApplication.Domain.Transaction;
import com.newDemom.BudgetApplication.Domain.UserEntity;

import java.util.List;

public interface TransactionService {

    public List<Transaction> getGroupTransactions(long groupId, UserEntity currentUser);

    public Transaction createTransaction(long groupId, Transaction transaction, UserEntity currentUser);

    Transaction updateTransaction(long groupId, long transId, Transaction transaction, UserEntity currentUser);
    void deleteTransaction(long groupId, long transId, UserEntity currentUser);
}
