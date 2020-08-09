package com.raphaelcoutu.labelizer.mapper;

import com.raphaelcoutu.labelizer.dto.LabelListDto;
import com.raphaelcoutu.labelizer.dto.LabelShowDto;
import com.raphaelcoutu.labelizer.entity.Label;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LabelMapper {
    LabelListDto entityToListDto(Label entity);
    LabelShowDto entityToShowDto(Label entity);
    Label listDtoToEntity(LabelListDto dto);

    List<LabelListDto> entitiesToListDtos(List<Label> entities);
}
