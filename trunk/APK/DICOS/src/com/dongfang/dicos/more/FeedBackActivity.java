package com.dongfang.dicos.more;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.thread.AdviceThread;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

public class FeedBackActivity extends Activity implements OnClickListener {
	public static final String	tag	= "FeedBackActivity";

	/** 返回按钮 */
	private Button				bBack;
	private Button				bOk;
	private EditText			etFeedback;
	private ProgressBar			progressBar;

	private MyHandler			handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_freeback);

		handler = new MyHandler();

		bBack = (Button) findViewById(R.id.button_setting_freeback_back);
		bBack.setOnClickListener(this);
		bOk = (Button) findViewById(R.id.button_setting_freeback_ok);
		bOk.setOnClickListener(this);

		progressBar = (ProgressBar) findViewById(R.id.progressbar_setting_freeback_back);
		etFeedback = (EditText) findViewById(R.id.edittext_setting_freeback);
		 findViewById(R.id.scrollview_feedbackactivity).setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Util.hideInput(v);
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_setting_freeback_ok:
			String msg = etFeedback.getText().toString().trim();
			etFeedback.setText(msg);
			if (msg.length() > 0) {
				progressBar.setVisibility(View.VISIBLE);
				new AdviceThread(FeedBackActivity.this, handler, msg, Util.getPhoneNumber(FeedBackActivity.this))
						.start();
			}
			else {
				Toast.makeText(FeedBackActivity.this, "请输入您的意见", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_setting_freeback_back:
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
			Bundle data;
			switch (msg.what) {
			case ComParams.HANDLER_RESULT_ADVICE:
				data = msg.getData();
				if (Actions.ACTIONS_TYPE_ADVICE.equalsIgnoreCase(data.getString(Actions.ACTIONS_KEY_ACT))
						&& "1".equals(data.get(Actions.ACTIONS_KEY_RESULT))) {
					Toast.makeText(FeedBackActivity.this, "意见递交成功", Toast.LENGTH_LONG).show();

					etFeedback.setText("");

				}
				else {
					Toast.makeText(FeedBackActivity.this, "意见递交失败", Toast.LENGTH_LONG).show();
				}
				progressBar.setVisibility(View.GONE);
				break;
			}
		}
	}

}
