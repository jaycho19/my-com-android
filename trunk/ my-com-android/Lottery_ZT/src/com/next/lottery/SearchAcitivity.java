package com.next.lottery;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.fragment.SearchHistoryFragment;
import com.next.lottery.fragment.SearchHotWordFragment;

public class SearchAcitivity extends BaseActivity {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHost fgtHost;
	@ViewInject(R.id.activity_search_tv_cancelsearch)
	private TextView tvCancelsearch;

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
		tab1.setText("最近搜索");
		tab2.setText("热门搜索");

		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), SearchHistoryFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), SearchHotWordFragment.class, null);
		
		fgtHost.getTabWidget().setDividerDrawable(new ColorDrawable(0xDFDFDF));
	}

	@OnClick(R.id.activity_search_tv_cancelsearch)
	public void onClick(View v) {
		ULog.d("id = " + v.getId());
		switch (v.getId()) {
		case R.id.activity_search_tv_cancelsearch:
			finish();
			break;
		default:
			break;
		}
	}
}
