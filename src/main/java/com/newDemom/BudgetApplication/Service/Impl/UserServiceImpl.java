package com.newDemom.BudgetApplication.Service.Impl;

import com.newDemom.BudgetApplication.Domain.UserEntity;
import com.newDemom.BudgetApplication.Exception.BlogAPIException;
import com.newDemom.BudgetApplication.Exception.ResourceNotFoundException;
import com.newDemom.BudgetApplication.Repository.UserRepository;
import com.newDemom.BudgetApplication.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return  userRepository.findByEmail(email).orElseThrow(
                () -> new BlogAPIException("Invalid Email", HttpStatus.BAD_REQUEST)
        );
    }
}
