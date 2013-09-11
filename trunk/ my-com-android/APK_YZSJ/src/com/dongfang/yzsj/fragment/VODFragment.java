package com.dongfang.yzsj.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.df.util.ULog;
import com.dongfang.utils.ACache;
import com.dongfang.utils.Util;
import com.dongfang.view.MyImageView;
import com.dongfang.yzsj.MovieListActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.Channel;
import com.dongfang.yzsj.bean.VODItem;
import com.dongfang.yzsj.params.ComParams;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 点播
 * 
 * @author dongfang
 * 
 */
public class VODFragment extends Fragment {

	public static final String TAG = "VODFragment";

	private GridView gridView;// 普通频道
	private GridView gridViewVIP;// 增值频道
	private VODAdapter vodAdapter;
	private VODAdapter vodAdapterVip;// 增值频道adapter
	private ArrayList<VODItem> listVODItem = null;
	private ArrayList<VODItem> listVODItemVIP = null; // vip频道
	private ArrayList<VODItem> listVODItemNOM = null; // 普通频道

	private com.dongfang.view.ProgressDialog progDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");
		View view = inflater.inflate(R.layout.fragment_vod, container, false);
		initView(view);

		if (null != savedInstanceState && savedInstanceState.containsKey(ComParams.INTENT_VODBEAN)) {
			listVODItem = savedInstanceState.getParcelableArrayList(ComParams.INTENT_VODBEAN);
		}

		return view;
	}

	private void initView(View view) {
		progDialog = com.dongfang.view.ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		gridView = (GridView) view.findViewById(R.id.gv_fragment_vod);
		gridViewVIP = (GridView) view.findViewById(R.id.gv_fragment_vod_vip);
		vodAdapter = new VODAdapter(getActivity(), null);
		gridView.setAdapter(vodAdapter);
		vodAdapterVip = new VODAdapter(getActivity(), null);
		gridViewVIP.setAdapter(vodAdapterVip);
	}

	@Override
	public void onResume() {
		super.onResume();

		// 非异常中断进入时，bean为空，判断是否有缓存
		if (null == listVODItem) {
			listVODItem = new com.google.gson.Gson().fromJson(
					ACache.get(getActivity()).getAsString(ComParams.INTENT_VODBEAN),
					new TypeToken<List<VODItem>>() {}.getType());
		}

		// bean非空初始化数据
		if (null != listVODItem) {
			setVODAdapterData();
			// ULog.d(TAG, "onResume --> " + bean.toString());
		}
		else {
			// bean为空，网络请求数据，需对网络进行判断
			new HttpUtils().send(HttpRequest.HttpMethod.GET, ComParams.HTTP_VOD, new RequestCallBack<String>() {
				@Override
				public void onLoading(long total, long current) {
					ULog.d(TAG, "total = " + total + "; current = " + current);
				}

				@Override
				public void onSuccess(String result) {
					ULog.d(TAG, "onSuccess  --" + result);

					listVODItem = new com.google.gson.Gson().fromJson(result,
							new TypeToken<List<VODItem>>() {}.getType());
					StringBuilder sb = new StringBuilder();
					for (int i = 0, length = listVODItem.size(); i < length; i++)
						sb.append("vod ").append(i).append(" --> ").append(listVODItem.get(i).toString());
					ULog.d(TAG, sb.toString());

					ACache.get(getActivity()).put(ComParams.INTENT_VODBEAN, result, 60 * 5);// 缓存数据

					setVODAdapterData();

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

	/** 通过 listVODItem 初始化 listVODItemVIP,再展现页面 */
	private void setVODAdapterData() {
		if (null == listVODItem)
			return;

		listVODItemVIP = null == listVODItemVIP ? new ArrayList<VODItem>() : listVODItemVIP;
		listVODItemVIP.clear();
		listVODItemNOM = null == listVODItemNOM ? new ArrayList<VODItem>() : listVODItemNOM;
		listVODItemNOM.clear();

		for (VODItem item : listVODItem) {
			if (item.isVip()) {
				listVODItemVIP.add(item);
			}
			else {
				listVODItemNOM.add(item);
			}
		}

		vodAdapter.setList(listVODItemNOM);
		vodAdapter.notifyDataSetChanged();
		vodAdapterVip.setList(listVODItemVIP);
		vodAdapterVip.notifyDataSetChanged();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		ULog.d(TAG, "listVODItem.size = " + listVODItem.size() + ", listVODItemVIP.size = " + listVODItemVIP.size());
		// if (null != listVODItemVIP && null != listVODItem) {
		// listVODItem.addAll(listVODItemVIP);
		// }
		if (null != listVODItem) {
			ULog.d(TAG, "listVODItem.size = " + listVODItem.size() + ", listVODItemVIP.size = " + listVODItemVIP.size());
			outState.putParcelableArrayList(ComParams.INTENT_VODBEAN, listVODItem);
		}
	}

	/** 直播页面adapter */
	class VODAdapter extends BaseAdapter {

		private List<VODItem> lives = null;
		private Context mContext;
		private int w = 0, h = 0;
		private AbsListView.LayoutParams lparams = null;

		public VODAdapter(Context mContext, List<VODItem> lives) {
			this.mContext = mContext;
			this.lives = lives;
			w = Util.getWindowWidth(getActivity()) / 3 - 10;
			h = w * 90 / 179;
			lparams = new AbsListView.LayoutParams(w, h);
		}

		public void setList(List<VODItem> lives) {
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
				convertView = LayoutInflater.from(mContext).inflate(R.layout.imageview_adapter, null);
			}
			convertView.setLayoutParams(lparams);
			((MyImageView) convertView).setImage(lives.get(position).getPoster());
			// BitmapUtils.create(mContext).display((ImageView) convertView,lives.get(position).getPoster() , w, h);

			convertView.setOnClickListener(new ClickListener(lives.get(position)));

			return convertView;
		}
	}

	/** 跳转到列表页 */
	private class ClickListener implements OnClickListener {

		private Channel channel = null;

		public ClickListener(VODItem voditem) {
			channel = new Channel();
			channel.setChannelId(voditem.getChannelId());
			channel.setId(voditem.getChannelId());
			channel.setName(voditem.getName());
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), MovieListActivity.class);
			intent.putExtra(ComParams.INTENT_MOVIELIST_CHANNEL, channel);
			getActivity().startActivity(intent);

		}
	}

}
