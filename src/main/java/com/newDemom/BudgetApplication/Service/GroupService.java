package com.newDemom.BudgetApplication.Service;

import com.newDemom.BudgetApplication.Domain.Group;

import java.util.List;

public interface GroupService {

    public List<Group> getAllGroups();

    public Group createGroup(Group group);
}
