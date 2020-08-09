package com.raphaelcoutu.labelizer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PhotoListDto {
    private Long id;
    private String filename;
    private String extension;
    private String originalFilename;
    private String originalExtension;
    private Boolean verified;
    private LocalDateTime createdAt;
}
