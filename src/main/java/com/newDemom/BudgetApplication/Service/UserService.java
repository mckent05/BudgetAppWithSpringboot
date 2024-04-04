package com.newDemom.BudgetApplication.Service;


import com.newDemom.BudgetApplication.Domain.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    UserEntity findByEmail(String email);

}
