package com.raphaelcoutu.labelizer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DatasetShowDto {
    private Long id;
    private String name;
    private List<LabelListDto> labels;
    private List<PhotoListDto> photos;
}
