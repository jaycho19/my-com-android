package com.dongfang.dicos.store;

import org.json.JSONException;
import org.json.JSONObject;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 门店搜索中的每一行对象
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
		// {"id":213213,province":"上海","city":"徐汇区","name":"豫园店","address":"黄浦区XX路","tel":"021-67823457","x":123.223323,"y":34.234234},
		// //id 门店id,province 省/直辖市 ,city 市/区，name 店名 ,address 地址, tel 地址, x
		// 经度，y 纬度

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
