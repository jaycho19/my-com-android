package com.dongfang.daohang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dongfang.daohang.fragment.DetailFragment;
import com.dongfang.daohang.fragment.ActivityFragment;
import com.dongfang.daohang.fragment.FloorFragment;
import com.dongfang.daohang.fragment.ShopsFragment;
import com.dongfang.daohang.fragment.UserFragment;
import com.dongfang.utils.ULog;
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
		// fgtHost.getTabWidget().setRightStripDrawable(getResources().getColor(R.color.black));

		// TextView tab1 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		// TextView tab2 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		// TextView tab3 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		// TextView tab4 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		// TextView tab5 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);

		// tab2.setText("分类");
		// tab3.setText("品牌");
		// tab4.setText("购物车");
		// tab5.setText("个人");
		//
		// tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_fenlei_bg, 0, 0);
		// tab3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_pinpai_bg, 0, 0);
		// tab4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_gouwuche_bg, 0, 0);
		// tab5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_usercenter_bg, 0, 0);

		// tab2.setBackgroundResource(R.drawable.mian_activity_tab_live_bg);
		// tab3.setBackgroundResource(R.drawable.mian_activity_tab_vod_bg);
		// tab4.setBackgroundResource(R.drawable.mian_activity_tab_search_bg);
		// tab5.setBackgroundResource(R.drawable.mian_activity_tab_user_bg);

		// Bundle data = new Bundle();
		// data.putParcelable("homebean", homeBean);

		// fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), PlaceholderFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), PlaceholderFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab3), PlaceholderFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator(tab4), PlaceholderFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator(tab5), PlaceholderFragment.class, null);

		fgtHost.getTabWidget().setDividerDrawable(null);

		// fgtHost.addTab(fgtHost.newTabSpec("6").setIndicator("66"), TypeFragment.class, null);

		fgtHost.addTab(fgtHost.newTabSpec("0").setIndicator("楼层"), FloorFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator("商户"), ShopsFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator("活动"), ActivityFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator("我的"), UserFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator("详情"), DetailFragment.class, null);

		// fgtHost.setOnBeforeChangeTab(new FragmentTabHostDF.OnBeforeChangeTab() {
		// @Override
		// public int onBeforeChangeTab(int index) {
		// if (3 == index && !User.isLogined(context)) {
		// context.startActivity(new Intent(context, UserLRLoginActivity.class));
		// return index;
		// }
		// return -1;
		// }
		// });
		// fgtHost.setCurrentTab(0);
	}

	@Override
	public void onClick(View v) {}

}
