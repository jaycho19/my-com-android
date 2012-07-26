package com.dongfang.dicos.lottery.info;

import org.json.JSONException;
import org.json.JSONObject;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ULog;

import android.widget.TextView;

public class ProbabilityElement {
	private static final String	tag	= "ProbabilityElement";

	public TextView				tvMobile;
	public TextView				tvTimes;

	public void initElement(String json) {
		// {"mobile":"134xxxx3492","times":10}， //mobile 手机号，times 次数

		ULog.d(tag, "initElement " + json);

		try {
			JSONObject js = new JSONObject(json);

			tvMobile.setText(js.getString(Actions.ACTIONS_KEY_MOBILE));
			tvTimes.setText(js.getString(Actions.ACTIONS_KEY_TIMES));

		} catch (JSONException e) {
			ULog.d(tag, e.toString());
		}
	}

}
