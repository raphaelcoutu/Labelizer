package com.raphaelcoutu.labelizer.controller;

import com.raphaelcoutu.labelizer.dto.PhotoShowDto;
import com.raphaelcoutu.labelizer.entity.LabelBox;
import com.raphaelcoutu.labelizer.entity.Photo;
import com.raphaelcoutu.labelizer.exception.ResourceNotFoundException;
import com.raphaelcoutu.labelizer.mapper.PhotoMapper;
import com.raphaelcoutu.labelizer.repository.PhotoRepository;
import com.raphaelcoutu.labelizer.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping({"/api/photos"})
public class PhotoController {
    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    private final StorageService storageService;

    public PhotoController(PhotoRepository photoRepository, PhotoMapper photoMapper, StorageService storageService) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
        this.storageService = storageService;
    }

    @GetMapping({"/{id}"})
    public PhotoShowDto get(@PathVariable Long id) {
        Photo photo = this.photoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return this.photoMapper.entityToShowDto(photo);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PhotoShowDto photoDto) {
        if (!id.equals(photoDto.getId())) {
            return ResponseEntity.badRequest().build();
        } else {
            this.photoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
            Photo photo = this.photoMapper.showDtoToEntity(photoDto);
            for (LabelBox labelBox : photo.getLabelBoxes()) {
                labelBox.setPhoto(photo);
            }
            this.photoRepository.save(photo);

            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        Photo photo = this.photoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        try {
            this.storageService.delete(photo.getFilename() + "." + photo.getExtension());
            this.storageService.delete(photo.getFilename() + "_thumb.jpg");

            this.photoRepository.delete(photo);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return ResponseEntity.noContent().build();
    }
}