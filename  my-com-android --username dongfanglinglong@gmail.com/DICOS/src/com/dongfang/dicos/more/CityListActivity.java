package com.dongfang.dicos.more;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.view.SideBar;

public class CityListActivity extends Activity implements OnClickListener {
	public static final String	tag	= "TuiSongActivity";

	/** 返回按钮 */
	private Button				bBack;
	private TextView			tvTitle;

	private CityListAdapter		adapter;

	private ArrayList<String>	stringList;
	private MyHandler			handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_citylsit);

		handler = new MyHandler();

		bBack = (Button) findViewById(R.id.button_setting_citylist_back);
		bBack.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.textview_setting_citylist_title);
		tvTitle.setText(ComParams.IPAREA);

		ListView list = (ListView) findViewById(R.id.listview_setting_citylist);
		stringList = InitListViewData();
		adapter = new CityListAdapter(this, stringList, handler);
		list.setAdapter(adapter);
		SideBar indexBar = (SideBar) findViewById(R.id.sidebar_setting_citylist);
		indexBar.setListView(list);
		ULog.d(tag, "indexBar hight = " + indexBar.getHeight());

		final EditText editText = (EditText) findViewById(R.id.edittext_setting_citylist_search);
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				ULog.d(tag, "onTextChanged start = " + start + ", before = " + before + ", count = " + count + ", --"
						+ s.toString());

				if (s.toString().trim().length() > 0) {
					adapter.setList(getListViewDataBy(s.toString().trim()));
					adapter.notifyDataSetChanged();
				} else {
					adapter.setList(stringList);
					adapter.notifyDataSetChanged();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				ULog.d(tag, "beforeTextChangedstart = " + start + ", after = " + after + ", count = " + count + ", --"
						+ s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {
				ULog.d(tag, "beforeTextChanged " + editText.getText().toString());
			}
		});

	}

	private ArrayList<String> InitListViewData() {
		ArrayList<String> stringList = new ArrayList<String>();
		String[] cityList = getResources().getStringArray(R.array.citylist);
		Arrays.sort(cityList, Collator.getInstance(java.util.Locale.CHINA));
		for (String s : cityList) {
			stringList.add(s);
		}
		return stringList;
	}

	/** 根据输入情况进行数据更新 */
	private ArrayList<String> getListViewDataBy(String sInput) {
		ArrayList<String> stringList = new ArrayList<String>();
		String[] cityList = getResources().getStringArray(R.array.citylist);
		Arrays.sort(cityList, Collator.getInstance(java.util.Locale.CHINA));
		for (String s : cityList) {
			if (s.contains(sInput)) {
				stringList.add(s);
			}
		}
		return stringList;
	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_setting_citylist_back:
			finish();
			break;
		}
	}

	class MyHandler extends Handler {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ComParams.HANDLER_FINISH_CITYLIST_ACTIVITY:
				finish();
				break;
			case ComParams.HANDLER_CHANGE_TITLE_CITYLIST_ACTIVITY:
				tvTitle.setText(ComParams.IPAREA);
				break;
			}
		}

	}

}
