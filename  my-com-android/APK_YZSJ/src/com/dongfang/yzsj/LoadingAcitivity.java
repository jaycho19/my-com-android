package com.dongfang.yzsj;

import com.df.util.ULog;
import com.dongfang.yzsj.bean.HomeBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.content.Intent;
import android.os.Bundle;

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

		new HttpUtils().send(HttpRequest.HttpMethod.GET, "http://m.fortune-net.cn/page/hbMobile/?jsonFormat=true",
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current) {
						ULog.d(TAG, "total = " + total + "; current = " + current);
					}

					@Override
					public void onSuccess(String result) {
						ULog.d(TAG, "onSuccess  --" + result);
						HomeBean bean = new com.google.gson.Gson().fromJson(result, HomeBean.class);
						bean.toLog();

						Intent intent = new Intent(LoadingAcitivity.this, MainActivity.class);
						intent.putExtra("homebean", bean);
						startActivity(intent);

						finish();

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
