package com.dongfang.yzsj;

import android.os.Bundle;

public class MovieListActivity extends BaseActivity {

	@Override
	protected void setBaseValues() {

		TAG = "MovieListActivity";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_list);
	}

}
