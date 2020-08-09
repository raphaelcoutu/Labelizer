package com.raphaelcoutu.labelizer.controller;

import com.raphaelcoutu.labelizer.dto.LabelListDto;
import com.raphaelcoutu.labelizer.dto.LabelShowDto;
import com.raphaelcoutu.labelizer.entity.Dataset;
import com.raphaelcoutu.labelizer.entity.Label;
import com.raphaelcoutu.labelizer.exception.ResourceNotFoundException;
import com.raphaelcoutu.labelizer.mapper.LabelMapper;
import com.raphaelcoutu.labelizer.repository.DatasetRepository;
import com.raphaelcoutu.labelizer.repository.LabelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/labels")
public class LabelController {

    private final LabelRepository labelRepository;
    private final DatasetRepository datasetRepository;

    private final LabelMapper labelMapper;

    public LabelController(LabelRepository labelRepository, DatasetRepository datasetRepository, LabelMapper labelMapper) {
        this.labelRepository = labelRepository;
        this.datasetRepository = datasetRepository;
        this.labelMapper = labelMapper;
    }

    @GetMapping("/{id}")
    public LabelShowDto get(@PathVariable Long id) {
        Label label = this.labelRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return this.labelMapper.entityToShowDto(label);
    }

    @PostMapping
    public ResponseEntity<LabelListDto> addToDataset(@RequestBody Label label) {
        Dataset dataset = this.datasetRepository.findById(label.getDataset().getId()).orElseThrow(ResourceNotFoundException::new);

        label.setDataset(dataset);
        Label newLabel = this.labelRepository.save(label);

        return ResponseEntity.ok(this.labelMapper.entityToListDto(newLabel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Label labelDto) {
        if(!id.equals(labelDto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Label label = this.labelRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        label.setName(labelDto.getName());
        this.labelRepository.save(label);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        Label label = this.labelRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        this.labelRepository.delete(label);

        return ResponseEntity.noContent().build();
    }
}
