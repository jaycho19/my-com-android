package com.dongfang.dicos.lottery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ULog;

public class DetailActivity extends Activity implements OnClickListener {
	public static final String	tag	= "DetailActivity";

	/** 返回按钮 */
	private Button				bBack;
	/** 确认按钮 */
	private Button				bOK;

	/** 抽奖详情 */
	// private TextView tvDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lottery_detail);

		bBack = (Button) findViewById(R.id.button_lottery_detail_back);
		bBack.setOnClickListener(this);

		bOK = (Button) findViewById(R.id.button_lottery_detail_ok);
		bOK.setOnClickListener(this);

		// tvDetail = (TextView)
		// findViewById(R.id.textView_lottery_detail_detail);

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_lottery_detail_ok:
			Intent intent = new Intent(this, JoinInActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.button_lottery_detail_back:
			finish();
			break;
		}
	}

}
