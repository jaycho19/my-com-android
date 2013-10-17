package com.dongfang.yzsj;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.RequestCallBack;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.ACache;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.utils.Util;
import com.dongfang.yzsj.bean.HomeBean;
import com.dongfang.yzsj.params.ComParams;

/**
 * 启动页面
 * 
 * @author dongfang
 * 
 */
public class LoadingAcitivity extends BaseActivity {

	@Override
	protected void setBaseValues() {
		TAG = "LoadingAcitivity";

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		// User.saveUserLoginStatu(this, false);

	}

	/** 跳转到MainActivity */
	private void intent(HomeBean bean) {
		// User.saveUserLoginStatu(this, false); // 设置用户处于登出状态

		Intent intent = new Intent(LoadingAcitivity.this, MainActivity.class);
		intent.putExtra(ComParams.INTENT_HOMEBEAN, bean);
		startActivity(intent);

		finish();
	}

	@Override
	protected void onStart() {
		super.onStart();
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
		// 需要判断网络
		// HomeBean bean = new com.google.gson.Gson().fromJson(ACache.get(this).getAsString(ComParams.INTENT_HOMEBEAN),
		// HomeBean.class);
		// if (null != bean && !TextUtils.isEmpty(bean.getMarquee()) && bean.getSlider().size() > 0) {
		// intent(bean);
		// }
		// else {
		new HttpUtils().send(HttpRequest.HttpMethod.GET, ComParams.HTTP_HOME, new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current) {
				ULog.d( "RequestCallBack.onLoading total = " + total + "; current = " + current);
			}

			@Override
			public void onSuccess(String result) {
				// ULog.d( "onSuccess  --" + result);
				HomeBean bean = new com.google.gson.Gson().fromJson(result, HomeBean.class);
				bean.toLog();
				// 缓存数据
				ACache.get(LoadingAcitivity.this).put(ComParams.INTENT_HOMEBEAN, result, ACache.TIME_HOUR);
				// ACache.get(LoadingAcitivity.this).put(ComParams.INTENT_HOMEBEAN, result, 60 * 5);
				intent(bean);

			}

			@Override
			public void onStart() {
				ULog.i( "RequestCallBack.onStart");

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.i( "RequestCallBack.onFailure");
			}
		});
		// }
	}

	class LoadingData extends AsyncTask<String, String, Integer> {
		@Override
		protected Integer doInBackground(String... paramArrayOfParams) {
			HomeBean bean = new com.google.gson.Gson().fromJson(
					ACache.get(LoadingAcitivity.this).getAsString(ComParams.INTENT_HOMEBEAN), HomeBean.class);
			if (null != bean && !TextUtils.isEmpty(bean.getMarquee()) && bean.getSlider().size() > 0) {
				intent(bean);
				return 0;
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
}
