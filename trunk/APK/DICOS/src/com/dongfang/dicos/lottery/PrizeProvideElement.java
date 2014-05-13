package com.dongfang.dicos.lottery;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.TextView;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ULog;

public class PrizeProvideElement {

	private static final String	tag	= "PrizeProvideElement";

	public TextView				tvCompanyName;
	public TextView				tvAddress;
	public TextView				tvTel;

	public void initElement(String json) {
		ULog.d(tag, "json = " + json);

		// {"address":"xx路xx号","tel":"021-65475683","company":"XX子公司"} //company
		// 公司名称,tel 联系电话,address 联系地址

		try {
			//json = new String(json.getBytes("GBK"), "UTF-8");
			//ULog.d(tag, "json = " + json);
			JSONObject js = new JSONObject(json);
			tvCompanyName.setText(js.getString(Actions.ACTIONS_KEY_COMPANY));
			tvAddress.setText(js.getString(Actions.ACTIONS_KEY_ADDRESS));
			tvTel.setText(js.getString(Actions.ACTIONS_KEY_TEL));

		} catch (JSONException e) {
			ULog.e(tag, e.toString());
			e.printStackTrace();
		} 

	}
}
