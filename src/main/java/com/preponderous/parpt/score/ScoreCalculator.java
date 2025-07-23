package com.preponderous.parpt.score;

import com.preponderous.parpt.domain.Project;
import org.springframework.stereotype.Component;

@Component
public class ScoreCalculator {

    public double ice(Project project) {
        return (project.getImpact() * project.getConfidence() * project.getEase());
    }

    public double rice(Project project) {
        return (double) (project.getReach() * project.getImpact() * project.getConfidence()) / project.getEffort();
    }
}
