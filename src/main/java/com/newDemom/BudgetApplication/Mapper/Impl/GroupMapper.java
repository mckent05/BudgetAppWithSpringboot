package com.newDemom.BudgetApplication.Mapper.Impl;

import com.newDemom.BudgetApplication.Domain.Dto.GroupDto;
import com.newDemom.BudgetApplication.Mapper.Mapper;
import com.newDemom.BudgetApplication.Domain.Group;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper implements Mapper<Group, GroupDto> {

    private ModelMapper modelMapper;

    public GroupMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GroupDto MapTo(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }

    @Override
    public Group MapFrom(GroupDto groupDto) {
        return modelMapper.map(groupDto, Group.class);
    }
}
