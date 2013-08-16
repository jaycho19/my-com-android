package com.dongfang.yzsj;

import android.os.Bundle;

public class DetailsActiivity extends BaseActivity {

	@Override
	protected void setBaseValues() {
		TAG = "DetailsActiivity";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
	}

}
