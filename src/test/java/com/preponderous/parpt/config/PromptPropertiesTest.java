package com.preponderous.parpt.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PromptPropertiesTest {

    @Autowired
    private PromptProperties promptProperties;

    @Test
    void shouldLoadTestValues() {
        // Verify base prompts
        assertThat(promptProperties.getProjectName()).isEqualTo("[TEST] Project name: ");
        assertThat(promptProperties.getProjectDescription()).isEqualTo("[TEST] Project description: ");

        // Verify impact prompts
        assertThat(promptProperties.getImpact()).hasSize(4);
        assertThat(promptProperties.getImpact()[0]).isEqualTo("[TEST] Impact 1: Money impact (1-5): ");
        assertThat(promptProperties.getImpact()[1]).isEqualTo("[TEST] Impact 2: User happiness (1-5): ");
        assertThat(promptProperties.getImpact()[2]).isEqualTo("[TEST] Impact 3: Competitive edge (1-5): ");
        assertThat(promptProperties.getImpact()[3]).isEqualTo("[TEST] Impact 4: Problem solving (1-5): ");

        // Verify confidence prompts
        assertThat(promptProperties.getConfidence()).hasSize(4);
        assertThat(promptProperties.getConfidence()[0]).isEqualTo("[TEST] Confidence 1: Requirements clarity (1-5): ");
        assertThat(promptProperties.getConfidence()[1]).isEqualTo("[TEST] Confidence 2: Prior experience (1-5): ");
        assertThat(promptProperties.getConfidence()[2]).isEqualTo("[TEST] Confidence 3: Team skills (1-5): ");
        assertThat(promptProperties.getConfidence()[3]).isEqualTo("[TEST] Confidence 4: Requirements stability (1-5): ");

        // Verify ease prompts
        assertThat(promptProperties.getEase()).hasSize(4);
        assertThat(promptProperties.getEase()[0]).isEqualTo("[TEST] Ease 1: Build complexity (1-5): ");
        assertThat(promptProperties.getEase()[1]).isEqualTo("[TEST] Ease 2: Tools availability (1-5): ");
        assertThat(promptProperties.getEase()[2]).isEqualTo("[TEST] Ease 3: Code reuse (1-5): ");
        assertThat(promptProperties.getEase()[3]).isEqualTo("[TEST] Ease 4: Testability (1-5): ");

        // Verify reach prompts
        assertThat(promptProperties.getReach()).hasSize(4);
        assertThat(promptProperties.getReach()[0]).isEqualTo("[TEST] Reach 1: User coverage (1-5): ");
        assertThat(promptProperties.getReach()[1]).isEqualTo("[TEST] Reach 2: New user attraction (1-5): ");
        assertThat(promptProperties.getReach()[2]).isEqualTo("[TEST] Reach 3: Change visibility (1-5): ");
        assertThat(promptProperties.getReach()[3]).isEqualTo("[TEST] Reach 4: Usage frequency (1-5): ");

        // Verify effort prompts
        assertThat(promptProperties.getEffort()).hasSize(4);
        assertThat(promptProperties.getEffort()[0]).isEqualTo("[TEST] Effort 1: Build time (1-5): ");
        assertThat(promptProperties.getEffort()[1]).isEqualTo("[TEST] Effort 2: Team size (1-5): ");
        assertThat(promptProperties.getEffort()[2]).isEqualTo("[TEST] Effort 3: Maintenance (1-5): ");
        assertThat(promptProperties.getEffort()[3]).isEqualTo("[TEST] Effort 4: Ongoing work (1-5): ");
    }

    @Test
    void shouldVerifyArraySizes() {
        assertThat(promptProperties.getImpact()).hasSize(4);
        assertThat(promptProperties.getConfidence()).hasSize(4);
        assertThat(promptProperties.getEase()).hasSize(4);
        assertThat(promptProperties.getReach()).hasSize(4);
        assertThat(promptProperties.getEffort()).hasSize(4);
    }

    @Test
    void shouldVerifyTestPrefixes() {
        assertThat(promptProperties.getProjectName()).startsWith("[TEST]");
        assertThat(promptProperties.getProjectDescription()).startsWith("[TEST]");

        assertThat(promptProperties.getImpact()).allMatch(prompt -> prompt.startsWith("[TEST]"));
        assertThat(promptProperties.getConfidence()).allMatch(prompt -> prompt.startsWith("[TEST]"));
        assertThat(promptProperties.getEase()).allMatch(prompt -> prompt.startsWith("[TEST]"));
        assertThat(promptProperties.getReach()).allMatch(prompt -> prompt.startsWith("[TEST]"));
        assertThat(promptProperties.getEffort()).allMatch(prompt -> prompt.startsWith("[TEST]"));
    }
}