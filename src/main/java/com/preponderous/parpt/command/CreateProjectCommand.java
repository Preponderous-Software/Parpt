package com.preponderous.parpt.command;

import com.preponderous.parpt.config.PromptProperties;
import com.preponderous.parpt.domain.Project;
import com.preponderous.parpt.repo.ProjectRepository;
import com.preponderous.parpt.score.ScoreCalculator;
import com.preponderous.parpt.service.ProjectService;
import com.preponderous.parpt.util.ConsoleInputProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class CreateProjectCommand {
    private final ProjectService projectService;
    private final ConsoleInputProvider inputProvider;
    private final ScoreCalculator scoreCalculator;
    private final PromptProperties promptProperties;

    public CreateProjectCommand(
            ProjectService projectService,
            ConsoleInputProvider inputProvider,
            ScoreCalculator scoreCalculator,
            PromptProperties promptProperties) {
        this.projectService = projectService;
        this.inputProvider = inputProvider;
        this.scoreCalculator = scoreCalculator;
        this.promptProperties = promptProperties;
    }


    private int getAverageScore(String[] prompts) throws InvalidScoreException {
        int total = 0;
        for (String prompt : prompts) {
            try {
                int score = Integer.parseInt(inputProvider.readLine(prompt));
                if (score < 1 || score > 5) {
                    throw new InvalidScoreException("Invalid score. Must be between 1 and 5.");
                }
                total += score;
            } catch (NumberFormatException e) {
                throw new InvalidScoreException("Invalid score. Must be a number between 1 and 5.");
            }
        }
        return Math.round((float) total / prompts.length);
    }


    @ShellMethod(key = "create", value = "Creates a new project with the given parameters.")
    public String execute(
            @ShellOption(value = {"-n", "--name"}, help = "The name of the project", defaultValue = ShellOption.NULL) String projectName,
            @ShellOption(value = {"-d", "--description"}, help = "Description of the project", defaultValue = ShellOption.NULL) String projectDescription,
            @ShellOption(value = {"-i", "--impact"}, help = "Impact score (1-5)", defaultValue = ShellOption.NULL) Integer impact,
            @ShellOption(value = {"-c", "--confidence"}, help = "Confidence score (1-5)", defaultValue = ShellOption.NULL) Integer confidence,
            @ShellOption(value = {"-e", "--ease"}, help = "Ease score (1-5)", defaultValue = ShellOption.NULL) Integer ease,
            @ShellOption(value = {"-r", "--reach"}, help = "Reach score (1-5)", defaultValue = ShellOption.NULL) Integer reach,
            @ShellOption(value = {"-f", "--effort"}, help = "Effort score (1-5)", defaultValue = ShellOption.NULL) Integer effort
    ) {
        // Interactive input if parameters are not provided
        if (projectName == null) {
            projectName = inputProvider.readLine(promptProperties.getProjectName());
        }
        if (projectName == null || projectName.isEmpty()) {
            return "Project name cannot be empty.";
        }
        if (projectDescription == null) {
            projectDescription = inputProvider.readLine(promptProperties.getProjectDescription());
        }
        if (projectDescription == null || projectDescription.isEmpty()) {
            return "Project description cannot be empty.";
        }
        if (impact == null) {
            boolean continueLoop = true;
            while (continueLoop) {
                try {
                    impact = getAverageScore(promptProperties.getImpact());
                    continueLoop = false;
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (confidence == null) {
            boolean continueLoop = true;
            while (continueLoop) {
                try {
                    confidence = getAverageScore(promptProperties.getConfidence());
                    continueLoop = false;
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (ease == null) {
            boolean continueLoop = true;
            while (continueLoop) {
                try {
                    ease = getAverageScore(promptProperties.getEase());
                    continueLoop = false;
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (reach == null) {
            boolean continueLoop = true;
            while (continueLoop) {
                try {
                    reach = getAverageScore(promptProperties.getReach());
                    continueLoop = false;
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (effort == null) {
            boolean continueLoop = true;
            while (continueLoop) {
                try {
                    effort = getAverageScore(promptProperties.getEffort());
                    continueLoop = false;
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        if (impact < 1 || impact > 5 || confidence < 1 || confidence > 5 ||
                ease < 1 || ease > 5 || reach < 1 || reach > 5 || effort < 1 || effort > 5) {
            return "All scores must be between 1 and 5.";
        }

        // Create the project using the service
        Project project;
        try {
            project = projectService.createProject(projectName, projectDescription, impact, confidence, ease, reach, effort);
        } catch (ProjectRepository.NameTakenException e) {
            return "Project name '" + projectName + "' is already taken. Please choose a different name.";
        } catch (Exception e) {
            return "An error occurred while creating the project: " + e.getMessage();
        }

        // Calculate scores
        double iceScore = scoreCalculator.ice(project);
        double riceScore = scoreCalculator.rice(project);
        return String.format("Project created successfully: %s\nICE Score: %.2f\nRICE Score: %.2f", projectName, iceScore, riceScore);
    }

    public class InvalidScoreException extends Exception {
        public InvalidScoreException(String message) {
            super(message);
        }
    }
}