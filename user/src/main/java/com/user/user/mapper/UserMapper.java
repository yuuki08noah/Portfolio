package com.user.user.mapper;

import com.user.user.dto.UserDTO;
import com.user.user.dto.UserPublicDTO;
import com.user.user.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface UserMapper {
  UserDTO toUserDTO(User user);
  
  @Mapping(target = "password", source = "password", qualifiedByName = "hashPassword")
  User toUser(UserDTO userDTO);
  UserPublicDTO toUserPublicDTO(User user);

  @Named("hashPassword")
  default String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  };
}
