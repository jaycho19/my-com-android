package com.next.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseActivity;
import com.dongfang.v4.app.FragmentTabHostDF;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.ClassifyFragment;
import com.next.lottery.fragment.HomeFragment;
import com.next.lottery.fragment.ShoppingCartFragment;
import com.next.lottery.fragment.TrademarkFragment;
import com.next.lottery.fragment.UserCenterFragment;

/**
 * 主ACTIVITY
 * 
 * @author dongfang
 * */
public class MainActivity extends BaseActivity {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHostDF fgtHost;

	// @ViewInject(R.id.tv_topbar_menu)
	// private TextView tvTopBarMenu;

	// @ViewInject(R.id.ribbonMenu_mainactivity)
	// private RibbonMenuView ribbonMenu;

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
		ULog.d("initData");
	}

	/** 初始化底部菜单 */
	private void initTabhostItems() {
		ULog.d("initTabhostItems");
		fgtHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// fgtHost.getTabWidget().setRightStripDrawable(getResources().getColor(R.color.black));

		View tab1 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab2 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab3 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab4 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		View tab5 = getLayoutInflater().inflate(R.layout.activity_main_tab, null);

		((TextView) tab2.findViewById(R.id.activity_main_tab_tv)).setText("分类");
		((TextView) tab3.findViewById(R.id.activity_main_tab_tv)).setText("品牌");
		((TextView) tab4.findViewById(R.id.activity_main_tab_tv)).setText("购物车");
		((TextView) tab5.findViewById(R.id.activity_main_tab_tv)).setText("个人");

		((ImageView) tab2.findViewById(R.id.activity_main_tab_iv))
				.setImageResource(R.drawable.mian_activity_tab_fenlei_bg);
		((ImageView) tab3.findViewById(R.id.activity_main_tab_iv))
				.setImageResource(R.drawable.mian_activity_tab_pinpai_bg);
		((ImageView) tab4.findViewById(R.id.activity_main_tab_iv))
				.setImageResource(R.drawable.mian_activity_tab_gouwuche_bg);
		((ImageView) tab5.findViewById(R.id.activity_main_tab_iv))
				.setImageResource(R.drawable.mian_activity_tab_usercenter_bg);

		// tab2.setBackgroundResource(R.drawable.mian_activity_tab_live_bg);
		// tab3.setBackgroundResource(R.drawable.mian_activity_tab_vod_bg);
		// tab4.setBackgroundResource(R.drawable.mian_activity_tab_search_bg);
		// tab5.setBackgroundResource(R.drawable.mian_activity_tab_user_bg);

		// Bundle data = new Bundle();
		// data.putParcelable("homebean", homeBean);

		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), HomeFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), ClassifyFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab3), TrademarkFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator(tab4), ShoppingCartFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator(tab5), UserCenterFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("6").setIndicator("66"), TypeFragment.class, null);

		// fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator("1"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator("2"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator("3"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator("4"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator("5"), AboutActivity.class, null);

		fgtHost.getTabWidget().setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				ULog.d("tag  = " + v.getTag().getClass().getName());
			}
		});

		fgtHost.getTabWidget().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ULog.d("tag  = " + v.getTag().getClass().getName());

			}
		});

		// 在fragment代用之前就代用该listener
		// fgtHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
		// @Override
		// public void onTabChanged(String tabId) {
		// ULog.d("OnTabChangeListener = " + tabId);
		// if ("5".equals(tabId) && !User.isLogined(MainActivity.this)) {
		// startActivity(new Intent(getApplicationContext(), LRLoginActivity.class));
		// }
		// }
		// });
		// tvTopBarMenu.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// startActivity(new Intent(getApplicationContext(), LRLoginActivity.class));
		// }
		// });

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
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if (KeyEvent.KEYCODE_BACK == keyCode && ribbonMenu.isShown()) {
		// ribbonMenu.toggleMenu();
		// return true;
		// }

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
