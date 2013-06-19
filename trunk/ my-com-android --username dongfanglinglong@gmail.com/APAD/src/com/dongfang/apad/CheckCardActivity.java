package com.dongfang.apad;

import android.os.Bundle;

public class CheckCardActivity extends BaseActivity {
	@Override
	protected void setBaseValues() {
		TAG = CheckCardActivity.class.getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkcard_activity);
	}

}
