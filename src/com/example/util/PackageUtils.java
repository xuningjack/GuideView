package com.example.util;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * apk包操作工具类
 * @author Jack  
 * @version 创建时间：2014年4月28日  上午10:52:25
 */
public class PackageUtils {
	
	/**
	  * 获取版本号
	  * @return 当前应用的版本号
	  */
	 public static String getVersion(Application app) {
	     try {
	         PackageManager manager = app.getPackageManager();
	         PackageInfo info = manager.getPackageInfo(app.getPackageName(), 0);
	         String version = info.versionName;
	         return  version;
	     } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	     }
	 }
}