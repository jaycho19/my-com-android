package com.dongfang.yzsj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.yzsj.bean.HomeBean;
import com.dongfang.yzsj.fragment.HomeFragment;
import com.dongfang.yzsj.fragment.LiveFragment;
import com.dongfang.yzsj.fragment.LoginFragment;
import com.dongfang.yzsj.fragment.SearchFragment;
import com.dongfang.yzsj.fragment.UserFragment;
import com.dongfang.yzsj.fragment.VODFragment;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;

public class MainActivity extends BaseActivity implements View.OnClickListener {

	@Override
	protected void setBaseValues() {
		this.TAG = MainActivity.class.getSimpleName();
	}

	private FragmentTabHost fgtHost;
	private FragmentManager mFragmentManager;
	private HomeBean homeBean;

	private LoginFragment loginFragment;// 登陆Fragment
	private FrameLayout frameLayout;// tab选择之后内容显示区域

	private TextView tvLogin;// 登陆
	private View tvhistory;// 播放信息
	private TextView tvLoginDesc;// 登陆信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData(getIntent());

		setContentView(R.layout.activity_main);
		mFragmentManager = getSupportFragmentManager();
		initTabhostItems();
		initView();
	}

	/** 获取首页数据 */
	private void initData(Intent intent) {
		ULog.d(TAG, "initData");
		if (null == intent || null == intent.getParcelableExtra(ComParams.INTENT_HOMEBEAN)) {
			ULog.d(TAG, " ---  -- --- ---- -- null");
			return;
		}

		homeBean = (HomeBean) intent.getParcelableExtra(ComParams.INTENT_HOMEBEAN);
	}

	/** 初始化底部菜单 */
	@SuppressLint("NewApi")
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

		// Bundle data = new Bundle();
		// data.putParcelable("homebean", homeBean);

		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), HomeFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), LiveFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab3), VODFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator(tab4), SearchFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator(tab5), UserFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("6").setIndicator("66"), TypeFragment.class, null);

		// fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator("1"), HomeFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator("2"), LiveFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator("3"), VODFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator("4"), SearchFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator("5"), UserFragment.class, null);

		// 在fragment代用之前就代用该listener
		fgtHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				ULog.d(TAG, "OnTabChangeListener = " + tabId);
				if ("5".equals(tabId) && !User.isLogined(MainActivity.this)) {
					frameLayout.setVisibility(View.GONE);
					ULog.d(TAG, "frameLayout.setVisibility(View.GONE)");
				}
				else {
					frameLayout.setVisibility(0);
				}
			}
		});

		// for (Fragment fg : mFragmentManager.getFragments()) {
		// ULog.w(TAG, "tag = " + fg.getTag() + ", id = " + fg.getId());
		// ULog.w(TAG, fg.toString());
		// }

		// ULog.w(TAG, fgtHost.getChildCount() + "");
		// for(int i = 0 ; i < fgtHost.getChildCount();i++){
		// ULog.w(TAG, fgtHost.getChildAt(i).getClass().getName());
		// }

		// if (null != getFragmentManager()..getFragment(null, "1")) {
		// ULog.w(TAG, getFragmentManager().getFragment(null, "1").getClass().getName());
		// }
		// else {
		// ULog.w(TAG, "null");
		//
		// }

	}

	/** 初始化view */
	private void initView() {
		tvLogin = (TextView) findViewById(R.id.tv_topbar_login);
		tvLogin.setText(User.isLogined(this) ? "注销" : "登陆");
		tvLogin.setOnClickListener(this);

		tvhistory = findViewById(R.id.tv_topbar_playhistory);
		tvhistory.setOnClickListener(this);
		tvLoginDesc = (TextView) findViewById(R.id.tv_topbar_login_decs);
		frameLayout = (FrameLayout) findViewById(R.id.realtabcontent);
		loginFragment = (LoginFragment) mFragmentManager.findFragmentById(R.id.fragment_login);
		loginFragment.setOnLoginAndVerify(new LoginFragment.OnLoginAndVerify() {

			@Override
			public void verify(boolean verify, String phoneNumber) {
			}

			@Override
			public void login(boolean login, String phoneNumber) {
				if (login) {
					tvLogin.setText("注销");
					frameLayout.setVisibility(0);
					fgtHost.refreshDrawableState();
				}
				else {
					tvLogin.setText("登陆");
				}
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		// frameLayout.requestFocus();
		// ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
		// fgtHost.getApplicationWindowToken(), 2); // (WidgetSearchActivity是当前的Activity)

		// ULog.w(TAG, frameLayout.getChildCount() + "");
		// for(int i = 0 ; i < frameLayout.getChildCount();i++){
		// ULog.w(TAG, frameLayout.getChildAt(i).getClass().getName());
		// }

		// ULog.w(TAG, fgtHost.getCurrentTabTag());
		// ULog.w(TAG, fgtHost.getCurrentTab() + "");
		// ULog.w(TAG, mFragmentManager.findFragmentById(fgtHost.getCurrentTab()) == null ? "null" : "!null");
		//
		// mFragmentManager.getFragments()
		//
		// ULog.w(TAG, mFragmentManager.findFragmentById(fgtHost.getCurrentTab()).getClass().getName());

		// 用于调试方便
		// fgtHost.setCurrentTabByTag("4");

	}

	@Override
	protected void onResume() {
		super.onResume();
		ULog.d(TAG, "onResume");
		
		if (User.isLogined(this)) {
			tvLogin.setText("注销");
			frameLayout.setVisibility(0);
		}
		else {
			tvLogin.setText("登陆");
		}
	}

	public HomeBean getHomeBean() {
		return homeBean;
	}

	/** 显示登陆界面 */
	public void showLoginFragment() {
		frameLayout.setVisibility(View.GONE);
	}

	/** 隐藏登陆界面 */
	public void hideLoginFragment() {
		frameLayout.setVisibility(View.GONE);
	}

	public FragmentTabHost getFgtHost() {
		return fgtHost;
	}

	public void changeTab(int i) {
		fgtHost.bringChildToFront(fgtHost.getChildAt(i));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		fgtHost = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && !frameLayout.isShown()) {
			frameLayout.setVisibility(0);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		ULog.d(TAG, "onClick = " + v.getId());
		switch (v.getId()) {
		case R.id.tv_topbar_login:
			if (User.isLogined(this)) {
				// 注销
				// User.saveUserLoginStatu(this, false);
				User.saveToken(this, "");

				tvLogin.setText("登陆");
				Toast.makeText(this, "注销成功", Toast.LENGTH_LONG).show();
			}
			else {
				frameLayout.setVisibility(View.GONE);
			}
			break;
		case R.id.tv_topbar_playhistory:
			fgtHost.setCurrentTabByTag("5");
			break;

		default:
			break;
		}

	}

}
