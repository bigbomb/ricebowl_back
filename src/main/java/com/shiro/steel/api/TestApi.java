//package com.shiro.steel.api;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shiro.steel.utils.ZxingUtil;
//
///** 
//
//* @author 作者 lujunjie: 
//
//* @version 创建时间：Nov 9, 2019 10:24:04 PM 
//
//* 类说明 
//
//*/
//@RestController
//@RequestMapping(value = "TestApi/v1")
//public class TestApi {
//
//	 @RequestMapping(value = "/testZxing" ,method = RequestMethod.POST)
//	 public void testZxing()
//	 {
//		 ZxingUtil.encodeZxing("http://www.baidu.com");
//	 }
//	 
//	 @RequestMapping(value = "/imageZxing" ,method = RequestMethod.POST)
//	 public void imageZxing()
//	 {
//		 ZxingUtil.decodeZxing();
//	 }
//}
