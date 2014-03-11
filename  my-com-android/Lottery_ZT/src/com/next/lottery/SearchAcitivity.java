package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.ClassifyFragment;

public class SearchAcitivity extends BaseActivity {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHost fgtHost;

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

		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), ClassifyFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), ClassifyFragment.class, null);
	}

}
