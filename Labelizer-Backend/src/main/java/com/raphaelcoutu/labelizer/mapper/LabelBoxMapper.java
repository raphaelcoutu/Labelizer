package com.raphaelcoutu.labelizer.mapper;

import com.raphaelcoutu.labelizer.dto.LabelBoxCreateDto;
import com.raphaelcoutu.labelizer.dto.LabelBoxListDto;
import com.raphaelcoutu.labelizer.dto.LabelBoxShowDto;
import com.raphaelcoutu.labelizer.entity.LabelBox;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LabelBoxMapper {
    LabelBox listDtoToEntity(LabelBoxListDto dto);
    LabelBox createDtoToEntity(LabelBoxCreateDto dto);
    LabelBoxShowDto entityToShowDto(LabelBox labelBox);
    List<LabelBox> listDtosToEntities(List<LabelBoxListDto> dtos);
}
