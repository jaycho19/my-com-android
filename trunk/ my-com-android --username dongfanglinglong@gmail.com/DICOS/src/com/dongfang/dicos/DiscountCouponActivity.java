package com.dongfang.dicos;

import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * ”≈ª›»Ø“≥√Ê
 * 
 * @author dongfang
 * */
public class DiscountCouponActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discountcoupon);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Util.showExitDialog(this);

			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

}
