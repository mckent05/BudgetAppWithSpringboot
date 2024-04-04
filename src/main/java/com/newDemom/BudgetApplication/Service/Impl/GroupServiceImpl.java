package com.newDemom.BudgetApplication.Service.Impl;


import com.newDemom.BudgetApplication.Domain.Group;
import com.newDemom.BudgetApplication.Domain.UserEntity;
import com.newDemom.BudgetApplication.Repository.GroupRepository;
import com.newDemom.BudgetApplication.Service.GroupService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAllGroups(UserEntity currentUser) {
        System.out.println(currentUser.getGroups());
        return groupRepository.findByUserId(currentUser.getId()).
                stream().collect(Collectors.toList());
    }

    @Override
    public Group createGroup(Group group, UserEntity user) {
        Group newGroup = new Group();
        newGroup.setIcon(group.getIcon());
        newGroup.setUser(user);
        newGroup.setName(group.getName());
        groupRepository.save(newGroup);
        System.out.println(newGroup.getTransactions());
        return newGroup;
    }
}
