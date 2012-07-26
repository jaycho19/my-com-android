package com.dongfang.dicos.lottery.info;

import org.json.JSONException;
import org.json.JSONObject;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ULog;

import android.widget.TextView;

public class MyInfoElement {
	private static final String	tag	= "MyInfoElement";

	public TextView				tvCode;
	public TextView				tvAmount;
	public TextView				tvStatus;
	public TextView				tvDate;

	public void initElement(String json) {
		// {"code":3747473272,"amount":20.5,"status":1,"date":"2012.7.1"},
		// //code 小票号，amount 金额, status 0未审核 1已审核，date 提交时间

		ULog.d(tag, "initElement " + json);

		try {
			JSONObject js = new JSONObject(json);

			tvCode.setText("小票号码 ：" + js.getString(Actions.ACTIONS_KEY_CODE));
			tvAmount.setText("小票金额 ：" + js.getString(Actions.ACTIONS_KEY_AMOUNT));
			tvDate.setText("递交时间 ：" + js.getString(Actions.ACTIONS_KEY_DATE));
			if ("1".equals(js.getString(Actions.ACTIONS_KEY_STATUS))) {
				tvStatus.setText("审核状态 ：已审核");
			} else {
				tvStatus.setText("审核状态 ：已审核");
			}

		} catch (JSONException e) {
			ULog.d(tag, e.toString());
		}
	}

}
