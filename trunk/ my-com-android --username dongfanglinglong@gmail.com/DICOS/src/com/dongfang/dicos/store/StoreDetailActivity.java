package com.dongfang.dicos.store;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.kzmw.SigneInActivity;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 门店详细信息
 * 
 * @author dongfang
 * */
public class StoreDetailActivity extends Activity implements OnClickListener {

	private static final String	tag	= "StoreDetailActivity";

	private Button				bBack;
	private Button				bSign;

	private TextView			tvName;
	private TextView			tvAddress;
	private TextView			tvTel;

	private String				json;
	private double				x, y;
	private String				telNumber, name;
	private String				id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_detail);

		bBack = (Button) findViewById(R.id.button_store_detail_back);
		bBack.setOnClickListener(this);

		bSign = (Button) findViewById(R.id.button_store_detail_sign);
		bSign.setOnClickListener(this);

		tvName = (TextView) findViewById(R.id.textview_store_detail_name);

		tvAddress = (TextView) findViewById(R.id.textview_store_detail_address);
		tvAddress.setOnClickListener(this);

		tvTel = (TextView) findViewById(R.id.textview_store_detail_tel);
		tvTel.setOnClickListener(this);

		Intent intent = getIntent();
		json = intent.getStringExtra("JSON");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		init(json);
	}

	/** 初始化页面元素 */
	public void init(String json) {
		// {"id":213213,province":"上海","city":"徐汇区","name":"豫园店","address":"黄浦区XX路","tel":"021-67823457","x":123.223323,"y":34.234234},
		// //id 门店id,province 省/直辖市 ,city 市/区，name 店名 ,address 地址, tel 地址, x
		// 经度，y 纬度

		ULog.d(tag, "initElement " + json);

		try {
			JSONObject js = new JSONObject(json);

			String address = js.getString(Actions.ACTIONS_KEY_ADDRESS);
			// String address = js.getString(Actions.ACTIONS_KEY_PROVINCE) +
			// js.getString(Actions.ACTIONS_KEY_CITY)
			// + js.getString(Actions.ACTIONS_KEY_ADDRESS);

			ULog.d(tag, "address = " + address);

			tvAddress.setText(address);

			telNumber = js.getString(Actions.ACTIONS_KEY_TEL);
			tvTel.setText((telNumber.equalsIgnoreCase("null") ? "" : telNumber));

			name = js.getString(Actions.ACTIONS_KEY_NAME).trim();
			tvName.setText(name.contains("德克士") ? name : "德克士" + name);

			x = js.getDouble(Actions.ACTIONS_KEY_X);
			y = js.getDouble(Actions.ACTIONS_KEY_Y);

			id = js.getInt(Actions.ACTIONS_KEY_ID) + "";
			ULog.d(tag, "x = " + x + " ; y = " + y + " ; id = " + id);

		} catch (JSONException e) {
			ULog.d(tag, e.toString());
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.button_store_detail_back:
			finish();
			break;
		case R.id.button_store_detail_sign:
			if (Util.isNetworkAvailable(StoreDetailActivity.this)) {
				intent = new Intent(StoreDetailActivity.this, SigneInActivity.class);
				intent.putExtra("store_name", tvName.getText().toString());
				intent.putExtra("store_add", tvAddress.getText().toString());
				intent.putExtra("store_tel", tvTel.getText().toString());
				intent.putExtra("x", x + "");
				intent.putExtra("y", y + "");
				intent.putExtra("id", id);
				startActivity(intent);
			} else {
				Util.showDialogSetNetWork(StoreDetailActivity.this);
			}

			break;
		case R.id.textview_store_detail_address:
			if (x > 0 && y > 0) {
				intent = new Intent(StoreDetailActivity.this, StoreDetailMapActivity.class);
				intent.putExtra("store_name", name);
				intent.putExtra("store_add", tvAddress.getText().toString());
				intent.putExtra("store_tel", tvTel.getText().toString());
				intent.putExtra("x", x);
				intent.putExtra("y", y);
				intent.putExtra("name", name);
				startActivity(intent);
			}
			break;
		case R.id.textview_store_detail_tel:
			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telNumber));
			startActivity(intent);
			break;
		}
	}
}
