package com.dongfang.yzsj;

import com.df.util.ULog;
import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
import com.dongfang.yzsj.fragment.LoginFragment;
import com.dongfang.yzsj.params.ComParams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 登陆activity
 * 
 * @author dongfang
 * 
 */
public class LoginActivity extends BaseActivity {

	private Bundle data;
	private LoginFragment loginFragment;// 登陆Fragment
	private Intent intent;

	@Override
	protected void setBaseValues() {
		TAG = "LoginActivity";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		data = getIntent().getExtras();
		findViewById(R.id.login_tv_back).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_login);
		loginFragment.setOnLoginAndVerify(new LoginFragment.OnLoginAndVerify() {

			@Override
			public void verify(boolean verify, String phoneNumber) {
				// TODO Auto-generated method stub
			}

			@Override
			public void login(boolean login, String phoneNumber) {
				if (login) {
					toResult();
					//finish();
				}
			}
		});

	}

	/** 返回信息给进入这个界面的activity */
	private void toResult() {
		if (null == data) {
			return;
		}

		if (ToDetailAsyncTask.TAG.equals(data.getString(ComParams.INTENT_TODO))) {
			new ToDetailAsyncTask(this, data.getString(ComParams.INTENT_MOVIEDETAIL_CHANNELID),
					data.getString(ComParams.INTENT_MOVIEDETAIL_CONNENTID)).execute();
		}

	}

}
