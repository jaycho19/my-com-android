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

	public static final String ACTION_UPDATE_MAINACTIVITY = "com.next.lottery.mainactivity.refresh.tab.item";

}