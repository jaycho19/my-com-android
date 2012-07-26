package com.dongfang.dicos;

import com.dongfang.dicos.net.thread.IPArea;
import com.dongfang.dicos.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * 开启应用前ACTIVITY
 * 
 * @author dongfang
 * */

public class FirstpageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstpage);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		new IPArea(this, Util.getPhoneNumber(this)).start();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Handler().postDelayed(gotoMain, 1000);
	}

	Runnable	gotoMain	= new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									// Toast.makeText(Firstpage.this,
									// "goto main page!!", Toast.LENGTH_LONG);
									
									Intent intent = new Intent(FirstpageActivity.this, DicosMainActivity.class);
									FirstpageActivity.this.startActivity(intent);
									finish();
								}
							};

}
