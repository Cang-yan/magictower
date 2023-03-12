package com.yibingo.race.common.utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/21 19:40
 */
public class SecurityUtil {

    // key：DES模式下，key必须为8位
    private String key = "18762481";
    // iv：偏移量，ECB模式不需要，CBC模式下必须为8位
    private String iv = "19847936";

    // DES des = new DES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
    private DES des = new DES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());


    public String encrypt(String text) {

        String encrypt = des.encryptBase64(text);
        return encrypt;

    }

    public String decrypt(String encrypt) {
        String decrypt = des.decryptStr(encrypt);
       // System.out.println(decrypt);
        return decrypt;
    }
}
