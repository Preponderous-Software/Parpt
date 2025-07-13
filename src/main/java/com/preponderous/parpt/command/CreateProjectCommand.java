package com.preponderous.parpt.command;

import com.preponderous.parpt.service.ProjectService;

public class CreateProjectCommand {
    private final ProjectService projectService;

    public CreateProjectCommand(ProjectService projectService) {
        this.projectService = projectService;
    }

    public String execute(String projectName, String projectDescription, int priority, int complexity, int risk, int effort, int value) {
        // Validate input parameters
        if (projectName == null || projectName.isEmpty()) {
            return "Project name cannot be empty.";
        }
        if (projectDescription == null || projectDescription.isEmpty()) {
            return "Project description cannot be empty.";
        }

        // Create the project using the service
        projectService.createProject(projectName, projectDescription, priority, complexity, risk, effort, value);
        return "Project created successfully: " + projectName;
    }
}
