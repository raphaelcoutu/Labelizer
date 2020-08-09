package com.raphaelcoutu.labelizer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelBoxListDto {
    private Long id;
    private LabelListDto label;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
}
