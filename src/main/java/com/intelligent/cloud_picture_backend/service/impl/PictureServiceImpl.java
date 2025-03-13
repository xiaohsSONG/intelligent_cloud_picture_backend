package com.intelligent.cloud_picture_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intelligent.cloud_picture_backend.exception.ErrorCode;
import com.intelligent.cloud_picture_backend.exception.ThrowUtils;
import com.intelligent.cloud_picture_backend.manager.FileManager;
import com.intelligent.cloud_picture_backend.mapper.PictureMapper;
import com.intelligent.cloud_picture_backend.model.dto.file.UploadPictureResult;
import com.intelligent.cloud_picture_backend.model.dto.picture.PictureUploadRequest;
import com.intelligent.cloud_picture_backend.model.entity.Picture;
import com.intelligent.cloud_picture_backend.model.entity.User;
import com.intelligent.cloud_picture_backend.model.vo.PictureVO;
import com.intelligent.cloud_picture_backend.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
* @author aidishen
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-03-12 23:10:57
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService {

    private final FileManager fileManager;

    public PictureServiceImpl(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public PictureVO uploadPicture(MultipartFile multipartFile, PictureUploadRequest pictureUploadRequest, User loginUser) {
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NO_AUTH_ERROR);

        //用于判断是新增还是更新图片
        Long pictureId = null;
        if(pictureUploadRequest != null) {
            pictureId = pictureUploadRequest.getId();
        }

        //如果是更新图片，需要校验图片是否存在
        if(pictureId != null) {
            boolean exists = this.lambdaQuery()
                    .eq(Picture::getId,pictureId)
                    .exists();
            ThrowUtils.throwIf(!exists, ErrorCode.NOT_FOUND_ERROR,"图片不存在");
        }

        //上传图片，得到信息
        //按照用户id划分目录
        String uploadPathPrefix = String.format("public/%s",loginUser.getId());
        UploadPictureResult uploadPictureResult = fileManager.uploadPicture(multipartFile, uploadPathPrefix);
        //构造要入库的图片信息
        Picture picture = new Picture();
        picture.setUrl(uploadPictureResult.getUrl());
        picture.setName(uploadPictureResult.getPicName());
        picture.setPicSize(uploadPictureResult.getPicSize());
        picture.setPicHeight(uploadPictureResult.getPicHeight());
        picture.setPicScale(uploadPictureResult.getPicScale());
        picture.setPicFormat(uploadPictureResult.getPicFormat());
        picture.setUserId(loginUser.getId());
        //如果pictureId不为空，表示更新，否则是新增
        if(pictureId != null) {
            picture.setId(pictureId);
            picture.setEditTime(new Date());
        }
        boolean result = this.saveOrUpdate(picture);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR,"图片上传失败");
        return PictureVO.objToVo(picture);
    }
}




