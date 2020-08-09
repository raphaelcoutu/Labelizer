package com.raphaelcoutu.labelizer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LabelShowDto {
    private Long id;
    private String name;
    private DatasetListDto dataset;
    private List<LabelBoxListDto> labelBoxes;
}
