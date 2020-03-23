package com.jinhee2.dto;

import com.jinhee2.model.UserRole;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UsersDTO {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Response {
        @NotEmpty
        private Integer id;

        @NotEmpty
        private String name;

        @NotEmpty
        private String address;

        @NotEmpty
        private String phonenumber;

        @NotEmpty
        private List<UserRole> userRoles = new ArrayList<>();
    }
}
