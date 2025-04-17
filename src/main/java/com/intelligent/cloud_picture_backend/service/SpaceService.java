package com.intelligent.cloud_picture_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.intelligent.cloud_picture_backend.model.dto.space.SpaceAddRequest;
import com.intelligent.cloud_picture_backend.model.dto.space.SpaceQueryRequest;
import com.intelligent.cloud_picture_backend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.intelligent.cloud_picture_backend.model.entity.User;
import com.intelligent.cloud_picture_backend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

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

    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别填充空间大小
     * @param space
     */
    void fillSpaceBySpaceLevel(Space space);
}
