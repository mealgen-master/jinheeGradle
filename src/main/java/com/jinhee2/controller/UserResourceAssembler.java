package com.jinhee2.controller;

import com.jinhee2.dto.UsersDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserResourceAssembler implements RepresentationModelAssembler<UsersDTO.Response, EntityModel<UsersDTO.Response>> {
    @Override
    public EntityModel<UsersDTO.Response> toModel(UsersDTO.Response entity) {
        return new EntityModel<>(
                // DTO.Response 객체
                entity,
                // linkTo(컨트롤러 method).withSelfrel(); 해당 링크를 가져옴
                linkTo(methodOn(UserController.class).selectUserDTO(entity.getId())).withSelfRel()
        );
    }
}
