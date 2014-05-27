package com.dongfang.daohang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dongfang.daohang.fragment.ActivityFragment;
import com.dongfang.daohang.fragment.DetailFragment;
import com.dongfang.daohang.fragment.FloorFragment;
import com.dongfang.daohang.fragment.ShopsFragment;
import com.dongfang.daohang.fragment.UserFragment;
import com.dongfang.utils.ULog;
import com.dongfang.utils.User;
import com.dongfang.v4.app.BaseActivity;
import com.dongfang.v4.app.FragmentTabHostDF;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * @author dongfang
 *
 */
public class MainDaohangActivity extends BaseActivity {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHostDF fgtHost;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_daohang);
		context = this;
		initData(getIntent());
		// ViewUtils.inject(this);
		initTabhostItems();
	}

	/** 获取首页数据 */
	private void initData(Intent intent) {
		ULog.d("initData");
	}

	/** 初始化底部菜单 */
	@SuppressLint("NewApi")
	private void initTabhostItems() {
		ULog.d("initTabhostItems");
		fgtHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		TextView tab1 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		TextView tab2 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		TextView tab3 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		TextView tab4 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		TextView tab5 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab_2, null);

		tab1.setText("楼层");
		tab2.setText("商户");
		tab3.setText("活动");
		tab4.setText("我的");
		tab5.setText("详情");
		fgtHost.getTabWidget().setDividerDrawable(null);

		fgtHost.addTab(fgtHost.newTabSpec("0").setIndicator(tab1), FloorFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab2), ShopsFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab3), ActivityFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab4), UserFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator(tab5), DetailFragment.class, null);

		fgtHost.setOnBeforeChangeTab(new FragmentTabHostDF.OnBeforeChangeTab() {
			@Override
			public int onBeforeChangeTab(int index) {
				if (3 == index && !User.isLogined(context)) {
					context.startActivity(new Intent(context, UserLRLoginActivity.class));
					return index;
				}
				return -1;
			}
		});
		// fgtHost.setCurrentTab(0);
	}

	@Override
	public void onClick(View v) {}

}
