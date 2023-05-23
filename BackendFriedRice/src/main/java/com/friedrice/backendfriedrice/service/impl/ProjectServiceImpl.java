package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.ProjectMapper;
import com.friedrice.backendfriedrice.pojo.Project;
import com.friedrice.backendfriedrice.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Override
    public List<Project> getProjectsByNameLike(String name) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), Project::getName, name);
        return this.list(queryWrapper);
    }

    @Override
    public List<Project> getProjectsByOwnerList(List<Integer> owners) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(owners != null, Project::getOwner, owners);
        return this.list(queryWrapper);
    }

    @Override
    public List<Project> getProjectsByOwnerList(Integer... owners) {
        return this.getProjectsByOwnerList(Arrays.asList(owners));
    }
}
