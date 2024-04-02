package com.newDemom.BudgetApplication.Repository;

import com.newDemom.BudgetApplication.Domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
            select t from Token t inner join userEntity u on t.userEntity.id = u.id
            where u.id = :userId and (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllUserTokens(Long userId);

    Optional<Token> findByToken(String token);
}
