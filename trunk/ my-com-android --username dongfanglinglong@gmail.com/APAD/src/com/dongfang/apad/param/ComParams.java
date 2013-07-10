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
	public static String		IP_CARD									= "192.168.1.102";
	public static String		IP_TEST									= "192.168.1.104";
	public static int			PORT_CARD								= 8899;
	public static int			PORT_TEST								= 8899;
	public static int			SOCKET_TIMEOUT							= 5000;

	// ------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------

	public static final String	SHAREDPREFERENCES_IPANDPROT				= "IPANDPROT";
	public static final String	SHAREDPREFERENCES_IPANDPROT_CARD_IP		= "card_ip";
	public static final String	SHAREDPREFERENCES_IPANDPROT_CARD_PORT	= "card_port";
	public static final String	SHAREDPREFERENCES_IPANDPROT_ZKT_IP		= "zkt_ip";
	public static final String	SHAREDPREFERENCES_IPANDPROT_ZKT_PORT	= "zkt_port";

	// ------------------------------------------------------------------------------------------

	public static final String	NAME_SPACE								= "http://tempuri.org/";
	public static final String	URL_BASE								= "http://service.jklaile.com/TiWeiService.asmx";
	public static final String	URL_GET									= URL_BASE + "/Internet";
	public static final String	URL_POST								= "http://service.jklaile.com/TiWeiService.asmx";
	public static final int		HTTP_TIMEOUT							= 5000;

	public static final String	URL_BASE_IMGAGE							= "http://img3.tv189.cn";

	// ------------------------------------------------------------------------------------------
	/** 用户的设置相关信息 */
	public static final String	SHAREDPREFERENCES_USER_SETTINGS			= "USER_SETTINGS";
	/** 用户下载路径 */
	public static final String	SHAREDPREFERENCES_USER_DOWNLOAD_PATH	= "DOWNLOAD_PATH";
	// ------------------------------------------------------------------------------------------
	/** 视频默认下载路径 */
	// public static final String DOWNLOAD_DEFAULT_SDCARD_PATH =
	// "sdcard/APAD/dl";
	public static final String	DOWNLOAD_DEFAULT_SDCARD_PATH			= Environment.getExternalStorageDirectory() + "/APAD/dl";
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
	/** SOCKET 写输入到卡内 */
	public static final int		HANDLER_SOCKET_WRITE_CARD_INFO			= 3050;
	/** SOCKET 写输入到卡内失败 */
	public static final int		HANDLER_SOCKET_WRITE_CARD_FAILED		= 3051;

	/** 连接socket网络testZKT */
	public static final int		HANDLER_SOCKET_CONNECT_TEST_ZKT			= 3100;
	/** 初始化中控的测试数据 */
	public static final int		HANDLER_SOCKET_GET_TEST_ZKT_START		= 3101;
	/** 获取测试数据 */
	public static final int		HANDLER_SOCKET_GET_TEST_ZKT_RESULT		= 3102;

	/** 数据保存到云端 */
	public static final int		HANDLER_SOCKET_SAVE_TO_CLOUD			= 3200;

	/** 关闭连接*/
	public static final int		HANDLER_SOCKET_CLOSE_CARD				= 3300;
	public static final int		HANDLER_SOCKET_CLOSE_TEST_ZKT			= 3301;

	// ------------------------------------------------------------------------------------------
	public static final String	CTWAP									= "CTWAP";
	public static final String	CTNET									= "CTNET";
	public static final String	WIFI									= "WIFI";

	// ------------------------------------------------------------------------------------------
	/** 启动服务中的命令 */
	public static final String	BROADCAST_HANDLER_ACTION_ID				= "bcHandler_id";
	/** 启动服务中的命令 */
	public static final String	BROADCAST_HANDLER_DES					= "bcHandler_des";
	/** 启动服务中的命令 */
	public static final String	BROADCAST_HANDLER_CARD_ID				= "card_id";
	/** 启动服务中的命令 */
	public static final String	BROADCAST_HANDLER_IS_CONNECTED			= "is_connected";
	// ------------------------------------------------------------------------------------------
	/** 启动服务中的命令 */
	public static final String	SERVICE_HANDLER_ACTION_ID				= "handler_action_id";
	public static final String	SERVICE_HANDLER_REMOVE_ALL				= "handler_remove_all";
	public static final String	SERVICE_CLEAR_TESTINFO					= "clear_testinfo";
	// ------------------------------------------------------------------------------------------
	public static final String	ACTIVITY_IMAGE_SRC_ID					= "imageId";
	public static final String	ACTIVITY_TITLE							= "title";
	public static final String	ACTIVITY_PAGENAME						= "pageName";
	public static final String	ACTIVITY_USERINFO						= "userInfo";
	public static final String	ACTIVITY_TESTRESULT						= "testResult";

	// 1 none AC-CF-23-20-68-8B ZKT
	// 2 none AC-CF-23-20-68-88 CARD
}