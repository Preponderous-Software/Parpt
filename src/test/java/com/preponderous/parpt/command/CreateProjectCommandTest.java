package com.preponderous.parpt.command;

import com.preponderous.parpt.repo.ProjectRepository;
import com.preponderous.parpt.service.ProjectService;
import com.preponderous.parpt.util.ConsoleInputProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreateProjectCommandTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private ConsoleInputProvider inputProvider;

    private CreateProjectCommand command;

    @BeforeEach
    void setUp() {
        command = new CreateProjectCommand(projectService, inputProvider);
    }

    @Test
    void testExecuteWithAllArgumentsProvided() throws ProjectRepository.NameTakenException {
        // Test when all arguments are provided directly
        String result = command.execute(
                "Test Project",
                "Test Description",
                5,
                5,
                5,
                5,
                5
        );

        // Verify service was called with correct arguments
        verify(projectService).createProject("Test Project", "Test Description", 5, 5, 5, 5, 5);
        assertEquals("Project created successfully: Test Project", result);

        // Verify no console interaction happened
        verifyNoInteractions(inputProvider);
    }

    @Test
    void testValidationFailsWithInvalidScores() throws ProjectRepository.NameTakenException {
        // Test validation with out-of-range scores
        String result = command.execute(
                "Test Project",
                "Test Description",
                0,  // Invalid: below range
                11, // Invalid: above range
                5,
                5,
                5
        );

        // Verify service was NOT called
        verify(projectService, never()).createProject(anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
        assertEquals("All scores must be between 1 and 5.", result);
    }

    @Test
    void testInteractiveInputWithAllFieldsMissing() throws ProjectRepository.NameTakenException {
        // Setup console mock responses
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_NAME_PROMPT)).thenReturn("Interactive Project");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_DESCRIPTION_PROMPT)).thenReturn("Interactive Description");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_IMPACT_PROMPT)).thenReturn("3");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_CONFIDENCE_PROMPT)).thenReturn("4");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_EASE_PROMPT)).thenReturn("5");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_REACH_PROMPT)).thenReturn("3");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_EFFORT_PROMPT)).thenReturn("2");

        // Call execute with all null arguments to trigger interactive mode
        String result = command.execute(null, null, null, null, null, null, null);

        // Verify all prompts were called
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_NAME_PROMPT);
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_DESCRIPTION_PROMPT);
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_IMPACT_PROMPT);
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_CONFIDENCE_PROMPT);
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_EASE_PROMPT);
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_REACH_PROMPT);
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_EFFORT_PROMPT);

        // Verify service was called with the expected values
        verify(projectService).createProject(
                "Interactive Project",
                "Interactive Description",
                3, 4, 5, 3, 2
        );

        assertEquals("Project created successfully: Interactive Project", result);
    }

    @Test
    void testInteractiveInputWithPartialArgumentsProvided() throws ProjectRepository.NameTakenException {
        // Setup console mock responses for missing fields
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_DESCRIPTION_PROMPT)).thenReturn("Interactive Description");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_EASE_PROMPT)).thenReturn("3");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_EFFORT_PROMPT)).thenReturn("4");

        // Call execute with some arguments provided and others null
        String result = command.execute(
                "Partial Project",  // Name provided
                null,               // Description missing
                5,                  // Impact provided
                5,                  // Confidence provided
                null,               // Ease missing
                4,                  // Reach provided
                null                // Effort missing
        );

        // Verify only the missing field prompts were called
        verify(inputProvider, never()).readLine(CreateProjectCommand.PROJECT_NAME_PROMPT);
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_DESCRIPTION_PROMPT);
        verify(inputProvider, never()).readLine(CreateProjectCommand.PROJECT_IMPACT_PROMPT);
        verify(inputProvider, never()).readLine(CreateProjectCommand.PROJECT_CONFIDENCE_PROMPT);
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_EASE_PROMPT);
        verify(inputProvider, never()).readLine(CreateProjectCommand.PROJECT_REACH_PROMPT);
        verify(inputProvider).readLine(CreateProjectCommand.PROJECT_EFFORT_PROMPT);

        // Verify service was called with the expected values
        verify(projectService).createProject(
                "Partial Project",
                "Interactive Description",
                5, 5, 3, 4, 4
        );

        assertEquals("Project created successfully: Partial Project", result);
    }

    @Test
    void testInteractiveInputWithInvalidNumericInput() throws ProjectRepository.NameTakenException {
        // Setup console to return invalid input for Impact
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_NAME_PROMPT)).thenReturn("Interactive Project");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_DESCRIPTION_PROMPT)).thenReturn("Interactive Description");
        when(inputProvider.readLine(CreateProjectCommand.PROJECT_IMPACT_PROMPT)).thenReturn("invalid");

        // Call execute with null arguments to trigger interactive mode
        String result = command.execute(null, null, null, null, null, null, null);

        // Verify error message is returned
        assertEquals("Invalid impact value. Please enter a number between 1 and 5.", result);

        // Verify service was NOT called
        verify(projectService, never()).createProject(anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }
}