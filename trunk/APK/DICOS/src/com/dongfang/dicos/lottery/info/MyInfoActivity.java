package com.dongfang.dicos.lottery.info;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.thread.LotteryHistoryThread;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

public class MyInfoActivity extends Activity implements OnClickListener {

	public static final String	tag	= "MyInfoActivity";

	/** 返回按钮 */
	private Button				bShowMore;

	/** 当前抽奖机会总数 */
	private TextView			tvOpportunityNumber;

	private ListView			lvInfo;

	private MyInfoAdapter		adapter;
	private MyHandler			handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lottery_info_myinfo);

		handler = new MyHandler();

		bShowMore = (Button) findViewById(R.id.button_lottery_info_myinfo_showmore);
		bShowMore.setOnClickListener(this);

		tvOpportunityNumber = (TextView) findViewById(R.id.textView_lottery_info_myinfo_opportunitynumber);
		lvInfo = (ListView) findViewById(R.id.listViewlottery_info_myinfo_info);
		adapter = new MyInfoAdapter(this);
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

		new LotteryHistoryThread(this, handler, Util.getPhoneNumber(this)).start();
	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_lottery_info_myinfo_showmore:
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
			case ComParams.HANDLER_RESULT_LOTTERYHISTORY:
				data = msg.getData();
				if (!TextUtils.isEmpty(data.getString(Actions.ACTIONS_KEY_ACT))
						&& Actions.ACTIONS_TYPE_LOTTERYHISTORY.equalsIgnoreCase(data.getString(Actions.ACTIONS_KEY_ACT))) {
					String[] as = data.getStringArray(Actions.ACTIONS_KEY_DATA);
					adapter.setArray(as);
					adapter.notifyDataSetChanged();

					tvOpportunityNumber.setText(String.format("我的抽奖机会总数：%1$d 次", as.length));
				}

				break;

			}

		}
	}
}
