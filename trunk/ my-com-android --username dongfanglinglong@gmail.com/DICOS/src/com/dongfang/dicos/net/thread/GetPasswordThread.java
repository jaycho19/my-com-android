package com.dongfang.dicos.net.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class GetPasswordThread extends Thread {

	public static final String	TAG	= "GetPasswordThread";

	private Handler				handler;
	private Context				context;
	private String				phoneNumber;

	public GetPasswordThread(Context context, Handler handler, String phoneNumber) {
		this.handler = handler;
		this.context = context;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public void run() {
		String str = new HttpActions(context).getPassword(phoneNumber);
		ULog.d(TAG, "result = " + str);
		int result = Integer.valueOf(str);
		Message msg = new Message();
		if (-999 == result) {
			msg.obj = "邮箱地址不正确";
		} else if (-1 == result) {
			msg.obj = "密码获取失败";
		} else {
			msg.obj = "密码获取成功，请查看对应邮箱";
		}
		msg.arg1 = result;
		msg.what = ComParams.HANDLER_RESULT_GET_PASSWORD;
		handler.sendMessage(msg);
	}
}
