package com.preponderous.parpt.score;

import com.preponderous.parpt.domain.Project;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ScoreCalculatorTest {

    ScoreCalculator scoreCalculator = new ScoreCalculator();

    @Test
    void testImpactConfidenceEaseScoreCalculation() {
        // Arrange
        int impact = 5;
        int confidence = 4;
        int ease = 3;
        int reach = 2;
        int effort = 1;
        Project project = Project.builder()
                .impact(impact)
                .confidence(confidence)
                .ease(ease)
                .reach(reach)
                .effort(effort)
                .build();

        // Act
        double score = scoreCalculator.ice(project);

        // Assert
        double expectedScore = 60.0; // (5 * 4 * 3)
        assertEquals(expectedScore, score, 0.01, "ICE Score calculation should match expected value");
    }

    @Test
    void testReachImpactConfidenceEffortScoreCalculation() {
        // Arrange
        int impact = 5;
        int confidence = 4;
        int ease = 3;
        int reach = 2;
        int effort = 1;
        Project project = Project.builder()
                .impact(impact)
                .confidence(confidence)
                .ease(ease)
                .reach(reach)
                .effort(effort)
                .build();

        // Act
        double score = scoreCalculator.rice(project);

        // Assert
        double expectedScore = 40.0; // (5 * 4 * 2) / 1
        assertEquals(expectedScore, score, 0.01, "RICE Score calculation should match expected value");
    }
}
