package com.intelligent.cloud_picture_backend.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intelligent.cloud_picture_backend.exception.BusinessException;
import com.intelligent.cloud_picture_backend.exception.ErrorCode;
import com.intelligent.cloud_picture_backend.exception.ThrowUtils;
import com.intelligent.cloud_picture_backend.model.dto.space.SpaceAddRequest;
import com.intelligent.cloud_picture_backend.model.entity.Space;
import com.intelligent.cloud_picture_backend.model.entity.User;
import com.intelligent.cloud_picture_backend.model.enums.SpaceLevelEnum;
import com.intelligent.cloud_picture_backend.service.SpaceService;
import com.intelligent.cloud_picture_backend.mapper.SpaceMapper;
import org.springframework.stereotype.Service;

/**
* @author hsong
* @description 针对表【space(空间)】的数据库操作Service实现
* @createDate 2025-03-20 16:33:50
*/
@Service
public class SpaceServiceImpl extends ServiceImpl<SpaceMapper, Space>
    implements SpaceService{

    @Override
    public long addSpace(SpaceAddRequest spaceAddRequest, User loginUser) {
        return 0;
    }

    @Override
    public void validSpace(Space space, boolean add) {
        ThrowUtils.throwIf(space == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String spaceName = space.getSpaceName();
        Integer spaceLevel = space.getSpaceLevel();
        SpaceLevelEnum spaceLevelEnum = SpaceLevelEnum.getEnumByValue(spaceLevel);
        // 要创建
        if (add) {
            if (StrUtil.isBlank(spaceName)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间名称不能为空");
            }
            if (spaceLevel == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间级别不能为空");
            }
        }
        // 修改数据时，如果要改空间级别
        if (spaceLevel != null && spaceLevelEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间级别不存在");
        }
        if (StrUtil.isNotBlank(spaceName) && spaceName.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "空间名称过长");
        }
    }

    @Override
    public void fillSpaceBySpaceLevel(Space space) {
        // 根据空间级别，自动填充限额
        SpaceLevelEnum spaceLevelEnum = SpaceLevelEnum.getEnumByValue(space.getSpaceLevel());
        if (spaceLevelEnum != null) {
            long maxSize = spaceLevelEnum.getMaxSize();
            if (space.getMaxSize() == null) {
                space.setMaxSize(maxSize);
            }
            long maxCount = spaceLevelEnum.getMaxCount();
            if (space.getMaxCount() == null) {
                space.setMaxCount(maxCount);
            }
        }
    }

}




