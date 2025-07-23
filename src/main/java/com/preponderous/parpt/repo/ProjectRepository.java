package com.preponderous.parpt.repo;

import com.preponderous.parpt.domain.Project;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {
    private final List<Project> projects = new ArrayList<>();

    public List<Project> findAll() {
        return new ArrayList<>(projects);
    }

    public void clear() {
        projects.clear();
    }

    public void add(Project project) throws NameTakenException {
        if (projects.stream().anyMatch(p -> p.getName().equals(project.getName()))) {
            throw new NameTakenException("Project with the same name already exists");
        }
        projects.add(project);
    }

    public Project findByName(String projectName) throws ProjectNotFoundException {
        Project retrievedProject =  projects.stream()
                .filter(project -> project.getName().equals(projectName))
                .findFirst()
                .orElse(null);
        if (retrievedProject == null) {
            throw new ProjectNotFoundException("Project not found: " + projectName);
        }
        return retrievedProject;
    }

    public static class NameTakenException extends Exception {
        public NameTakenException(String message) {
            super(message);
        }
    }

    public static class ProjectNotFoundException extends Exception {
        public ProjectNotFoundException(String message) {
            super(message);
        }
    }
}
