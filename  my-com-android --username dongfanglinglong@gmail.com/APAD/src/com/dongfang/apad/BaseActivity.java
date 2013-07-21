package com.dongfang.apad;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.dongfang.apad.broadcast.CloseAppReceiver;
import com.dongfang.apad.util.ULog;

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

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(closeApp);
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

	/** 关闭所有activity接口 */
	protected void appExit() {
		sendBroadcast(new Intent(getPackageName().toString() + "." + CloseAppReceiver.TAG));
	}

}