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
        Group getGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new ResourceNotFoundException(groupId, "id", "Group" ));
        if (getGroup.getUser().getId() != currentUser.getId()) {
            throw new BlogAPIException(String.format("No group with id %s for this user", groupId),
                    HttpStatus.BAD_REQUEST);
        }
        return getGroup.getTransactions().stream().collect(Collectors.toList());
    }

    @Override
    public Transaction createTransaction(long groupId,
                                         Transaction transaction, UserEntity currentUser) {
        Group getGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new ResourceNotFoundException(groupId, "id", "Group" ));
        Transaction newTransaction = new Transaction();
        Set<Group> groups = newTransaction.getGroups();
        newTransaction.setUser(currentUser);
        newTransaction.setName(transaction.getName());
        newTransaction.setAmount(transaction.getAmount());
        groups.add(getGroup);
        newTransaction.setGroups(groups);
        transactionRepository.save(newTransaction);
//        System.out.println(getGroup.getTransactions());
        return newTransaction;

    }
}
