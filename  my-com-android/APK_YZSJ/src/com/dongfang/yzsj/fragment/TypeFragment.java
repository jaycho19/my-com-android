package com.dongfang.yzsj.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.df.util.ULog;
import com.dongfang.view.PullToRefreshView;
import com.dongfang.yzsj.MovieListActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.HomeMovie;
import com.dongfang.yzsj.bean.TypeBean;
import com.dongfang.yzsj.fragment.adp.TypeAdp;
import com.dongfang.yzsj.params.ComParams;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 各种类型的更多页面
 * 
 * @author dongfang
 * 
 */
public class TypeFragment extends Fragment implements View.OnClickListener {
	public static final String TAG = TypeFragment.class.getSimpleName();

	private com.dongfang.view.ProgressDialog progDialog;
	private TextView btnBack; // 返回
	private TextView btnChangeType; // 切换频道
	private TextView tvTitle; // 类型

	private PopupWindow popuWindow; // 频道切换框

	/** 显示列表 */
	private List<HomeMovie> listData;
	private TypeAdp channelAdp;
	private ListView listView;
	private PullToRefreshView pulltoRefreshView;
	/** 当前列表显示的频道id */
	private String channelId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		progDialog = com.dongfang.view.ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		View view = inflater.inflate(R.layout.fragment_type, container, false);
		initPopuWindow(inflater);
		initView(view);
		return view;
	}

	/** 初始化选择框 */
	private void initPopuWindow(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.type_popw_change, null);
		popuWindow = new PopupWindow(view, -1, -2);
		// 展开特效
		popuWindow.setAnimationStyle(R.style.Animations_GrowFromTop);
		// when a touch even happens outside of the window
		// make the window go away
		popuWindow.setBackgroundDrawable(new ColorDrawable(0));
		popuWindow.setFocusable(true);
		popuWindow.setOutsideTouchable(true);
		popuWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					popuWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		int[] type = { R.id.type_tv_change_type_baishitong, R.id.type_tv_change_type_dianshiju,
				R.id.type_tv_change_type_dianying, R.id.type_tv_change_type_fengshang, R.id.type_tv_change_type_huashu,
				R.id.type_tv_change_type_jiaoyu, R.id.type_tv_change_type_jishi, R.id.type_tv_change_type_minglanmu,
				R.id.type_tv_change_type_tiyu, R.id.type_tv_change_type_xinwen, R.id.type_tv_change_type_yinyue,
				R.id.type_tv_change_type_youpeng, R.id.type_tv_change_type_yule, R.id.type_tv_change_type_zhuanti };
		for (int i = 0; i < type.length; i++)
			view.findViewById(type[i]).setOnClickListener(this);

	}

	private void initView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.type_tv_title);
		btnBack = (TextView) view.findViewById(R.id.type_tv_back);
		btnChangeType = (TextView) view.findViewById(R.id.type_tv_change_type);
		btnChangeType.setOnClickListener(this);
		btnBack.setOnClickListener(this);

		channelAdp = new TypeAdp(getActivity());
		listData = new ArrayList<HomeMovie>();
		channelAdp.setList(listData);
		listView = (ListView) view.findViewById(R.id.type_listview);
		listView.setAdapter(channelAdp);
		pulltoRefreshView = (PullToRefreshView) view.findViewById(R.id.type_PullToRefreshView);
	}

	@Override
	public void onStart() {
		super.onStart();
		String channelId = ((MovieListActivity) getActivity()).channel;
		ULog.d(TAG, "channelId = " + channelId);
		getMovies(channelId, 0, 10);
	}

	/** 获取网络数据 */
	private void getMovies(String channelId, int start, int limit) {

		start = start < 0 ? 0 : start;
		limit = limit < 10 ? 0 : limit;
		limit = limit > 30 ? 30 : limit;

		String url = ComParams.HTTP_CHANNEL + "channelId=" + channelId + "&start=" + start + "&limit=" + limit;

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current) {
				ULog.d(TAG, "RequestCallBack.onLoading total = " + total + "; current = " + current);
			}

			@Override
			public void onSuccess(String result) {
				ULog.d(TAG, "onSuccess  --" + result);
//				try {
//					result = new String(result.getBytes("UTF-8"), "GBK");
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//				ULog.d(TAG, "onSuccess  --" + result);
				
				progDialog.dismiss();
				TypeBean bean = new com.google.gson.Gson().fromJson(result, TypeBean.class);
				ULog.d(TAG, bean.toString());
				listData.addAll(bean.getListData());
				channelAdp.notifyDataSetChanged();

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
	public void onDestroy() {
		super.onDestroy();
		if (null != popuWindow && popuWindow.isShowing()) {
			popuWindow.dismiss();
		}
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Override
	public void onClick(View v) {
		ULog.w("TypeFragment", v.getId() + "");
		switch (v.getId()) {
		case R.id.type_tv_back:
			this.onDetach();
			break;
		case R.id.type_tv_change_type:
			if (null != popuWindow && !popuWindow.isShowing())
				popuWindow.showAsDropDown(v, 0, 5);
			else if (null != popuWindow) {
				popuWindow.dismiss();
			}
			break;
		case R.id.type_tv_change_type_baishitong:
			tvTitle.setText("百视通VIP");
			break;
		case R.id.type_tv_change_type_dianshiju:
			tvTitle.setText("电视剧");
			break;
		case R.id.type_tv_change_type_dianying:
			tvTitle.setText("电影");
			break;
		case R.id.type_tv_change_type_fengshang:
			tvTitle.setText("风尚");
			break;
		case R.id.type_tv_change_type_huashu:
			tvTitle.setText("华数VIP");
			break;
		case R.id.type_tv_change_type_jiaoyu:
			tvTitle.setText("教育");
			break;
		case R.id.type_tv_change_type_jishi:
			tvTitle.setText("纪实");
			break;
		case R.id.type_tv_change_type_minglanmu:
			tvTitle.setText("名栏目");
			break;
		case R.id.type_tv_change_type_tiyu:
			tvTitle.setText("体育");
			break;
		case R.id.type_tv_change_type_xinwen:
			tvTitle.setText("新闻");
			break;
		case R.id.type_tv_change_type_yinyue:
			tvTitle.setText("音乐");
			break;
		case R.id.type_tv_change_type_youpeng:
			tvTitle.setText("优朋VIP");
			break;
		case R.id.type_tv_change_type_yule:
			tvTitle.setText("娱乐");
			break;
		case R.id.type_tv_change_type_zhuanti:
			tvTitle.setText("专题");
			break;

		default:
			break;
		}
	}
}
