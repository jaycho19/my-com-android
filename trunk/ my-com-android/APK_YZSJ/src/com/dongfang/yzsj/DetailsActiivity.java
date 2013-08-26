package com.dongfang.yzsj;

import com.df.util.ULog;
import com.dongfang.utils.ACache;
import com.dongfang.yzsj.bean.HomeBean;
import com.dongfang.yzsj.params.ComParams;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.os.Bundle;
import android.text.TextUtils;

/**
 * 视频详情页
 * 
 * @author dongfang
 * 
 */
public class DetailsActiivity extends BaseActivity {

	private String conntentId; // 视频id

	@Override
	protected void setBaseValues() {
		TAG = "DetailsActiivity";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		// conntentId = getIntent().getStringExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID);
		conntentId = "299198836";// 龙门镖局
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (!TextUtils.isEmpty(conntentId)) {
			
			String url ="http://tv.inhe.net/page/hbMobile/detail.jsp?jsonFormat=true&contentId=" + conntentId;
			ULog.d(TAG, url);
			
			new HttpUtils().send(HttpRequest.HttpMethod.GET,
					// m.fortune-net.cn
					url,
					new RequestCallBack<String>() {
						@Override
						public void onLoading(long total, long current) {
							ULog.d(TAG, "RequestCallBack.onLoading total = " + total + "; current = " + current);
						}

						@Override
						public void onSuccess(String result) {
							ULog.d(TAG, "onSuccess  --" + result);
							// HomeBean bean = new com.google.gson.Gson().fromJson(result, HomeBean.class);
							// bean.toLog();
							// 缓存数据
							// ACache.get(DetailsActiivity.this).put(ComParams.INTENT_HOMEBEAN, result,
							// ACache.TIME_DAY * 7);
							// ACache.get(LoadingAcitivity.this).put(ComParams.INTENT_HOMEBEAN, result, 60 * 5);
						}

						@Override
						public void onStart() {
							ULog.i(TAG, "RequestCallBack.onStart");
						}

						@Override
						public void onFailure(Throwable error, String msg) {
							ULog.i(TAG, "RequestCallBack.onFailure");
						}
					});
		}

	}
}
