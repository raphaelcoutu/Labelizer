package com.raphaelcoutu.labelizer.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();
    void save(MultipartFile file, String filename, String ext);
    Resource load(String filename);
    void delete(String filename) throws IOException;
    void deleteAll();
    Stream<Path> loadAll();
}
