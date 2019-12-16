package com.shiro.steel.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * @desc: 封装返回结果集
 * 
 * @author: jwy
 * @date: 2017/12/15
 */
public class ResultUtil {

    public static String result(final Integer status,final String msg,final Object data,final long l) {
        JSONObject jsonObject = new JSONObject() {
            {
                put("status", status);
                put("msg", msg);
                put("data", data);
                put("total", l);
            }
        };
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    public static String result(final Integer status,final String msg,final Object data,final long l,final long m) {
        JSONObject jsonObject = new JSONObject() {
            {
                put("status", status);
                put("msg", msg);
                put("data", data);
                put("total", l);
                put("pages", m);
            }
        };
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }
    
    public static String result(final Integer status,final String msg,final Object data) {
        JSONObject jsonObject = new JSONObject() {
            {
                put("status", status);
                put("msg", msg);
                put("data", data);
            }
        };
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    public static String result(final Integer status,final String msg) {
        JSONObject jsonObject = new JSONObject() {
            {
                put("status", status);
                put("msg", msg);
            }
        };
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

}
