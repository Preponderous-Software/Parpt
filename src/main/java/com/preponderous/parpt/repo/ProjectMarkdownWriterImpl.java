package com.preponderous.parpt.repo;

import com.preponderous.parpt.domain.Project;
import com.preponderous.parpt.score.ScoreCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Component
public class ProjectMarkdownWriterImpl implements ProjectMarkdownWriter {

    private final String markdownFilePath;
    private final ScoreCalculator scoreCalculator;

    public ProjectMarkdownWriterImpl(
            @Value("${app.projects.markdown-file}") String markdownFilePath,
            ScoreCalculator scoreCalculator) {
        this.markdownFilePath = markdownFilePath;
        this.scoreCalculator = scoreCalculator;
    }

    @Override
    public void writeMarkdown(List<Project> projects, boolean sortByRice) {
        if (projects == null) {
            throw new IllegalArgumentException("Projects list cannot be null");
        }

        try (FileWriter writer = new FileWriter(markdownFilePath)) {
            // Write header
            writer.write("# Project Priorities\n\n");
            writer.write("*Generated on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "*\n\n");
            
            if (projects.isEmpty()) {
                writer.write("No projects found.\n");
                return;
            }

            // Sort projects by the chosen scoring method
            List<Project> sortedProjects = projects.stream()
                    .sorted(sortByRice ? 
                        Comparator.comparingDouble((Project p) -> scoreCalculator.rice(p)).reversed() :
                        Comparator.comparingDouble((Project p) -> scoreCalculator.ice(p)).reversed())
                    .toList();

            String scoreType = sortByRice ? "RICE" : "ICE";
            writer.write("*Sorted by " + scoreType + " score (highest to lowest)*\n\n");

            // Write projects
            for (int i = 0; i < sortedProjects.size(); i++) {
                Project project = sortedProjects.get(i);
                writeProject(writer, project, i + 1);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to write projects to Markdown file", e);
        }
    }

    private void writeProject(FileWriter writer, Project project, int rank) throws IOException {
        double iceScore = scoreCalculator.ice(project);
        double riceScore = scoreCalculator.rice(project);

        writer.write("## " + rank + ". " + project.getName() + "\n\n");
        writer.write("**Description:** " + project.getDescription() + "\n\n");
        
        // Scores summary
        writer.write("### Scores\n");
        writer.write("- **ICE Score:** " + String.format("%.2f", iceScore) + "\n");
        writer.write("- **RICE Score:** " + String.format("%.2f", riceScore) + "\n\n");
        
        // Individual components
        writer.write("### Components\n");
        writer.write("- **Impact:** " + project.getImpact() + "/5 (" + getScoreDescription(project.getImpact()) + ")\n");
        writer.write("- **Confidence:** " + project.getConfidence() + "/5 (" + getScoreDescription(project.getConfidence()) + ")\n");
        writer.write("- **Ease:** " + project.getEase() + "/5 (" + getScoreDescription(project.getEase()) + ")\n");
        writer.write("- **Reach:** " + project.getReach() + "/5 (" + getScoreDescription(project.getReach()) + ")\n");
        writer.write("- **Effort:** " + project.getEffort() + "/5 (" + getScoreDescription(project.getEffort()) + ")\n\n");
        
        writer.write("---\n\n");
    }

    private String getScoreDescription(int score) {
        return switch (score) {
            case 1 -> "very low";
            case 2 -> "low";
            case 3 -> "medium";
            case 4 -> "high";
            case 5 -> "very high";
            default -> String.valueOf(score);
        };
    }
}