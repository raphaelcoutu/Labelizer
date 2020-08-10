package com.raphaelcoutu.labelizer.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path root = Paths.get("uploads");

    private final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Override
    public void init() {
        try {
            if (Files.notExists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            logger.error("Could not initialize folder for upload! Error: " + e.getMessage(), e);
        }
    }

    @Override
    public void save(MultipartFile file, String filename, String extension) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(filename + "." + extension));
            Thumbnails.of(file.getInputStream())
                    .size(192, 108)
                    .outputFormat("jpg")
                    .outputQuality(1)
                    .toFile("uploads/" + filename + "_thumb");
        } catch (Exception e) {
            logger.error("Could not store the file. Error: " + e.getMessage(), e);
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                logger.error("Could not read the file!");
                throw new RuntimeException("Error.");
            }
        } catch (MalformedURLException e) {
            logger.error("Error: " + e.getMessage(), e);
            throw new RuntimeException("Error.");
        }
    }

    @Override
    public void delete(String filename) throws IOException {
        FileUtils.forceDelete(root.resolve(filename).toFile());
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            logger.error("Could not load the files!");
            throw new RuntimeException("Could not load the files!");
        }
    }
}
