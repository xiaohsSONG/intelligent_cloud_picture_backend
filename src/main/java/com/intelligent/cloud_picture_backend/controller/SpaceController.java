package com.intelligent.cloud_picture_backend.controller;

import com.intelligent.cloud_picture_backend.annotation.AuthCheck;
import com.intelligent.cloud_picture_backend.common.BaseResponse;
import com.intelligent.cloud_picture_backend.common.DeleteRequest;
import com.intelligent.cloud_picture_backend.common.ResultUtils;
import com.intelligent.cloud_picture_backend.constant.UserConstant;
import com.intelligent.cloud_picture_backend.exception.BusinessException;
import com.intelligent.cloud_picture_backend.exception.ErrorCode;
import com.intelligent.cloud_picture_backend.exception.ThrowUtils;
import com.intelligent.cloud_picture_backend.model.dto.space.SpaceAddRequest;
import com.intelligent.cloud_picture_backend.model.dto.space.SpaceUpdateRequest;
import com.intelligent.cloud_picture_backend.model.entity.Space;
import com.intelligent.cloud_picture_backend.model.entity.User;
import com.intelligent.cloud_picture_backend.model.vo.SpaceVO;
import com.intelligent.cloud_picture_backend.service.SpaceService;
import com.intelligent.cloud_picture_backend.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/space")
public class SpaceController {

    @Resource
    private SpaceService spaceService;

    @Resource
    private UserService userService;

    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateSpace(@RequestBody SpaceUpdateRequest spaceUpdateRequest) {
        if (spaceUpdateRequest == null || spaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将实体类和 DTO 进行转换
        Space space = new Space();
        BeanUtils.copyProperties(spaceUpdateRequest, space);
        // 自动填充数据
        spaceService.fillSpaceBySpaceLevel(space);
        // 数据校验
        spaceService.validSpace(space, false);
        // 判断是否存在
        long id = spaceUpdateRequest.getId();
        Space oldSpace = spaceService.getById(id);
        ThrowUtils.throwIf(oldSpace == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = spaceService.updateById(space);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteSpace(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        //判断是否存在
        Space space = spaceService.getById(id);
        ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR);
        //仅允许空间创建者或管理员删除
        if(!space.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //操作数据库
        boolean result = spaceService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    @PostMapping("/add")
    public BaseResponse<Long> addSpace(@RequestBody SpaceAddRequest spaceAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(spaceAddRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        long newId = spaceService.addSpace(spaceAddRequest, loginUser);
        return ResultUtils.success(newId);
    }
}
