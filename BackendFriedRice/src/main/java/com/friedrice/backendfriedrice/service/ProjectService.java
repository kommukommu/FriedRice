package com.friedrice.backendfriedrice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.friedrice.backendfriedrice.pojo.Project;

import java.util.List;

public interface ProjectService extends IService<Project> {
    public List<Project> getProjectsByNameLike(String name);
    public List<Project> getProjectsByOwnerList(List<Integer> owners);
    public List<Project> getProjectsByOwnerList(Integer... owners);
}
