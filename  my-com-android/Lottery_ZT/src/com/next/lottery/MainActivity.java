package com.next.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.UserCenterFragment;
import com.next.lottery.menu.RibbonMenuView;

/**
 * 主ACTIVITY
 * 
 * @author dongfang
 * */
public class MainActivity extends BaseActivity {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHost fgtHost;
	@ViewInject(R.id.tv_topbar_menu)
	private TextView tvTopBarMenu;

	@ViewInject(R.id.ribbonMenu_mainactivity)
	private RibbonMenuView ribbonMenu;

	// @ViewInject(R.id.imageview_show_menu)
	// private ImageView showMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData(getIntent());
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);

		initTabhostItems();
	}

	/** 获取首页数据 */
	private void initData(Intent intent) {
		LogUtils.d("initData");
	}

	/** 初始化底部菜单 */
	private void initTabhostItems() {
		LogUtils.d("initTabhostItems");
		fgtHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		View tab1 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab2 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab3 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab4 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab5 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		// tab2.setBackgroundResource(R.drawable.mian_activity_tab_live_bg);
		// tab3.setBackgroundResource(R.drawable.mian_activity_tab_vod_bg);
		// tab4.setBackgroundResource(R.drawable.mian_activity_tab_search_bg);
		// tab5.setBackgroundResource(R.drawable.mian_activity_tab_user_bg);

		// Bundle data = new Bundle();
		// data.putParcelable("homebean", homeBean);

		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), AboutActivity.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), AboutActivity.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab3), AboutActivity.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator(tab4), AboutActivity.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator(tab5), UserCenterFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("6").setIndicator("66"), TypeFragment.class, null);

		// fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator("1"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator("2"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator("3"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator("4"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator("5"), AboutActivity.class, null);

		// 在fragment代用之前就代用该listener
		// fgtHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
		// @Override
		// public void onTabChanged(String tabId) {
		// ULog.d("OnTabChangeListener = " + tabId);
		// if ("5".equals(tabId) && !User.isLogined(MainActivity.this)) {
		// frameLayout.setVisibility(View.GONE);
		// ULog.d("frameLayout.setVisibility(View.GONE)");
		// }
		// else {
		// frameLayout.setVisibility(0);
		// }
		// }
		// });
		tvTopBarMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), LRLoginActivity.class));
			}
		});

		// showMenu.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// if (!ribbonMenu.isShown())
		// showMenu.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rbm_in_from_left));
		// else
		// showMenu.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rbm_out_to_left));
		//
		// ribbonMenu.toggleMenu();
		// }
		// });

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode && ribbonMenu.isShown()) {
			ribbonMenu.toggleMenu();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
