package com.dongfang.dicos.lottery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ULog;

public class PrizeInfoTempActivity extends Activity implements OnClickListener {

	public static final String	tag	= "PrizeInfoActivity";

	/** 返回按钮 */
	private Button				bBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lottery_prizeinfo_temp);

		bBack = (Button) findViewById(R.id.button_lottery_prizeinfo_temp_back);
		bBack.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_lottery_prizeinfo_temp_back:
			finish();
			break;
		}
	}

}
