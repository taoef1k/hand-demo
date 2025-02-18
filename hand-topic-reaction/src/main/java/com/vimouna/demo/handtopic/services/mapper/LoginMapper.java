package com.vimouna.demo.handtopic.services.mapper;

import com.vimouna.demo.handtopic.dto.register.RegisterRequest;
import com.vimouna.demo.handtopic.entities.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface LoginMapper {
    @Mapping(source = "fullname", target = "fullName")
    UserEntity toUserEntity(RegisterRequest registerRequest);

    @AfterMapping
    default void toUserEntity(@MappingTarget UserEntity userEntity, RegisterRequest registerRequest) {
        String[] emails = registerRequest.getEmail().split("@");
        userEntity.setUsername(emails[0]);
        userEntity.setRole("USER");
    }
}
