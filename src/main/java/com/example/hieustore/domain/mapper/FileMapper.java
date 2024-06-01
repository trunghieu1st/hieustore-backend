package com.example.hieustore.domain.mapper;

import com.example.hieustore.constant.CommonConstant;
import com.example.hieustore.domain.dto.response.FileDto;
import com.example.hieustore.domain.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FileMapper {
    @Mappings({
            @Mapping(target = "lastModifiedDate", source = "lastModifiedDate", dateFormat = CommonConstant.PATTERN_DATE_TIME),
            @Mapping(target = "createdDate", source = "createdDate", dateFormat = CommonConstant.PATTERN_DATE_TIME),
    })
    FileDto mapFileToFileDto(File file);

    List<FileDto> mapFileToFileDto(List<File> files);
}
