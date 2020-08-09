package com.raphaelcoutu.labelizer.controller;

import com.raphaelcoutu.labelizer.dto.*;
import com.raphaelcoutu.labelizer.entity.Dataset;
import com.raphaelcoutu.labelizer.entity.Label;
import com.raphaelcoutu.labelizer.entity.Photo;
import com.raphaelcoutu.labelizer.exception.ResourceNotFoundException;
import com.raphaelcoutu.labelizer.mapper.DatasetMapper;
import com.raphaelcoutu.labelizer.mapper.LabelMapper;
import com.raphaelcoutu.labelizer.mapper.PhotoMapper;
import com.raphaelcoutu.labelizer.repository.DatasetRepository;
import com.raphaelcoutu.labelizer.repository.LabelRepository;
import com.raphaelcoutu.labelizer.repository.PhotoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("api/datasets")
public class DatasetController {

    private final DatasetRepository datasetRepository;
    private final DatasetMapper datasetMapper;

    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    public DatasetController(DatasetRepository datasetRepository, DatasetMapper datasetMapper,
                             LabelRepository labelRepository, LabelMapper labelMapper,
                             PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.datasetRepository = datasetRepository;
        this.datasetMapper = datasetMapper;
        this.labelRepository = labelRepository;
        this.labelMapper = labelMapper;
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    @GetMapping
    public ResponseEntity<Iterable<DatasetListDto>> getAll() {
        List<Dataset> datasets = (List<Dataset>) this.datasetRepository.findAll();
        List<DatasetListDto> datasetDtos = this.datasetMapper.entitiesToListDtos(datasets);
        return ResponseEntity.ok(datasetDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatasetShowDto> get(@PathVariable Long id) {
        Dataset dataset = this.datasetRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        DatasetShowDto datasetDto = this.datasetMapper.entityToShowDto(dataset);
        return ResponseEntity.ok(datasetDto);
    }

    @GetMapping("/{id}/labels")
    public ResponseEntity<List<LabelListDto>> getAllLabelsFromDataset(@PathVariable Long id) {
        List<Label> labels = this.labelRepository.findLabelsByDataset_Id(id);
        return ResponseEntity.ok(this.labelMapper.entitiesToListDtos(labels));
    }

    @GetMapping("/{id}/photos")
    public ResponseEntity<List<PhotoListDto>> getAllPhotosFromDataset(@PathVariable Long id) {
        List<Photo> photos = this.photoRepository.findPhotosByDataset_Id(id);
        return ResponseEntity.ok(this.photoMapper.entitiesToListDtos(photos));
    }

    @GetMapping("/{id}/nextUnverified")
    public ResponseEntity<PhotoShowDto> getNextUnverifiedPhoto(@PathVariable Long id, @RequestParam(required = false) Long exceptId) {
        Photo photo;
        if (exceptId == null) {
            photo = this.photoRepository.findFirstByDataset_IdAndVerifiedFalse(id);
        } else {
            photo = this.photoRepository.findFirstByDataset_IdAndIdNotAndVerifiedFalse(id, exceptId);
        }

        return ResponseEntity.ok(this.photoMapper.entityToShowDto(photo));
    }

    @PostMapping
    public ResponseEntity<Dataset> add(@Valid @RequestBody Dataset datasetDto) {
        Dataset newDataset = this.datasetRepository.save(datasetDto);
        return ResponseEntity.ok(newDataset);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Dataset datasetDto) {
        if (!id.equals(datasetDto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Dataset dataset = this.datasetRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        dataset.setName(datasetDto.getName());
        this.datasetRepository.save(dataset);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        Dataset dataset = this.datasetRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        this.datasetRepository.delete(dataset);

        return ResponseEntity.noContent().build();
    }
}
