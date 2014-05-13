package com.dongfang.daohang.params;

import android.app.AlarmManager;
import android.os.Environment;

public class ComParams {
	public static String		SDCARDPATH											= "./sdcard/";
	/** 存放系统文件路径，在Login.java中有可能对其进行修改 */
	/** 有些系统的SD卡目录为external_sdcard*/
//	public static String		FILESAVEPATH										= "./sdcard/downloads/";
	public static String		FILESAVEPATH										= Environment.getExternalStorageDirectory().getPath()+"/downloads/";
	/** 存放系统文件路径，在Login.java中有可能对其进行修改, 无SDCARD的情况下，存放在本地内存 */
	public static String        FILESAVEPATH_NOSDCARD                               = "./udisk/downloads/";
	/** 默认appwidget中存放数据地址 + 源号码文件集存放地 */
	public static final String	FILESAVEPATH_SYSTEM									= "./data/data/com.telecom.video/files/";
	/** 欢迎页面路径 */
	public static final String	FILESAVEPATH_IMAGE									= "./data/data/com.telecom.video/cache/";

	public static String		CONFIGVERSION										= "1.0.0.0";
	public static String		FRISTURL											= "http://client31.v.vnet.mobi/";

	// 首次启动的提示图片
	public static String		PTOMPTICON											= "firstloadicon.png";
	/** 启动客户端进入主页时，主菜单显示的id */
	public static String		DEFAULE_PAGE										= "2";

	public static String		DEFAULTICON											= "defaulticon.png";
	// 客户端启动方式标识位
	public static final String	LOGINTYPE_DEFAULT									= "";
	public static String		LOGINTYPE											= LOGINTYPE_DEFAULT;
	// 是否启动QAS 目前关闭
	public static boolean		ISQAS												= true;

	// ----------file final -----
	public static final int		BUFSIZE												= 2 * 1024;
	public static final String	ZIPFILENAME											= "config.zip";
	public static final String	CLIENTAPKNAME										= "tysx.apk";
	public static final String	CONFILEFILENAME										= "ClientConfigurationFile.xml";
	public static final String	SAVE3GPFILSUFFIX									= ".3gp";
	public static final String	WEBVIEW_404											= "file:///android_asset/404.html";
	/** 短信检测源号码集文件 */
	public static final String	ORIGINATINGNUMBER_FILE								= "src_code_list.txt";							// 短信检测源号码集文件
	/** 判断是否有外部存储器 */

	/*
	 * public static final String SAVE3GPFILEPATH =
	 * Environment.getExternalStoragePublicDirectory(
	 * Environment.DIRECTORY_DOWNLOADS).toString() + "s/";
	 */
	public static final String	SAVE3GPFILEPATH										= FILESAVEPATH;
	public static final String	SAVEAPKFILEPATH										= FILESAVEPATH_SYSTEM;

	// ---------net final -----------
	public static final int		DELAYCONNECT_SECOND									= 1000;										// ms
	public static final int		CONNECT_TIME										= 3;
	public static final String	TRUE												= "true";
	public static final String	FALSE												= "false";
	public static final String	ERROR												= "error";
	public static final String	FAIL_LOGIN											= "fail_login";

	/** 登录正常类型 */
	public static final String	LOGIN_RESULT_TRUE									= "login_result_true";
	/** 登录异常和正常时类型 */
	public static final String	LOGIN_RESULT_TYPE									= "login_result_type";
	/** 访问登录url返回错误信息 */
	public static final String	LOGIN_RESULT_ERROR_FIRST_REQUEST					= "login_result_error_first_request";
	/** 登录返回后需要的解析的xml信息错误（下发错误导致解析错误，或者仅仅解析错误） */
	public static final String	LOGIN_RESULT_ERROR_XML_MSGMAP_NULL					= "login_result_error_xml_msgmap_null";
	/** 登录返回后解析的xml信息显示登录失败 */
	public static final String	LOGIN_RESULT_ERROR_LOGIN_FAIL						= "login_result_error_login_fail";

	/** 登录返回后需要的解析的xml信息错误编号（下发错误导致解析错误，或者仅仅解析错误） */
	public static final String	LOGIN_RESULT_ERROR_XML_MSGMAP_NULL_CODE				= "900";

	/** 解压配置出错 */
	public static final String	LOGIN_RESULT_ERROR_UNZIP_FILE						= "901";

	/** 登录返回后需要的解析的xml信息解析正常，但是缺少升级标识数据 */
	public static final String	LOGIN_RESULT_ERROR_XML_MSGMAP_NO_UPGRADETYPE		= "930";
	/** 登录返回后需要的解析的xml信息解析正常，但是缺少客户端升级地址 */
	public static final String	LOGIN_RESULT_ERROR_XML_MSGMAP_NO_CLIENTUPDATEURI	= "931";
	/** 登录返回后需要的解析的xml信息解析正常，但是缺少客户端升级信息 */
	public static final String	LOGIN_RESULT_ERROR_XML_MSGMAP_NO_RELEASENOTE		= "932";
	/** 登录返回后需要的解析的xml信息解析正常，但是缺少配置文件升级标识数据 */
	public static final String	LOGIN_RESULT_ERROR_XML_MSGMAP_NO_ISCONFIGUPDATE		= "933";
	/** 登录返回后需要的解析的xml信息解析正常，但是缺少配置文件升级地址 */
	public static final String	LOGIN_RESULT_ERROR_XML_MSGMAP_NO_CONFIGUPDATEURI	= "934";
	/** 登录返回后需要的解析的xml信息解析正常，但是缺少UUID标识数据 */
	public static final String	LOGIN_RESULT_ERROR_XML_MSGMAP_NO_UUID				= "935";
	/** 登录返回后需要的解析的xml信息解析正常，但是缺少QAS标识数据 */
	public static final String	LOGIN_RESULT_ERROR_XML_MSGMAP_NO_QAS				= "936";
	
	/** 解压配置文件失败 Zip下发错误 */
	public static final int		CONFIG_UPZIP_ERROR									= 950;
	/** 解析配置文件失败 解析ClientConfigurationFile.xml发生异常 */
	public static final int		CONFIG_ANALYSIS_ERROR								= 951;
	/** 加载配置文件失败 */
	public static final int		CONFIG_UPLOAD_ERROR									= 952;
	

	/** 登录时服务端返回请求非200时状态 */
	public static final String	FAIL_LOGIN_WITH_HTTPSTATUS							= "fail_login_with_httpstatus";

	public static final String	TABTOP												= "TabTop";
	public static final String	SUBMENU												= "SubMenu";
	public static final String	isDowningAPK										= "DOWNING_APK";

	// ------------login final --------
	public static final String	QAS_FIRSTLOADING_PARAM								= "QASFirstLoading";							// 首次登陆提醒用户该应用会上报数据
	public static final String	CONFIGVERSION_PARAM									= "ConfigVersion";
	public static final String	FIRSTLOADING_PARAM									= "FirstLoading";
	public static final String	FIRSTLOADING_SHORTCUT								= "shortcut";									// 首次登陆，创建桌面快捷方式
	public static final String	ISDOWNED_LOGINCONFIG								= "isDowned_loginConfig";						// 是否下载过配置文件素材

	// ------------view handler message.what final ---------
	public static final int		REFRESHSUBMENU										= 201;											// 刷新submenu菜单
	public static final int		SHOWTABTOP											= 202;
	public static final int		SHOWSUBMENU											= 203;
	public static final int		HIDETABTOP											= 204;
	public static final int		HIDESUBMENU											= 205;
	public static final int		TABEVENTLISTENER									= 206;
	public static final int		DISMISSDIALOG										= 207;
	public static final int		SHOWLOADINGDIALOG									= 208;
	public static final int		DISABLETOUCH										= 209;
	public static final int		ENABLETOUCH											= 210;
	public static final int		HANDLER_PLAYVIEW_LOCAL								= 211;
	/** 刷新单个子栏目图片 */
	public static final int		REFRESHSUBMENUSINGLE								= 212;
	/** 在视频播放完毕的推荐页进行播放 */
	public static final int		HANDLER_PLAYVIEW_ISFROMRECOMMEND					= 213;
	/** 视频互动题目解析完毕，显示右侧互动模块 */
	public static final int		QUESTION_SHOW_RIGHTSWITCH							= 214;
	/** 视频互动答题每完成一题，返回信息提示 */
	public static final int		QUESTION_ANSWER_SHOW_PROMPTS						= 215;
	/** 视频互动答题全部完成，返回信息提示 */
	public static final int		QUESTION_ANSWER_OVER_PROMPTS						= 216;
	/** 视频互动答题全部完成，3秒之后关闭panel */
	public static final int		QUESTION_ANSWER_OVER_CLOSE_PANEL					= 217;
	/** 启动外部应用时，外部应该不存在，出现提示信息事件 */
	public static final int		EXTASK_SHOW_NOT_EXIST_MSG							= 218;
	/** 启动下载外部应用事件 */
	public static final int		EXTASK_DOWNLAOD_START								= 219;
	/** 外部应用下载完成事件 ，返回文件名称 */
	public static final int		EXTASK_DOWNLAOD_DONE								= 220;
	/** 下载外部应用出错事件 ，返回文件名称 */
	public static final int		EXTASK_DOWNLAOD_ERROR								= 221;
	/** 外部应用下载进度更变事件 ，返回当前进度和文件长度 */
	public static final int		EXTASK_DOWNLAOD_PROCHG								= 222;

	/**
	 * webview 加载超时 20120412
	 */
	public static final int		WEBVIEW_TIMEOUT										= 223;

	/** 清除缓存结束提示 */
	public static final int		WEBVIEW_CLEARCACHE_OVER								= 224;
	/** 清除缓存开始提示 */
	public static final int		WEBVIEW_CLEARCACHE_START							= 225;

	public static final int		PLAY_CONTROL_BACK									= 226;
	public static final int		PLAY_ERROR_RECEIVE									= 227;
	/** 重播视频 */
	public static final int		PLAY_CONTROL_REPLAY									= 228;
	public static final int		PLAY_CONTROL_VIEWTIMEOUT							= 229;
	/** 视频播放是隐藏webview广告 */
	public static final int		PLAY_CONTROL_GONEWEBVIEW							= 230;

	/** 下载图片和保存首页 */
	public static final int		SAVEIMAGESD											= 231;
	/***取消通知*/
	public static final int		CANCELSTATUS										= 232;
	/***准备视频*/
	public static final int		PREPARESTATE										= 233;
	/***pauseorpaly*/
	public static final int		PAUSEORPLAY										    = 234;
	/***pause*/
	public static final int		PAUSE												= 235;
	public static final String	STREAMVOLUME										= "mStreamVolume";
	
	// -------- 外部应用下载返回信息 ---------------
	/** 文件下载路径 */
	public static final String	EXTASK_KEY_FILE_PATH								= "extask_file_path";
	/** 文件名称 */
	public static final String	EXTASK_KEY_FILE_NAME								= "extask_file_name";
	/** 文件当前下载长度 */
	public static final String	EXTASK_KEY_FILE_CURRENT_LENGTH						= "extask_file_current_length";
	/** 文件总长度 */
	public static final String	EXTASK_KEY_FILE_TOTAL_LENGTH						= "extask_file_total_length";
	/** 文件是否正在下载 */
	public static boolean		EXTASK_STATUS_IS_DOWNLOADING						= false;

	// --------- 互动答题键值 ----------------
	/** 问题id */
	public static final String	QUESTION_ANSWER_KEY_QUESTIONID						= "questionID";
	/** 所选择问题答案的id */
	public static final String	QUESTION_ANSWER_KEY_ANSWERID						= "AnswerID";
	/** 服务端返回信息的信息id */
	public static final String	QUESTION_ANSWER_KEY_ANSWER_RESULTCODE				= "answer_resultID";
	/** 服务端返回信息之后的信息内容 */
	public static final String	QUESTION_ANSWER_KEY_ANSWER_RESULTDESC				= "answer_resultDesc";

	// ------------play view mode and frompage ---------
	// IsFromPage: IsFromPage, IsFromRecommend
	/************** MediaplayActivity播放器用到的常量参数：playMode: Local , Program , Live *******************/
	public static final String	PLAY_VIEW_LOCAL										= "local";
	public static final String	PLAY_VIEW_PROGRAM									= "program";
	public static final String	PLAY_VIEW_LIVE										= "live";
	public static final String	PLAY_VIEW_DEFAULT									= PLAY_VIEW_PROGRAM;							// program
																																	// 为默认播放模式
	public static final String	PLAY_VIEW_ISFROMPAGE								= "isfrompage";
	public static final String	PLAY_VIEW_ISFROMRECOMMEND							= "isfromrecommend";
	/** 第三方页面进入的播放 */
	public static final String	PLAY_VIEW_EXTERNALPAGE								= "externalpages";								// 第三页面进入的播放
	public static final String	PLAY_VIEW_RESULT									= "schedule";

	public static final String	PLAY_KEY_BUNDLEDATA									= "mediadata";
	public static final String	PLAY_KEY_CURPOSITON									= "curpositon";

	public static final String	PLAY_KEY_FROMPLACE									= "fromPlace";
	public static final String	PLAY_KEY_PLAYMODE									= "playMode";
	public static final String	PLAY_KEY_ADPAGE										= "adPage";
	public static final String	PLAY_KEY_DETAILURL									= "detailURL";
	public static final String	PLAY_KEY_VIDEOURL									= "videoURL";
	public static final String	PLAY_KEY_VIEWINFO									= "viewinfo";
	public static final String	PLAY_KEY_VIDEOSTATUS								= "videostatue";
	public static final String	PLAY_KEY_CONTENTID									= "contentid";

	/** @deprecated */
	public static final int		PLAY_LOADING_VIDEO_TIMEOUT							= 15 * 1000 * 4;								// 加载视频需要花费的时间
	/** @deprecated */
	public static final int		PLAY_BUFFER_TIME									= 1000;										// 取每次缓冲值的间隔时间

	// -------------IDS.txt ------------
	public static final String	IDS_FILENAME										= "IDS.txt";
	public static final String	IDS_UTF8											= "utf-8";
	public static final String	IDS_SPLITE											= ":";
	public static final String	IDS_COMMENT											= "//";

	// ------------- AlarmManager -----------
	public static final long	CHK_TIME_REPEAT_ONE_DAY								= 24 * 60 * 60 * 1000;							// 24小时
	public static final long	CHK_TIME_REPEAT_ONE_MINUTE							= 60 * 1000;									// 一分钟

	// activity id --> ChgStatusBar
	public static short			CURRENTACTIVITY_ID									= 0;											// 当前打开对于状态栏来说，打开的

	// 对没一个activity进行编号，用于状态点击事件，具体要返回到的activity
	public static final short	LOGIN_ID											= 0;
	public static final short	LOGINACTIVITY_ID									= 1;
	public static final short	MEDIAPLAYERACTIVITY_ID								= 2;

	/** 获取视频信息时的个类别，分别表示已经下载完成的视频和正在下载的视频 */
	/** 表示下载完成 */
	public static final String	DOWNLOADS											= "downloads";									// 表示下载完成
	/** 表示正在下载 */
	public static final String	DOWNLOADING											= "downloading";								// 表示正在下载

	/**
	 * UUID, 在登陆时由服务端下发，保存在SharedPreferences下
	 */
	public static String		UUID												= "";

	public static String		LOGIN												= "Login";

	public static String		LOGOUT												= "Logout";

	/** 登录成功是xml解析正常的返回值 */
	public static final String	STATE_0												= "0";

	/** 当前版本是最新，无需更新 */
	public static String		STATE_00											= "00";										// 当前版本是最新，无需更新

	/** 有最新版本，强制更新 */
	public static String		STATE_01											= "01";										// 有最新版本，强制更新

	/** 有最新版本，选择更新 */
	public static String		STATE_02											= "02";										// 有最新版本，选择更新

	// -------- 通过appwidget或者notify方式登录客户端 -----------------

	/** 通过intent开启activity时，传递数据的key */
	public static final String	LOGINFORM											= "loginform";
	/** 通过notify方式登录时，登录访问时的url */
	public static final String	LOGIN_LOADURL										= "login_loadurl";

	/** appwidget相关信息保存到SharedPreferences的文件名称 */
	public static final String	APPWIDGET_SHAREDPREFERENCES_NAME					= "appwidget_sharedpreferences";
	/** appwidget自动更新时间间隔保存到SharedPreferences 中字段 */
	public static final String	APPWIDGET_CONTENTLIST_GET_INTERVAL_KEY				= "appwidget_interval";
	/** appwidget当前显示内容的编号id保存到SharedPreferences 中字段 */
	public static final String	APPWIDGET_CONTENTLIST_CURRENT_ID_KEY				= "contentlist_current_id";
	/** notify自动更新时间间隔保存到SharedPreferences 中字段 */
	public static final String	NOTIFY_CONTENTLIST_GET_INTERVAL_KEY					= "notify_interval";

	/** appwidget默认更新时间间隔为30分钟  */
	public static final long	APPWIDGET_CONTENTLIST_GET_INTERVAL_DEFAULT			= 1 * AlarmManager.INTERVAL_HALF_HOUR;
	/** appwidget自动显下一条时间间隔为10秒 */
	public static final long	APPWIDGET_SHOW_NEXT_INTERVAL_DEFAULT				= 10 * 1000;
	/** notify默认更新时间间隔为30分钟 */
	public static final long	NOTIFY_CONTENTLIST_GET_INTERVAL_DEFAULT				= 1 * AlarmManager.INTERVAL_HALF_DAY;
	/** appwidget自动更新时间，默认为{@link #APPWIDGET_CONTENTLIST_GET_INTERVAL_DEFAULT} */
	public static long			APPWIDGET_CONTENTLIST_GET_INTERVAL					= APPWIDGET_CONTENTLIST_GET_INTERVAL_DEFAULT;
	/** appwidget自动显下一条时间间，默认时间为{@link #APPWIDGET_SHOW_NEXT_INTERVAL_DEFAULT} */
	public static long			APPWIDGET_SHOW_NEXT_INTERVAL						= APPWIDGET_SHOW_NEXT_INTERVAL_DEFAULT;
	/** notify的自动更新时间，默认时间为{@link #NOTIFY_CONTENTLIST_GET_INTERVAL_DEFAULT} */
	public static long			NOTIFY_CONTENTLIST_GET_INTERVAL						= NOTIFY_CONTENTLIST_GET_INTERVAL_DEFAULT;

	// -------- appwidget或者notify保存到本地的文件名称 -----------------
	/** appwidget保存到文件的名称 */
	public static final String	APPWIDGET_CONTENTS_FILENAME							= "appwidget_contents.xml";
	/** 订阅提醒保存到文件的名称 */
	public static final String	NOTIFY_CONTENTS_FILENAME							= "notify_contents.xml";

	// -------- 用户信息保存到本地的文件名称 -----------------
	public static final String	USER_INFO											= "userinfo";
	public static final String	USER_INFO_NAME										= "name";
	public static final String	USER_INFO_PASSWORLD									= "passworld";

	// 图片地址
	public static final String	PicUrl												= "PicUrl";
	// 过期时间
	public static final String	ExpTime												= "ExpTime";
	// cover下载成功标识
	public static final String	IsLoadSucess										= "IsLoadSucess";

	// 图片地址
	public static final String	SeverPicUrl											= "SeverPicUrl";
	// 过期时间
	public static final String	SeverExpTime										= "SeverExpTime";
	/**
	 * 默认 webview 超时时间
	 */
	public static final int		WEBVIEW_TIMEOUT_TIME								= 12000;

}
