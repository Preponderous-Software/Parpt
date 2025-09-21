package com.preponderous.parpt.repo;

import com.preponderous.parpt.domain.Project;
import com.preponderous.parpt.score.ScoreCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMarkdownWriterImplTest {

    private ProjectMarkdownWriterImpl markdownWriter;
    private ScoreCalculator scoreCalculator;
    private Path tempMarkdownFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws IOException {
        tempMarkdownFile = tempDir.resolve("test-projects.md");
        scoreCalculator = new ScoreCalculator();
        markdownWriter = new ProjectMarkdownWriterImpl(
                tempMarkdownFile.toString(),
                scoreCalculator
        );
    }

    @Test
    void writeMarkdown_ShouldCreateMarkdownFile() throws IOException {
        // Given
        Project project = Project.builder()
                .name("Test Project")
                .description("A test project")
                .impact(4)
                .confidence(3)
                .ease(5)
                .reach(2)
                .effort(3)
                .build();
        List<Project> projects = List.of(project);

        // When
        markdownWriter.writeMarkdown(projects, false);

        // Then
        assertTrue(Files.exists(tempMarkdownFile));
        String content = Files.readString(tempMarkdownFile);
        assertAll(
                () -> assertTrue(content.contains("# Project Priorities")),
                () -> assertTrue(content.contains("Test Project")),
                () -> assertTrue(content.contains("A test project")),
                () -> assertTrue(content.contains("ICE Score")),
                () -> assertTrue(content.contains("RICE Score")),
                () -> assertTrue(content.contains("Sorted by ICE score"))
        );
    }

    @Test
    void writeMarkdown_ShouldSortByICEByDefault() throws IOException {
        // Given
        Project highICE = Project.builder()
                .name("High ICE")
                .description("High ICE project")
                .impact(5).confidence(5).ease(5)
                .reach(1).effort(5)
                .build();
        
        Project lowICE = Project.builder()
                .name("Low ICE")
                .description("Low ICE project")
                .impact(1).confidence(1).ease(1)
                .reach(5).effort(1)
                .build();
        
        List<Project> projects = Arrays.asList(lowICE, highICE);

        // When
        markdownWriter.writeMarkdown(projects);

        // Then
        String content = Files.readString(tempMarkdownFile);
        int highICEIndex = content.indexOf("High ICE");
        int lowICEIndex = content.indexOf("Low ICE");
        assertTrue(highICEIndex < lowICEIndex, "High ICE project should appear before Low ICE project");
    }

    @Test
    void writeMarkdown_ShouldSortByRICEWhenRequested() throws IOException {
        // Given
        Project highRICE = Project.builder()
                .name("High RICE")
                .description("High RICE project")
                .impact(5).confidence(5).ease(1)
                .reach(5).effort(1)
                .build();
        
        Project lowRICE = Project.builder()
                .name("Low RICE")
                .description("Low RICE project")
                .impact(1).confidence(1).ease(5)
                .reach(1).effort(5)
                .build();
        
        List<Project> projects = Arrays.asList(lowRICE, highRICE);

        // When
        markdownWriter.writeMarkdown(projects, true);

        // Then
        String content = Files.readString(tempMarkdownFile);
        assertAll(
                () -> assertTrue(content.contains("Sorted by RICE score")),
                () -> assertTrue(content.indexOf("High RICE") < content.indexOf("Low RICE"))
        );
    }

    @Test
    void writeMarkdown_WithEmptyList_ShouldWriteNoProjectsMessage() throws IOException {
        // Given
        List<Project> projects = List.of();

        // When
        markdownWriter.writeMarkdown(projects, false);

        // Then
        String content = Files.readString(tempMarkdownFile);
        assertAll(
                () -> assertTrue(content.contains("# Project Priorities")),
                () -> assertTrue(content.contains("No projects found"))
        );
    }

    @Test
    void writeMarkdown_WithNullList_ShouldThrowException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, 
                () -> markdownWriter.writeMarkdown(null, false));
    }

    @Test
    void writeMarkdown_ShouldIncludeAllProjectDetails() throws IOException {
        // Given
        Project project = Project.builder()
                .name("Detailed Project")
                .description("A project with all details")
                .impact(4)
                .confidence(3)
                .ease(2)
                .reach(5)
                .effort(3)
                .build();
        List<Project> projects = List.of(project);

        // When
        markdownWriter.writeMarkdown(projects, false);

        // Then
        String content = Files.readString(tempMarkdownFile);
        assertAll(
                () -> assertTrue(content.contains("Impact:** 4/5 (high)")),
                () -> assertTrue(content.contains("Confidence:** 3/5 (medium)")),
                () -> assertTrue(content.contains("Ease:** 2/5 (low)")),
                () -> assertTrue(content.contains("Reach:** 5/5 (very high)")),
                () -> assertTrue(content.contains("Effort:** 3/5 (medium)"))
        );
    }
}