package com.preponderous.parpt.repo;

import com.preponderous.parpt.domain.Project;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectJsonReaderWriterImpl implements ProjectJsonReaderWriter {
    @Override
    public void writeJson(List<Project> projects) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Project> readJson() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
