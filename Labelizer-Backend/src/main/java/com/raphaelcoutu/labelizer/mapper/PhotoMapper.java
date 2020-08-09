package com.raphaelcoutu.labelizer.mapper;

import com.raphaelcoutu.labelizer.dto.PhotoListDto;
import com.raphaelcoutu.labelizer.dto.PhotoShowDto;
import com.raphaelcoutu.labelizer.entity.Photo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PhotoMapper {
    PhotoListDto entityToListDto(Photo entity);
    PhotoShowDto entityToShowDto(Photo entity);
    Photo showDtoToEntity(PhotoShowDto dto);
    Photo listDtoToEntity(PhotoListDto dto);

    List<PhotoListDto> entitiesToListDtos(List<Photo> entities);
}
