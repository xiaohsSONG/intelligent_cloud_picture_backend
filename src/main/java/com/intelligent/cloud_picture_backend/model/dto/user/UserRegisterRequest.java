package com.intelligent.cloud_picture_backend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = -7194313203711288417L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;


}
