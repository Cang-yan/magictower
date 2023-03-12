package com.yibingo.race.ocr;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/21 18:31
 */
@Data
@Component
public class BaseConfig {

    @Value("${baidu.appId}")
    private String baiduAppId;

    @Value("${baidu.apiKey}")
    private String baiduApiKey;

    @Value("${baidu.secretKey}")
    private String baiduSecretKey;


}
