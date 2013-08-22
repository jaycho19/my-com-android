package com.dongfang.yzsj;

import com.dongfang.yzsj.params.ComParams;

import android.os.Bundle;

public class MovieListActivity extends BaseActivity {

	@Override
	protected void setBaseValues() {
		TAG = "MovieListActivity";
	}

	public String channel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_list);
		channel = getIntent().getExtras().getString(ComParams.INTENT_MOVIELIST_CHANNLID, "");
	}

}
