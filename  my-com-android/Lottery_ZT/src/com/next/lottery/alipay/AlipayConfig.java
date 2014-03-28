package com.next.lottery.alipay;

public class AlipayConfig {

	// 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String	PARTNER				= "201403283200107783";
	// 商户收款的支付宝账号
	public static final String	SELLER				= "fangengbiao@126.com";
	
	// 测试sign
	public static final String	SIGN				= "Rw6hokzjeSwOEoGgodWzgH1Y4S3QjfwMzs2ghS2anN8Qh19IC0M6nL9adzU+Har85BeAzr5IR/ZbDzUEPucF50D62x2H9" +
			"aYlPxBUA10e7ZRAGbhwlVP6GjM20VdvArhWjweZpGE3HmKpqy4reC5XBmyZW7piQnwsQlKMd9G1Sgk=";
	// 测试descripiton
	public static final String	DESCRIPTION				= "仰天长啸三声，罚款2元" ;

	public static final String	NOTIFY_URL			= "http://paymentgw.tv189.com/bmspay/msp/alipay/alipayMspNotify.action";
	// 商户（RSA）私钥
	public static final String	RSA_PRIVATE			= "";
	// 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
	public static final String	RSA_ALIPAY_PUBLIC	= "";

	// 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
	public static final String	ALIPAY_PLUGIN_NAME	= "alipay_plugin_20120428msp.apk";

	/** 快捷支付更新接口 */
	public final static String	SERVER_URL			= "https://msp.alipay.com/x.htm";

	/** 支付宝快捷支付在AMS挂包的appid；当进行支付时，客户端未安装快捷支付，默认安装AMS挂的包 */
	public final static String	ALIPAY_APPID		= "50370019000";

	/** 快捷支付包名 */
	public final static String	ALIPAY_PACKAGENAME	= "com.alipay.android.app";
	
	
	

}