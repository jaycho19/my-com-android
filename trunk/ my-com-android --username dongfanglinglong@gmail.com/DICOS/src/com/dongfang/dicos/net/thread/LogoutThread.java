package com.dongfang.dicos.net.thread;

import android.content.Context;
import android.os.Handler;

import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class LogoutThread extends Thread {

	public static final String	tag	= "LogoutThread";

	private Handler				handler;
	private Context				context;
	private String				phoneNumber;

	public LogoutThread(Context context, Handler handler, String phoneNumber) {
		this.handler = handler;
		this.context = context;
		this.phoneNumber = phoneNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		String result = new HttpActions(context).logout(phoneNumber);
		handler.sendEmptyMessage(ComParams.HANDLER_RESULT_LOGOUT);

		ULog.d(tag, result);
		// Bundle data = new Bundle();
		//
		// try {
		// JSONObject js = new JSONObject(result.substring(result.indexOf("=") +
		// 1));
		// data.putString(Actions.ACTIONS_KEY_ACT,
		// js.getString(Actions.ACTIONS_KEY_ACT));
		// data.putString(Actions.ACTIONS_KEY_RESULT,
		// js.getString(Actions.ACTIONS_KEY_RESULT));
		// } catch (JSONException e) {
		// ULog.e(tag, e.toString());
		// }
		//
		// Message msg = new Message();
		// msg.setData(data);
		// msg.what = ComParams.HANDLER_RESULT_LOGOUT;
		// handler.sendMessage(msg);
	}

}
