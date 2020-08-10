package com.raphaelcoutu.labelizer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PhotoShowDto {
    private Long id;
    private String filename;
    private String extension;
    private String originalFilename;
    private String originalExtension;
    private Integer width;
    private Integer height;
    private Boolean verified;
    private LocalDateTime createdAt;
    private DatasetListDto dataset;
    private List<LabelBoxListDto> labelBoxes;
}
