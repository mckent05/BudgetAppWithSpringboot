package com.newDemom.BudgetApplication.Service.Impl;


import com.newDemom.BudgetApplication.Domain.Group;
import com.newDemom.BudgetApplication.Repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements com.newDemom.BudgetApplication.Service.GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Group createGroup(Group group) {
        groupRepository.save(group);
        return group;
    }
}
