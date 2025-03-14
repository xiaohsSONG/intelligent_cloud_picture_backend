package com.intelligent.cloud_picture_backend.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class PictureTagCategory {

    private List<String> tagList;
    private List<String> categoryList;
}
