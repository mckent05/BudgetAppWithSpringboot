package com.newDemom.BudgetApplication.Service.Impl;


import com.newDemom.BudgetApplication.Domain.Group;
import com.newDemom.BudgetApplication.Domain.UserEntity;
import com.newDemom.BudgetApplication.Exception.BlogAPIException;
import com.newDemom.BudgetApplication.Exception.ResourceNotFoundException;
import com.newDemom.BudgetApplication.Repository.GroupRepository;
import com.newDemom.BudgetApplication.Service.GroupService;
import org.springframework.http.HttpStatus;
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
        return groupRepository.findByUserId(currentUser.getId()).
                stream().collect(Collectors.toList());
    }

    @Override
    public Group createGroup(Group group, UserEntity user) {
        Group newGroup = new Group();
        newGroup.setIcon(group.getIcon());
        newGroup.setUser(user);
        newGroup.setName(group.getName());
        groupRepository.save(newGroup);;
        return newGroup;
    }

    @Override
    public Group updateGroupDetails(Long id, Group group, UserEntity currentUser) {
        Group findGroup = findGroup(id, currentUser);
        findGroup.setName(group.getName());
        findGroup.setIcon(group.getIcon());
        groupRepository.save(findGroup);
        return findGroup;
    }

    @Override
    public void deleteGroup(long id, UserEntity currentUser) {
        Group findGroup = findGroup(id, currentUser);
        groupRepository.delete(findGroup);
    }


    private Group findGroup(long id, UserEntity currentUser) {
        Group findGroup = groupRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(id, "group", "Group"));
        if(findGroup.getUser().getId() != currentUser.getId()) {
            throw new BlogAPIException("You are not the owner of this group",
                    HttpStatus.BAD_REQUEST);
        }
        return findGroup;
    }

}
