package com.newDemom.BudgetApplication.Service;

import com.newDemom.BudgetApplication.Domain.Token;
import com.newDemom.BudgetApplication.Domain.UserEntity;

import java.util.List;

public interface TokenService {

    public void createToken(String jwt, UserEntity userEntity);

    public List<Token> getAllUserTokens(Long userId);

    public void saveAllTokens(List<Token> token);

    public void logOutToken(String jwtToken);
    public boolean isTokenValidate(String token);
}
