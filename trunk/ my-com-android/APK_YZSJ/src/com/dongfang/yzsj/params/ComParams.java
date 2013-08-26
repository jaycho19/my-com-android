package com.dongfang.yzsj.params;

/**
 * 公共数据
 * 
 * @author dongfang
 * 
 */
public class ComParams {

	public static final String INTENT_HOMEBEAN = "homebean";
	public static final String INTENT_LIVEBEAN = "livebean";
	public static final String INTENT_VODBEAN = "vodbean";
	public static final String INTENT_VODBEAN_VIP = "vodbeanVIP";
	public static final String INTENT_MOVIELIST_CHANNEL = "channel";
	public static final String INTENT_MOVIEDETAIL_CONNENTID = "connentid";

	// ------------------------------------------------------------------------------
	/** 首页 */
	public static final String HTTP_HOME = "http://m.fortune-net.cn/page/hbMobile/?jsonFormat=true";
	// public static final String HTTP_HOME = "http://tv.inhe.net/page/hbMobile/?jsonFormat=true";
	/** 直播 */
	public static final String HTTP_LIVE = "http://tv.inhe.net/page/hbMobile/live.jsp?jsonFormat=true";
	/** 点播 */
	public static final String HTTP_VOD = "http://tv.inhe.net/page/hbMobile/vod.jsp?jsonFormat=true";
	/** 验证码 */
	public static final String HTTP_AUTHCODE = "http://tv.inhe.net/user/user!createVerifyCode.action?";
	/** 登陆 */
	public static final String HTTP_LOGIN = "http://tv.inhe.net/user/user!phoneLogin.action?";
	/** 列表页， 频道页 */
	public static final String HTTP_CHANNEL = "http://tv.inhe.net/page/js/list.jsp?";

	// ------------------------------------------------------------------------------
	public static final String SP_NAME_WINDOW = "SP_NAME_WINDOW";
	public static final String SP_WINDOW_WIDTH = "SP_WINDOW_WIDTH";
	public static final String SP_WINDOW_HEIGHT = "SP_WINDOW_HEIGHT";
	public static final String SP_WINDOW_DENSITY = "SP_WINDOW_DENSITY";
	public static final String SP_NAME_MAIN = "SP_NAME_MAIN";
	public static final String SP_MAIN_LOADING_URL = "SP_MAIN_LOADING_URL";
	public static final String SP_MAIN_LOADING_URL_LASTTIME = "SP_MAIN_LOADING_URL_LASTTIME";
	public static final String SP_MAIN_SHORTCUT_CREATED = "SP_MAIN_SHORTCUT_CREATED";
}