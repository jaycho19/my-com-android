package com.dongfang.dicos.mydicos;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.TextView;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 历史签到中的签到列表元素
 * 
 * @author dongfang
 * */
public class SignHistoryElement {
	private static final String	tag	= "SignHistoryElement";

	public TextView				tvStoreName;
	public TextView				tvSignTime;

	private String				infoJSON;

	public void initElement(String json) {
		// {"name":"豫园店","time":"7月1号 18:38"}, //address 签到的地址，time 签到的时间

		ULog.d(tag, "initElement " + json);

		infoJSON = json;

		try {
			JSONObject js = new JSONObject(json);

			String name = Util.initNameDicos(js.getString(Actions.ACTIONS_KEY_NAME));
			tvStoreName.setText(name);
			tvSignTime.setText(js.getString(Actions.ACTIONS_KEY_TIME));

		} catch (JSONException e) {
			ULog.d(tag, e.toString());
		}
	}
}
