package com.yibingo.race.pay.enums;

/**
 *
 * @author Javen
 */
public enum SignType {
    /**
     * MD5 加密
     */
    MD5("MD5"),
    /**
     * RSA2
     */
    RSA2("RSA2"),
    /**
     * RSA
     */
    RSA("RSA");

    SignType(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }
}
