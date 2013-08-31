package com.dongfang.yzsj;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.df.util.ULog;
import com.dongfang.yzsj.bean.DetailBean;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.Util;
import com.lidroid.xutils.BitmapUtils;

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
	private Button btnPlay;// 播放按钮
	private Button btnAddFavorite;// 添加到收藏
	private LinearLayout llLikeContain_0, llLikeContain_1;// 我的喜欢

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
		tvBack = (TextView) findViewById(R.id.detail_tv_back);
		tvBack.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.detail_tv_title);
		ivMovieIcon = (ImageView) findViewById(R.id.detail_iv_icon);
		tvMovieName = (TextView) findViewById(R.id.detail_tv_movie_name);
		tvMovieLength = (TextView) findViewById(R.id.detail_tv_length);
		tvMovieDesc = (TextView) findViewById(R.id.detail_tv_desc);
		btnPlay = (Button) findViewById(R.id.detail_btn_play);
		btnPlay.setOnClickListener(this);
		btnAddFavorite = (Button) findViewById(R.id.detail_btn_addfavorite);
		btnAddFavorite.setOnClickListener(this);
		llLikeContain_0 = (LinearLayout) findViewById(R.id.detail_ll_like_contain_0);
		llLikeContain_1 = (LinearLayout) findViewById(R.id.detail_ll_like_contain_1);

		initViewData(bean);

	}

	private void initViewData(DetailBean bean) {
		if (null == bean)
			return;

		tvTitle.setText(bean.getChannel().getName());
		BitmapUtils.create(this).display(ivMovieIcon, bean.getContent().getPC_MEDIA_POSTER_BIG());
		tvMovieName.setText(bean.getContent().getMEDIA_NAME());
		tvMovieLength.setText(bean.getContent().getMEDIA_LENGTH());
		tvMovieDesc.setText(bean.getContent().getMEDIA_INTRO());

		if (null != bean.getRelateContents() && bean.getRelateContents().size() > 0) {
			llLikeContain_0.removeAllViews();
			llLikeContain_1.removeAllViews();

			int length = bean.getRelateContents().size();
			ULog.d(TAG, "length = " + length);
			int w = Util.getWindowWidth(this) / 3 - 10;
			LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(w, LinearLayout.LayoutParams.WRAP_CONTENT);
			lparam.setMargins(5, 5, 5, 5);
			for (int i = 0, l = Math.min(length, 3); i < l; i++) {
				ImageView imageView = new ImageView(this);
				imageView.setLayoutParams(lparam);
				BitmapUtils.create(this).display(imageView, bean.getRelateContents().get(i).getPC_MEDIA_POSTER_BIG(),
						w, LinearLayout.LayoutParams.WRAP_CONTENT);
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// new ToDetailAsyncTask(this, item.getChannel().getChannelId(), movie.getId()).execute();
					}
				});

				llLikeContain_0.addView(imageView);
			}
			for (int i = 3, l = Math.min(length, 6); i < l; i++) {
				ImageView imageView = new ImageView(this);
				imageView.setLayoutParams(lparam);
				BitmapUtils.create(this).display(imageView, bean.getRelateContents().get(i).getPC_MEDIA_POSTER_BIG(),
						w, LinearLayout.LayoutParams.WRAP_CONTENT);
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// new ToDetailAsyncTask(this, item.getChannel().getChannelId(), movie.getId()).execute();
					}
				});

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_tv_back:
			finish();
			break;

		default:
			break;
		}

	}
}
