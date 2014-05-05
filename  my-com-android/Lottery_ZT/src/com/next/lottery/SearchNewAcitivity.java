package com.next.lottery;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.fragment.SearchHistoryFragment;
import com.next.lottery.fragment.SearchHotWordFragment;
import com.next.lottery.fragment.SearchNewHistoryFragment;
import com.next.lottery.params.ComParams;

public class SearchNewAcitivity extends BaseActivity {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHost fgtHost;
	@ViewInject(R.id.activity_search_tv_cancelsearch)
	private ImageView imgChangeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		ViewUtils.inject(this);
		initTabhostItems();
	}

	/** 初始化底部菜单 */
	private void initTabhostItems() {
		fgtHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		TextView tab1 = (TextView) getLayoutInflater().inflate(R.layout.activity_search_tab, null);
		TextView tab2 = (TextView) getLayoutInflater().inflate(R.layout.activity_search_tab, null);
		TextView tab3 = (TextView) getLayoutInflater().inflate(R.layout.activity_search_tab, null);
		TextView tab4 = (TextView) getLayoutInflater().inflate(R.layout.activity_search_tab, null);
		tab1.setText("最新");
		tab2.setText("价格");
		tab3.setText("销量");
		tab4.setText("评论");

		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), SearchNewHistoryFragment.class, this.getIntent().getExtras());
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), SearchNewHistoryFragment.class, this.getIntent().getExtras());
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab3), SearchNewHistoryFragment.class, this.getIntent().getExtras());
		fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator(tab4), SearchNewHistoryFragment.class, this.getIntent().getExtras());
		fgtHost.getTabWidget().setDividerDrawable(new ColorDrawable(0xF4F4F4));
	}

	@OnClick(R.id.activity_search_tv_cancelsearch)
	public void onClick(View v) {
		ULog.d("id = " + v.getId());
		switch (v.getId()) {
		case R.id.activity_search_tv_cancelsearch:
			Intent intent = new Intent(ComParams.ACTION_CHANGE_SEARCH_FRAGMENT_ADAPTER);
			this.sendBroadcast(intent);
			
//			fgtHost.get
//			finish();
			break;
		default:
			break;
		}
	}
}
