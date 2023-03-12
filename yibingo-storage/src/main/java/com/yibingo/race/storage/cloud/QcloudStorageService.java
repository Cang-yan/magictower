package com.yibingo.race.storage.cloud;

import cn.hutool.core.util.IdUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.qiniu.util.IOUtils;
import com.yibingo.race.common.exception.BaseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/5/19 11:05
 */
public class QcloudStorageService extends OssStorageService {

    private COSClient client;

    public QcloudStorageService(OssStorageConfig config) {
        this.config = config;
        //初始化
        init();
    }

    private void init() {
        ;
        COSCredentials credentials = new BasicCOSCredentials(config.getQcloudSecretId(),
                config.getQcloudSecretKey());
        //初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        clientConfig.setRegion(new Region(config.getQcloudRegion()));

        client = new COSClient(credentials, clientConfig);
    }

    @Override
    public String upload(InputStream inputStream, String path) {

        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new BaseException("上传文件失败", e);
        }
    }

    @Override
    public String upload(byte[] data, String path) {
        //腾讯云必需要以"/"开头
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

/*
        //上传到腾讯云

        UploadFileRequest request = new UploadFileRequest(config.getQcloudBucketName(), path, data);
        String response = client.uploadFile(request);

        JSONObject jsonObject = JSONObject.parseObject(response);
        if (jsonObject.getInteger("code") != 0) {
            throw new BaseException("文件上传失败，" + jsonObject.getString("message"));
        }*/

        return config.getDomain() + path;


    }

    @Override
    public String upload(MultipartFile multfile, String path) {
        try {
            //腾讯云必需要以"/"开头

            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            // 获取文件名
            String fileName = multfile.getOriginalFilename();
            // 获取文件后缀
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            // 用uuid作为文件名，防止生成的临时文件重复
            final File tempFile = File.createTempFile(IdUtil.simpleUUID(), prefix);
            // MultipartFile to File
            multfile.transferTo(tempFile);

            //上传到腾讯云

            PutObjectRequest putObjectRequest = new PutObjectRequest(config.getBucketName(), path, tempFile);
            PutObjectResult putObjectResult = client.putObject(putObjectRequest);
            //删除临时文件
            tempFile.deleteOnExit();

        } catch (IOException e) {
            throw new BaseException("文件转换失败");

        }

        return config.getDomain() + path;


    }

    @Override
    public void delete(String path) {

    }


}
