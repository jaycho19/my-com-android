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

public class LotteryThread extends Thread {

	public static final String	tag	= "LotteryThread";

	private Handler				handler;
	private Context				context;
	private String				code;
	private String				amount;
	private String				phoneNumber;

	public LotteryThread(Context context, Handler handler, String code, String amount, String phoneNumber) {
		this.handler = handler;
		this.context = context;
		this.code = code;
		this.amount = amount;
		this.phoneNumber = phoneNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		String result = new HttpActions(context).lottery(code, amount, phoneNumber);
		ULog.d(tag, result);
		Bundle data = new Bundle();

		try {
			JSONObject js = new JSONObject(result.substring(result.indexOf("=") + 1));
			data.putString(Actions.ACTIONS_KEY_ACT, js.getString(Actions.ACTIONS_KEY_ACT));
			data.putString(Actions.ACTIONS_KEY_RESULT, js.getString(Actions.ACTIONS_KEY_RESULT));
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		Message msg = new Message();
		msg.setData(data);
		msg.what = ComParams.HANDLER_RESULT_LOTTERY;
		handler.sendMessage(msg);
	}

}
