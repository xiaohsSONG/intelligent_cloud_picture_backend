package com.intelligent.cloud_picture_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intelligent.cloud_picture_backend.mapper.PictureMapper;
import com.intelligent.cloud_picture_backend.model.entity.Picture;
import com.intelligent.cloud_picture_backend.service.PictureService;
import org.springframework.stereotype.Service;

/**
* @author aidishen
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-03-12 23:10:57
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService {

}




