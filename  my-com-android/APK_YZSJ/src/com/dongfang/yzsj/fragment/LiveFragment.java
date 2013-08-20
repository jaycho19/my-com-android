package com.dongfang.yzsj.fragment;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.df.util.ULog;
import com.dongfang.utils.ACache;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.HomeLivesItem;
import com.dongfang.yzsj.bean.LiveBean;
import com.dongfang.yzsj.params.ComParams;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
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

	private ProgressDialog progressDialog;

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
		progressDialog = new ProgressDialog(getActivity());
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

					progressDialog.dismiss();
				}

				@Override
				public void onStart() {
					ULog.i(TAG, "onStart");
					progressDialog.show();

				}

				@Override
				public void onFailure(Throwable error, String msg) {
					ULog.i(TAG, "onFailure");
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

	/** 直播页面adapter */
	class LiveAdapter extends BaseAdapter {

		private List<HomeLivesItem> lives = null;
		private Context mContext;

		public LiveAdapter(Context mContext, List<HomeLivesItem> lives) {
			this.mContext = mContext;
			this.lives = lives;
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
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new ImageView(mContext);
			}
			BitmapUtils.create(mContext).display((ImageView) convertView, lives.get(position).PHONE_MEDIA_POSTER_SMALL);
			return convertView;
		}

	}

}
