package com.preponderous.parpt.command;

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

    protected static final String PROJECT_NAME_PROMPT = "What is the name of the project? ";
    protected static final String PROJECT_DESCRIPTION_PROMPT = "How would you describe the project? ";
    protected static final String PROJECT_IMPACT_PROMPT = "On a scale of 1 to 5, how much potential benefit or revenue impact does this project have? ";
    protected static final String PROJECT_CONFIDENCE_PROMPT = "On a scale of 1 to 5, how confident are you in the estimates for this project? ";
    protected static final String PROJECT_EASE_PROMPT = "On a scale of 1 to 5, how easy or quick is this project to implement? ";
    protected static final String PROJECT_REACH_PROMPT = "On a scale of 1 to 5, how many users or customers will this project impact? ";
    protected static final String PROJECT_EFFORT_PROMPT = "On a scale of 1 to 5, how much effort is required for this project? (1 being minimal, 5 being significant) ";

    public CreateProjectCommand(ProjectService projectService, ConsoleInputProvider inputProvider, ScoreCalculator scoreCalculator) {
        this.projectService = projectService;
        this.inputProvider = inputProvider;
        this.scoreCalculator = scoreCalculator;
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
            projectName = inputProvider.readLine(PROJECT_NAME_PROMPT);
        }
        if (projectDescription == null) {
            projectDescription = inputProvider.readLine(PROJECT_DESCRIPTION_PROMPT);
        }
        if (impact == null) {
            try {
                impact = Integer.parseInt(inputProvider.readLine(PROJECT_IMPACT_PROMPT));
            } catch (NumberFormatException e) {
                return "Invalid impact value. Please enter a number between 1 and 5.";
            }
        }
        if (confidence == null) {
            try {
                confidence = Integer.parseInt(inputProvider.readLine(PROJECT_CONFIDENCE_PROMPT));
            } catch (NumberFormatException e) {
                return "Invalid confidence value. Please enter a number between 1 and 5.";
            }
        }
        if (ease == null) {
            try {
                ease = Integer.parseInt(inputProvider.readLine(PROJECT_EASE_PROMPT));
            } catch (NumberFormatException e) {
                return "Invalid ease value. Please enter a number between 1 and 5.";
            }
        }
        if (reach == null) {
            try {
                reach = Integer.parseInt(inputProvider.readLine(PROJECT_REACH_PROMPT));
            } catch (NumberFormatException e) {
                return "Invalid reach value. Please enter a number between 1 and 5.";
            }
        }
        if (effort == null) {
            try {
                effort = Integer.parseInt(inputProvider.readLine(PROJECT_EFFORT_PROMPT));
            } catch (NumberFormatException e) {
                return "Invalid effort value. Please enter a number between 1 and 5.";
            }
        }

        // Validate input parameters
        if (projectName == null || projectName.isEmpty()) {
            return "Project name cannot be empty.";
        }
        if (projectDescription == null || projectDescription.isEmpty()) {
            return "Project description cannot be empty.";
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
}