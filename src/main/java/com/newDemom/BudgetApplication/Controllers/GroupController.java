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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<GroupDto>> getAllGroups(Authentication authentication) {
        UserEntity currentUser = userService.findByEmail(authentication.getName());
        List<Group> groups = groupService.getAllGroups(currentUser);
        var allGroups = groups.stream().map(groupMapper::MapTo).
                collect(Collectors.toList());
        return new ResponseEntity<>(allGroups, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createNewGroup(@Valid @RequestBody GroupDto groupDto,
                                                   Authentication authentication) {
        Group mappedGroup = groupMapper.MapFrom(groupDto);
        UserEntity currentUser = userService.findByEmail(authentication.getName());
        Group savedGroup = groupService.createGroup(mappedGroup, currentUser);
        GroupDto groupDto1 = groupMapper.MapTo(savedGroup);
        return new ResponseEntity<>(groupDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> updateGroup(
            @Valid @RequestBody GroupDto groupDto,
            Authentication authentication, @PathVariable("id") Long id

    ) {
        Group group = groupMapper.MapFrom(groupDto);
        UserEntity currentUser = userService.findByEmail(authentication.getName());
        Group savedGroup = groupService.updateGroupDetails(id, group, currentUser);
        return new ResponseEntity<>(groupMapper.MapTo(savedGroup), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(
            Authentication authentication,
            @PathVariable("id") long id

    ) {
        UserEntity currentUser = userService.findByEmail(authentication.getName());
        groupService.deleteGroup(id, currentUser);
        return new ResponseEntity<>("Group deleted", HttpStatus.OK);
    }
}
