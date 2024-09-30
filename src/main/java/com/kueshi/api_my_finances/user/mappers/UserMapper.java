package com.kueshi.api_my_finances.user.mappers;

import com.kueshi.api_my_finances.user.documents.User;
import com.kueshi.api_my_finances.user.dto.UserResponseDTO;
import com.kueshi.api_my_finances.user.dto.UserUpdateDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDTO userToUserResponseDTO(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateDTO dto, @MappingTarget User entity);
}