
package com.preponderous.parpt.repo;

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
     * @param projects the list of project names to be written to JSON
     */
    void writeJson(List<String> projects);

    /**
     * Reads and deserializes projects from JSON format.
     *
     * @return a list of project names read from JSON
     */
    List<String> readJson();
}