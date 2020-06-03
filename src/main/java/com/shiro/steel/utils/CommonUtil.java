/**
 * Copyright (c) 2011-2016, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.shiro.steel.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器演示
 *
 * @author who?
 * @date 2017/12/14
 */
public class CommonUtil {

    
   public static String getTimeStamp()
   {
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	   String str = sdf.format(new Date());
	   return str;
   }
   
   public static String[] insert(String[] arr, String... str) {
		int size = arr.length; // 获取原数组长度
		int newSize = size + str.length; // 原数组长度加上追加的数据的总长度
		
		// 新建临时字符串数组
		String[] tmp = new String[newSize]; 
		// 先遍历将原来的字符串数组数据添加到临时字符串数组
		for (int i = 0; i < size; i++) { 
			tmp[i] = arr[i];
		}
		// 在末尾添加上需要追加的数据
		for (int i = size; i < newSize; i++) {
			tmp[i] = str[i - size];
		} 
		return tmp; // 返回拼接完成的字符串数组
	}
	public static File MultipartFileToFile(MultipartFile file) {
		File f = null;
		try {
			InputStream is = file.getInputStream();
			f = new File(file.getOriginalFilename());

			OutputStream os = new FileOutputStream(f);

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return f;
	}

}
