package com.newDemom.BudgetApplication.Security;

import com.newDemom.BudgetApplication.Domain.UserEntity;
import com.newDemom.BudgetApplication.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow
                (() -> new UsernameNotFoundException("User not found, please try again"));

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("User"));

        return new
                User(user.getEmail(), user.getPassword(), authorities);
    }
}
