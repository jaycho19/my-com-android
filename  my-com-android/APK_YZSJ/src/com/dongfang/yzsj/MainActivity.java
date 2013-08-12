package com.dongfang.yzsj;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.dongfang.yzsj.fragment.HomeFragment;
import com.dongfang.yzsj.fragment.LiveFragment;
import com.dongfang.yzsj.fragment.SearchFragment;
import com.dongfang.yzsj.fragment.TypeFragment;
import com.dongfang.yzsj.fragment.UserFragment;
import com.dongfang.yzsj.fragment.VODFragment;

public class MainActivity extends BaseActivity {

	@Override
	protected void setBaseValues() {
		this.TAG = MainActivity.class.getSimpleName();
	}

	private FragmentTabHost	fgtHost;
	private FragmentManager	mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFragmentManager = getSupportFragmentManager();
		initTabhostItems();
	}

	/** 初始化底部菜单 */
	private void initTabhostItems() {
		fgtHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		fgtHost.setup(this, mFragmentManager, R.id.realtabcontent);

		View tab1 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab2 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab3 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab4 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab5 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		tab2.setBackgroundResource(R.drawable.mian_activity_tab_live_bg);
		tab3.setBackgroundResource(R.drawable.mian_activity_tab_vod_bg);
		tab4.setBackgroundResource(R.drawable.mian_activity_tab_search_bg);
		tab5.setBackgroundResource(R.drawable.mian_activity_tab_user_bg);

		fgtHost.addTab(fgtHost.newTabSpec("6").setIndicator("66"), TypeFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), HomeFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), LiveFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab3), VODFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator(tab4), SearchFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator(tab5), UserFragment.class, null);

		// fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator("1"), HomeFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator("2"), LiveFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator("3"), VODFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator("4"), SearchFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator("5"), UserFragment.class, null);
	}

}
