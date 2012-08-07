package com.dongfang.dicos.store;

import org.json.JSONException;
import org.json.JSONObject;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * �ŵ������е�ÿһ�ж���
 * 
 * @author dongfang
 * 
 * */
public class StoreListElement {
	private static final String	tag	= "StoreListElement";

	public ImageView			ivDICOS_IC;
	public ImageView			ivGoto;

	public TextView				tvStoreName;
	public TextView				tvStoreAddress;

	private String				infoJSON;

	public String getInfo() {
		return infoJSON;
	}

	public void initElement(String json) {
		// {"id":213213,province":"�Ϻ�","city":"�����","name":"ԥ԰��","address":"������XX·","tel":"021-67823457","x":123.223323,"y":34.234234},
		// //id �ŵ�id,province ʡ/ֱϽ�� ,city ��/����name ���� ,address ��ַ, tel ��ַ, x
		// ���ȣ�y γ��

		ULog.d(tag, "initElement " + json);

		infoJSON = json;

		try {
			JSONObject js = new JSONObject(json);

			// String address = js.getString(Actions.ACTIONS_KEY_PROVINCE) +
			// js.getString(Actions.ACTIONS_KEY_CITY)
			// + js.getString(Actions.ACTIONS_KEY_ADDRESS);

			String address = js.getString(Actions.ACTIONS_KEY_ADDRESS);

			ULog.d(tag, "address = " + address);

			tvStoreAddress.setText(address);
			String name = Util.initNameDicos(js.getString(Actions.ACTIONS_KEY_NAME));
			tvStoreName.setText(name);

		} catch (JSONException e) {
			ULog.d(tag, e.toString());
		}
	}

}
