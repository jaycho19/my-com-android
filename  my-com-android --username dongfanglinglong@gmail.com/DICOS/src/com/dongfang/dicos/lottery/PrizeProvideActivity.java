package com.dongfang.dicos.lottery;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class PrizeProvideActivity extends Activity implements OnClickListener {

	public static final String	tag	= "PrizeInfoActivity";

	/** 返回按钮 */
	private Button				bBack;
	/** 返回按钮 */
	private Button				bShowMore;

	private ListView			lvInfo;

	private PrizeProvideAdapter	adapter;
	private MyHandler			handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lottery_prizeprovide);
		handler = new MyHandler();

		bBack = (Button) findViewById(R.id.button_lottery_prizeprovide_back);
		bBack.setOnClickListener(this);

		bShowMore = (Button) findViewById(R.id.button_lottery_prizeprovide_showmore);
		bShowMore.setOnClickListener(this);

		lvInfo = (ListView) findViewById(R.id.listview_lottery_prizeprovide_storeinfo);
		adapter = new PrizeProvideAdapter(this);
		lvInfo.setAdapter(adapter);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();

		// new LotteryDistributionInfoThread(this, handler,
		// Util.getPhoneNumber(this)).start();

		String[] company = getResources().getStringArray(R.array.lotteryDistributionInfo_company);
		String[] add = getResources().getStringArray(R.array.lotteryDistributionInfo_address);
		String[] tel = getResources().getStringArray(R.array.lotteryDistributionInfo_tel);

		try {
			String[] as = new String[company.length];
			for (int i = 0; i < company.length; i++) {
				JSONObject json = new JSONObject();
				json.put(Actions.ACTIONS_KEY_COMPANY, company[i]);
				json.put(Actions.ACTIONS_KEY_ADDRESS, add[i]);
				json.put(Actions.ACTIONS_KEY_TEL, tel[i]);
				as[i] = json.toString();
			}

			adapter.setArray(as);
			adapter.notifyDataSetChanged();

		} catch (Exception e) {
			ULog.d(tag, e.toString());
		}

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_lottery_prizeprovide_showmore:
			break;
		case R.id.button_lottery_prizeprovide_back:
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
			ULog.d(tag, "handleMessage msg.what = " + msg.what);
			Bundle data;
			switch (msg.what) {
			case ComParams.HANDLER_RESULT_LOTTERYDISTRIBUTIONINFO:
				data = msg.getData();
				if (!TextUtils.isEmpty(data.getString(Actions.ACTIONS_KEY_ACT))
						&& Actions.ACTIONS_TYPE_LOTTERYDISTRIBUTIONINFO.equalsIgnoreCase(data
								.getString(Actions.ACTIONS_KEY_ACT))) {
					String[] as = data.getStringArray(Actions.ACTIONS_KEY_DATA);
					adapter.setArray(as);
					adapter.notifyDataSetChanged();
				}

				break;

			}

		}
	}

}
