package com.raphaelcoutu.labelizer.controller;

import com.raphaelcoutu.labelizer.entity.ReportRequest;
import com.raphaelcoutu.labelizer.repository.LabelRepository;
import com.raphaelcoutu.labelizer.repository.PhotoRepository;
import com.raphaelcoutu.labelizer.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;

@RequestMapping("api/reports")
@Controller
public class ReportController {

    private final ReportService reportService;

    Logger logger = LoggerFactory.getLogger(ReportController.class);

    public ReportController(LabelRepository labelRepository, PhotoRepository photoRepository, ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<String> getReport(@RequestBody ReportRequest request, HttpServletResponse response) {
        try {
            // Création du dossier temp actuel
            Path tempDir = reportService.createTempDirectory();
            reportService.createClassesFile(tempDir, request.getLabels());
            reportService.createPhotosAndAnnotations(tempDir, request);
            reportService.createDownload(tempDir, response);
            reportService.deleteTempFiles(tempDir);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return ResponseEntity.ok(request.toString());
    }

}
