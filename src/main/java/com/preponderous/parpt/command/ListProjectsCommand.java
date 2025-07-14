package com.preponderous.parpt.command;

import com.preponderous.parpt.domain.Project;
import com.preponderous.parpt.score.ScoreCalculator;
import com.preponderous.parpt.service.ProjectService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class ListProjectsCommand {

    private final ProjectService projectService;
    private final ScoreCalculator scoreCalculator;

    public ListProjectsCommand(ProjectService projectService, ScoreCalculator scoreCalculator) {
        this.projectService = projectService;
        this.scoreCalculator = scoreCalculator;
    }

    @ShellMethod(key = "list", value = "Lists all projects.")
    public String execute() {
        List<Project> projects = projectService.getProjects();

        if (projects.isEmpty()) {
            return "No projects found.";
        }

        StringBuilder result = new StringBuilder("Projects:\n");
        for (Project project : projects) {
            result.append(String.format("- %s: %s (ICE: %s | RICE: %s)\n", project.getName(), project.getDescription(), scoreCalculator.ice(project), scoreCalculator.rice(project)));
        }

        return result.toString();
    }
}
