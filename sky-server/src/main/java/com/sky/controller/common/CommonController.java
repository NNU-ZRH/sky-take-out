package com.sky.controller.common;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author zrh
 * @version 1.0.0
 * @title CommonController
 * @description <通用接口>
 * @create 2025/3/25 23:13
 **/
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @RequestMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        if (file == null) {
            return Result.error("文件为空");
        }
        log.info("文件开始上传");
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return Result.error("获取文件类型失败");
        }
        //获取文件扩展名.jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        String url = "";
        try {
            url = aliOssUtil.upload(file.getBytes(), fileName);
        } catch (Exception e) {
            log.error("文件上传失败:{0}", e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
        return Result.success(url);
    }
}
