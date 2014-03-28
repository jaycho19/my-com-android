package com.next.lottery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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
import com.next.lottery.params.ComParams;
import com.next.lottery.utils.Keys;

/**
 * 主ACTIVITY
 * 
 * @author dongfang
 * */
public class MainActivity extends BaseActivity {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHostDF fgtHost;
	
	public static int changeTab = 0;

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
		registerBoradcastReceiver();
	}

	/** 获取首页数据 */
	private void initData(Intent intent) {
		ULog.d("initData");
	}
	
	public void registerBoradcastReceiver() {// 注册广播
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ComParams.ACTION_UPDATE_MAINACTIVITY);
		registerReceiver(receiver, myIntentFilter);
	}

	/** 初始化底部菜单 */
	private void initTabhostItems() {
		ULog.d("initTabhostItems");
		fgtHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// fgtHost.getTabWidget().setRightStripDrawable(getResources().getColor(R.color.black));

		TextView tab1 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		TextView tab2 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		TextView tab3 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		TextView tab4 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		TextView tab5 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);

		tab2.setText("分类");
		tab3.setText("品牌");
		tab4.setText("购物车");
		tab5.setText("个人");

		tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_fenlei_bg, 0, 0);
		tab3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_pinpai_bg, 0, 0);
		tab4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_gouwuche_bg, 0, 0);
		tab5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_usercenter_bg, 0, 0);

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

		fgtHost.getTabWidget().setDividerDrawable(null);


		// fgtHost.addTab(fgtHost.newTabSpec("6").setIndicator("66"), TypeFragment.class, null);

		// fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator("1"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator("2"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator("3"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator("4"), AboutActivity.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator("5"), AboutActivity.class, null);

		fgtHost.setCurrentTab(0);

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
		ULog.i("onResume");
		if (changeTab!=0) {
			fgtHost.setCurrentTab(changeTab);
			changeTab = 0;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
	private BroadcastReceiver	receiver	= new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ComParams.ACTION_UPDATE_MAINACTIVITY)&&intent.hasExtra(Keys.KEY_MAIN_ITEM)) {
				int targetTab = intent.getIntExtra(Keys.KEY_MAIN_ITEM, fgtHost.getCurrentTab());
				ULog.i("getCurrentTabTag-->"+fgtHost.getCurrentTabTag());
				if (fgtHost.getCurrentTab() !=targetTab) {
					ULog.i("targetTab-->"+targetTab);
					changeTab = targetTab;
				}
				
			}
		}
	};
}
