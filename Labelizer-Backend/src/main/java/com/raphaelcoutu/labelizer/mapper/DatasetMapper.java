package com.raphaelcoutu.labelizer.mapper;

import com.raphaelcoutu.labelizer.dto.DatasetListDto;
import com.raphaelcoutu.labelizer.dto.DatasetShowDto;
import com.raphaelcoutu.labelizer.entity.Dataset;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface DatasetMapper {
    DatasetShowDto entityToShowDto(Dataset entity);
    Dataset listDtoToEntity(DatasetListDto dto);

    List<DatasetListDto> entitiesToListDtos(List<Dataset> entities);
}
