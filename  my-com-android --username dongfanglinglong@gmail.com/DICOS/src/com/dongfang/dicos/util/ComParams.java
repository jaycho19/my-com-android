package com.dongfang.dicos.util;

public class ComParams {

	/** �����ߵ�App Key */
	public static final String	SINA_APP_KEY								= "1938380460";
	/** �����ߵ�App Secret */
	public static final String	SINA_APP_SECRET								= "ac2200609e4dea39f5d55864f035c5f2";
	/** �����ߵ�Ӧ�õ�ַ */
	public static final String	SINA_APP_URL								= "http://www.dicos.com.cn/";

	/** ����΢����activity���н�Ȩʱ����intent�д�urlʱ�ļ� */
	public static final String	SINA_AUTH_BY_ACTIVITY_INTENT_URL_NAME		= "URL";

	/** �����ߵ�App Key */
	public static final String	KAIXIN_APP_KEY								= "82490134807554cd3c38f8d54ac5306e";
	/** �����ߵ�App Secret */
	public static final String	KAIXIN_APP_SECRET							= "ca94b8c5e86b6e8eaaa6b7c73e794df5";

	/** �����ߵ�Ӧ�õ�ַ */
	public static final String	KAIXIN_APP_URL								= "http://www.dicos.com.cn/";

	/** ������activity���н�Ȩʱ����intent�д�urlʱ�ļ� */
	public static final String	KAIXIN_AUTH_BY_ACTIVITY_INTENT_URL_NAME		= "URL";

	public static final String	CURRENT_SEASON_IMG_ARRARY					= "img_list";

	/** ���ػ��浥Ԫ��С */
	public static final int		BUF_SIZE									= 2 * 1024;

	// ----------------------------------------------------------------------------
	/** ��Ŀ�У�SharedPreferences ���ļ�Ŀ¼ */
	public static final String	SHAREDPREFERENCES_FILE_NAME					= "dicos";
	/** �ֻ����� */
	public static final String	SHAREDPREFERENCES_PHONE_NUMBER				= "phoneNumber";
	/** �ǳ� */
	public static final String	SHAREDPREFERENCES_NICK_NAME					= "nickName";
	/** token */
	public static final String	SHAREDPREFERENCES_TOKEN						= "token";
	/** ts */
	public static final String	SHAREDPREFERENCES_TS						= "ts";
	/** �û����� */
	public static final String	SHAREDPREFERENCES_USER_NO					= "uno";

	/** ��¼״̬ */
	public static final String	SHAREDPREFERENCES_LOGIN_STATUE				= "loginStatus";

	/** ip��ַ */
	public static final String	SHAREDPREFERENCES_IP_AREA					= "iparea";

	/** �����ͼƬ��ַ */
	public static final String	SHAREDPREFERENCES_CRUUENTSEASON_IMG_URLS	= "cruuentSeasonImgUrls";
	/** ������ζ��ҳ���� */
	public static final String	SHAREDPREFERENCES_KZME_IFNO					= "kzme_ifno";
	/** ������ζ�����͵�������� */
	public static final String	SHAREDPREFERENCES_KZME_IFNO_TYPE_CATE		= "kzme_ifno_type_cate";

	// ----------------------------------------------------------------------------
	/** ��ȡ��֤��ʱ��������ȡ��֤�밴ť�¼����룩 */
	public static final int		BUTTON_GET_AUTH_CODE_LOCKED_SECOND			= 60;
	/** ���Ľ�Ȩʧ�� */
	public static final int		HANDLER_KAIXIN_LOGIN_ERROR					= 400;

	// ----------------------------------------------------------------------------

	/** ��ȡ������֤�� */
	public static final int		HANDLER_LOGIN_GET_AUTH_CODE					= 1000;
	/** ������֤���ڼ䣬�������ٴε����ȡ��֤�밴ť */
	public static final int		HANDLER_LOGIN_GET_AUTH_CODE_LOCKED			= 1001;
	/** ��¼�¼� */
	public static final int		HANDLER_LOGIN_LOGIN							= 1002;
	/** ǩ���¼� */
	public static final int		HANDLER_SIGNE_IN							= 1003;

	// ----------------------------------------------------------------------------
	/** ��ȡ��֤������֮ǰ������¼��ť */
	public static final int		HANDLER_RESULT_LOGIN						= 2000;
	/** ��¼�������������֮ǰ������¼��ť */
	public static final int		HANDLER_RESULT_VALIDATE						= 2001;
	/** �ǳ�������� */
	public static final int		HANDLER_RESULT_LOGOUT						= 2002;
	/** �齱������� */
	public static final int		HANDLER_RESULT_LOTTERY						= 2003;
	/** �齱��ʷ������� */
	public static final int		HANDLER_RESULT_LOTTERYHISTORY				= 2004;
	/** ��ǰ�齱��Ƿ���� ������� */
	public static final int		HANDLER_RESULT_LOTTERYLEGAL					= 2005;
	/** �񽱹��� ������� */
	public static final int		HANDLER_RESULT_LOTTERYWINNER				= 2006;
	/** ��Ʒ���� ������� */
	public static final int		HANDLER_RESULT_LOTTERYDISTRIBUTIONINFO		= 2007;
	/** �齱�������а� ������� */
	public static final int		HANDLER_RESULT_LOTTERYRATELIST				= 2008;
	/** ��ȡ�ŵ��б� ������� */
	public static final int		HANDLER_RESULT_RESTAURENTLIST				= 2009;
	/** ��ʷǩ�� ������� */
	public static final int		HANDLER_RESULT_SIGNHISTORY					= 2010;
	/** ǩ�� ������� */
	public static final int		HANDLER_RESULT_SIGN							= 2011;
	/** ������� ������� */
	public static final int		HANDLER_RESULT_ADVICE						= 2012;
	/** �������룬��ȡ���� */
	public static final int		HANDLER_RESULT_GET_PASSWORD					= 2013;
	/** �û�ע�� */
	public static final int		HANDLER_RESULT_REGISTER						= 2014;

	// ----------------------------------------------------------------------------

	/** ��ת���ŵ�����ҳ */
	public static final int		HANDLER_INTENT_GOTO_STORE_DETAIL			= 3000;
	/** finish �����л�ҳ�� */
	public static final int		HANDLER_FINISH_CITYLIST_ACTIVITY			= 3001;
	/** �޸ĳ����л�ҳ��title */
	public static final int		HANDLER_CHANGE_TITLE_CITYLIST_ACTIVITY		= 3002;

	// ----------------------------------------------------------------------------

	/** ͼƬ������� */
	public static final int		HANDLER_IMAGE_DOWNLOAD_END					= 4000;
	/** ͼƬ���ؿ�ʼ */
	public static final int		HANDLER_IMAGE_DOWNLOAD_START				= 4001;
	/** ͼƬ����ING */
	public static final int		HANDLER_IMAGE_DOWNLOAD_ING					= 4002;
	/** ͼƬ���س��� */
	public static final int		HANDLER_IMAGE_DOWNLOAD_ERROR				= 4003;
	/** ͼƬ����û������ */
	public static final int		HANDLER_IMAGE_DOWNLOAD_NONET				= 4004;

	// ----------------------------------------------------------------------------
	public static String		IPAREA										= "";

}
