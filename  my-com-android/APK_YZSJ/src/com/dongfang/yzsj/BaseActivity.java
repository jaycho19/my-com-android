package com.dongfang.yzsj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.df.util.ULog;
import com.dongfang.yzsj.broadcast.CloseAppReceiver;

/**
 * 1. 所有该项目activity都需要继承该类；<br>
 * 
 * @author dongfang
 * 
 */
public abstract class BaseActivity extends FragmentActivity {
	protected String			TAG	= BaseActivity.class.getSimpleName();

	private CloseAppReceiver	closeApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		closeApp = new CloseAppReceiver(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(getPackageName().toString() + "." + CloseAppReceiver.TAG);
		registerReceiver(closeApp, filter);
		// 系统崩溃日志,崩溃重启
		// MyUnCaughtExceptionHandler.getInstance(this);
		setBaseValues();
	}

	protected abstract void setBaseValues();

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter connectFilter = new IntentFilter();
		connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(connectReciver, connectFilter);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			unregisterReceiver(connectReciver);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(closeApp);
		connectReciver = null;
		closeApp = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		ULog.d(TAG, "onCreateOptionsMenu");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * 原先五个Activity均有复写该退出口<br>
	 * 现改为只有推荐页面有，其余页面跳转至该页面
	 * 
	 * @author wwei
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {}
		return super.onKeyDown(keyCode, event);
	}

	protected void showAppExitDialog() {}

	protected void showOfflineViewExitDialog() {}

	protected void appExit() {}

	private BroadcastReceiver	connectReciver	= new BroadcastReceiver() {

													@Override
													public void onReceive(Context context, Intent intent) {}
												};

}