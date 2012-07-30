package com.dongfang.dicos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.thread.RestaurentListThread;
import com.dongfang.dicos.store.StoreDetailActivity;
import com.dongfang.dicos.store.StoreListAdapter;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

public class StoreSearchActivity extends Activity implements OnClickListener {

	public static final String		tag	= "StoreSearchActivity";

	/** 顶部菜单右侧进度条 */
	private ProgressBar				progressBar;

	private AutoCompleteTextView	etProvince;
	private AutoCompleteTextView	etCity;

	private Button					bSeach;
	private Button					bShowMore;
	private Button					bBack;

	// private Button bShowProvinceList;
	// private Button bShowCityList;

	private ListView				lvStoreInfo;
	private StoreListAdapter		adapter;

	private StoreSeachHandler		handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storesearch);
		handler = new StoreSeachHandler();

		progressBar = (ProgressBar) findViewById(R.id.progressbar_storesearch);

		bBack = (Button) findViewById(R.id.button_storesearch_back);
		bBack.setOnClickListener(this);

		etProvince = (AutoCompleteTextView) findViewById(R.id.autocompletetextview_storeseach_province);
		etProvince.setAdapter(new ArrayAdapter<String>(this, R.layout.storesearch_province, getResources()
				.getStringArray(R.array.province)));

		etProvince.setOnClickListener(this);

		etCity = (AutoCompleteTextView) findViewById(R.id.autocompletetextview_storeseach_city);
		etCity.setOnClickListener(this);

		// etCity.setOnFocusChangeListener(new OnFocusChangeListener() {
		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// * android.view.View.OnFocusChangeListener#onFocusChange(android
		// * .view.View, boolean)
		// */
		// @Override
		// public void onFocusChange(View v, boolean hasFocus) {
		// ULog.d(tag, "onFocusChange " + hasFocus);
		// if (hasFocus) {
		// String[] aCity =
		// getCityArray(etProvince.getText().toString().trim());
		// if (null != aCity) {
		// for (String s : aCity) {
		// ULog.d(tag, "onFocusChange " + s);
		// }
		//
		// etCity.setAdapter(new ArrayAdapter<String>(StoreSearchActivity.this,
		// R.layout.storesearch_province, aCity));
		// etCity.showDropDown();
		// }
		// }
		// }
		// });

		// bShowProvinceList = (Button)
		// findViewById(R.id.button_storeseach_province_showlist);
		// bShowProvinceList.setOnClickListener(this);
		// bShowCityList = (Button)
		// findViewById(R.id.button_storeseach_city_showlist);
		// bShowCityList.setOnClickListener(this);

		bSeach = (Button) findViewById(R.id.button_storeseach_seach);
		bSeach.setOnClickListener(this);

		bShowMore = (Button) findViewById(R.id.button_storeseach_showmore);
		bShowMore.setOnClickListener(this);

		lvStoreInfo = (ListView) findViewById(R.id.listView_storeseach_info);
		adapter = new StoreListAdapter(this, handler);

		Intent intent = getIntent();
		if (intent.getBooleanExtra("visibility", false)) {
			bBack.setVisibility(View.VISIBLE);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		lvStoreInfo.setAdapter(adapter);
	}

	private String[] getCityArray(String province) {
		ULog.d(tag, "getCityArray province = " + province);
		String[] aCity = null;
		if (province.startsWith("安徽")) {
			aCity = getResources().getStringArray(R.array.city_anhui);
		} else if (province.startsWith("北京")) {
			aCity = getResources().getStringArray(R.array.city_beijing);
		} else if (province.startsWith("福建")) {
			aCity = getResources().getStringArray(R.array.city_fujian);
		} else if (province.startsWith("甘肃")) {
			aCity = getResources().getStringArray(R.array.city_gansu);
		} else if (province.startsWith("广东")) {
			aCity = getResources().getStringArray(R.array.city_guangdong);
		} else if (province.startsWith("广西")) {
			aCity = getResources().getStringArray(R.array.city_guangxi);
		} else if (province.startsWith("贵州")) {
			aCity = getResources().getStringArray(R.array.city_guizhou);
		} else if (province.startsWith("河北")) {
			aCity = getResources().getStringArray(R.array.city_hebei);
		} else if (province.startsWith("河南")) {
			aCity = getResources().getStringArray(R.array.city_henan);
		} else if (province.startsWith("黑龙")) {
			aCity = getResources().getStringArray(R.array.city_heilongjiang);
		} else if (province.startsWith("湖北")) {
			aCity = getResources().getStringArray(R.array.city_hubei);
		} else if (province.startsWith("湖南")) {
			aCity = getResources().getStringArray(R.array.city_hunan);
		} else if (province.startsWith("吉林")) {
			aCity = getResources().getStringArray(R.array.city_jilin);
		} else if (province.startsWith("江苏")) {
			aCity = getResources().getStringArray(R.array.city_jiangsu);
		} else if (province.startsWith("江西")) {
			aCity = getResources().getStringArray(R.array.city_jiangxi);
		} else if (province.startsWith("辽宁")) {
			aCity = getResources().getStringArray(R.array.city_liaoning);
		} else if (province.startsWith("内蒙")) {
			aCity = getResources().getStringArray(R.array.city_neimenggu);
		} else if (province.startsWith("宁夏")) {
			aCity = getResources().getStringArray(R.array.city_ningxia);
		} else if (province.startsWith("青海")) {
			aCity = getResources().getStringArray(R.array.city_qinghai);
		} else if (province.startsWith("山东")) {
			aCity = getResources().getStringArray(R.array.city_shandong);
		} else if (province.startsWith("山西")) {
			aCity = getResources().getStringArray(R.array.city_shanxi_1);
		} else if (province.startsWith("陕西")) {
			aCity = getResources().getStringArray(R.array.city_shanxi_3);
		} else if (province.startsWith("上海")) {
			aCity = getResources().getStringArray(R.array.city_shanghai);
		} else if (province.startsWith("四川")) {
			aCity = getResources().getStringArray(R.array.city_sichuan);
		} else if (province.startsWith("西藏")) {
			aCity = getResources().getStringArray(R.array.city_xizang);
		} else if (province.startsWith("新疆")) {
			aCity = getResources().getStringArray(R.array.city_xinjiang);
		} else if (province.startsWith("云南")) {
			aCity = getResources().getStringArray(R.array.city_yunnan);
		} else if (province.startsWith("浙江")) {
			aCity = getResources().getStringArray(R.array.city_zhejiang);
		} else if (province.startsWith("重庆")) {
			aCity = getResources().getStringArray(R.array.city_chongqing);
		} else if (province.startsWith("天津")) {
			aCity = getResources().getStringArray(R.array.city_tianjin);
		}

		return aCity;

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick " + v.getId());
		switch (v.getId()) {
		// case R.id.button_storeseach_city_showlist:
		// break;
		// case R.id.button_storeseach_province_showlist:
		// ULog.d(tag, "R.id.button_storeseach_province_showlist");
		// if (etProvince.isPopupShowing()) {
		// etProvince.dismissDropDown();
		// } else {
		// etProvince.showDropDown();
		// }
		// break;
		case R.id.button_storesearch_back:
			finish();
			break;
		case R.id.autocompletetextview_storeseach_city:
			String[] aCity = getCityArray(etProvince.getText().toString().trim());
			if (null != aCity) {
				for (String s : aCity) {
					ULog.d(tag, "onFocusChange " + s);
				}

				etCity.setAdapter(new ArrayAdapter<String>(StoreSearchActivity.this, R.layout.storesearch_province,
						aCity));
				etCity.showDropDown();
			}
			break;
		case R.id.autocompletetextview_storeseach_province:
			etProvince.showDropDown();
			break;
		case R.id.button_storeseach_seach:
			if (Util.isNetworkAvailable(StoreSearchActivity.this)) {
				String province = etProvince.getText().toString().trim();
				String city = etCity.getText().toString().trim();
				if (province.length() > 0 && city.length() > 0) {
					String phoneNumber = Util.getPhoneNumber(StoreSearchActivity.this);
					if (!progressBar.isShown()) {
						progressBar.setVisibility(View.VISIBLE);
					}
					new RestaurentListThread(StoreSearchActivity.this, handler, phoneNumber, province, city).start();
					bSeach.setClickable(false);
				} else {
					Toast.makeText(StoreSearchActivity.this, "请输入省市和县区...", Toast.LENGTH_LONG).show();
				}
			} else {
				Util.showDialogSetNetWork(this);
			}
			break;
		}
	}

	class StoreSeachHandler extends Handler {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Bundle data;
			switch (msg.what) {
			case ComParams.HANDLER_INTENT_GOTO_STORE_DETAIL:
				ULog.d(tag, msg.obj.toString());
				Intent intent = new Intent(StoreSearchActivity.this, StoreDetailActivity.class);
				intent.putExtra("JSON", msg.obj.toString());
				startActivity(intent);
				break;
			case ComParams.HANDLER_RESULT_RESTAURENTLIST:
				data = msg.getData();
				if (!TextUtils.isEmpty(data.getString(Actions.ACTIONS_KEY_ACT))
						&& data.getString(Actions.ACTIONS_KEY_ACT)
								.equalsIgnoreCase(Actions.ACTIONS_TYPE_RESTAURENTLIST)) {
					for (String s : data.getStringArray(Actions.ACTIONS_KEY_DATA)) {
						ULog.d(tag, s);
					}

					adapter.setArray(data.getStringArray(Actions.ACTIONS_KEY_DATA));
					adapter.notifyDataSetChanged();

				}
				if (progressBar.isShown()) {
					progressBar.setVisibility(View.GONE);
				}

				// JSONObject json = new JSONObject();
				// try {
				// json.put(Actions.ACTIONS_KEY_ID, "213213");
				//
				// json.put(Actions.ACTIONS_KEY_PROVINCE, "上海");
				// json.put(Actions.ACTIONS_KEY_CITY, "徐汇区");
				// json.put(Actions.ACTIONS_KEY_NAME, "豫园店");
				// json.put(Actions.ACTIONS_KEY_ADDRESS, "黄浦区XX路");
				// json.put(Actions.ACTIONS_KEY_TEL, "021-67823457");
				// json.put(Actions.ACTIONS_KEY_X, 121.624156);
				// json.put(Actions.ACTIONS_KEY_Y, 31.259928);
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// String s = json.toString();
				// String[] as = new String[] { s, s, s, s, s };
				//
				// adapter.setArray(as);
				// adapter.notifyDataSetChanged();
				//
				// if (progressBar.isShown()) {
				// progressBar.setVisibility(View.GONE);
				// }
				bSeach.setClickable(true);
				break;

			default:
				break;
			}
		}
	}
}
