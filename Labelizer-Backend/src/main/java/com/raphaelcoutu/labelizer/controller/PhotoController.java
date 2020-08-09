package com.raphaelcoutu.labelizer.controller;

import com.raphaelcoutu.labelizer.dto.PhotoShowDto;
import com.raphaelcoutu.labelizer.entity.Label;
import com.raphaelcoutu.labelizer.entity.LabelBox;
import com.raphaelcoutu.labelizer.entity.Photo;
import com.raphaelcoutu.labelizer.exception.ResourceNotFoundException;
import com.raphaelcoutu.labelizer.mapper.LabelBoxMapper;
import com.raphaelcoutu.labelizer.mapper.PhotoMapper;
import com.raphaelcoutu.labelizer.repository.DatasetRepository;
import com.raphaelcoutu.labelizer.repository.PhotoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/photos"})
public class PhotoController {
    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    public PhotoController(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    @GetMapping({"/{id}"})
    public PhotoShowDto get(@PathVariable Long id) {
        Photo photo = (Photo)this.photoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
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
        this.photoRepository.delete(photo);

        return ResponseEntity.noContent().build();
    }
}