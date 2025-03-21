package com.intelligent.cloud_picture_backend.service;

import com.intelligent.cloud_picture_backend.model.dto.space.SpaceAddRequest;
import com.intelligent.cloud_picture_backend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.intelligent.cloud_picture_backend.model.entity.User;

/**
* @author hsong
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2025-03-20 16:33:50
*/
public interface SpaceService extends IService<Space> {
    /**
     * 创建空间
     *
     * @param spaceAddRequest
     * @param loginUser
     * @return
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    void validSpace(Space space, boolean add);

    void fillSpaceBySpaceLevel(Space space);
}
