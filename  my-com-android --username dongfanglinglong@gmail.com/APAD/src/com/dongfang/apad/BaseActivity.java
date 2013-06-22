package com.dongfang.apad;

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

import com.dongfang.apad.util.ULog;
import com.telecom.video.broadcast.CloseAppReceiver;

/**
 * 1. 所有该项目activity都需要继承该类；<br>
 * 2. 该类集成了QAS功能
 * 
 * @author dongfang
 * 
 */
public abstract class BaseActivity extends FragmentActivity {
	protected String			TAG	= BaseActivity.class.getSimpleName();

	private CloseAppReceiver	closeApp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		closeApp = new CloseAppReceiver(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(getPackageName().toString() + "." + CloseAppReceiver.TAG);
		registerReceiver(closeApp, filter);
		setBaseValues();
	}

	protected abstract void setBaseValues();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter connectFilter = new IntentFilter();
		connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(connectReciver, connectFilter);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onWindowFocusChanged(boolean)
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(connectReciver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(closeApp);
		connectReciver = null;
		closeApp = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#oonCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		ULog.d(TAG, "onCreateOptionsMenu");
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
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
		return super.onKeyDown(keyCode, event);
	}

	/** 关闭所有activity接口 */
	protected void appExit() {
		sendBroadcast(new Intent(getPackageName().toString() + "." + CloseAppReceiver.TAG));

	}

	private BroadcastReceiver	connectReciver	= new BroadcastReceiver() {

													@Override
													public void onReceive(Context context, Intent intent) {
														// TODO Auto-generated
														// method stub
														if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent
																.getAction())) {}
													}
												};

}