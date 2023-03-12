/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.yibingo.race.common.utils;

import com.yibingo.race.common.constant.ResponseCodeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private int code = ResponseCodeConstants.SUCCESS;

    private String msg;

    private Object data;


    public static Result success() {
        return restResult(null, ResponseCodeConstants.SUCCESS, null);
    }

    public static Result success(Object data) {
        return restResult(data, ResponseCodeConstants.SUCCESS, null);
    }

    public static Result success(Object data, String msg) {
        return restResult(data, ResponseCodeConstants.SUCCESS, msg);
    }

    public static Result failed() {
        return restResult(null, ResponseCodeConstants.FAIL, null);
    }


    public static Result failed(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static Result failed(String msg) {
        return restResult(null, ResponseCodeConstants.FAIL, msg);
    }

    public static Result failed(Object data, String msg) {
        return restResult(data, ResponseCodeConstants.FAIL, msg);
    }


    public static Result restResult(Object data, int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
    public Map<String,Object> map(){
        Map<String,Object> map = new HashMap<>();
        map.put("message", this.getMsg());
        map.put("code", this.getCode());
        map.put("data", this.getData());
        return map;

    }
}
