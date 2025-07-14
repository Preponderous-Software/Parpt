package com.preponderous.parpt.command;

import com.preponderous.parpt.service.ProjectService;
import com.preponderous.parpt.util.ConsoleInputProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class CreateProjectCommand {
    private final ProjectService projectService;
    private final ConsoleInputProvider inputProvider;

    public CreateProjectCommand(ProjectService projectService, ConsoleInputProvider inputProvider) {
        this.projectService = projectService;
        this.inputProvider = inputProvider;
    }

    @ShellMethod(key = "create", value = "Creates a new project with the given parameters.")
    public String execute(
            @ShellOption(value = {"-n", "--name"}, help = "The name of the project", defaultValue = ShellOption.NULL) String projectName,
            @ShellOption(value = {"-d", "--description"}, help = "Description of the project", defaultValue = ShellOption.NULL) String projectDescription,
            @ShellOption(value = {"-i", "--impact"}, help = "Impact score (1-5)", defaultValue = ShellOption.NULL) Integer impact,
            @ShellOption(value = {"-c", "--confidence"}, help = "Complexity score (1-5)", defaultValue = ShellOption.NULL) Integer confidence,
            @ShellOption(value = {"-ea", "--ease"}, help = "Risk score (1-5)", defaultValue = ShellOption.NULL) Integer ease,
            @ShellOption(value = {"-r", "--reach"}, help = "Effort score (1-5)", defaultValue = ShellOption.NULL) Integer reach,
            @ShellOption(value = {"-ef", "--effort"}, help = "Effort score (1-5)", defaultValue = ShellOption.NULL) Integer effort
    ) {
        // Interactive input if parameters are not provided
        if (projectName == null) {
            projectName = inputProvider.readLine("Project name: ");
        }
        if (projectDescription == null) {
            projectDescription = inputProvider.readLine("Project description: ");
        }
        if (impact == null) {
            try {
                impact = Integer.parseInt(inputProvider.readLine("Impact (1-5): "));
            } catch (NumberFormatException e) {
                return "Invalid impact value. Please enter a number between 1 and 5.";
            }
        }
        if (confidence == null) {
            try {
                confidence = Integer.parseInt(inputProvider.readLine("Confidence (1-5): "));
            } catch (NumberFormatException e) {
                return "Invalid confidence value. Please enter a number between 1 and 5.";
            }
        }
        if (ease == null) {
            try {
                ease = Integer.parseInt(inputProvider.readLine("Ease (1-5): "));
            } catch (NumberFormatException e) {
                return "Invalid ease value. Please enter a number between 1 and 5.";
            }
        }
        if (reach == null) {
            try {
                reach = Integer.parseInt(inputProvider.readLine("Reach (1-5): "));
            } catch (NumberFormatException e) {
                return "Invalid reach value. Please enter a number between 1 and 5.";
            }
        }
        if (effort == null) {
            try {
                effort = Integer.parseInt(inputProvider.readLine("Effort (1-5): "));
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
        projectService.createProject(projectName, projectDescription, impact, confidence, ease, reach, effort);
        return "Project created successfully: " + projectName;
    }
}