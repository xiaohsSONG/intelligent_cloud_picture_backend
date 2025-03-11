package com.intelligent.cloud_picture_backend.exception;

/**
 * @ClassName : ThrowUtils
 * @Author : 凤梨气泡韭
 * @Description :
 * @Date: 2025-03-05 20:25
 */
public class ThrowUtils {
    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param runtimeException 异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     */
    public static void throwIf(boolean condition,ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static void throwIf(boolean condition,ErrorCode errorCode,String message) {
        throwIf(condition, new BusinessException(errorCode,message));
    }
}
