package com.example.hieustore.domain.mapper;

import com.example.hieustore.domain.dto.request.SlideRequestDto;
import com.example.hieustore.domain.dto.response.SlideDto;
import com.example.hieustore.domain.entity.Slide;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SlideMapper {
    @Mappings({
            @Mapping(target = "avatar", ignore = true)
    })
    Slide mapSlideRequestDtoToSlide(SlideRequestDto createDto);

    @Mappings({
            @Mapping(target = "avatar", ignore = true)
    })
    void updateSlide(@MappingTarget Slide slide, SlideRequestDto updateDto);

    @Mappings({
            @Mapping(target = "productId", source = "product.id"),
            @Mapping(target = "productName", source = "product.name")
    })
    SlideDto mapSlideToSlideDto(Slide slide);

    List<SlideDto> mapSlideToSlideDto(List<Slide> slides);
}
