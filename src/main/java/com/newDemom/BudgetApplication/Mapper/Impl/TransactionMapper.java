package com.newDemom.BudgetApplication.Mapper.Impl;

import com.newDemom.BudgetApplication.Domain.Dto.TransactionDto;
import com.newDemom.BudgetApplication.Domain.Transaction;
import com.newDemom.BudgetApplication.Mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionDto> {

    private ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionDto MapTo(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public Transaction MapFrom(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }
}
