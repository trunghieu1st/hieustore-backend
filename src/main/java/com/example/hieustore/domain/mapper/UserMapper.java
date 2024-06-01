package com.example.hieustore.domain.mapper;

import com.example.hieustore.constant.CommonConstant;
import com.example.hieustore.domain.dto.request.UserCreateDto;
import com.example.hieustore.domain.dto.request.UserUpdateDto;
import com.example.hieustore.domain.dto.response.UserDto;
import com.example.hieustore.domain.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User mapUserCreateDtoToUser(UserCreateDto userCreateDTO);

    @Mappings({
            @Mapping(target = "roleName", source = "role.name")
    })
    UserDto mapUserToUserDto(User user);

    List<UserDto> mapUsersToUserDtos(List<User> user);

    @Mappings({
            @Mapping(target = "birthday", source = "birthday", dateFormat = CommonConstant.PATTERN_DATE),
            @Mapping(target = "avatar", source = "avatar", ignore = true)
    })
    void updateUser(@MappingTarget User user, UserUpdateDto userUpdateDto);
}
