package com.newDemom.BudgetApplication.Service.Impl;

import com.newDemom.BudgetApplication.Domain.Group;
import com.newDemom.BudgetApplication.Domain.Transaction;
import com.newDemom.BudgetApplication.Exception.ResourceNotFoundException;
import com.newDemom.BudgetApplication.Repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements com.newDemom.BudgetApplication.Service.TransactionService {

    private GroupRepository groupRepository;

    public TransactionServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Transaction> getGroupTransactions(long groupId) {
        Group getGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new ResourceNotFoundException(groupId, "id", "Group" ));
        Set<Transaction> transactions = getGroup.getTransactions();
        return transactions.stream().collect(Collectors.toList());
    }

    @Override
    public Transaction createTransaction(long groupId,
                                            Transaction transaction ) {
        Group getGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new ResourceNotFoundException(groupId, "id", "Group" ));
        Set<Transaction> transactions = getGroup.getTransactions();
        transactions.add(transaction);
        getGroup.setTransactions(transactions);
        groupRepository.save(getGroup);
        return transaction;

    }
}
