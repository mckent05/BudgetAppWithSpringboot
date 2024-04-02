package com.newDemom.BudgetApplication.Controllers;


import com.newDemom.BudgetApplication.Domain.Dto.GroupDto;
import com.newDemom.BudgetApplication.Domain.Group;
import com.newDemom.BudgetApplication.Mapper.Impl.GroupMapper;
import com.newDemom.BudgetApplication.Service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public GroupController(GroupMapper groupMapper, GroupService groupService) {
        this.groupMapper = groupMapper;
        this.groupService = groupService;
    }


    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        var allGroups = groups.stream().map(groupMapper::MapTo).
                collect(Collectors.toList());
        return new ResponseEntity<>(allGroups, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createNewGroup(@Valid @RequestBody GroupDto groupDto) {
        Group mappedGroup = groupMapper.MapFrom(groupDto);
        Group savedGroup = groupService.createGroup(mappedGroup);
        GroupDto groupDto1 = groupMapper.MapTo(savedGroup);
        return new ResponseEntity<>(groupDto1, HttpStatus.CREATED);
    }
}
