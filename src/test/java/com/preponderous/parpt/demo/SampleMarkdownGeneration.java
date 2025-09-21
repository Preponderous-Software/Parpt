package com.preponderous.parpt.demo;

import com.preponderous.parpt.domain.Project;
import com.preponderous.parpt.repo.ProjectMarkdownWriterImpl;
import com.preponderous.parpt.score.ScoreCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Demo test to generate sample markdown output for verification
 */
class SampleMarkdownGeneration {

    @Test
    void generateSampleMarkdown(@TempDir Path tempDir) throws IOException {
        // Create sample projects
        List<Project> projects = Arrays.asList(
                Project.builder()
                        .name("Mobile App Redesign")
                        .description("Complete overhaul of our mobile application with modern UI/UX")
                        .impact(5).confidence(4).ease(2).reach(5).effort(4)
                        .build(),
                
                Project.builder()
                        .name("Quick Bug Fix")
                        .description("Fix critical login issue affecting 10% of users")
                        .impact(4).confidence(5).ease(5).reach(3).effort(1)
                        .build(),
                
                Project.builder()
                        .name("Database Migration")
                        .description("Migrate legacy database to modern cloud solution")
                        .impact(3).confidence(3).ease(1).reach(2).effort(5)
                        .build(),
                
                Project.builder()
                        .name("Feature Toggle System")
                        .description("Implement feature flags for better deployment control")
                        .impact(3).confidence(4).ease(3).reach(4).effort(3)
                        .build()
        );

        // Generate markdown files with both sorting options
        Path iceFile = tempDir.resolve("sample-ice-sorted.md");
        Path riceFile = tempDir.resolve("sample-rice-sorted.md");
        
        ScoreCalculator calculator = new ScoreCalculator();
        ProjectMarkdownWriterImpl writer = new ProjectMarkdownWriterImpl(
                iceFile.toString(), calculator);
        
        // Generate ICE-sorted version
        writer.writeMarkdown(projects, false);
        
        // Generate RICE-sorted version  
        writer = new ProjectMarkdownWriterImpl(riceFile.toString(), calculator);
        writer.writeMarkdown(projects, true);
        
        // Output the content for verification
        System.out.println("=== ICE SORTED MARKDOWN ===");
        System.out.println(Files.readString(iceFile));
        
        System.out.println("\n\n=== RICE SORTED MARKDOWN ===");
        System.out.println(Files.readString(riceFile));
    }
}