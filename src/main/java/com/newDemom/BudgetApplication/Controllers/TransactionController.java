package com.newDemom.BudgetApplication.Controllers;

import com.newDemom.BudgetApplication.Domain.Dto.TransactionDto;
import com.newDemom.BudgetApplication.Domain.Transaction;
import com.newDemom.BudgetApplication.Domain.UserEntity;
import com.newDemom.BudgetApplication.Mapper.Impl.TransactionMapper;
import com.newDemom.BudgetApplication.Service.TransactionService;
import com.newDemom.BudgetApplication.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/groups/{id}")
public class TransactionController {

    private TransactionMapper transactionMapper;

    private TransactionService transactionService;

    private UserService userService;

    public TransactionController(TransactionMapper transactionMapper,
                                 TransactionService transactionService,
                                 UserService userService) {
        this.transactionMapper = transactionMapper;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionDto> createTransaction(
            @PathVariable("id") long groupId,
            @RequestBody TransactionDto transactionDto,
            Authentication authentication){
        UserEntity currentUser = userService.findByEmail(authentication.getName());
        Transaction transaction = transactionMapper.MapFrom(transactionDto);
        var savedTransaction = transactionService.createTransaction(groupId, transaction,
                currentUser);
        TransactionDto mappedTransactionDto = transactionMapper.MapTo(savedTransaction);
        return new ResponseEntity<>(mappedTransactionDto, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getGroupTransaction(
            @PathVariable("id") long groupId,
            Authentication authentication
    ){
        UserEntity currentUser = userService.findByEmail(authentication.getName());
        var getTransactions = transactionService.getGroupTransactions(groupId, currentUser);
        return new ResponseEntity<>(
                getTransactions.stream().map(transactionMapper::MapTo).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

}
