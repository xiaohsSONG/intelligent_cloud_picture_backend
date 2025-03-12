package com.intelligent.cloud_picture_backend.controller;

import com.intelligent.cloud_picture_backend.annotation.AuthCheck;
import com.intelligent.cloud_picture_backend.common.BaseResponse;
import com.intelligent.cloud_picture_backend.common.ResultUtils;
import com.intelligent.cloud_picture_backend.constant.UserConstant;
import com.intelligent.cloud_picture_backend.exception.BusinessException;
import com.intelligent.cloud_picture_backend.exception.ErrorCode;
import com.intelligent.cloud_picture_backend.manager.CosManager;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName : FileController
 * @Author : 凤梨气泡韭
 * @Description :
 * @Date: 2025-03-12 22:41
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    private final CosManager cosManager;

    public FileController(CosManager cosManager) {
        this.cosManager = cosManager;
    }

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/upload")
    public BaseResponse<String> testUploadFile(@RequestPart MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String filePath = String.format("/test/%s", fileName);

        File file = null;
        try {
            file = File.createTempFile(filePath,null);
            multipartFile.transferTo(file);
            cosManager.putObject(filePath,file);
            return ResultUtils.success(filePath);
        } catch (IOException e) {
            log.error("file upload error, filepath = " + filePath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"上传失败");
        } finally {
            if (file != null) {
                //删除临时文件
                boolean deleted = file.delete();
                if (!deleted) {
                    log.error("file delete error, filepath = {}",filePath);
                }
            }
        }

    }

    /**
     * 测试文件下载
     * @param filepath 文件路径
     * @param response 响应对象
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/download/")
    public void testDownloadFile(String filepath, HttpServletResponse response) throws IOException {
        COSObjectInputStream cosObjectInputStream = null;
        try {
            COSObject cosObject = cosManager.getObject(filepath);
            cosObjectInputStream = cosObject.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(cosObjectInputStream);
            // 设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filepath);
            //写入响应
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件下载失败");
        } finally {
            //释放流
            if (cosObjectInputStream != null) {
                cosObjectInputStream.close();
            }
        }

    }
}
