package com.newDemom.BudgetApplication.Repository;

import com.newDemom.BudgetApplication.Domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
  List<Group> findByUserId(Long userId);

}
