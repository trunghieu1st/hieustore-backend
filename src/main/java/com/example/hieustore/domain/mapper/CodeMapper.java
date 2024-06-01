package com.example.hieustore.domain.mapper;

import com.example.hieustore.domain.dto.response.CodeDto;
import com.example.hieustore.domain.entity.Code;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CodeMapper {
    @Mappings({
            @Mapping(target = "expirationTime", source = "expirationTime"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "userId", source = "user.id"),
    })
    CodeDto codeToCodeResponseDto(Code code);

}
