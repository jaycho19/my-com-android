package com.dongfang.dicos.net.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class ValidateThread extends Thread {
	private static final String	tag	= "ValidateThread";

	private Handler				handler;
	private Context				context;
	private String				uName;
	private String				uPassword;

	public ValidateThread(Context context, Handler handler, String uName, String uPassword) {
		this.handler = handler;
		this.context = context;
		this.uName = uName;
		this.uPassword = uPassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		String str = new HttpActions(context).validate(uName, uPassword);
		ULog.d(tag, str);
		int result = Integer.valueOf(str);

		Message msg = new Message();
		if (-1000 == result) {
			msg.obj = "用户名密码不得为空";
		} else if (-1001 == result) {
			msg.obj = "已登录";
		} else if (-1 == result) {
			msg.arg1 = -1;
			msg.obj = "用户名或密码错误";
		} else {
			msg.obj = "登录成功";
		}
		msg.arg1 = result;
		msg.what = ComParams.HANDLER_RESULT_VALIDATE;
		handler.sendMessage(msg);

	}

}
