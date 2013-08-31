package com.dongfang.yzsj;

import android.content.Intent;
import android.os.Bundle;

import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 可以根据多个请求代码来作相应的操作
		if (10 == resultCode && null != data) {
			if (ToDetailAsyncTask.TAG.equals(data.getStringExtra(ComParams.INTENT_TODO))) {
				new ToDetailAsyncTask(this, data.getStringExtra(ComParams.INTENT_MOVIEDETAIL_CHANNELID),
						data.getStringExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID)).execute();
			}
		}
	}
}
