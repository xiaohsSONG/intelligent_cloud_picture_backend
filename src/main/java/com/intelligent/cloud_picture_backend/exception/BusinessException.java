package com.intelligent.cloud_picture_backend.exception;

import lombok.Getter;

/**
 * @ClassName : BusinessException
 * @Author : 凤梨气泡韭
 * @Description :
 * @Date: 2025-03-05 20:21
 */

@Getter
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
