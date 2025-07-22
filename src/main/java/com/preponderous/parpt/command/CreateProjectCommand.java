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

    // Impact prompts - focusing on benefits
    protected static final String[] PROJECT_IMPACT_PROMPTS = {
            "Will this make/save money? (1=tiny impact, 5=huge impact) ",
            "Will this make users happier? (1=slightly, 5=dramatically) ",
            "Will this give us an edge over competitors? (1=small edge, 5=game-changing) ",
            "Will this solve any major problems? (1=minor issues, 5=critical problems) "
    };

    // Confidence prompts - how sure are we?
    protected static final String[] PROJECT_CONFIDENCE_PROMPTS = {
            "Do we understand what needs to be built? (1=very unclear, 5=crystal clear) ",
            "Have we done something similar before? (1=never, 5=many times) ",
            "Do we have the right skills in the team? (1=missing key skills, 5=perfect fit) ",
            "Are the requirements likely to change? (1=constantly changing, 5=very stable) "
    };

    // Ease prompts - how simple is it?
    protected static final String[] PROJECT_EASE_PROMPTS = {
            "How straightforward is this to build? (1=very complex, 5=very simple) ",
            "Do we have the tools we need? (1=need many new tools, 5=have everything) ",
            "Can we reuse existing code/systems? (1=start from scratch, 5=mostly reusable) ",
            "How easy is it to test? (1=very difficult, 5=very easy) "
    };

    // Reach prompts - who will it affect?
    protected static final String[] PROJECT_REACH_PROMPTS = {
            "How many users will this help? (1=very few, 5=almost all) ",
            "Will this attract new users? (1=unlikely, 5=definitely) ",
            "Will users notice this change? (1=barely noticeable, 5=very visible) ",
            "How often will users benefit from this? (1=rarely, 5=daily) "
    };

    // Effort prompts - what will it take?
    protected static final String[] PROJECT_EFFORT_PROMPTS = {
            "How long will this take to build? (1=very quick, 5=very long) ",
            "How many people need to be involved? (1=just a few people, 5=many teams) ",
            "Will this be hard to maintain? (1=very easy, 5=very difficult) ",
            "Does this need ongoing work? (1=set and forget, 5=lots of upkeep) "
    };

    public CreateProjectCommand(ProjectService projectService, ConsoleInputProvider inputProvider, ScoreCalculator scoreCalculator) {
        this.projectService = projectService;
        this.inputProvider = inputProvider;
        this.scoreCalculator = scoreCalculator;
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
            projectName = inputProvider.readLine(PROJECT_NAME_PROMPT);
        }
        if (projectDescription == null) {
            projectDescription = inputProvider.readLine(PROJECT_DESCRIPTION_PROMPT);
        }
        if (impact == null) {
            boolean continueLoop = true;
            while (continueLoop) {
                try {
                    impact = getAverageScore(PROJECT_IMPACT_PROMPTS);
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
                    confidence = getAverageScore(PROJECT_CONFIDENCE_PROMPTS);
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
                    ease = getAverageScore(PROJECT_EASE_PROMPTS);
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
                    reach = getAverageScore(PROJECT_REACH_PROMPTS);
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
                    effort = getAverageScore(PROJECT_EFFORT_PROMPTS);
                    continueLoop = false;
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
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

    public class InvalidScoreException extends Exception {
        public InvalidScoreException(String message) {
            super(message);
        }
    }
}