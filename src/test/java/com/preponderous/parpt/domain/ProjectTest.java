package com.preponderous.parpt.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectTest {

    @Test
    void testBuilder() {
        Project project = Project.builder()
                .name("Test Project")
                .description("This is a test project.")
                .impact(5)
                .confidence(4)
                .ease(3)
                .reach(2)
                .effort(1)
                .build();

        assertNotNull(project);
        assertEquals("Test Project", project.getName());
        assertEquals("This is a test project.", project.getDescription());
        assertEquals(5, project.getImpact());
        assertEquals(4, project.getConfidence());
        assertEquals(3, project.getEase());
        assertEquals(2, project.getReach());
        assertEquals(1, project.getEffort());
    }
}