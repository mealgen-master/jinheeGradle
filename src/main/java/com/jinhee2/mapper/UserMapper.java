package com.jinhee2.mapper;

import com.jinhee2.dto.UsersDTO;
import com.jinhee2.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

// 스프링 컨텍스트 & 의존성주입을 사용하기위해 componentModel 속성을 spring으로 지정
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UsersDTO.Response, Users> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target="id",source="id"),
            @Mapping(target="name",source="name"),
            @Mapping(target="address", ignore=true)
    })
    UsersDTO.Response toDto(Users entity);

    @Mappings({
            @Mapping(target="id",source="id"),
            @Mapping(target="name",source="name")
    })
    Users toEntity(UsersDTO.Response dto);
}
