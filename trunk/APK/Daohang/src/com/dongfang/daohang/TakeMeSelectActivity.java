package com.dongfang.daohang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dongfang.daohang.net.HttpActions;
import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseActivity;
import com.dongfang.views.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class TakeMeSelectActivity extends BaseActivity {

	@ViewInject(R.id.top_bar_search_iv_cancel)
	private ImageView cancel;
	@ViewInject(R.id.top_bar_search_et_input)
	private EditText inputEditText;

	@ViewInject(R.id.activity_takeme_select_pulltorefreshview)
	private PullToRefreshView pullToRefreshView;
	@ViewInject(R.id.activity_takeme_select_listview)
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takeme_select);
		pullToRefreshView.setVisibility(View.GONE);
		inputEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				handler.removeMessages(100);
				if (TextUtils.isEmpty(s)) {
					pullToRefreshView.setVisibility(View.GONE);
				}
				else {
					handler.sendEmptyMessageDelayed(100, 300);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void afterTextChanged(Editable s) {}
		});

	}

	@OnClick({ R.id.top_bar_search_iv_cancel })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_bar_search_iv_cancel:
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("result", "0987654321");
			resultIntent.putExtras(bundle);
			setResult(RESULT_OK, resultIntent);
			
			finish();
			break;

		default:
			break;
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100: {
				new HttpUtils().send(HttpMethod.GET, HttpActions.getShop(TakeMeSelectActivity.this, 10, 1),
						new RequestCallBack<String>() {

							@Override
							public void onSuccess(ResponseInfo<String> responseInfo) {
								ULog.d(responseInfo.result);
								ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
								Map<String, String> map = new HashMap<String, String>();
								map.put("t", responseInfo.result);
								data.add(map);

								SimpleAdapter adapter = new SimpleAdapter(TakeMeSelectActivity.this, data,
										android.R.layout.simple_list_item_1, new String[] { "t" },
										new int[] { android.R.id.text1 });

								listView.setAdapter(adapter);

								pullToRefreshView.setVisibility(View.VISIBLE);
							}

							@Override
							public void onFailure(HttpException error, String msg) {}

						});
			}

				break;

			default:
				break;
			}

		}

	};

}
