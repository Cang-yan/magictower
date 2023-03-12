package com.yibingo.race.storage.cloud;

import com.yibingo.race.storage.enums.OssTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: Oss配置
 * @author: smalljop
 * @create: 2018-10-18 13:53
 **/
@Data
@Component
@Slf4j
public class OssStorageConfig {


    /**
     * oss 类型
     * 参考 OssTypeEnum.java
     */
    private OssTypeEnum ossType = OssTypeEnum.QCLOD;


    /**
     * 阿里云：endpoint
     */
    private String endpoint;


    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;


    /**
     * 桶名
     */
    private String bucketName = "meta-1312685046";


    /**
     * 预览域名
     */
    private String domain = "https://meta-1312685046.cos-website.ap-nanjing.myqcloud.com";


    /*
     *腾讯云AppId
     * */
    private Integer qcloudAppId = 1312685046;


    /*
     *腾讯云SecretId
     * */
    private String qcloudSecretId = "AKIDMVP3QiuqUrxmKbmnZ4IGxNVMZWBYpwpS";

    /*
     *腾讯云SecretKey
     * */
    private String qcloudSecretKey = "RdbIkIPIxNuDG3btvhGckdHfojOnwrWw";

    /*
     *腾讯云地域信息
     * */
    private String qcloudRegion = "ap-nanjing";

    /**
     * 本地存储文件存放地址
     */
    private String uploadFolder;


    /**
     * 本地存储文件访问路径
     */
    private String accessPathPattern;


}
