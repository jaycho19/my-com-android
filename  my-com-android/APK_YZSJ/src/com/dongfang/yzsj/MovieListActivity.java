package com.dongfang.yzsj;

import android.os.Bundle;

import com.dongfang.yzsj.bean.Channel;
import com.dongfang.yzsj.params.ComParams;

public class MovieListActivity extends BaseActivity {

	@Override
	protected void setBaseValues() {
		TAG = "MovieListActivity";
	}

	public Channel channel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_list);
		channel = getIntent().getExtras().getParcelable(ComParams.INTENT_MOVIELIST_CHANNEL);
	}

}
