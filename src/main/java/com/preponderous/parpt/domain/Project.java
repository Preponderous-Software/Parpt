package com.preponderous.parpt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a project with various attributes including its impact, confidence, ease,
 * reach, and effort. This class is typically used to define and evaluate a project's
 * characteristics and its potential effectiveness.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private String name;
    private String description;
    private int impact;
    private int confidence;
    private int ease;
    private int reach;
    private int effort;
}
