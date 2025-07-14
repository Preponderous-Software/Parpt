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

    public static class NameTakenException extends Exception {
        public NameTakenException(String message) {
            super(message);
        }
    }
}
