package com.newDemom.BudgetApplication.Service.Impl;

import com.newDemom.BudgetApplication.Domain.Group;
import com.newDemom.BudgetApplication.Domain.Transaction;
import com.newDemom.BudgetApplication.Domain.UserEntity;
import com.newDemom.BudgetApplication.Exception.BlogAPIException;
import com.newDemom.BudgetApplication.Exception.ResourceNotFoundException;
import com.newDemom.BudgetApplication.Repository.GroupRepository;
import com.newDemom.BudgetApplication.Repository.TransactionRepository;
import com.newDemom.BudgetApplication.Service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private GroupRepository groupRepository;

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(GroupRepository groupRepository,
                                  TransactionRepository transactionRepository) {
        this.groupRepository = groupRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getGroupTransactions(long groupId, UserEntity currentUser) {
        Group getGroup = findGroup(groupId, currentUser);
        return getGroup.getTransactions().stream().
                collect(Collectors.toList());
    }

    @Override
    public Transaction createTransaction(long groupId,
                                         Transaction transaction, UserEntity currentUser) {
        Group getGroup = findGroup(groupId, currentUser);
        Transaction newTransaction = new Transaction();
        Set<Group> groups = newTransaction.getGroups();
        newTransaction.setUser(currentUser);
        newTransaction.setName(transaction.getName());
        newTransaction.setAmount(transaction.getAmount());
        groups.add(getGroup);
        newTransaction.setGroups(groups);
        transactionRepository.save(newTransaction);
        return newTransaction;

    }

    @Override
    public Transaction updateTransaction(long groupId,
                                         long transId,
                                         Transaction transaction, UserEntity currentUser) {
        Transaction fetchTransaction  = findTransaction(transId, groupId,currentUser);
        fetchTransaction.setAmount(transaction.getAmount());
        fetchTransaction.setName(transaction.getName());
        transactionRepository.save(fetchTransaction);
        return fetchTransaction;
    }

    @Override
    public void deleteTransaction(long groupId, long transId, UserEntity currentUser) {
        Transaction fetchTransaction  = findTransaction(transId, groupId,currentUser);
        transactionRepository.delete(fetchTransaction);
    }

    private Group findGroup(long groupId, UserEntity currentUser) {
        Group getGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new ResourceNotFoundException(groupId, "id", "Group"));
        if (getGroup.getUser().getId() != currentUser.getId()) {
            throw new BlogAPIException(String.format("No group with id %s for this user", groupId),
                    HttpStatus.BAD_REQUEST);
        }
        return getGroup;
    }

    private Transaction findTransaction(long transId, long groupId, UserEntity currentUser) {
        Group findGroup = findGroup(groupId, currentUser);
        Set<Transaction> groupTransactions = findGroup.getTransactions();
        Transaction getTrans = transactionRepository.findById(transId).orElseThrow(() ->
                new ResourceNotFoundException(transId, "id", "Transaction"));
        if (getTrans.getUser().getId() != currentUser.getId()) {
            throw new BlogAPIException(String.format("No transaction with id %s for this user", transId),
                    HttpStatus.BAD_REQUEST);
        }
        if(!groupTransactions.contains(getTrans)) {
            throw new BlogAPIException(String.
                    format("Transaction with id: %s does not belong to group with id: %s", transId, groupId),
                    HttpStatus.BAD_REQUEST);
        }
        return getTrans;
    }
}
