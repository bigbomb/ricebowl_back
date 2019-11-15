package com.shiro.steel.api;

import com.shiro.steel.Enum.EnumCode;
import com.shiro.steel.service.LoginLogService;
import com.shiro.steel.service.UserService;
import com.shiro.steel.utils.ResultUtil;
import com.xiaoleilu.hutool.crypto.SecureUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "LoginApi/v1")
public class LoginApi {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object login(String name, String pass, HttpSession session, HttpServletRequest request) {
        return userService.login(name, pass, session, request);
    }

    @RequestMapping(value = "/loginOut",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return ResultUtil.result(EnumCode.OK.getValue(), "退出登陆成功");
        } catch (Exception e) {
            return "/login";
        }
    }
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object test() {
        try {
        	String temp = SecureUtil.md5("123456");
        	return(temp);
        } catch (Exception e) {
            return "/login";
        }
    }
    @RequestMapping(value = "/relogin",method = RequestMethod.GET)
    @CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})//跨域
    public Object relogin() throws IOException {
 
            return ResultUtil.result(EnumCode.UNAUTHORIZED.getValue(), "退出登陆成功"); 
    }
}
