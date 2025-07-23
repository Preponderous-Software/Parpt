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
class ListProjectsCommandTest {

    ListProjectsCommand listProjectsCommand;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ScoreCalculator scoreCalculator;

    @BeforeEach
    void setUp() {
        // Initialize the command with the project service
        listProjectsCommand = new ListProjectsCommand(projectService, scoreCalculator);

        // Clear any existing projects in the repository before each test
        projectRepository.clear();
    }

    @Test
    void shouldReturnEmptyListWhenNoProjectsExist() {
        // Given no projects exist

        // When the command is executed
        var result = listProjectsCommand.execute();

        // Then the result should be an empty list
        assertTrue(result.contains("No projects found."));
    }

    @Test
    void shouldReturnListOfProjectsWhenProjectsExist() throws ProjectRepository.NameTakenException {
        // Given some projects exist
        projectService.createProject("Project A", "Description A", 5, 4, 3, 2, 1);
        projectService.createProject("Project B", "Description B", 4, 3, 2, 1, 5);

        // When the command is executed
        var result = listProjectsCommand.execute();

        // Then the result should contain the projects
        assertTrue(result.contains("Projects:"));
        assertTrue(result.contains("Project A: Description A"));
        assertTrue(result.contains("Project B: Description B"));
    }
}
