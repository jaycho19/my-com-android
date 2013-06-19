package com.dongfang.apad;

import android.os.Bundle;
import android.view.View;

public class EndActivity extends BaseActivity implements android.view.View.OnClickListener {
	@Override
	protected void setBaseValues() {
		TAG = EndActivity.class.getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_activity);
		findViewById(R.id.tv_back).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}

	}

}
