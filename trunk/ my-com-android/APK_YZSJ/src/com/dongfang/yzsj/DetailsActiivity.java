package com.dongfang.yzsj;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.df.util.ULog;
import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
import com.dongfang.yzsj.bean.DetailBean;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.dongfang.yzsj.utils.Util;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 视频详情页
 * 
 * @author dongfang
 * 
 */
public class DetailsActiivity extends BaseActivity implements OnClickListener {

	private String conntentId; // 视频id
	private String channelId; // 频道id

	private DetailBean bean;

	private TextView tvBack; // 返回按钮
	private TextView tvTitle; // 顶部按钮
	private ImageView ivMovieIcon;// 图片
	private TextView tvMovieName;// 名称
	private TextView tvMovieLength;// 视频长度
	private TextView tvMovieDesc;// 详情
	private Button btnPlay_liuchang, btnPlay_qingxi, btnPlay_gaoqing;// 播放按钮
	private Button btnAddFavorite;// 添加到收藏
	private LinearLayout llJuJiContain, llJuJiTitle;// 我的喜欢
	private LinearLayout llLikeContain_0, llLikeContain_1;// 我的喜欢

	private com.dongfang.view.ProgressDialog progDialog;

	@Override
	protected void setBaseValues() {
		TAG = "DetailsActiivity";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		bean = getIntent().getParcelableExtra(ComParams.INTENT_MOVIEDETAIL_BEAN);
		// ULog.d(TAG, bean.toString());

		// conntentId = getIntent().getStringExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID);
		// channelId = getIntent().getStringExtra(ComParams.INTENT_MOVIEDETAIL_CHANNELID);
		// conntentId = "299198836";// 龙门镖局
		// channelId = "0";//
		initView();

	}

	private void initView() {
		progDialog = com.dongfang.view.ProgressDialog.show(this);
		progDialog.setCancelable(true);

		tvBack = (TextView) findViewById(R.id.detail_tv_back);
		tvBack.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.detail_tv_title);
		ivMovieIcon = (ImageView) findViewById(R.id.detail_iv_icon);
		tvMovieName = (TextView) findViewById(R.id.detail_tv_movie_name);
		tvMovieLength = (TextView) findViewById(R.id.detail_tv_length);
		tvMovieDesc = (TextView) findViewById(R.id.detail_tv_desc);
		btnPlay_liuchang = (Button) findViewById(R.id.detail_btn_play);
		btnPlay_liuchang.setOnClickListener(this);
		btnPlay_qingxi = (Button) findViewById(R.id.detail_btn_play_qingxi);
		btnPlay_qingxi.setOnClickListener(this);
		btnPlay_gaoqing = (Button) findViewById(R.id.detail_btn_play_gaoqing);
		btnPlay_gaoqing.setOnClickListener(this);
		btnAddFavorite = (Button) findViewById(R.id.detail_btn_addfavorite);
		btnAddFavorite.setOnClickListener(this);
		llJuJiContain = (LinearLayout) findViewById(R.id.detail_ll_juji_contain);
		llJuJiTitle = (LinearLayout) findViewById(R.id.detail_ll_juji_title);
		llLikeContain_0 = (LinearLayout) findViewById(R.id.detail_ll_like_contain_0);
		llLikeContain_1 = (LinearLayout) findViewById(R.id.detail_ll_like_contain_1);

		initViewData(bean);

	}

	private void initViewData(final DetailBean bean) {
		if (null == bean)
			return;

		tvTitle.setText(bean.getChannel().getName());
		BitmapUtils.create(this).display(ivMovieIcon, bean.getContent().getPC_MEDIA_POSTER_BIG());
		tvMovieName.setText(bean.getContent().getMEDIA_NAME());
		tvMovieLength.setText(bean.getContent().getMEDIA_LENGTH());
		tvMovieDesc.setText(bean.getContent().getMEDIA_INTRO());

		// 剧集
		if (bean.getContent().getCLIP_COUNT() > 1) {
			int w = Util.getWindowWidth(this) / 5 - 10;
			int rows = (bean.getContent().getCLIP_COUNT() / 5) + ((bean.getContent().getCLIP_COUNT() % 5) > 0 ? 1 : 0);
			LinearLayout.LayoutParams lpTV = new LayoutParams(-2, -2, 1);
			lpTV.setMargins(5, 5, 5, 5);
			for (int row = 0; row < rows; row++) {
				LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_detail_juji_row,
						null);
				if (row == (rows - 1)) {
					for (int i = 0, length = bean.getContent().getCLIP_COUNT() % 5; i < length; i++) {
						TextView tv = (TextView) LayoutInflater.from(this).inflate(
								R.layout.activity_detail_textview_juji, null);
						tv.setLayoutParams(lpTV);
						ULog.d(TAG, Integer.toString(row * 5 + i + 1));
						tv.setText(Integer.toString(row * 5 + i + 1));
						ll.addView(tv);
					}

				}
				else {
					for (int i = 0; i < 5; i++) {
						TextView tv = (TextView) LayoutInflater.from(this).inflate(
								R.layout.activity_detail_textview_juji, null);
						tv.setLayoutParams(lpTV);
						ULog.d(TAG, Integer.toString(row * 5 + i + 1));
						tv.setText(Integer.toString(row * 5 + i + 1));
						ll.addView(tv);
					}
				}
				llJuJiContain.addView(ll);
			}

		}
		else {
			llJuJiTitle.setVisibility(View.GONE);
			llJuJiContain.setVisibility(View.GONE);
		}

		// 我的喜欢，相关推荐
		if (null != bean.getRelateContents() && bean.getRelateContents().size() > 0) {
			llLikeContain_0.removeAllViews();
			llLikeContain_1.removeAllViews();

			int length = bean.getRelateContents().size();
			ULog.d(TAG, "length = " + length);
			int w = Util.getWindowWidth(this) / 3 - 10;
			LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(w, w * 456 / 330);
			lparam.setMargins(5, 5, 5, 5);
			for (int i = 0, l = Math.min(length, 3); i < l; i++) {
				ImageView imageView = new ImageView(this);
				imageView.setLayoutParams(lparam);
				BitmapUtils.create(this).display(imageView, bean.getRelateContents().get(i).getPC_MEDIA_POSTER_BIG(),
						w, LinearLayout.LayoutParams.WRAP_CONTENT);
				imageView.setOnClickListener(new MyOnClickListener(bean.getChannel().getChannelId(), bean
						.getRelateContents().get(i).getId()));
				llLikeContain_0.addView(imageView);
			}
			for (int i = 3, l = Math.min(length, 6); i < l; i++) {
				ImageView imageView = new ImageView(this);
				imageView.setLayoutParams(lparam);
				BitmapUtils.create(this).display(imageView, bean.getRelateContents().get(i).getPC_MEDIA_POSTER_BIG(),
						w, LinearLayout.LayoutParams.WRAP_CONTENT);
				imageView.setOnClickListener(new MyOnClickListener(bean.getChannel().getChannelId(), bean
						.getRelateContents().get(i).getId()));
				llLikeContain_1.addView(imageView);
			}

		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		// if (!TextUtils.isEmpty(conntentId)) {
		//
		// StringBuilder sb = new StringBuilder(ComParams.HTTP_DETAIL);
		// sb.append("token=").append(User.getToken(this)).append("&");
		// sb.append("phone=").append(User.getPhone(this)).append("&");
		// sb.append("channelId=").append(channelId).append("&");
		// sb.append("contentId=").append(conntentId);
		// ULog.i(TAG, sb.toString());
		//
		// new HttpUtils().send(HttpRequest.HttpMethod.GET, sb.toString(), new RequestCallBack<String>() {
		// @Override
		// public void onLoading(long total, long current) {
		// ULog.d(TAG, "RequestCallBack.onLoading total = " + total + "; current = " + current);
		// }
		//
		// @Override
		// public void onSuccess(String result) {
		// ULog.d(TAG, "onSuccess  --" + result);
		// DetailBean bean = new com.google.gson.Gson().fromJson(result, DetailBean.class);
		// ULog.d(TAG, bean.toString());
		// }
		//
		// @Override
		// public void onStart() {
		// ULog.i(TAG, "RequestCallBack.onStart");
		// }
		//
		// @Override
		// public void onFailure(Throwable error, String msg) {
		// ULog.i(TAG, "RequestCallBack.onFailure");
		// }
		// });
		// }

	}

	/** 进入详情页 */
	private class MyOnClickListener implements OnClickListener {
		private String contentId;
		private String channelId;

		public MyOnClickListener(String channelId, String contentId) {
			this.contentId = contentId;
			this.channelId = channelId;
		}

		@Override
		public void onClick(View v) {
			ULog.d(TAG, v.toString());
			new ToDetailAsyncTask(DetailsActiivity.this, channelId, contentId).execute();
		}
	}

	private void toPlay(String conntentId, String band) {
		StringBuilder url = new StringBuilder(ComParams.HTTP_PLAYURL);
		url.append("token=").append(User.getToken(this));
		url.append("&").append("phone=").append(User.getPhone(this));
		url.append("&").append("contentId=").append(conntentId);
		url.append("&").append("bandwidth=").append(band);

		ULog.i(TAG, url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current) {
				ULog.d(TAG, "RequestCallBack.onLoading total = " + total + "; current = " + current);
			}

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onStart() {
				ULog.i(TAG, "RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(Throwable error, String msg) {
				ULog.i(TAG, "RequestCallBack.onFailure");
				progDialog.dismiss();
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_tv_back:
			finish();
			break;
		case R.id.detail_btn_addfavorite:
			addToFavorite();
			break;
		case R.id.detail_btn_play:
			toPlay(bean.getContent().getId(), bean.getContent().getCLIP_BANDWITHS().get(0).getCode());
			break;
		case R.id.detail_btn_play_qingxi:
			toPlay(bean.getContent().getId(), bean.getContent().getCLIP_BANDWITHS().get(1).getCode());
			break;
		case R.id.detail_btn_play_gaoqing:
			toPlay(bean.getContent().getId(), bean.getContent().getCLIP_BANDWITHS().get(2).getCode());
			break;
		default:
			break;
		}
	}

	private void addToFavorite() {
		StringBuilder url = new StringBuilder(ComParams.HTTP_FAVORITE_ADD);
		url.append("contentId=").append(bean.getContent().getId()).append("&");
		url.append("token=").append(User.getToken(this)).append("&");
		url.append("userTelephone=").append(User.getPhone(this));

		ULog.i(TAG, url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current) {
				ULog.d(TAG, "RequestCallBack.onLoading total = " + total + "; current = " + current);
			}

			@Override
			public void onSuccess(String result) {
				ULog.d(TAG, "onSuccess  --" + result);
				progDialog.dismiss();

				try {
					JSONObject json = new JSONObject(result);
					if (json.getBoolean("success")) {
						Toast.makeText(DetailsActiivity.this, "收藏成功", Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(DetailsActiivity.this, json.getJSONArray("error").getString(0),
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					Toast.makeText(DetailsActiivity.this, "收藏失败~~~~(>_<)~~~~ ", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}

			@Override
			public void onStart() {
				ULog.i(TAG, "RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(Throwable error, String msg) {
				ULog.i(TAG, "RequestCallBack.onFailure");
				progDialog.dismiss();
			}
		});
	}

	private void addHistory() {
		StringBuilder url = new StringBuilder(ComParams.HTTP_HISTORY_ADD);
		url.append("contentId=").append(bean.getContent().getId()).append("&");
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
