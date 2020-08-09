package com.raphaelcoutu.labelizer.controller;

import com.raphaelcoutu.labelizer.dto.LabelBoxCreateDto;
import com.raphaelcoutu.labelizer.dto.LabelBoxShowDto;
import com.raphaelcoutu.labelizer.dto.LabelListDto;
import com.raphaelcoutu.labelizer.entity.Dataset;
import com.raphaelcoutu.labelizer.entity.Label;
import com.raphaelcoutu.labelizer.entity.LabelBox;
import com.raphaelcoutu.labelizer.exception.ResourceNotFoundException;
import com.raphaelcoutu.labelizer.mapper.LabelBoxMapper;
import com.raphaelcoutu.labelizer.repository.LabelBoxRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/labelBoxes")
public class LabelBoxController {

    private final LabelBoxRepository labelBoxRepository;
    private final LabelBoxMapper labelBoxMapper;

    public LabelBoxController(LabelBoxRepository labelBoxRepository, LabelBoxMapper labelBoxMapper) {
        this.labelBoxRepository = labelBoxRepository;
        this.labelBoxMapper = labelBoxMapper;
    }

    @GetMapping("/{id}")
    public LabelBoxShowDto get(@PathVariable Long id) {
        LabelBox labelBox = this.labelBoxRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return this.labelBoxMapper.entityToShowDto(labelBox);
    }

    @PostMapping
    public ResponseEntity<LabelBoxShowDto> add(@RequestBody LabelBoxCreateDto labelBoxCreateDto) {
        LabelBox newLabelBox = this.labelBoxRepository.save(this.labelBoxMapper.createDtoToEntity(labelBoxCreateDto));
        this.labelBoxRepository.refresh(newLabelBox);

        return ResponseEntity.ok(this.labelBoxMapper.entityToShowDto(newLabelBox));
    }


}
