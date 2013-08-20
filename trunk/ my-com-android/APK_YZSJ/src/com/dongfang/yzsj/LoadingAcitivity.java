package com.dongfang.yzsj;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.df.util.ULog;
import com.dongfang.utils.ACache;
import com.dongfang.yzsj.bean.HomeBean;
import com.dongfang.yzsj.params.ComParams;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

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
		HomeBean bean = new com.google.gson.Gson().fromJson(ACache.get(this).getAsString(ComParams.INTENT_HOMEBEAN),
				HomeBean.class);
		if (null != bean && !TextUtils.isEmpty(bean.getMarquee()) && bean.getSlider().size() > 0) {
			intent(bean);
		}
		else {
			new HttpUtils().send(HttpRequest.HttpMethod.GET, ComParams.HTTP_HOME, new RequestCallBack<String>() {
				@Override
				public void onLoading(long total, long current) {
					ULog.d(TAG, "total = " + total + "; current = " + current);
				}

				@Override
				public void onSuccess(String result) {
					// ULog.d(TAG, "onSuccess  --" + result);
					HomeBean bean = new com.google.gson.Gson().fromJson(result, HomeBean.class);
					bean.toLog();
					// 缓存数据
					ACache.get(LoadingAcitivity.this).put(ComParams.INTENT_HOMEBEAN, result, 60 * 5);

					intent(bean);

				}

				@Override
				public void onStart() {
					ULog.i(TAG, "onStart");
				}

				@Override
				public void onFailure(Throwable error, String msg) {
					ULog.i(TAG, "onFailure");
				}
			});
		}
	}

	/** 跳转到MainActivity */
	private void intent(HomeBean bean) {
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

		// 需要判断网络

	}

}
