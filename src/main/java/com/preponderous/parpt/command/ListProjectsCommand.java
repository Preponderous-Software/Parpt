package com.preponderous.parpt.command;

import com.preponderous.parpt.domain.Project;
import com.preponderous.parpt.service.ProjectService;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

public class ListProjectsCommand {

    private final ProjectService projectService;

    public ListProjectsCommand(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ShellMethod("Lists all projects.")
    public List<Project> execute() {
        return projectService.getProjects();
    }
}
