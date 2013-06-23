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
	public static String		IP_CARD									= "192.168.100.191";
	public static String		IP_TEST									= "192.168.100.1";
	public static final int		PORT_CARD								= 80;
	public static final int		PORT_TEST								= 8080;
	public static final int		SOCKET_TIMEOUT							= 2000;

	public static final byte[]	READ_ID									= { 0x40, (byte) 0x96, 0x00, 0x00, 0x00, 0x00,
			0x00, (byte) 0xD6											};
	/** 从第30(0x1E)位开始读取数据,长度为41(0x29),可能一次只能读12位数据，那需要拆分读取数据 */
	public static final byte[]	READ_USERINFO							= { 0x40, (byte) 0x98, 0x00, 0x01, 0x00, 0x00,
			0x02, (byte) 0xDb, 0x1E, 0x29, 0x00, 0x00					};

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
	/** 连接socket网络card */
	public static final int		HANDLER_SOCKET_CONNECT_CARD				= 3000;
	/** SOCKET 获取到卡号 */
	public static final int		HANDLER_SOCKET_GET_CARD_ID				= 3001;
	/** SOCKET 获取到卡内信息 */
	public static final int		HANDLER_SOCKET_GET_USER_INFO			= 3002;

	/** 连接socket网络testZKT */
	public static final int		HANDLER_SOCKET_CONNECT_TEST_ZKT			= 3100;
	/** 初始化中控的测试数据 */
	public static final int		HANDLER_SOCKET_GET_TEST_ZKT_RESTART		= 3101;
	/** 获取测试数据 */
	public static final int		HANDLER_SOCKET_GET_TEST_ZKT_RESULT		= 3102;

	// ------------------------------------------------------------------------------------------
	public static final String	CTWAP									= "CTWAP";
	public static final String	CTNET									= "CTNET";
	public static final String	WIFI									= "WIFI";

	// ------------------------------------------------------------------------------------------
	/** 启动服务中的命令 */
	public static final String	BROADCAST_HANDLER_ACTION_ID				= "bcHandler_id";
	/** 启动服务中的命令 */
	public static final String	BROADCAST_HANDLER_DES					= "bcHandler_des";
	// ------------------------------------------------------------------------------------------
	/** 启动服务中的命令 */
	public static final String	SERVICE_HANDLER_ACTION_ID				= "handler_action_id";
	// ------------------------------------------------------------------------------------------
	public static final String	ACTIVITY_IMAGE_SRC_ID					= "imageId";
	public static final String	ACTIVITY_TITLE							= "title";
	public static final String	ACTIVITY_PAGENAME						= "pageName";
	public static final String	ACTIVITY_USERINFO						= "userInfo";
	public static final String	ACTIVITY_TESTRESULT						= "testResult";

}