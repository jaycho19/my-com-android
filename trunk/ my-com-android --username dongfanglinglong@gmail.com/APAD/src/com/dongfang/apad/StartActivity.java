package com.dongfang.apad;

import android.os.Bundle;

public class StartActivity extends BaseActivity {
	@Override
	protected void setBaseValues() {
		TAG = StartActivity.class.getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
	}

}
