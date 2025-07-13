package com.preponderous.parpt.command;

import com.preponderous.parpt.repo.ProjectRepository;
import com.preponderous.parpt.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CreateProjectCommandTest {

    CreateProjectCommand createProjectCommand;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        // Initialize the command with the project service
        createProjectCommand = new CreateProjectCommand(projectService);

        // Clear any existing projects in the repository before each test
        projectRepository.clear();
    }

    @Test
    void shouldCreateProjectSuccessfully() {
        // Given a project name and description
        String projectName = "Test Project";
        String projectDescription = "This is a test project.";

        // When the command is executed
        var result = createProjectCommand.execute(projectName, projectDescription, 5, 4, 3, 2, 1);

        // Then the result should indicate success
        assertTrue(result.contains("Project created successfully: " + projectName));

    }
}
