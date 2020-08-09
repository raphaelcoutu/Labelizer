package com.raphaelcoutu.labelizer.dto;

import com.raphaelcoutu.labelizer.entity.Label;
import com.raphaelcoutu.labelizer.entity.Photo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelBoxShowDto {
    private Long id;
    private PhotoListDto photo;
    private LabelListDto label;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
}
