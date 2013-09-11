package com.dongfang.yzsj;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.df.util.ULog;
import com.dongfang.yzsj.bean.OrderBean;
import com.dongfang.yzsj.fragment.adp.OrderAdp;
import com.dongfang.yzsj.interf.OrderResult;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class OrderAcitivity extends BaseActivity implements OnClickListener {

	@Override
	protected void setBaseValues() {
		TAG = "OrderAcitivity";
	}

	private TextView tvBack; // 返回按钮
	private ListView listView;
	private OrderAdp orderAdp;

	private OrderBean bean;

	private String channelId, conntentId, band;
	private int clipId;
	private com.dongfang.view.ProgressDialog progDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		tvBack = (TextView) findViewById(R.id.order_tv_back);
		tvBack.setOnClickListener(this);

		bean = getIntent().getParcelableExtra(ComParams.INTENT_ORDER_BEAN);
		channelId = getIntent().getStringExtra(ComParams.INTENT_MOVIEDETAIL_CHANNELID);
		conntentId = getIntent().getStringExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID);
		band = getIntent().getStringExtra(ComParams.INTENT_MOVIEDETAIL_BAND);
		clipId = getIntent().getIntExtra(ComParams.INTENT_MOVIEDETAIL_CHANNELID, 1);

		if (null != bean && null != bean.getProducts() && bean.getProducts().size() > 0) {

			progDialog = com.dongfang.view.ProgressDialog.show(this);
			progDialog.setCancelable(true);

			listView = (ListView) findViewById(R.id.order_listview);
			orderAdp = new OrderAdp(this, bean.getProducts());
			orderAdp.setOrderResult(new OrderResult() {

				@Override
				public void successed() {
					toPlay(conntentId, band, clipId);
					finish();
				}

				@Override
				public void failed() {

				}
			});
			listView.setAdapter(orderAdp);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_tv_back:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 获取播放地址，进入播放页面
	 * 
	 * @param conntentId
	 *            内容id
	 * @param band
	 *            码流类型
	 * @param clipId
	 *            第几集
	 */
	private void toPlay(String conntentId, String band, int clipId) {
		StringBuilder url = new StringBuilder(ComParams.HTTP_PLAYURL);
		url.append("token=").append(User.getToken(this));
		url.append("&").append("phone=").append(User.getPhone(this));
		url.append("&").append("contentId=").append(conntentId);
		url.append("&").append("bandwidth=").append(band);
		url.append("&").append("clipId=").append(clipId < 1 ? 1 : clipId);

		ULog.i(TAG, url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {

			@Override
			public void onSuccess(String result) {
				progDialog.dismiss();
				ULog.d(TAG, "onSuccess  --" + result);
				try {
					JSONObject json = new JSONObject(result);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					String type = "video/*";
					Uri uri = Uri.parse(json.getString("url"));
					intent.setDataAndType(uri, type);
					startActivity(intent);

					addHistory();
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onStart() {
				ULog.i(TAG, "RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.i(TAG, "RequestCallBack.onFailure");
				progDialog.dismiss();
			}
		});
	}

	/** 增加播放历史 */
	private void addHistory() {
		StringBuilder url = new StringBuilder(ComParams.HTTP_HISTORY_ADD);
		url.append("contentId=").append(conntentId).append("&");
		url.append("token=").append(User.getToken(this)).append("&");
		url.append("userTelephone=").append(User.getPhone(this));

		ULog.i(TAG, url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onSuccess(String result) {
				ULog.d(TAG, "onSuccess  --" + result);
			}

		});

	}

}
