package com.preponderous.parpt.command;

import com.preponderous.parpt.domain.Project;
import com.preponderous.parpt.repo.ProjectRepository;
import com.preponderous.parpt.score.ScoreCalculator;
import com.preponderous.parpt.service.ProjectService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class ViewProjectCommand {

    private final ProjectService projectService;
    private final ScoreCalculator scoreCalculator;

    public ViewProjectCommand(ProjectService projectService, ScoreCalculator scoreCalculator) {
        this.projectService = projectService;
        this.scoreCalculator = scoreCalculator;
    }

    @ShellMethod(key = "view", value = "Views a specific project by name.")
    public String execute(String projectName) {
        Project project;
        try {
            project = projectService.getProject(projectName);
        } catch (ProjectRepository.ProjectNotFoundException e) {
            return "Project not found: " + projectName;
        }

        return String.format("Project: %s\n", project.getName()) +
                String.format("Description: %s\n", project.getDescription()) +
                String.format("Impact: %d\n", project.getImpact()) +
                String.format("Confidence: %d\n", project.getConfidence()) +
                String.format("Ease: %d\n", project.getEase()) +
                String.format("Reach: %d\n", project.getReach()) +
                String.format("Effort: %d\n", project.getEffort()) +
                String.format("ICE Score: %.2f\n", scoreCalculator.ice(project)) +
                String.format("RICE Score: %.2f\n", scoreCalculator.rice(project));
    }
}
