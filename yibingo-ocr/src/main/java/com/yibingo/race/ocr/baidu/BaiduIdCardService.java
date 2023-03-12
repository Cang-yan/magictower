package com.yibingo.race.ocr.baidu;

import com.baidu.aip.ocr.AipOcr;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.ocr.BaseConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/21 15:41
 */

@Service
public class BaiduIdCardService {

    @Autowired
    private BaseConfig baseConfig;


    public Map<String, Object> idCardOcr(String imgBase64, byte[] imgByte) {
        // 初始化一个AipOcr
        AipOcr aipOcr = new AipOcr(baseConfig.getBaiduAppId(), baseConfig.getBaiduApiKey(), baseConfig.getBaiduSecretKey());
        Map<String, Object> data = new HashMap<>();
        try {

            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<>();
            options.put("detect_direction", "false");//检查图片朝向。默认false不检测
            options.put("detect_risk", "false");//是否开启身份证类型检测功能(类型为身份证复印件、临时身份证、身份证翻拍、修改过的身份证)，默认false不开启
            String idCardSide = "front";//身份证含照片一面
//        String idCardSide = "back";//身份证含国徽一面
            //本地图片识别，返回JSON对象
            JSONObject jsonObject = aipOcr.idcard(imgByte, idCardSide, options);
            //获取到我们需要的信息
            JSONObject result = jsonObject.getJSONObject("words_result");

            String name = result.getJSONObject("姓名").getString("words");
            String idCard = result.getJSONObject("公民身份号码").getString("words");


            data.put("realName", name);
            data.put("idCard", idCard);
            return data;
        } catch (Exception e) {
            throw new BaseException(e.toString());
        }

    }
}
