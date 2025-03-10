package com.baibei.urls.dto.core;

import lombok.Data;

@Data
public class UserPasswordDto {

    private String oldPassword;
    private String password;
    private String passwordConfirm;

}
