package com.newDemom.BudgetApplication.Service;

import com.newDemom.BudgetApplication.Domain.Dto.LoginDto;
import com.newDemom.BudgetApplication.Domain.Dto.RegisterDto;

public interface AuthService {

    public String login(LoginDto loginDto);

    public String register(RegisterDto registerDto);
}
