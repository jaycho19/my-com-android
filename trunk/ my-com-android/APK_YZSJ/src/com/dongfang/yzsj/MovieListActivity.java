package com.dongfang.yzsj;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dongfang.yzsj.bean.Channel;
import com.dongfang.yzsj.params.ComParams;

public class MovieListActivity extends BaseActivity implements android.view.View.OnClickListener {

	private ImageView tab1, tab2, tab3, tab4, tab5;
	public Channel channel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_list);
		tab1 = (ImageView) findViewById(R.id.iv_movie_list_tabs_1);
		tab2 = (ImageView) findViewById(R.id.iv_movie_list_tabs_2);
		tab3 = (ImageView) findViewById(R.id.iv_movie_list_tabs_3);
		tab4 = (ImageView) findViewById(R.id.iv_movie_list_tabs_4);
		tab5 = (ImageView) findViewById(R.id.iv_movie_list_tabs_5);

		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
		tab4.setOnClickListener(this);
		tab5.setOnClickListener(this);

		channel = getIntent().getExtras().getParcelable(ComParams.INTENT_MOVIELIST_CHANNEL);

	}

	@Override
	public void onClick(View v) {
		/**修改返回到MainActivity时的跳转*/
		switch (v.getId()) {
		case R.id.iv_movie_list_tabs_1:
			MainActivity.changeTab = 1;
			break;
		case R.id.iv_movie_list_tabs_2:
			MainActivity.changeTab = 2;
			break;
		case R.id.iv_movie_list_tabs_3:
			MainActivity.changeTab = 3;
			break;
		case R.id.iv_movie_list_tabs_4:
			MainActivity.changeTab = 4;
			break;
		case R.id.iv_movie_list_tabs_5:
			MainActivity.changeTab = 5;
			break;
		}
		finish();
	}

}
