package com.next.lottery.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

import com.dongfang.utils.ULog;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

public class AlipayUtil {
	
	public static String getOrderInfo(String tradeNo, String pName, String pDesc, int subAmount, int fee) {
		String sb = "partner=\"" + AlipayConfig.PARTNER + "\"";
		sb += "&seller=\"" + AlipayConfig.SELLER + "\"";
		sb += "&out_trade_no=\"" + tradeNo + "\"";
		sb += "&subject=\"" + pName + "\"";
		sb += "&body=\"" + pDesc + "\"";
		sb += "&total_fee=\"" + new DecimalFormat("0.00").format((fee * 0.01) * subAmount) + "\"";
		sb += "&notify_url=\"" + AlipayConfig.NOTIFY_URL + "\"";
		return sb;
	}
	
	public static void PayHelper(final Context context,final String info ){
		
		if (TextUtils.isEmpty(info))
			return;
		MobileSecurePayHelper mp = new MobileSecurePayHelper(context);
		// if (!mp.isMobile_spExist()) {// 未安装
		// ULog.d(TAG, "Alipay not install!");
		// mp.firstInstall();
		// }
		// else {
		// 1 未安装，下载安装；2 已安装，更新安装
		mp.updateMobile_sp(new MobileSecurePayHelper.AlipayUpdateCallBack() {

			@Override
			public void onResult(boolean hasUpdate) {
				ULog.d("Alipay is need update : " + hasUpdate);
				if (!hasUpdate) { // 无更新
					Looper.prepare();
					new MobileSecurePayer().pay(info, new Handler() {
						@Override
						public void handleMessage(Message msg) {
							super.handleMessage(msg);
							String ret = (String) msg.obj;
							ULog.d("alipayResult : " + ret);// strRet范例：resultStatus={9000};memo={};result={partner="2088201564809153"&seller="2088201564809153"&out_trade_no="050917083121576"&subject="123456"&body="2010新款NIKE 耐克902第三代板鞋 耐克男女鞋 386201 白红"&total_fee="0.01"&notify_url="http://notify.java.jpxx.org/index.jsp"&success="true"&sign_type="RSA"&sign="d9pdkfy75G997NiPS1yZoYNCmtRbdOP0usZIMmKCCMVqbSG1P44ohvqMYRztrB6ErgEecIiPj9UldV5nSy9CrBVjV54rBGoT6VSUF/ufjJeCSuL510JwaRpHtRPeURS1LXnSrbwtdkDOktXubQKnIMg2W0PreT1mRXDSaeEECzc="}
							switch (msg.what) {
							case AlixId.RQF_PAY: {
								// 处理交易结果
								try {
									// 获取交易状态码，具体状态代码请参看文档
									String tradeStatus = "resultStatus={";
									int imemoStart = ret.indexOf("resultStatus=");
									imemoStart += tradeStatus.length();
									int imemoEnd = ret.indexOf("};memo=");
									tradeStatus = ret.substring(imemoStart, imemoEnd);
									
									ULog.i("tradeStatus"+tradeStatus);

//									if (tradeStatus.equals("9000"))// 判断交易状态码，只有9000表示交易成功
//										new DialogFactory(context).showDialog(context.getString(R.string.warning),
//												context.getString(R.string.subscirbe_result_success),
//												context.getString(R.string.ok),
//												new DialogFactory.onBtnClickListener() {
//
//													@Override
//													public void btnLeftClickListener(View v) {
//														handleAction(result);
//													}
//
//													@Override
//													public void btnNeutralClickListener(View v) {}
//
//													@Override
//													public void btnCloseClickListener(View v) {
//														handleAction(result);
//													}
//
//													@Override
//													public void btnRightClickListener(View v) {
//														handleAction(result);
//													}
//												});
//									else
//										new DialogFactory(context).showDialog(context.getString(R.string.warning),
//												"支付失败。交易状态码:" + tradeStatus, context.getString(R.string.ok), null);
									// }

								} catch (Exception e) {
									String temp = ret.split(";")[0];
									String content = "";
									try {
										content = temp.substring(temp.indexOf("{") + 1, temp.lastIndexOf("}"));
									} catch (Exception e2) {
										content = "未知错误！";
									}
								}
							}
								break;
							}
						}
					}, AlixId.RQF_PAY, (Activity) context);
					Looper.loop();
				}
			}
		});
		
	}
	
	
	/*调出支付宝 Activity*/
	public static void doPayment(Context context){
		try {
			String orderinfo = AlipayUtil.getOrderInfo(AlipayConfig.PARTNER, "lottery", AlipayConfig.DESCRIPTION, 1,
					2);
			String encodeSign = URLEncoder.encode(AlipayConfig.SIGN, "UTF-8");
			String info = orderinfo + "&sign=" + "\"" + encodeSign + "\"" + "&sign_type=" + "\"RSA\"";
			ULog.v("alipay orderInfo = " + info);
			
			AlipayUtil.PayHelper(context, info);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
