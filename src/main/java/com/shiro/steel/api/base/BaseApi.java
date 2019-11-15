package com.shiro.steel.api.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.entity.User;
import com.shiro.steel.pojo.dto.UserInfoDto;
import com.shiro.steel.utils.HttpUtil;
import com.shiro.steel.utils.ResultUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * @desc:
 * 
 * @author: jwy
 * @date: 2018/1/2
 */
public class BaseApi {

	private final String appId = "wxbbbadfd09a15528a";
	private final String secretKey = "bb5cdff21e04efd5f1dc702e5597b2bd";
    /**
     * 获取 request
     *
     * @return
     */
    protected HttpServletRequest getRequest() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest();
        return request;

    }

    /**
     * 获取用户名称
     * @return
     */
    protected String getUserName() {

        Subject subject = SecurityUtils.getSubject();
        UserInfoDto user = (UserInfoDto) subject.getPrincipal();
        if (null == user) {
            return null;
        }
        return null == user.getUsername() ? null : user.getUsername();

    }

    /**
     * 获取用户名id
     * @return
     */
    protected String getUserId() {

        Subject subject = SecurityUtils.getSubject();
        UserInfoDto user = (UserInfoDto) subject.getPrincipal();
        if (null == user) {
            return null;
        }
        return null == user.getId() ? null : user.getId();

    }

    /**
     * 获取角色id
     * @return
     */
    protected String getRoleId() {

        Subject subject = SecurityUtils.getSubject();
        UserInfoDto user = (UserInfoDto) subject.getPrincipal();
        if (null == user) {
            return null;
        }
        return null == user.getRoleid() ? null : user.getRoleid();

    }

    /**
     * 获取角色名称
     * @return
     */
    protected String getRoleName() {

        Subject subject = SecurityUtils.getSubject();
        UserInfoDto user = (UserInfoDto) subject.getPrincipal();
        if (null == user) {
            return null;
        }
        return null == user.getRoleName() ? null : user.getRoleName();

    }
    
    protected String getWxKey(String code) {
    	 //授权（必填）
        String grant_type = "authorization_code";
        //URL
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        //请求参数
        String params = "appid=" + appId + "&secret=" + secretKey + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String data = HttpUtil.get(requestUrl, params);
        return data;
    	
    }
    

}
