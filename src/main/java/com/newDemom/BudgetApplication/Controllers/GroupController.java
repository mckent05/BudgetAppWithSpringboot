package com.newDemom.BudgetApplication.Controllers;


import com.newDemom.BudgetApplication.Domain.Dto.GroupDto;
import com.newDemom.BudgetApplication.Domain.Group;
import com.newDemom.BudgetApplication.Domain.UserEntity;
import com.newDemom.BudgetApplication.Mapper.Impl.GroupMapper;
import com.newDemom.BudgetApplication.Service.GroupService;
import com.newDemom.BudgetApplication.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Controller
@RequestMapping("/api/groups")
public class GroupController {

    private GroupMapper groupMapper;

    private GroupService groupService;

    private UserService userService;

    public GroupController(GroupMapper groupMapper, GroupService groupService,
                           UserService userService) {
        this.groupMapper = groupMapper;
        this.groupService = groupService;
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        var currentUserEmail = SecurityContextHolder.
                getContext().getAuthentication().getName();
        UserEntity currentUser = userService.findByEmail(currentUserEmail);
        List<Group> groups = groupService.getAllGroups(currentUser);
        var allGroups = groups.stream().map(groupMapper::MapTo).
                collect(Collectors.toList());
        return new ResponseEntity<>(allGroups, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createNewGroup(@Valid @RequestBody GroupDto groupDto) {
        Group mappedGroup = groupMapper.MapFrom(groupDto);
        var currentUserEmail = SecurityContextHolder.
                getContext().getAuthentication().getName();
        UserEntity currentUser = userService.findByEmail(currentUserEmail);
        Group savedGroup = groupService.createGroup(mappedGroup, currentUser);
        GroupDto groupDto1 = groupMapper.MapTo(savedGroup);
        return new ResponseEntity<>(groupDto1, HttpStatus.CREATED);
    }
}
