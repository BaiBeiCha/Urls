package com.baibei.urls.dto.api;

import lombok.Data;

@Data
public class CreateUserDto {

    private String username;
    private String password;
    private String email;

}
