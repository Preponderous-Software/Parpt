package com.preponderous.parpt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "parpt.prompts")
public class PromptProperties {
    private String projectName;
    private String projectDescription;
    private String[] impact;
    private String[] confidence;
    private String[] ease;
    private String[] reach;
    private String[] effort;
}