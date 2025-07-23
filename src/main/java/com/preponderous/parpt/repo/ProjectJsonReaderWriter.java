
package com.preponderous.parpt.repo;

import com.preponderous.parpt.domain.Project;

import java.util.List;

/**
 * Interface for reading and writing project data to/from JSON format.
 * This interface provides methods to persist and retrieve project data
 * using JSON serialization.
 */
public interface ProjectJsonReaderWriter {
    /**
     * Writes a list of projects to JSON format.
     *
     * @param projects the list of projects to be written to JSON
     */
    void writeJson(List<Project> projects);

    /**
     * Reads and deserializes projects from JSON format.
     *
     * @return a list of projects read from JSON
     */
    List<Project> readJson();
}