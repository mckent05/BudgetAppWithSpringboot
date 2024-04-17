package com.newDemom.BudgetApplication.Controllers;

import com.newDemom.BudgetApplication.Domain.Dto.LoginDto;
import com.newDemom.BudgetApplication.Domain.Dto.RegisterDto;
import com.newDemom.BudgetApplication.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signin")
    public ResponseEntity<String> handleSignIn(@RequestBody LoginDto loginDto){
        String response = authService.login(loginDto);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("register")
    public ResponseEntity<String> handleRegister(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
