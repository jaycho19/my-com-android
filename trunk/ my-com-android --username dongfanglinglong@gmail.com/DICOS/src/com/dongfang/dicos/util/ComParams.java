package com.dongfang.dicos.util;

public class ComParams {

	/** 开发者的App Key */
	public static final String	SINA_APP_KEY								= "1938380460";
	/** 开发者的App Secret */
	public static final String	SINA_APP_SECRET								= "ac2200609e4dea39f5d55864f035c5f2";
	/** 开发者的应用地址 */
	public static final String	SINA_APP_URL								= "http://www.dicos.com.cn/";

	/** 新浪微博用activity进行建权时，在intent中传url时的键 */
	public static final String	SINA_AUTH_BY_ACTIVITY_INTENT_URL_NAME		= "URL";

	/** 开发者的App Key */
	public static final String	KAIXIN_APP_KEY								= "82490134807554cd3c38f8d54ac5306e";
	/** 开发者的App Secret */
	public static final String	KAIXIN_APP_SECRET							= "ca94b8c5e86b6e8eaaa6b7c73e794df5";

	/** 开发者的应用地址 */
	public static final String	KAIXIN_APP_URL								= "http://www.dicos.com.cn/";

	/** 开心用activity进行建权时，在intent中传url时的键 */
	public static final String	KAIXIN_AUTH_BY_ACTIVITY_INTENT_URL_NAME		= "URL";

	public static final String	CURRENT_SEASON_IMG_ARRARY					= "img_list";

	/** 下载缓存单元大小 */
	public static final int		BUF_SIZE									= 2 * 1024;

	// ----------------------------------------------------------------------------
	/** 项目中，SharedPreferences 的文件目录 */
	public static final String	SHAREDPREFERENCES_FILE_NAME					= "dicos";
	/** 手机号码 */
	public static final String	SHAREDPREFERENCES_PHONE_NUMBER				= "phoneNumber";
	/** 昵称 */
	public static final String	SHAREDPREFERENCES_NICK_NAME					= "nickName";
	/** token */
	public static final String	SHAREDPREFERENCES_TOKEN						= "token";
	/** ts */
	public static final String	SHAREDPREFERENCES_TS						= "ts";
	/** 用户号码 */
	public static final String	SHAREDPREFERENCES_USER_NO					= "uno";

	/** 登录状态 */
	public static final String	SHAREDPREFERENCES_LOGIN_STATUE				= "loginStatus";

	/** ip地址 */
	public static final String	SHAREDPREFERENCES_IP_AREA					= "iparea";

	/** 当季活动图片地址 */
	public static final String	SHAREDPREFERENCES_CRUUENTSEASON_IMG_URLS	= "cruuentSeasonImgUrls";
	/** 卡滋美味首页数据 */
	public static final String	SHAREDPREFERENCES_KZME_IFNO					= "kzme_ifno";
	/** 卡滋美味二级餐单点击数据 */
	public static final String	SHAREDPREFERENCES_KZME_IFNO_TYPE_CATE		= "kzme_ifno_type_cate";

	// ----------------------------------------------------------------------------
	/** 获取验证码时，锁定获取验证码按钮事件（秒） */
	public static final int		BUTTON_GET_AUTH_CODE_LOCKED_SECOND			= 60;
	/** 开心建权失败 */
	public static final int		HANDLER_KAIXIN_LOGIN_ERROR					= 400;

	// ----------------------------------------------------------------------------

	/** 获取短信验证码 */
	public static final int		HANDLER_LOGIN_GET_AUTH_CODE					= 1000;
	/** 短信验证码期间，不允许再次点击获取验证码按钮 */
	public static final int		HANDLER_LOGIN_GET_AUTH_CODE_LOCKED			= 1001;
	/** 登录事件 */
	public static final int		HANDLER_LOGIN_LOGIN							= 1002;
	/** 签到事件 */
	public static final int		HANDLER_SIGNE_IN							= 1003;

	// ----------------------------------------------------------------------------
	/** 获取验证，反馈之前锁定登录按钮 */
	public static final int		HANDLER_RESULT_LOGIN						= 2000;
	/** 登录结果反馈，反馈之前锁定登录按钮 */
	public static final int		HANDLER_RESULT_VALIDATE						= 2001;
	/** 登出结果反馈 */
	public static final int		HANDLER_RESULT_LOGOUT						= 2002;
	/** 抽奖结果反馈 */
	public static final int		HANDLER_RESULT_LOTTERY						= 2003;
	/** 抽奖历史结果反馈 */
	public static final int		HANDLER_RESULT_LOTTERYHISTORY				= 2004;
	/** 当前抽奖活动是否结束 结果反馈 */
	public static final int		HANDLER_RESULT_LOTTERYLEGAL					= 2005;
	/** 获奖公布 结果反馈 */
	public static final int		HANDLER_RESULT_LOTTERYWINNER				= 2006;
	/** 奖品发放 结果反馈 */
	public static final int		HANDLER_RESULT_LOTTERYDISTRIBUTIONINFO		= 2007;
	/** 抽奖次数排行榜 结果反馈 */
	public static final int		HANDLER_RESULT_LOTTERYRATELIST				= 2008;
	/** 获取门店列表 结果反馈 */
	public static final int		HANDLER_RESULT_RESTAURENTLIST				= 2009;
	/** 历史签到 结果反馈 */
	public static final int		HANDLER_RESULT_SIGNHISTORY					= 2010;
	/** 签到 结果反馈 */
	public static final int		HANDLER_RESULT_SIGN							= 2011;
	/** 意见反馈 结果反馈 */
	public static final int		HANDLER_RESULT_ADVICE						= 2012;
	/** 忘记密码，获取密码 */
	public static final int		HANDLER_RESULT_GET_PASSWORD					= 2013;
	/** 用户注册 */
	public static final int		HANDLER_RESULT_REGISTER						= 2014;

	// ----------------------------------------------------------------------------

	/** 跳转到门店详情页 */
	public static final int		HANDLER_INTENT_GOTO_STORE_DETAIL			= 3000;
	/** finish 城市切换页面 */
	public static final int		HANDLER_FINISH_CITYLIST_ACTIVITY			= 3001;
	/** 修改城市切换页面title */
	public static final int		HANDLER_CHANGE_TITLE_CITYLIST_ACTIVITY		= 3002;

	// ----------------------------------------------------------------------------

	/** 图片下载完成 */
	public static final int		HANDLER_IMAGE_DOWNLOAD_END					= 4000;
	/** 图片下载开始 */
	public static final int		HANDLER_IMAGE_DOWNLOAD_START				= 4001;
	/** 图片下载ING */
	public static final int		HANDLER_IMAGE_DOWNLOAD_ING					= 4002;
	/** 图片下载出错 */
	public static final int		HANDLER_IMAGE_DOWNLOAD_ERROR				= 4003;
	/** 图片下载没有网络 */
	public static final int		HANDLER_IMAGE_DOWNLOAD_NONET				= 4004;

	// ----------------------------------------------------------------------------
	public static String		IPAREA										= "";

}
