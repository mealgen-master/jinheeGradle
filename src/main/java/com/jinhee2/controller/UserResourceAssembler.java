package com.jinhee2.controller;

import com.jinhee2.dto.UsersDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserResourceAssembler implements RepresentationModelAssembler<UsersDTO.Response, EntityModel<UsersDTO.Response>> {
    @Override
    public EntityModel<UsersDTO.Response> toModel(UsersDTO.Response entity) {
        
        return new EntityModel<>(
                entity,
                linkTo(methodOn(UserController.class).selectUserDTO(entity.getId())).withSelfRel()
        );
    }
}
