package com.raphaelcoutu.labelizer.service;

import com.raphaelcoutu.labelizer.entity.Label;
import com.raphaelcoutu.labelizer.entity.ReportRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ReportService {
    public void init();
    public Path createTempDirectory() throws IOException;
    void createClassesFile(Path tempDir, List<Label> labels) throws IOException;
    void createPhotosAndAnnotations(Path tempDir, ReportRequest request) throws IOException;
    void createDownload(Path tempDir, HttpServletResponse response);

    public void deleteTempFiles(Path tempDir) throws IOException;
}
