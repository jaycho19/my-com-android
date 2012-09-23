package com.dongfang.dicos.net.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

public class IPArea extends Thread {

	public static final String	tag	= "IPArea";

	private Context				context;
	private String				phoneNumber;

	public IPArea(Context context, String phoneNumber) {
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
		if (TextUtils.isEmpty(phoneNumber))
			return;

		String result = new HttpActions(context).ipArea(phoneNumber);
		ULog.d(tag, result);
		try {
			JSONObject js = new JSONObject(result.substring(result.indexOf("=") + 1));
			if (Actions.ACTIONS_TYPE_IPAREA.equalsIgnoreCase(js.getString(Actions.ACTIONS_KEY_ACT))
					&& !TextUtils.isEmpty(js.getString(Actions.ACTIONS_KEY_AREA))) {
				ComParams.IPAREA = js.getString(Actions.ACTIONS_KEY_AREA);
				Util.saveIpArea(context);
			}
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

	}
}
