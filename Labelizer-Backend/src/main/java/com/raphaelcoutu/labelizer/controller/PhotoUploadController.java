package com.raphaelcoutu.labelizer.controller;

import com.raphaelcoutu.labelizer.entity.Dataset;
import com.raphaelcoutu.labelizer.entity.Photo;
import com.raphaelcoutu.labelizer.exception.ResourceNotFoundException;
import com.raphaelcoutu.labelizer.repository.DatasetRepository;
import com.raphaelcoutu.labelizer.repository.PhotoRepository;
import com.raphaelcoutu.labelizer.service.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

@Controller
public class PhotoUploadController {

    private final PhotoRepository photoRepository;
    private final DatasetRepository datasetRepository;

    private final StorageService storageService;

    public PhotoUploadController(PhotoRepository photoRepository, DatasetRepository datasetRepository, StorageService storageService) {
        this.photoRepository = photoRepository;
        this.datasetRepository = datasetRepository;
        this.storageService = storageService;
    }

    @PostMapping("/api/datasets/{datasetId}/upload")
    public ResponseEntity<Void> uploadFile(@PathVariable Long datasetId, @RequestParam("files") MultipartFile[] files) {
        Dataset dataset = this.datasetRepository.findById(datasetId).orElseThrow(ResourceNotFoundException::new);

        for (MultipartFile file : files) {
            // Restrict file type to jpeg/png
            if (!Arrays.asList("image/jpeg", "image/png").contains(file.getContentType())) {
                throw new RuntimeException("File type not supported.");
            }

            try {
                Photo photo = new Photo();
                photo.setDataset(dataset);

                UUID filenameUUID = UUID.randomUUID();
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());

                photo.setOriginalFilename(file.getOriginalFilename());
                photo.setFilename(filenameUUID.toString());
                photo.setOriginalExtension(extension);
                photo.setExtension(extension);
                photo.setVerified(false);

                BufferedImage img = ImageIO.read(file.getInputStream());
                photo.setWidth(img.getWidth());
                photo.setHeight(img.getHeight());

                storageService.save(file, filenameUUID.toString(), extension);
                this.photoRepository.save(photo);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.unprocessableEntity().build();
            }
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) throws IOException {

        Resource file = storageService.load(filename);

        InputStream fileInputStream = file.getInputStream();

        byte[] bytes = StreamUtils.copyToByteArray(fileInputStream);

        fileInputStream.close();

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}
