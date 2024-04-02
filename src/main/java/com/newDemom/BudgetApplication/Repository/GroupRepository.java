package com.newDemom.BudgetApplication.Repository;

import com.newDemom.BudgetApplication.Domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
