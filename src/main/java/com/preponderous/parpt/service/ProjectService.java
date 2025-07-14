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

    public Project createProject(String name, String description, int impact, int confidence, int ease, int reach, int effort) throws ProjectRepository.NameTakenException {
        Project project = Project.builder()
                .name(name)
                .description(description)
                .impact(impact)
                .confidence(confidence)
                .ease(ease)
                .reach(reach)
                .effort(effort)
                .build();
        projectRepository.add(project);
        return project;
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
}
