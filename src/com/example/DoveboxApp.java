package com.example;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.example.support.ConfigWrapper;

/**
 * 自定义application类
 * @author Jack
 * @version 创建时间：2014-3-10 下午6:45:02
 */
public class DoveboxApp extends Application {
	public static String L99 = "wwere9D_baidu";
	public final String KEY_USER = "com.l99.firsttime.user";
	public boolean isUp = false;
	public static int screenWidth;
	public static int screenHeight;
	/**编辑后保存的图片路径*/
	private String mSaveDirPath;
	private String mSaveImagePath;
	private String mSaveVideoPath;
	protected static boolean s_distribute_wwere_switchable = false;
	private static DoveboxApp s_instance;
	public boolean s_forOrlast = true;
	/** 如果处于聊天界面时，当前聊天对象的龙号 */
	public long CURRENT_CHATTER_LONG_NO;
	/** 是否出席每个机构 */
	public static boolean isPresenceSended;
	/**是否进入注册界面*/
	public boolean isRegister = false;
	/**判断是否是第一次使用app*/
	public static boolean isFirst = false;
	/**进入应用时的纬度*/
	public static double mLatitude;
	/**进入应用时的经度*/
	public static double mLongitude;
	/**引导页已经访问到的位置*/
	private int mPosition = 0;
	public int mWidth;
	public int mHeight;
	
	
	public static String storageDirectory;
	

	public static boolean isDistributeWwereSwitchable() {
		return s_distribute_wwere_switchable;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		s_instance = this;
		ConfigWrapper.initialize(this);
  	}


	/**
	 * 创建文件目录
	 * @param storage
	 */
	private void createAppDir() {
		String state = Environment.getExternalStorageState();
		String storage = null;
		if (Environment.MEDIA_MOUNTED.equals(state)) { // We can read and write the media
			storage = Environment.getExternalStorageDirectory().toString();
		} else {
			storage = getFilesDir().toString();
		}
		storageDirectory = storage;
		StringBuffer sb = new StringBuffer();
		mSaveDirPath = sb.append(storage).append(File.separator).append("FirstTime").append(File.separator).toString();   //注意拼接的路径的工程名称
		createDir(mSaveDirPath);

		sb = new StringBuffer();
		mSaveImagePath = sb.append(mSaveDirPath).append(File.separator).append("image").append(File.separator).toString();
		createDir(mSaveImagePath);

		sb = new StringBuffer();
		mSaveVideoPath = sb.append(mSaveDirPath).append(File.separator).append("video").append(File.separator).toString();
		createDir(mSaveVideoPath);
	}

	private void createDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	
	public static DoveboxApp getInstance() {
		return s_instance;
	}

	public String getSavePath() {
		return mSaveDirPath;
	}

	public String getSaveImagePath() {
		return mSaveImagePath;
	}

	public String getSaveVideoPath() {
		return mSaveVideoPath;
	}
	
	public void setPosition(int i) {
        mPosition = i;
    }

    public int getPosition() {
        return mPosition;
    }
    
    public static int dip2px(Context context, float dipValue){ 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(dipValue * scale + 0.5f); 
    }	 
}