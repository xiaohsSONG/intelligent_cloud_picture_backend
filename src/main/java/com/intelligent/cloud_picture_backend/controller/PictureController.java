package com.intelligent.cloud_picture_backend.controller;

import com.intelligent.cloud_picture_backend.annotation.AuthCheck;
import com.intelligent.cloud_picture_backend.common.BaseResponse;
import com.intelligent.cloud_picture_backend.common.ResultUtils;
import com.intelligent.cloud_picture_backend.constant.UserConstant;
import com.intelligent.cloud_picture_backend.model.dto.picture.PictureUploadRequest;
import com.intelligent.cloud_picture_backend.model.entity.User;
import com.intelligent.cloud_picture_backend.model.vo.PictureVO;
import com.intelligent.cloud_picture_backend.service.PictureService;
import com.intelligent.cloud_picture_backend.service.UserService;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    private UserService userService;

    @Resource
    private PictureService pictureService;

    /**
     * 上传图片（可重新上传）
     */
    @PostMapping("/upload")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PictureVO> uploadPicture(
            @RequestPart("file") MultipartFile multipartFile,
            PictureUploadRequest pictureUploadRequest,
            HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        PictureVO pictureVO = pictureService.uploadPicture(multipartFile, pictureUploadRequest, loginUser);
        return ResultUtils.success(pictureVO);
    }


}
