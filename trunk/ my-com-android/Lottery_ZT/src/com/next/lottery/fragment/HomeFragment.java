package com.next.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.MCaptureActivity;
import com.next.lottery.R;
import com.next.lottery.SearchAcitivity;
import com.next.lottery.view.PullToRefreshView;
import com.next.lottery.view.VerticalScrollView;

public class HomeFragment extends BaseFragment {
	@ViewInject(R.id.app_top_title_iv_rigth)
	private ImageView ivSearch;

	@ViewInject(R.id.app_top_title_iv_left)
	private ImageView ivQR;
	@ViewInject(R.id.home_fragment_sv)
	private VerticalScrollView mScrollView;
	@ViewInject(R.id.home_fragment_pull_to_refreshview)
	private  PullToRefreshView mPullToRefreshView;
	private HomeFragmentManager mRecommendFragmentManager;

	private MyHandler mHandler;
	public static final int START_REFRESH = 0;
	public static final int REFRESH_SUCCESS = 1;
	public static final int REFRESH_FAIL = 4;
	public static final int CANCEL_PROGRESS = 2;
	public static final int GET_MORE = 3;
	public static final int RESET_FRAGMENT = 5;
	public static final int GET_DATA_FAILURE = 6;
	public static final int UN_NORMAL_STATE = 7;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		ViewUtils.inject(this, view);
		initView();

		return view;
	}

	private void initView() {
		mHandler = new MyHandler(this);
		mRecommendFragmentManager = new HomeFragmentManager(getActivity(),
				R.id.home_fragment_layout, R.id.home_fragment_layout,
				mPullToRefreshView);
		
			mHandler.sendEmptyMessage(START_REFRESH);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	@OnClick({ R.id.app_top_title_iv_rigth, R.id.app_top_title_iv_left,R.id.fragment_home_img_btn })
	public void onClick(View v) {
		ULog.d("v.id = " + v.getId());
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			Intent intent = new Intent();
			intent.setClass(getActivity(), MCaptureActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, 1);
			break;
		case R.id.app_top_title_iv_rigth:
			
			startActivity(new Intent(getActivity(), SearchAcitivity.class));
			break;
		case R.id.fragment_home_img_btn:
			if (mScrollView!=null) {
				mScrollView.scrollTo(0, 0);
			}
			break;
		default:
			break;
		}
	}

	private  class MyHandler extends Handler {

		public MyHandler(HomeFragment homeFragment) {
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case START_REFRESH:
				ULog.i("start refresh");
				mPullToRefreshView.disablePullUpRefresh();
				mPullToRefreshView.disablePullDownRefresh();
				ULog.i("测试数据 直接加载 ");
				setContent();
				break;
			case REFRESH_SUCCESS:
				break;
			case CANCEL_PROGRESS:
				break;
			case GET_MORE:
				break;
			case GET_DATA_FAILURE:
				break;
			case REFRESH_FAIL:
				break;
			case UN_NORMAL_STATE:
				break;
			}
		}
	}

	public void setContent() {
		
		mRecommendFragmentManager.addFragments();
		
	};

}
