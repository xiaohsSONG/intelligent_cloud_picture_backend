package com.intelligent.cloud_picture_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.intelligent.cloud_picture_backend.model.dto.picture.PictureUploadRequest;
import com.intelligent.cloud_picture_backend.model.entity.Picture;
import com.intelligent.cloud_picture_backend.model.entity.User;
import com.intelligent.cloud_picture_backend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

/**
* @author aidishen
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-03-12 23:10:57
*/
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);

}
