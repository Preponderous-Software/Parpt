package com.preponderous.parpt.service;

import com.preponderous.parpt.domain.Project;
import com.preponderous.parpt.repo.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void createProject(String name, String descriptionA, int i, int i1, int i2, int i3, int i4) {
        Project project = Project.builder()
                .name(name)
                .description(descriptionA)
                .impact(i)
                .confidence(i1)
                .ease(i2)
                .reach(i3)
                .effort(i4)
                .build();
        projectRepository.add(project);
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
}
