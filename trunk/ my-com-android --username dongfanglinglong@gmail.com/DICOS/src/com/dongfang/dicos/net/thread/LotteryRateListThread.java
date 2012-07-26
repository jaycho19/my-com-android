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

public class LotteryRateListThread extends Thread {

	public static final String	tag	= "LotteryRateListThread";

	private Handler				handler;
	private Context				context;
	private String				phoneNumber;

	public LotteryRateListThread(Context context, Handler handler, String phoneNumber) {
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
		String result = new HttpActions(context).lotteryRateList(phoneNumber);
		ULog.d(tag, result);
		Bundle data = new Bundle();

		try {
			JSONObject js = new JSONObject(result.substring(result.indexOf("=") + 1));
			data.putString(Actions.ACTIONS_KEY_ACT, js.getString(Actions.ACTIONS_KEY_ACT));
			int length = js.getJSONArray(Actions.ACTIONS_KEY_DATA).length();
			String[] s = new String[length];
			for (int i = 0; i < length; i++) {
				s[i] = js.getJSONArray(Actions.ACTIONS_KEY_DATA).getString(i);
			}
			data.putStringArray(Actions.ACTIONS_KEY_DATA, s);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		Message msg = new Message();
		msg.setData(data);
		msg.what = ComParams.HANDLER_RESULT_LOTTERYRATELIST;
		handler.sendMessage(msg);
	}

}
