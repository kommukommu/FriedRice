package com.friedrice.backendfriedrice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.friedrice.backendfriedrice.mapper.ProjectMapper;
import com.friedrice.backendfriedrice.pojo.Project;
import com.friedrice.backendfriedrice.service.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
}
