package com.intelligent.cloud_picture_backend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private String userAccount;

    private String userPassword;
}
