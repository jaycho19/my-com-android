package com.dongfang.yzsj;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.RequestCallBack;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.utils.Util;
import com.dongfang.view.MyImageView;
import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
import com.dongfang.yzsj.bean.DelAddResult;
import com.dongfang.yzsj.bean.DetailBean;
import com.dongfang.yzsj.bean.OrderBean;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.dongfang.yzsj.utils.UtilOfTime;

/**
 * 视频详情页
 * 
 * @author dongfang
 * 
 */
public class DetailsActiivity extends BaseActivity implements OnClickListener {

	private DetailBean bean;

	private TextView tvBack; // 返回按钮
	private TextView tvTitle; // 顶部按钮
	private MyImageView ivMovieIcon;// 图片
	private TextView tvMovieName;// 名称
	private TextView tvMovieLength;// 视频长度
	private TextView tvMovieDesc;// 详情
	private Button btnPlay_liuchang, btnPlay_qingxi, btnPlay_gaoqing, btnPlay_chaoqing;// 播放按钮
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

		// ULog.d( bean.toString());

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
		ivMovieIcon = (MyImageView) findViewById(R.id.detail_iv_icon);
		tvMovieName = (TextView) findViewById(R.id.detail_tv_movie_name);
		tvMovieLength = (TextView) findViewById(R.id.detail_tv_length);
		tvMovieDesc = (TextView) findViewById(R.id.detail_tv_desc);
		btnPlay_liuchang = (Button) findViewById(R.id.detail_btn_play);
		btnPlay_liuchang.setOnClickListener(this);
		btnPlay_qingxi = (Button) findViewById(R.id.detail_btn_play_qingxi);
		btnPlay_qingxi.setOnClickListener(this);
		btnPlay_gaoqing = (Button) findViewById(R.id.detail_btn_play_gaoqing);
		btnPlay_gaoqing.setOnClickListener(this);
		btnPlay_chaoqing = (Button) findViewById(R.id.detail_btn_play_chaoqing);
		btnPlay_chaoqing.setOnClickListener(this);
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
		{
			int w = Util.getWindowWidth(this) / 3 - 10;
			RelativeLayout.LayoutParams Rparam = new RelativeLayout.LayoutParams(w, w * 456 / 330);
			Rparam.setMargins(5, 5, 5, 5);
			ivMovieIcon.setLayoutParams(Rparam);
		}
		ivMovieIcon.setImage(bean.getContent().getPC_MEDIA_POSTER_BIG());
		tvMovieName.setText(bean.getContent().getMEDIA_NAME());

		try {
			String ll = UtilOfTime.formatSeconds2Date(Long.valueOf(TextUtils.isEmpty(bean.getContent()
					.getMEDIA_LENGTH()) ? "0" : bean.getContent().getMEDIA_LENGTH()));
			tvMovieLength.setText(ll);
		} catch (Exception e) {
			tvMovieLength.setText(bean.getContent().getMEDIA_LENGTH());
		}

		tvMovieDesc.setText(bean.getContent().getMEDIA_INTRO());

		// 不同码流按钮初始化
		if (TextUtils.isEmpty(bean.getContent().getBandByName(ComParams.PLAY_BAND_LIUCHANG))) {
			btnPlay_liuchang.setEnabled(false);
		}
		if (TextUtils.isEmpty(bean.getContent().getBandByName(ComParams.PLAY_BAND_QINGXI))) {
			btnPlay_qingxi.setEnabled(false);
		}
		if (TextUtils.isEmpty(bean.getContent().getBandByName(ComParams.PLAY_BAND_GAOQING))) {
			btnPlay_gaoqing.setEnabled(false);
		}
		if (TextUtils.isEmpty(bean.getContent().getBandByName(ComParams.PLAY_BAND_CHAOQING))) {
			btnPlay_chaoqing.setEnabled(false);
		}

		if (bean.isHasFavoriteIt())
			btnAddFavorite.setText("取消收藏");
		else
			btnAddFavorite.setText("收藏");

		// 剧集
		int clipCount = bean.getContent().getCLIP_COUNT(); // 集数
		ULog.d( "CLIP_COUNT = " + clipCount);
		if (clipCount > 1) {
			int rows = (clipCount / 5) + ((clipCount % 5) > 0 ? 1 : 0);
			LinearLayout.LayoutParams lpTV = new LayoutParams(-2, -2, 1);
			lpTV.setMargins(5, 5, 5, 5);
			for (int row = 0; row < rows; row++) {
				LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_detail_juji_row,
						null);
				if (row == (rows - 1)) {
					for (int i = 0, length = clipCount - row * 5; i < length; i++) {
						TextView tv = (TextView) LayoutInflater.from(this).inflate(
								R.layout.activity_detail_textview_juji, null);
						tv.setLayoutParams(lpTV);
						ULog.d( Integer.toString(row * 5 + i + 1));
						tv.setText(Integer.toString(row * 5 + i + 1));
						tv.setOnClickListener(new JuJiOnClickListener(row * 5 + i + 1));
						ll.addView(tv);
					}
					for (int i = 0, length = 5 - ll.getChildCount(); i < length; i++) {
						TextView tv = (TextView) LayoutInflater.from(this).inflate(
								R.layout.activity_detail_textview_juji, null);
						tv.setLayoutParams(lpTV);
						tv.setVisibility(View.INVISIBLE);
						ULog.d( Integer.toString(clipCount + 1 + i) + "  invisible");
						ll.addView(tv);
					}
				}
				else {
					for (int i = 0; i < 5; i++) {
						TextView tv = (TextView) LayoutInflater.from(this).inflate(
								R.layout.activity_detail_textview_juji, null);
						tv.setLayoutParams(lpTV);
						ULog.d( Integer.toString(row * 5 + i + 1));
						tv.setText(Integer.toString(row * 5 + i + 1));
						tv.setOnClickListener(new JuJiOnClickListener(row * 5 + i + 1));
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
			ULog.d( "RelateContents size = " + length);
			int w = Util.getWindowWidth(this) / 3 - 10;
			LinearLayout.LayoutParams lParam = new LinearLayout.LayoutParams(w, -2);
			lParam.setMargins(5, 5, 5, 5);
			LinearLayout.LayoutParams mivParam = new LinearLayout.LayoutParams(w, w * 456 / 330);

			for (int i = 0, l = Math.min(length, 3); i < l; i++) {
				View view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.fragment_home_item_image, null);
				view.setLayoutParams(lParam);
				MyImageView imageView = (MyImageView) view.findViewById(R.id.fragment_home_iv_item_myimage);
				imageView.setLayoutParams(mivParam);
				imageView.setImage(bean.getRelateContents().get(i).getPC_MEDIA_POSTER_BIG());
				((TextView) view.findViewById(R.id.fragment_home_tv_item_myimage)).setText(bean.getRelateContents()
						.get(i).getMEDIA_NAME());
				view.setOnClickListener(new MyOnClickListener(bean.getChannel().getChannelId(), bean
						.getRelateContents().get(i).getId()));
				llLikeContain_0.addView(view);
			}
			for (int i = 3, l = Math.min(length, 6); i < l; i++) {
				View view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.fragment_home_item_image, null);
				view.setLayoutParams(lParam);
				MyImageView imageView = (MyImageView) view.findViewById(R.id.fragment_home_iv_item_myimage);
				imageView.setLayoutParams(mivParam);
				imageView.setImage(bean.getRelateContents().get(i).getPC_MEDIA_POSTER_BIG());
				((TextView) view.findViewById(R.id.fragment_home_tv_item_myimage)).setText(bean.getRelateContents()
						.get(i).getMEDIA_NAME());
				view.setOnClickListener(new MyOnClickListener(bean.getChannel().getChannelId(), bean
						.getRelateContents().get(i).getId()));
				llLikeContain_1.addView(view);
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
		// ULog.i( sb.toString());
		//
		// new HttpUtils().send(HttpRequest.HttpMethod.GET, sb.toString(), new RequestCallBack<String>() {
		// @Override
		// public void onLoading(long total, long current) {
		// ULog.d( "RequestCallBack.onLoading total = " + total + "; current = " + current);
		// }
		//
		// @Override
		// public void onSuccess(String result) {
		// ULog.d( "onSuccess  --" + result);
		// DetailBean bean = new com.google.gson.Gson().fromJson(result, DetailBean.class);
		// ULog.d( bean.toString());
		// }
		//
		// @Override
		// public void onStart() {
		// ULog.i( "RequestCallBack.onStart");
		// }
		//
		// @Override
		// public void onFailure(Throwable error, String msg) {
		// ULog.i( "RequestCallBack.onFailure");
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
			ULog.d( v.toString());
			new ToDetailAsyncTask(DetailsActiivity.this, channelId, contentId).execute();
		}
	}

	/** 剧集进入播放 */
	private class JuJiOnClickListener implements OnClickListener {
		private int clipId;

		public JuJiOnClickListener(int clipId) {
			this.clipId = clipId;
		}

		@Override
		public void onClick(View v) {
			ULog.d( v.toString());
			toPlayAuth(bean.getChannel().getChannelId(), bean.getContent().getId(), bean.getContent()
					.getCLIP_BANDWITHS().get(0).getCode(), clipId);
		}
	}

	/** 播放鉴权 */
	private void toPlayAuth(final String channelId, final String conntentId, final String band, final int clipId) {
		StringBuilder url = new StringBuilder(ComParams.HTTP_PLAYAUTH);
		url.append("userId=").append(User.getPhone(this));
		url.append("&").append("contentId=").append(conntentId);
		url.append("&").append("channelId=").append(channelId);

		ULog.i( url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onSuccess(String result) {
				// progDialog.dismiss();
				ULog.d( "onSuccess  --" + result);
				try {
					JSONObject json = new JSONObject(result);
					if (json.getBoolean("success")) {
						// 鉴权通过
						toPlay(conntentId, band, clipId);
					}
					else {
						toOrder(channelId, conntentId, band, clipId);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onStart() {
				ULog.i( "RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.i( "RequestCallBack.onFailure");
				progDialog.dismiss();
			}
		});

	}

	/** 鉴权不通过，显示订购页面 */
	private void toOrder(final String channelId, final String conntentId, final String band, final int clipId) {
		// StringBuilder url = new StringBuilder("http://tv.inhe.net/page/hbMobile/buyList.jsp?jsonFormat=true&");
		StringBuilder url = new StringBuilder(ComParams.HTTP_ORDERLIST);
		url.append("u_serId=").append(User.getPhone(this));
		url.append("&").append("token=").append(User.getToken(this));
		url.append("&").append("contentId=").append(conntentId);
		url.append("&").append("channelId=").append(channelId);

		ULog.i( url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {

			@Override
			public void onSuccess(String result) {
				progDialog.dismiss();
				ULog.d( "onSuccess  -- " + result);
				OrderBean bean = new com.google.gson.Gson().fromJson(result, OrderBean.class);

				ULog.d( bean.toString());

				if (bean.isSuccess()) {
					if (null != bean.getProducts() && bean.getProducts().size() > 0) {
						Intent intent = new Intent(DetailsActiivity.this, OrderAcitivity.class);
						intent.putExtra(ComParams.INTENT_ORDER_BEAN, bean);
						intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CHANNELID, channelId);
						intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID, conntentId);
						intent.putExtra(ComParams.INTENT_MOVIEDETAIL_BAND, band);
						intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CLIPID, clipId);
						startActivity(intent);
					}
					else {
						// 获取订购页面失败，尝试着进行播放
						toPlay(conntentId, band, clipId);
					}
				}
				else {
					Toast.makeText(DetailsActiivity.this, bean.getError().get(0), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
			}
		});
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
	private void toPlay(final String conntentId, final String band, final int clipId) {
		StringBuilder url = new StringBuilder(ComParams.HTTP_PLAYURL);
		url.append("token=").append(User.getToken(this));
		url.append("&").append("phone=").append(User.getPhone(this));
		url.append("&").append("contentId=").append(conntentId);
		url.append("&").append("bandwidth=").append(band);
		url.append("&").append("clipId=").append(clipId < 1 ? 1 : clipId);

		ULog.i( url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current) {
				ULog.d( "RequestCallBack.onLoading total = " + total + "; current = " + current);
			}

			@Override
			public void onSuccess(String result) {
				progDialog.dismiss();
				ULog.d( "onSuccess  --" + result);
				try {
					JSONObject json = new JSONObject(result);
					Bundle data = new Bundle();
					data.putString(ComParams.INTENT_MOVIEDETAIL_CONNENTID, conntentId);
					data.putInt(ComParams.INTENT_MOVIEDETAIL_CLIPID, clipId < 1 ? 1 : clipId);
					PlayerActivity.toPlay(DetailsActiivity.this, json.getString("url"),data);
					// addHistory();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onStart() {
				ULog.i( "RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.i( "RequestCallBack.onFailure");
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
			if (bean.isHasFavoriteIt())
				delFromFavorite();
			else
				addToFavorite();
			break;
		case R.id.detail_btn_play:
			toPlayAuth(bean.getChannel().getChannelId(), bean.getContent().getId(),
					bean.getContent().getBandByName(ComParams.PLAY_BAND_LIUCHANG), 1);
			break;
		case R.id.detail_btn_play_qingxi:
			toPlayAuth(bean.getChannel().getChannelId(), bean.getContent().getId(),
					bean.getContent().getBandByName(ComParams.PLAY_BAND_QINGXI), 1);
			break;
		case R.id.detail_btn_play_gaoqing:
			toPlayAuth(bean.getChannel().getChannelId(), bean.getContent().getId(),
					bean.getContent().getBandByName(ComParams.PLAY_BAND_GAOQING), 1);
			break;
		case R.id.detail_btn_play_chaoqing:
			toPlayAuth(bean.getChannel().getChannelId(), bean.getContent().getId(),
					bean.getContent().getBandByName(ComParams.PLAY_BAND_CHAOQING), 1);
			break;
		default:
			break;
		}
	}

	/** 添加到收藏 */
	private void addToFavorite() {
		StringBuilder url = new StringBuilder(ComParams.HTTP_FAVORITE_ADD);
		url.append("contentId=").append(bean.getContent().getId());
		url.append("&").append("token=").append(User.getToken(this));
		url.append("&").append("userTelephone=").append(User.getPhone(this));

		ULog.i( url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current) {
				ULog.d( "RequestCallBack.onLoading total = " + total + "; current = " + current);
			}

			@Override
			public void onSuccess(String result) {
				ULog.d( "onSuccess  --" + result);
				progDialog.dismiss();

				DelAddResult addResult = new com.google.gson.Gson().fromJson(result, DelAddResult.class);

				if (addResult.isSuccess()) {
					Toast.makeText(DetailsActiivity.this, "收藏成功!", Toast.LENGTH_LONG).show();
					bean.setHasFavoriteIt(true);
					btnAddFavorite.setText("取消收藏");
				}
				else {
					Toast.makeText(DetailsActiivity.this, addResult.getMsg(), Toast.LENGTH_LONG).show();
				}

				// try {
				// JSONObject json = new JSONObject(result);
				// if (json.getBoolean("success")) {
				// Toast.makeText(DetailsActiivity.this, "收藏成功", Toast.LENGTH_LONG).show();
				// bean.setHasFavoriteIt(true);
				// btnAddFavorite.setText("取消收藏");
				//
				// }
				// else {
				// Toast.makeText(DetailsActiivity.this, json.getJSONArray("error").getString(0),
				// Toast.LENGTH_LONG).show();
				// }
				// } catch (JSONException e) {
				// Toast.makeText(DetailsActiivity.this, "收藏失败~~~~(>_<)~~~~ ", Toast.LENGTH_LONG).show();
				// e.printStackTrace();
				// }
			}

			@Override
			public void onStart() {
				ULog.i( "RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.i( "RequestCallBack.onFailure");
				progDialog.dismiss();
			}
		});
	}

	/** 取消收藏 */
	private void delFromFavorite() {
		StringBuilder url = new StringBuilder(ComParams.HTTP_FAVORITE_DEL);
		url.append("contentId=").append(bean.getContent().getId());
		url.append("&").append("token=").append(User.getToken(this));
		url.append("&").append("userTelephone=").append(User.getPhone(this));

		ULog.i( url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current) {
				ULog.d( "RequestCallBack.onLoading total = " + total + "; current = " + current);
			}

			@Override
			public void onSuccess(String result) {
				ULog.d( "onSuccess  --" + result);
				progDialog.dismiss();

				DelAddResult delResult = new com.google.gson.Gson().fromJson(result, DelAddResult.class);

				if (delResult.isSuccess()) {
					Toast.makeText(DetailsActiivity.this, "取消收藏成功!", Toast.LENGTH_LONG).show();
					bean.setHasFavoriteIt(false);
					btnAddFavorite.setText("收藏");
				}
				else {
					Toast.makeText(DetailsActiivity.this, delResult.getMsg(), Toast.LENGTH_LONG).show();
				}

				// try {
				// JSONObject json = new JSONObject(result);
				// if (json.getBoolean("success")) {
				// Toast.makeText(DetailsActiivity.this, "删除成功", Toast.LENGTH_LONG).show();
				// bean.setHasFavoriteIt(false);
				// btnAddFavorite.setText("收藏");
				// }
				// else {
				// Toast.makeText(DetailsActiivity.this, json.getJSONArray("error").getString(0),
				// Toast.LENGTH_LONG).show();
				// }
				// } catch (JSONException e) {
				// Toast.makeText(DetailsActiivity.this, "删除收藏失败~~~~(>_<)~~~~ ", Toast.LENGTH_LONG).show();
				// e.printStackTrace();
				// }
			}

			@Override
			public void onStart() {
				ULog.i( "RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.i( "RequestCallBack.onFailure");
				progDialog.dismiss();
			}
		});
	}

	/**
	 * 增加播放历史
	 * 
	 * @deprecated
	 */
	private void addHistory() {
		StringBuilder url = new StringBuilder(ComParams.HTTP_HISTORY_ADD);
		url.append("contentId=").append(bean.getContent().getId()).append("&");
		url.append("token=").append(User.getToken(this)).append("&");
		url.append("userTelephone=").append(User.getPhone(this));

		ULog.i( url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onSuccess(String result) {
				ULog.d( "onSuccess  --" + result);
			}

		});

	}
}
