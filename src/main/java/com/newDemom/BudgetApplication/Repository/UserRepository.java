package com.newDemom.BudgetApplication.Repository;

import com.newDemom.BudgetApplication.Domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserNameOrEmail(String userName, String Email);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

}
