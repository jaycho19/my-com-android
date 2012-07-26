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
		// //code СƱ�ţ�amount ���, status 0δ��� 1����ˣ�date �ύʱ��

		ULog.d(tag, "initElement " + json);

		try {
			JSONObject js = new JSONObject(json);

			tvCode.setText("СƱ���� ��" + js.getString(Actions.ACTIONS_KEY_CODE));
			tvAmount.setText("СƱ��� ��" + js.getString(Actions.ACTIONS_KEY_AMOUNT));
			tvDate.setText("�ݽ�ʱ�� ��" + js.getString(Actions.ACTIONS_KEY_DATE));
			if ("1".equals(js.getString(Actions.ACTIONS_KEY_STATUS))) {
				tvStatus.setText("���״̬ �������");
			} else {
				tvStatus.setText("���״̬ �������");
			}

		} catch (JSONException e) {
			ULog.d(tag, e.toString());
		}
	}

}
