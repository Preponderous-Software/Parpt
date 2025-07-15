package com.preponderous.parpt.command;

import com.preponderous.parpt.repo.ProjectRepository;
import com.preponderous.parpt.score.ScoreCalculator;
import com.preponderous.parpt.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ViewProjectCommandTest {

    ViewProjectCommand viewProjectCommand;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ScoreCalculator scoreCalculator;

    @BeforeEach
    void setUp() {
        // Initialize the command with the project service
        viewProjectCommand = new ViewProjectCommand(projectService, scoreCalculator);

        // Clear any existing projects in the repository before each test
        projectRepository.clear();
    }

    @Test
    void shouldReturnProjectNotFoundWhenProjectDoesNotExist() {
        // Given no projects exist

        // When the command is executed with a non-existing project name
        var result = viewProjectCommand.execute("NonExistingProject");

        // Then the result should indicate that the project was not found
        assertTrue(result.contains("Project not found: NonExistingProject"));
    }

    @Test
    void shouldReturnProjectDetailsWhenProjectExists() throws ProjectRepository.NameTakenException {
        // Given a project exists
        projectService.createProject("Test Project", "This is a test project", 5, 4, 3, 2, 1);

        // When the command is executed with the existing project name
        var result = viewProjectCommand.execute("Test Project");

        // Then the result should contain the project details
        assertTrue(result.contains("Project: Test Project"));
        assertTrue(result.contains("Description: This is a test project"));
        assertTrue(result.contains("Impact: 5"));
        assertTrue(result.contains("Confidence: 4"));
        assertTrue(result.contains("Ease: 3"));
        assertTrue(result.contains("Reach: 2"));
        assertTrue(result.contains("Effort: 1"));
        assertTrue(result.contains("ICE Score"));
        assertTrue(result.contains("RICE Score"));
    }
}
