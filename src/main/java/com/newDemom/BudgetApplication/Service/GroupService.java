package com.newDemom.BudgetApplication.Service;

import com.newDemom.BudgetApplication.Domain.Group;
import com.newDemom.BudgetApplication.Domain.UserEntity;

import java.util.List;

public interface GroupService {

    public List<Group> getAllGroups(UserEntity currentUser);

    public Group createGroup(Group group, UserEntity user);

    Group updateGroupDetails(Long id, Group group, UserEntity currentUser);

    void deleteGroup(long id, UserEntity currentUser);
}
