package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.id.SaIdUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.yibingo.race.common.utils.Result;

import com.yibingo.race.core.service.UploadFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author : smalljop
 * @description : 上传文件
 * @create : 2020-11-27 14:00
 **/
@Api(
        value = "upload",
        tags = "上传图片"
)
@RestController
@RequestMapping("core/upload")
public class UploadFileController {

    @Autowired
    UploadFileService uploadFileService;


    /**
     * 上传用户文件
     * <p>
     * 用户Id MD5加密 同一个用户的文件放在一个目录下
     *
     * @param file
     * @return
     * @throws IOException
     */

    @ApiOperation(
            value = "存储图片",
            notes = "存储图片"
    )
    @RequestMapping(
            value = "/storage/file/",
            method = RequestMethod.POST
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> uploadUserFile(@RequestParam("file") MultipartFile file) throws IOException {
        String userId = StpUtil.getLoginIdAsString();
        return Result.success(
                uploadFileService.uploadUserFile(file, userId)
        ).map();
    }


}
