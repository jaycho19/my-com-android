package com.dongfang.yzsj.params;

/**
 * 公共数据
 * 
 * @author dongfang
 * 
 */
public class ComParams {

	public static final String INTENT_TODO = "TODO";
	public static final String INTENT_HOMEBEAN = "homebean";
	public static final String INTENT_LIVEBEAN = "livebean";
	public static final String INTENT_VODBEAN = "vodbean";
	public static final String INTENT_SEARCH_CHANNELS = "search_channels";
	public static final String INTENT_VODBEAN_VIP = "vodbeanVIP";
	public static final String INTENT_MOVIELIST_CHANNEL = "channel";
	public static final String INTENT_MOVIEDETAIL_CHANNELID = "channelId";
	public static final String INTENT_MOVIEDETAIL_CONNENTID = "connentid";
	public static final String INTENT_MOVIEDETAIL_BAND = "band";
	public static final String INTENT_MOVIEDETAIL_CLIPID = "clipId";
	public static final String INTENT_MOVIEDETAIL_BEAN = "detailBean";
	public static final String INTENT_ORDER_BEAN = "order";

	// ------------------------------------------------------------------------------
	// private static final String HTTP_BASE = "http://tv.inhe.net/";
	private static final String HTTP_BASE = "http://m.fortune-net.cn";
	/** 首页 */
	public static final String HTTP_HOME = HTTP_BASE + "/page/hbMobile/?jsonFormat=true";
	/** 直播 */
	public static final String HTTP_LIVE = HTTP_BASE + "/page/hbMobile/live.jsp?jsonFormat=true";
	/** 点播 */
	public static final String HTTP_VOD = HTTP_BASE + "/page/hbMobile/vod.jsp?jsonFormat=true";
	/** 详情页 */
	public static final String HTTP_DETAIL = HTTP_BASE + "/page/hbMobile/detail.jsp?jsonFormat=true&";
	/** 验证码 */
	public static final String HTTP_AUTHCODE = HTTP_BASE + "/user/user!createVerifyCode.action?";
	/** 登陆 */
	public static final String HTTP_LOGIN = HTTP_BASE + "/user/user!phoneLogin.action?";
	/** 列表页， 频道页 */
	public static final String HTTP_CHANNEL = HTTP_BASE + "/page/js/list.jsp?";
	/** 搜索 */
	public static final String HTTP_SEARCH = HTTP_BASE + "/page/js/list.jsp?isChannelIds=true&";
	/** 播放鉴权 */
	public static final String HTTP_PLAYAUTH = HTTP_BASE + "/user/user!checkPlayPermissions.action?";
	/** 鉴权不通过，获取订购列表 */
	public static final String HTTP_ORDERLIST = HTTP_BASE + "/page/hbMobile/buyList.jsp?jsonFormat=true&";
	/** 订购产品和退订产品 */
	public static final String HTTP_SUBSCRIPTION_PRODUCT = HTTP_BASE + "/user/user!operateOrder.action?";

	/** 播放地址 */
	public static final String HTTP_PLAYURL = HTTP_BASE
			+ "/user/getPlayUrl.jsp?jsonFormat=true&clientType=m3u8&clipId=1&";
	/** 播放记录 */
	public static final String HTTP_HISTORY = HTTP_BASE
			+ "/page/hbMobile/userOptions.jsp?jsonFormat=true&command=listPlayHistory&";
	/** 增加播放记录 */
	public static final String HTTP_HISTORY_ADD = HTTP_BASE
			+ "/page/hbMobile/userOptions.jsp?jsonFormat=true&command=addPlayHistory&";
	/** 删除播放记录 */
	public static final String HTTP_HISTORY_DEL = HTTP_BASE
			+ "/page/hbMobile/userOptions.jsp?jsonFormat=true&command=removePlayHistory&";
	/** 个人收藏 */
	public static final String HTTP_FAVORITE = HTTP_BASE
			+ "/page/hbMobile/userOptions.jsp?jsonFormat=true&command=listFavorite&";
	/** 添加个人收藏 */
	public static final String HTTP_FAVORITE_ADD = HTTP_BASE
			+ "/page/hbMobile/userOptions.jsp?jsonFormat=true&command=addFavorite&";
	/** 删除个人收藏 */
	public static final String HTTP_FAVORITE_DEL = HTTP_BASE
			+ "/page/hbMobile/userOptions.jsp?jsonFormat=true&command=removeCollect&";
	/** 我的订阅 */
	public static final String HTTP_MY_ORDER = HTTP_BASE
			+ "/page/hbMobile/userOptions.jsp?command=myBilling&jsonFormat=true&";

}