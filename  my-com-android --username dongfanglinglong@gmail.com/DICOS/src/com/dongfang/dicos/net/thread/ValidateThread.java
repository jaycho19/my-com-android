package com.dongfang.dicos.net.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.dongfang.dicos.kzmw.RegisterActivity;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

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
		Message msg = new Message();
		try {
			JSONObject json = new JSONObject(str);
			Util.setNickName(context, json.optString("nick", ""));
			Util.saveToken(context, json.optString("token", ""));
			Util.saveTS(context, json.optString("ts", ""));
			Util.saveUserNO(context, json.optString("uno", ""));

			int result = json.getInt("s");
			if (-1000 == result) {
				msg.obj = "用户名密码不得为空";
			}
			else if (-1001 == result) {
				msg.obj = "已登录";
			}
			else if (-1 == result) {
				msg.arg1 = -1;
				msg.obj = "用户名或密码错误";
			}
			else {
				msg.obj = "登录成功";
			}
			msg.arg1 = result;
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
			msg.arg1 = -1;
			msg.obj = "用户名或密码错误";
		}

		msg.what = ComParams.HANDLER_RESULT_VALIDATE;
		handler.sendMessage(msg);

	}

}
