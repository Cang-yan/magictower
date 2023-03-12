package com.yibingo.race.ocr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.RecognizeTravelCardOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.RecognizeTravelCardOCRResponse;

/**
 * @description: 腾讯云文字识别 和行程卡识别
 * @author: Yang Xin
 * @time: 2022/5/19 17:39
 */

public class QcloudBasicGeneral {

    /*
    * 通用文字识别
    * */
    public static JSONObject basicGeneralOcr(String imgUrl) throws TencentCloudSDKException {


        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential("AKIDRhhRu63j8G7i28xWpseAPO7ZkeHdjY6N", "2nGTP5UbR1rQikVzC8nBI8hz0kESP5Ud");
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        //接口请求域名
        httpProfile.setEndpoint("ocr.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        OcrClient client = new OcrClient(cred, "ap-shanghai", clientProfile);
        // 实例化一个请求对象,每个接口都会对应一个request对象
        GeneralBasicOCRRequest req = new GeneralBasicOCRRequest();

        req.setImageUrl(imgUrl);
        // 返回的resp是一个GeneralBasicOCRResponse的实例，与请求对象对应
        GeneralBasicOCRResponse resp = client.GeneralBasicOCR(req);
        // 输出json格式的字符串回包
        String s = GeneralBasicOCRResponse.toJsonString(resp);
        JSONObject jsonObject = JSON.parseObject(s);
        return jsonObject;


    }

    /*
    * 行程卡识别
    * */
    public static JSONObject recognizeTravelCardOCR(String imgUrl) throws TencentCloudSDKException {

        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential("AKIDRhhRu63j8G7i28xWpseAPO7ZkeHdjY6N", "2nGTP5UbR1rQikVzC8nBI8hz0kESP5Ud");
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("ocr.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        OcrClient client = new OcrClient(cred, "ap-shanghai", clientProfile);
        // 实例化一个请求对象,每个接口都会对应一个request对象
        RecognizeTravelCardOCRRequest req = new RecognizeTravelCardOCRRequest();
        req.setImageUrl(imgUrl);
        // 返回的resp是一个RecognizeTravelCardOCRResponse的实例，与请求对象对应
        RecognizeTravelCardOCRResponse resp = client.RecognizeTravelCardOCR(req);
        // 输出json格式的字符串回包
        String s = GeneralBasicOCRResponse.toJsonString(resp);
        JSONObject jsonObject = JSON.parseObject(s);
        return jsonObject;


    }

}
