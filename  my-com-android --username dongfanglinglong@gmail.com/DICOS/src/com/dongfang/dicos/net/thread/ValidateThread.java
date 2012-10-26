package com.dongfang.dicos.net.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class ValidateThread extends Thread {
	private static final String	tag	= "ValidateThread";

	private Handler				handler;
	private Context				context;
	private String				phoneNumber;
	private String				authCode;

	public ValidateThread(Context context, Handler handler, String phoneNumber, String authCode) {
		this.handler = handler;
		this.context = context;
		this.phoneNumber = phoneNumber;
		this.authCode = authCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		String str = new HttpActions(context).validate(phoneNumber, authCode);
		ULog.d(tag, str);
		int result = Integer.valueOf(str);

		Message msg = new Message();
		if (-1000 == result) {
			msg.obj = "用户名密码不得为空";
			msg.arg1 = -1000;
		} else if (-999 == result) {
			msg.arg1 = -999;
			msg.obj = "手机号码格式错误";
		} else if (-1 == result) {
			msg.arg1 = -1;
			msg.obj = "帐号或密码错误";
		} else if (1 == result) {
			msg.arg1 = 1;
			msg.obj = "登录成功";
		}
		msg.what = ComParams.HANDLER_RESULT_VALIDATE;
		handler.sendMessage(msg);

	}

}
