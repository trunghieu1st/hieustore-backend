package com.example.hieustore.domain.mapper;

import com.example.hieustore.domain.dto.response.ReviewFileDto;
import com.example.hieustore.domain.entity.ReviewFile;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewFileMapper {

    ReviewFileDto mapReviewFileToReviewFileDto(ReviewFile reviewFile);

    List<ReviewFileDto> mapReviewFilesToReviewFileDtos(List<ReviewFile> reviews);

}
