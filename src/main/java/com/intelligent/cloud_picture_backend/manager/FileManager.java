package com.intelligent.cloud_picture_backend.manager;

import com.intelligent.cloud_picture_backend.config.CosClientConfig;
import com.qcloud.cos.COSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName : FileManager
 * @Author : 凤梨气泡韭
 * @Description :
 * @Date: 2025-03-12 23:21
 */
@Slf4j
@Service
public class FileManager {
    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;

}
