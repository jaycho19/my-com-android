package com.dongfang.apad.param;

import android.os.Environment;

/**
 * 公共数据
 * 
 * @author dongfang
 * 
 */
public class ComParams {

	// ------------------------------------------------------------------------------------------
	public static String		IP										= "192.168.1.106";
	public static final int		PORT									= 8899;
	public static final byte[]	READ_ID									= { 0x40, (byte) 0x96, 0x00, 0x00, 0x00, 0x00,
			0x00, (byte) 0xD6											};
	public static final byte[]	READ									= { 0x40, (byte) 0x98, 0x00, 0x01, 0x00, 0x00,
			0x02, (byte) 0xDb, 0x07, 0x04, 0x00, 0x00					};

	public static final byte[]	WRITE									= {};

	// ------------------------------------------------------------------------------------------
	public static final String	URL_BASE								= "http://api.tv189.com";
	public static final String	URL_BASE_IMGAGE							= "http://img3.tv189.cn";
	// public static final String URL_BASE = "http://180.168.69.121:8089";
	public static final String	URL_GET									= URL_BASE + "/Internet";
	public static final String	URL_POST								= URL_BASE + "/Internet";

	// ------------------------------------------------------------------------------------------
	/** 用户的设置相关信息 */
	public static final String	SHAREDPREFERENCES_USER_SETTINGS			= "USER_SETTINGS";
	/** 用户下载路径 */
	public static final String	SHAREDPREFERENCES_USER_DOWNLOAD_PATH	= "DOWNLOAD_PATH";
	// ------------------------------------------------------------------------------------------
	/** 视频默认下载路径 */
	// public static final String DOWNLOAD_DEFAULT_SDCARD_PATH =
	// "sdcard/APAD/dl";
	public static final String	DOWNLOAD_DEFAULT_SDCARD_PATH			= Environment.getExternalStorageDirectory()
																				+ "/APAD/dl";
	/** 下载缓存单元大小 */
	public static final int		BUF_SIZE								= 2 * 1024;
	/** 图片缓存URL */
	public static final String	IMAGECACHE_URL							= "/imagecache";

	// ------------------------------------------------------------------------------------------

	/** 图片下载完成 */
	public static final int		HANDLER_IMAGE_DOWNLOAD_END				= 4000;
	/** 图片下载开始 */
	public static final int		HANDLER_IMAGE_DOWNLOAD_START			= 4001;
	/** 图片下载ING */
	public static final int		HANDLER_IMAGE_DOWNLOAD_ING				= 4002;
	/** 图片下载出错 */
	public static final int		HANDLER_IMAGE_DOWNLOAD_ERROR			= 4003;
	/** 图片下载没有网络 */
	public static final int		HANDLER_IMAGE_DOWNLOAD_NONET			= 4004;

	// ------------------------------------------------------------------------------------------
	public static final String	CTWAP									= "CTWAP";
	public static final String	CTNET									= "CTNET";
	public static final String	WIFI									= "WIFI";

	// ------------------------------------------------------------------------------------------

}