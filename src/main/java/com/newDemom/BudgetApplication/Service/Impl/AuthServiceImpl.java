package com.newDemom.BudgetApplication.Service.Impl;

import com.newDemom.BudgetApplication.Domain.Dto.LoginDto;
import com.newDemom.BudgetApplication.Domain.Dto.RegisterDto;
import com.newDemom.BudgetApplication.Domain.Token;
import com.newDemom.BudgetApplication.Domain.UserEntity;
import com.newDemom.BudgetApplication.Exception.BlogAPIException;
import com.newDemom.BudgetApplication.Repository.UserRepository;
import com.newDemom.BudgetApplication.Security.JwtTokenProvider;
import com.newDemom.BudgetApplication.Service.AuthService;
import com.newDemom.BudgetApplication.Service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    AuthenticationManager authenticationManager;
    UserRepository userRepository;

    private JwtTokenProvider jwtTokenProvider;

    private TokenService tokenService;

    PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenService = tokenService;

    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtTokenProvider.generateToken(authenticate);
        UserEntity user = userRepository.findByUserNameOrEmail(loginDto.getUserNameOrEmail(),
                loginDto.getUserNameOrEmail()).orElseThrow(() ->
                new BlogAPIException("Invalid username or Email", HttpStatus.BAD_REQUEST));
        revokeAlluserTokens(user);
        tokenService.createToken(token, user);

//        System.out.println(loginDto.getUserNameOrEmail());
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return token;

    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException("Email already exists!", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUserName(registerDto.getUserName())){
            throw new BlogAPIException("Username already exists!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setName(registerDto.getName());
        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

//        Set<RoleEntity> roles = new HashSet<>();
//        user.setRoles(roles);
        userRepository.save(user);
        return "User successfully signed up";

    }

    private void revokeAlluserTokens(UserEntity user) {

        List<Token> allUserTokens = tokenService.getAllUserTokens(user.getId());
        if(allUserTokens.isEmpty()){
            return;
        }
        allUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenService.saveAllTokens(allUserTokens);

    }
}
