package com.dongfang.yzsj.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.dongfang.utils.ACache;
import com.dongfang.utils.ULog;
import com.dongfang.utils.Util;
import com.dongfang.view.MyImageView;
import com.dongfang.yzsj.LoginActivity;
import com.dongfang.yzsj.PlayerActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.HomeLivesItem;
import com.dongfang.yzsj.bean.LiveBean;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 直播
 * 
 * @author dongfang
 * 
 */
public class LiveFragment extends Fragment {
	public static final String TAG = "LiveFragment";

	private GridView gridView;
	private LiveAdapter listAdapter;
	private LiveBean bean;

	private com.dongfang.view.ProgressDialog progDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");
		View view = inflater.inflate(R.layout.fragment_live, container, false);
		initView(view);

		if (null != savedInstanceState && savedInstanceState.containsKey(ComParams.INTENT_LIVEBEAN)) {
			bean = savedInstanceState.getParcelable(ComParams.INTENT_LIVEBEAN);
		}

		return view;
	}

	private void initView(View view) {
		progDialog = com.dongfang.view.ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
		gridView = (GridView) view.findViewById(R.id.gv_fragment_live);
		listAdapter = new LiveAdapter(getActivity(), null);
		gridView.setAdapter(listAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();

		// 非异常中断进入时，bean为空，判断是否有缓存
		if (null == bean) {
			bean = new com.google.gson.Gson().fromJson(
					ACache.get(getActivity()).getAsString(ComParams.INTENT_LIVEBEAN), LiveBean.class);

		}

		// bean非空初始化数据
		if (null != bean && bean.getTotal() > 0) {
			listAdapter.setList(bean.getLives());
			listAdapter.notifyDataSetChanged();

			ULog.d(TAG, "onResume --> " + bean.toString());
		}
		else {
			// bean为空，网络请求数据，需对网络进行判断
			new HttpUtils().send(HttpRequest.HttpMethod.GET, ComParams.HTTP_LIVE, new RequestCallBack<String>() {
				@Override
				public void onLoading(long total, long current) {
					ULog.d(TAG, "total = " + total + "; current = " + current);
				}

				@Override
				public void onSuccess(String result) {
					ULog.d(TAG, "onSuccess  --" + result);
					bean = new com.google.gson.Gson().fromJson(result, LiveBean.class);
					ULog.i(TAG, "HttpUtils --> " + bean.toString());
					ACache.get(getActivity()).put(ComParams.INTENT_LIVEBEAN, result, 60 * 5);// 缓存数据

					listAdapter.setList(bean.getLives());
					listAdapter.notifyDataSetChanged();

					progDialog.dismiss();
				}

				@Override
				public void onStart() {
					ULog.i(TAG, "onStart");
					progDialog.show();
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					ULog.i(TAG, "onFailure");
					progDialog.dismiss();
				}
			});
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (null != bean) {
			outState.putParcelable(ComParams.INTENT_LIVEBEAN, bean);
			ULog.d(TAG, "onSaveInstanceState --> " + bean.toString());
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
	private void toPlay(final String conntentId) {
		StringBuilder url = new StringBuilder(ComParams.HTTP_PLAYURL);
		url.append("token=").append(User.getToken(getActivity()));
		url.append("&").append("phone=").append(User.getPhone(getActivity()));
		url.append("&").append("contentId=").append(conntentId);
		url.append("&").append("bandwidth=").append("Media_Url_Source");
		url.append("&").append("clipId=").append(1);

		ULog.i(TAG, url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onSuccess(String result) {
				progDialog.dismiss();
				ULog.d(TAG, "onSuccess  --" + result);
				try {
					JSONObject json = new JSONObject(result);

					PlayerActivity.toPlay(getActivity(), json.getString("url"));

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

	/** 直播页面adapter */
	class LiveAdapter extends BaseAdapter {

		private List<HomeLivesItem> lives = null;
		private Context mContext;
		private int w = 0, h = 0;

		private AbsListView.LayoutParams lparams = null;

		public LiveAdapter(Context mContext, List<HomeLivesItem> lives) {
			this.mContext = mContext;
			this.lives = lives;
			h = w = Util.getWindowWidth(getActivity()) / 3 - 10;
			lparams = new AbsListView.LayoutParams(w, h);
		}

		public void setList(List<HomeLivesItem> lives) {
			this.lives = lives;
		}

		@Override
		public int getCount() {
			return (null == lives) ? 0 : lives.size();
		}

		@Override
		public Object getItem(int position) {
			return (null == lives) ? null : lives.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.imageview_adapter, null);
			}
			((MyImageView) convertView).setImage(lives.get(position).PHONE_MEDIA_POSTER_SMALL);
			convertView.setLayoutParams(lparams);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (User.isLogined(getActivity())) {
						toPlay(lives.get(position).id);
					}
					else {
						toLogin(lives.get(position).id);
					}
				}
			});

			return convertView;
		}

		/** 跳转到登陆页面,播放直播 */
		private void toLogin(String liveId) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			intent.putExtra(ComParams.INTENT_TODO, "TOPLAYR");
			intent.putExtra(ComParams.INTENT_MOVIEDETAIL_LIVEID, liveId);
			startActivity(intent);
		}
	}

}
