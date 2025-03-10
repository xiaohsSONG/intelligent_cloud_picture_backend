package com.yupi.yupi_picture_backend.controller;

import com.yupi.yupi_picture_backend.common.BaseResponse;
import com.yupi.yupi_picture_backend.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("/health")
    public BaseResponse<String> health(){
        return ResultUtils.success("Health");
    }
}
