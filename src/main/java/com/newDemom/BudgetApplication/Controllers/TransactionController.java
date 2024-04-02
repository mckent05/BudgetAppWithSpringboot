package com.newDemom.BudgetApplication.Controllers;

import com.newDemom.BudgetApplication.Domain.Dto.TransactionDto;
import com.newDemom.BudgetApplication.Domain.Transaction;
import com.newDemom.BudgetApplication.Mapper.Impl.TransactionMapper;
import com.newDemom.BudgetApplication.Service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/groups/{id}")
public class TransactionController {

    private TransactionMapper transactionMapper;

    private TransactionService transactionService;

    public TransactionController(TransactionMapper transactionMapper,
                                 TransactionService transactionService) {
        this.transactionMapper = transactionMapper;
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionDto> createTransaction(
            @PathVariable("id") long groupId,
            @RequestBody TransactionDto transactionDto){
        Transaction transaction = transactionMapper.MapFrom(transactionDto);
        var savedTransaction = transactionService.createTransaction(groupId, transaction);
        TransactionDto mappedTransactionDto = transactionMapper.MapTo(savedTransaction);
        return new ResponseEntity<>(mappedTransactionDto, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getGroupTransaction(
            @PathVariable("id") long groupId
    ){
        var getTransactions = transactionService.getGroupTransactions(groupId);
        return new ResponseEntity<>(
                getTransactions.stream().map(transactionMapper::MapTo).collect(Collectors.toList()),
                HttpStatus.CREATED
        );
    }
}
