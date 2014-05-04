package com.next.lottery.params;

/**
 * 公共数据
 * 
 * @author gfan,dongfang
 * 
 */
public class ComParams {
	private static final String HTTP_BASE = "http://211.149.200.227:22012";

	public static final String HTTP_URL = HTTP_BASE + "/www/interface/index.php";

	/** KV图 */
	public static final int AREA_CODE_HOME_FRAGMENT_KV = 1;
	/** 推荐 */
	public static final int AREA_CODE_HOME_FRAGMENT_RECOMMEND = 2;
	/** 销售冠军 */
	public static final int AREA_CODE_HOME_FRAGMENT_SAIL_CHAMPION = 3;
	/** 新品 */
	public static final int AREA_CODE_HOME_FRAGMENT_NEW_PRODUCT = 4;
	/** bottom KV 图 */
	public static final int AREA_CODE_HOME_FRAGMENT_BOTTOM_KV = 5;

	public static final String ACTION_UPDATE_MAINACTIVITY = ComParams.class.getName() + ".refresh.tabitem";

	// 右侧菜单行为
	public static final int MENU_TAB_HOME = 0; // 首页
	public static final int MENU_TAB_CLASSIFY = 1; // 分类
	public static final int MENU_TAB_TRADEMARK = 2; // 品牌
	public static final int MENU_TAB_SHOPPINGCART = 3; // 购物车
	public static final int MENU_TAB_USERCENTER = 4; // 个人中心

}