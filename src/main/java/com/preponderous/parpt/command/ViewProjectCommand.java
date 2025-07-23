
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

    private String getScoreDescription(int score) {
        return switch (score) {
            case 1 -> "very low";
            case 2 -> "low";
            case 3 -> "medium";
            case 4 -> "high";
            case 5 -> "very high";
            default -> String.valueOf(score);
        };
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
                String.format("Impact: %s\n", getScoreDescription(project.getImpact())) +
                String.format("Confidence: %s\n", getScoreDescription(project.getConfidence())) +
                String.format("Ease: %s\n", getScoreDescription(project.getEase())) +
                String.format("Reach: %s\n", getScoreDescription(project.getReach())) +
                String.format("Effort: %s\n", getScoreDescription(project.getEffort())) +
                String.format("ICE Score: %.2f\n", scoreCalculator.ice(project)) +
                String.format("RICE Score: %.2f\n", scoreCalculator.rice(project));
    }
}