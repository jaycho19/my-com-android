package com.dongfang.yzsj.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.df.util.ULog;
import com.dongfang.utils.ACache;
import com.dongfang.view.CheckTextView;
import com.dongfang.view.PullToRefreshView;
import com.dongfang.view.PullToRefreshView.OnFooterRefreshListener;
import com.dongfang.view.PullToRefreshView.OnHeaderRefreshListener;
import com.dongfang.yzsj.MovieListActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.Channel;
import com.dongfang.yzsj.bean.Movie;
import com.dongfang.yzsj.bean.TypeBean;
import com.dongfang.yzsj.bean.VODItem;
import com.dongfang.yzsj.fragment.adp.TypeAdp;
import com.dongfang.yzsj.params.ComParams;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 各种类型的更多页面
 * 
 * @author dongfang
 * 
 * 
 *         页面各个频道和子频道的交互不是很清楚
 */
public class TypeFragment extends Fragment implements View.OnClickListener {
	public static final String TAG = TypeFragment.class.getSimpleName();
	/** 每次请求长度 */
	public static final int LIMIT = 10;
	/** 每次请求页数 */
	private int pageStart = 0;

	private com.dongfang.view.ProgressDialog progDialog;
	private TextView btnBack; // 返回
	private TextView btnChangeType; // 切换频道
	private TextView tvTitle; // 类型
	private TextView tvSort; // 类型

	private PopupWindow pinDaoPopuWindow; // 频道切换框
	private PopupWindow paiXuPopuWindow; // 排序切换框

	private LinearLayout llSubChannels; // 子频道
	private LayoutInflater inflater;

	private List<Movie> listData; // 显示列表
	private String listDataChannel; // 最近一次的频道信息
	private TypeAdp channelAdp;
	private ListView listView;
	private PullToRefreshView pulltoRefreshView;
	private Channel channel; // 当前列表显示的频道信息
	private int sortNumber = 1;
	private List<CheckTextView> sortList = new ArrayList<CheckTextView>();

	private int lastTotal;// 最近一次加载更多时返回的数据量

	private List<VODItem> listChannels = null; // 频道列表

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		progDialog = com.dongfang.view.ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		if (null != savedInstanceState && savedInstanceState.containsKey(ComParams.INTENT_SEARCH_CHANNELS)) {
			listChannels = savedInstanceState.getParcelableArrayList(ComParams.INTENT_SEARCH_CHANNELS);
		}

		View view = inflater.inflate(R.layout.fragment_type, container, false);
		initPinDaoPopuWindow();
		initPaiXuPopuWindow();
		initView(view);
		return view;
	}

	/** 初始化频道选择框 */
	private void initPinDaoPopuWindow() {
		if (null == listChannels || listChannels.size() == 0) {
			return;
		}

		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.type_popw_change, null);
		pinDaoPopuWindow = new PopupWindow(view, -1, -2);
		// 展开特效
		pinDaoPopuWindow.setAnimationStyle(R.style.Animations_GrowFromTop);
		// when a touch even happens outside of the window
		// make the window go away
		pinDaoPopuWindow.setBackgroundDrawable(new ColorDrawable(0));
		pinDaoPopuWindow.setFocusable(true);
		pinDaoPopuWindow.setOutsideTouchable(true);
		pinDaoPopuWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					pinDaoPopuWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-1, -2);
		llp.gravity = Gravity.CENTER;
		llp.setMargins(5, 5, 5, 5);

		LinearLayout.LayoutParams lpTV = new LayoutParams(-2, -2, 1);
		lpTV.setMargins(5, 5, 5, 5);

		int length = listChannels.size();
		for (int row = 0; row < 3; row++) {
			LinearLayout ll = new LinearLayout(getActivity());
			ll.setLayoutParams(llp);

			for (int i = 5 * row; i < Math.min(length, 5 * (row + 1)); i++) {
				TextView tv = (TextView) inflater.inflate(R.layout.fragment_type_pop_channel_textview, null);
				tv.setLayoutParams(lpTV);
				tv.setText(listChannels.get(i).getName());
				tv.setOnClickListener(new onChangeChannel(listChannels.get(i)));
				ll.addView(tv);
			}
			view.addView(ll);
		}
	}

	private void initPaiXuPopuWindow() {
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.type_popw_sort, null);
		paiXuPopuWindow = new PopupWindow(view, -2, -2);
		// 展开特效
		paiXuPopuWindow.setAnimationStyle(R.style.Animations_GrowFromTop);
		// when a touch even happens outside of the window
		// make the window go away
		paiXuPopuWindow.setBackgroundDrawable(new ColorDrawable(0));
		paiXuPopuWindow.setFocusable(true);
		paiXuPopuWindow.setOutsideTouchable(true);
		paiXuPopuWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					paiXuPopuWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		CheckTextView ctv1 = (CheckTextView) view.findViewById(R.id.type_tv_sort_week);
		CheckTextView ctv2 = (CheckTextView) view.findViewById(R.id.type_tv_sort_month);
		CheckTextView ctv3 = (CheckTextView) view.findViewById(R.id.type_tv_sort_all);
		CheckTextView ctv4 = (CheckTextView) view.findViewById(R.id.type_tv_sort_new);
		ctv1.setOnClickListener(new onSortChannel(1));
		ctv2.setOnClickListener(new onSortChannel(2));
		ctv3.setOnClickListener(new onSortChannel(3));
		ctv4.setOnClickListener(new onSortChannel(4));

		ctv1.setChecked(true);

		sortList.add(ctv1);
		sortList.add(ctv2);
		sortList.add(ctv3);
		sortList.add(ctv4);

	}

	private void initView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.type_tv_title);
		tvSort = (TextView) view.findViewById(R.id.type_tv_sort);
		tvSort.setOnClickListener(this);
		btnBack = (TextView) view.findViewById(R.id.type_tv_back);
		btnChangeType = (TextView) view.findViewById(R.id.type_tv_change_type);
		btnChangeType.setOnClickListener(this);
		btnBack.setOnClickListener(this);

		llSubChannels = (LinearLayout) view.findViewById(R.id.type_ll_subChannels);

		channelAdp = new TypeAdp(getActivity(), "0");
		listData = new ArrayList<Movie>();
		channelAdp.setList(listData);
		listView = (ListView) view.findViewById(R.id.type_listview);
		listView.setAdapter(channelAdp);
		pulltoRefreshView = (PullToRefreshView) view.findViewById(R.id.type_PullToRefreshView);
		pulltoRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				listData.clear();
				getMovies(channel.getChannelId(), 0, LIMIT);
			}
		});

		pulltoRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {

			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				getMovies(channel.getChannelId(), pageStart, LIMIT);
			}
		});

	}

	@Override
	public void onStart() {
		super.onStart();
		channel = ((MovieListActivity) getActivity()).channel;
		tvTitle.setText(channel.getName());

		ULog.d(TAG, "channel = " + channel.toString());
		getMovies(channel.getChannelId(), 0, LIMIT);
		// ------------- 获取切换频道数据 ---------------
		getListChannels();
	}

	public void getListChannels() {
		super.onResume();
		ULog.d(TAG, "onResume");

		// 非异常中断进入时，bean为空，判断是否有缓存
		if (null == listChannels) {
			listChannels = new com.google.gson.Gson().fromJson(
					ACache.get(getActivity()).getAsString(ComParams.INTENT_SEARCH_CHANNELS),
					new TypeToken<List<VODItem>>() {}.getType());
		}

		// bean非空初始化数据
		if (null != listChannels) {
			initPinDaoPopuWindow();
		}
		else {
			// bean为空，网络请求数据，需对网络进行判断
			ULog.d(TAG, ComParams.HTTP_VOD);
			new HttpUtils().send(HttpRequest.HttpMethod.GET, ComParams.HTTP_VOD, new RequestCallBack<String>() {
				@Override
				public void onLoading(long total, long current) {
					ULog.d(TAG, "total = " + total + "; current = " + current);
				}

				@Override
				public void onSuccess(String result) {
					ULog.d(TAG, "onSuccess  --" + result);

					listChannels = new com.google.gson.Gson().fromJson(result,
							new TypeToken<List<VODItem>>() {}.getType());
					StringBuilder sb = new StringBuilder();
					for (int i = 0, length = listChannels.size(); i < length; i++)
						sb.append("vod ").append(i).append(" --> ").append(listChannels.get(i).toString());
					ULog.d(TAG, sb.toString());

					ACache.get(getActivity()).put(ComParams.INTENT_SEARCH_CHANNELS, result, 60 * 5);// 缓存数据

					initPinDaoPopuWindow();

					progDialog.dismiss();
				}

				@Override
				public void onStart() {
					ULog.i(TAG, "onStart");
					progDialog.show();

				}

				@Override
				public void onFailure(HttpException error, String msg) {
					progDialog.dismiss();
					ULog.i(TAG, "onFailure");
				}
			});
		}
	}

	/** 获取网络数据 */
	private void getMovies(final String channelId, final int start, final int limit) {

		if (0 == start) {
			lastTotal = 0;
		}

		else if (start > 0 && limit > lastTotal) {
			Toast.makeText(getActivity(), "没有更多内容啦O(∩_∩)O", Toast.LENGTH_LONG).show();
			return;
		}

		// start = start < 0 ? 0 : start;
		// limit = limit < 10 ? 0 : limit;
		// limit = limit > 30 ? 30 : limit;

		StringBuilder sb = new StringBuilder(ComParams.HTTP_CHANNEL);
		sb.append("channelId=").append(channelId);
		sb.append("&").append("start=").append(start);
		sb.append("&").append("limit=").append(limit);
		sb.append("&").append("sortNum=").append(sortNumber);

		// String url = ComParams.HTTP_CHANNEL + "channelId=" + channelId + "&start=" + start + "&limit=" + limit;
		ULog.d(TAG, sb.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, sb.toString(), new RequestCallBack<String>() {
			@Override
			public void onSuccess(String result) {
				// ULog.d(TAG, "onSuccess  --" + result);
				progDialog.dismiss();
				pageStart = 1 + start;

				if (0 == start) {
					pulltoRefreshView.onHeaderRefreshComplete();
				}
				else {
					pulltoRefreshView.onFooterRefreshComplete();
				}

				TypeBean bean = new com.google.gson.Gson().fromJson(result, TypeBean.class);
				if (null == bean)
					return;

				// ULog.d(TAG, bean.toString());
				if (TextUtils.isEmpty(listDataChannel) || !channelId.equals(listDataChannel)) {
					listData.clear();
					listDataChannel = channelId;
				}
				lastTotal = bean.getListData().getObjs().size();

				listData.addAll(bean.getListData().getObjs());
				channelAdp.setChannelId(channelId);
				channelAdp.notifyDataSetChanged();
				ULog.d(TAG, "list length = " + listData.size());

				if (bean.getSubChannels().size() > 0 && null != llSubChannels && llSubChannels.getChildCount() == 0) {
					for (final Channel channel : bean.getSubChannels()) {
						CheckTextView textview = (CheckTextView) inflater.inflate(R.layout.fragment_type_subchannel,
								null);
						textview.setText(channel.getName());
						textview.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if (!v.isSelected()) {
									getListBySubChannel(v, channel);
								}
							}
						});
						llSubChannels.addView(textview);
					}
					// llSubChannels.getChildAt(0).setSelected(true);
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

				if (0 == start) {
					pulltoRefreshView.onHeaderRefreshComplete();
				}
				else {
					pulltoRefreshView.onFooterRefreshComplete();
				}
			}
		});
	}

	/** 点击子栏目进行内容修改 */
	private void getListBySubChannel(View v, Channel channel) {
		for (int i = 0, l = llSubChannels.getChildCount(); i < l; i++) {
			((CheckTextView) llSubChannels.getChildAt(i)).setChecked(false);
		}

		((CheckTextView) v).setChecked(true);

		// 修改当前channel的数值
		setChannel(channel);

		getMovies(channel.getChannelId(), 0, LIMIT);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (null != pinDaoPopuWindow && pinDaoPopuWindow.isShowing()) {
			pinDaoPopuWindow.dismiss();
		}
		if (null != paiXuPopuWindow && paiXuPopuWindow.isShowing()) {
			paiXuPopuWindow.dismiss();
		}
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void onClick(View v) {
		ULog.w("TypeFragment", v.getId() + "");
		switch (v.getId()) {
		case R.id.type_tv_back:
			getActivity().finish();
			break;
		case R.id.type_tv_change_type:
			if (null != pinDaoPopuWindow && !pinDaoPopuWindow.isShowing())
				pinDaoPopuWindow.showAsDropDown(v, 0, 5);
			else if (null != pinDaoPopuWindow) {
				pinDaoPopuWindow.dismiss();
			}
			break;
		case R.id.type_tv_sort:
			if (null != paiXuPopuWindow && !paiXuPopuWindow.isShowing())
				paiXuPopuWindow.showAsDropDown(v, 0, 5);
			else if (null != paiXuPopuWindow) {
				paiXuPopuWindow.dismiss();
			}
			break;
		default:
			break;
		}
	}

	private class onChangeChannel implements View.OnClickListener {
		private VODItem voditem;

		public onChangeChannel(VODItem voditem) {
			this.voditem = voditem;
		}

		@Override
		public void onClick(View v) {
			channel.setChannelId(voditem.getChannelId());
			channel.setId(voditem.getChannelId());
			channel.setName(voditem.getName());

			tvTitle.setText(channel.getName());

			pinDaoPopuWindow.dismiss();

			ULog.d(TAG, "channel = " + channel.toString());

			listData.clear();
			getMovies(channel.getChannelId(), 0, LIMIT);

		}
	}

	/** 排序获得数据 */
	private class onSortChannel implements View.OnClickListener {
		int sortNum = 1;

		public onSortChannel(int sortNum) {
			this.sortNum = sortNum;
		}

		@Override
		public void onClick(View v) {
			if (sortNumber != sortNum) {
				sortNumber = sortNum;

				for (CheckTextView ctv : sortList) {
					ctv.setChecked(false);
				}
				sortList.get(sortNum - 1).setChecked(true);

				paiXuPopuWindow.dismiss();

				getMovies(channel.getChannelId(), 0, LIMIT);
			}
		}
	}
}
