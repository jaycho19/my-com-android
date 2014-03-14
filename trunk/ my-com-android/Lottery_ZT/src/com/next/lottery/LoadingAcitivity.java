package com.next.lottery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dongfang.v4.app.BaseActivity;
import com.next.lottery.bean.HomeBean;
import com.next.lottery.utils.User;
import com.next.lottery.utils.Util;

/**
 * 启动页面
 * 
 * @author dongfang
 * 
 */
public class LoadingAcitivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
	}

	/** 跳转到MainActivity */
	private void intent(HomeBean bean) {
		User.saveToken(this, "111111111111"); // 表示登录
		Intent intent = new Intent(LoadingAcitivity.this, MainActivity.class);
		// intent.putExtra(ComParams.INTENT_HOMEBEAN, bean);
		startActivity(intent);

		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Util.isNetworkAvailable(this)) {
			new LoadingData().execute();
		}
		else {
			Toast.makeText(this, "请检查网络连接", Toast.LENGTH_LONG).show();
		}
	}

	private void initData() {
		intent(null);
	}

	class LoadingData extends AsyncTask<String, String, Integer> {
		@Override
		protected Integer doInBackground(String... paramArrayOfParams) {
			int i = 10000000;
			while (i > 0) {
				i--;
			}
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (1 == result.intValue()) {
				initData();
			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
