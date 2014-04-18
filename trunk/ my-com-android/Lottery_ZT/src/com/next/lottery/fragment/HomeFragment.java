package com.next.lottery.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.MCaptureActivity;
import com.next.lottery.R;
import com.next.lottery.SearchAcitivity;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.GoodsBean;
import com.next.lottery.beans.HomeStaticBean;
import com.next.lottery.beans.UserBean;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.view.PullToRefreshView;
import com.next.lottery.view.VerticalScrollView;

public class HomeFragment extends BaseFragment {
	@ViewInject(R.id.app_top_title_iv_rigth)
	private ImageView					ivSearch;
	@ViewInject(R.id.app_top_title_iv_left)
	private ImageView					ivQR;
	@ViewInject(R.id.home_fragment_sv)
	private VerticalScrollView			mScrollView;
	@ViewInject(R.id.home_fragment_pull_to_refreshview)
	private PullToRefreshView			mPullToRefreshView;
	
	
	private HomeFragmentManager			mRecommendFragmentManager;
	private ProgressDialog				progDialog;
	private ArrayList<HomeStaticBean>	homeBean;

	private MyHandler					mHandler;
	public static final int				START_REFRESH		= 0;
	public static final int				REFRESH_SUCCESS		= 1;
	public static final int				REFRESH_FAIL		= 4;
	public static final int				CANCEL_PROGRESS		= 2;
	public static final int				GET_MORE			= 3;
	public static final int				RESET_FRAGMENT		= 5;
	public static final int				GET_DATA_FAILURE	= 6;
	public static final int				UN_NORMAL_STATE		= 7;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.i("HomeFragment oncreat");
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		ViewUtils.inject(this, view);
		initView();

		return view;
	}

	private void initView() {
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
		mHandler = new MyHandler(this);
		mRecommendFragmentManager = new HomeFragmentManager(getActivity(), R.id.home_fragment_layout,
				R.id.home_fragment_layout, mPullToRefreshView);

		mHandler.sendEmptyMessage(START_REFRESH);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	@OnClick({ R.id.app_top_title_iv_rigth, R.id.app_top_title_iv_left, R.id.fragment_home_img_btn })
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
			if (mScrollView != null) {
				mScrollView.scrollTo(0, 0);
			}
			break;
		default:
			break;
		}
	}

	private class MyHandler extends Handler {

		public MyHandler(HomeFragment homeFragment) {}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case START_REFRESH:
				ULog.i("start refresh");
				mPullToRefreshView.disablePullUpRefresh();
				mPullToRefreshView.disablePullDownRefresh();
				ULog.i("测试数据 直接加载 ");
				getDataFromInter();
				// setContent();
				break;
			case REFRESH_SUCCESS:
				setContent();
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
		mRecommendFragmentManager.addFragments(homeBean);

	};

	public void getDataFromInter() {
		String url = HttpActions.GetHomeStaticData(getActivity());
		ULog.d("getDataFromInter url = " + url);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					progDialog.dismiss();
					// BaseEntity<GoodsBean> bean = new
					// Gson().fromJson(responseInfo.result,
					// new TypeToken<BaseEntity<GoodsBean>>() {}.getType());
					homeBean = new Gson().fromJson(responseInfo.result,
							new TypeToken<ArrayList<HomeStaticBean>>() {}.getType());

					if (null != homeBean) {
						mHandler.sendEmptyMessage(REFRESH_SUCCESS);
						ULog.d(homeBean.toString());
					}
					else {
						// Toast.makeText(getActivity(), bean.getMsg(),
						// Toast.LENGTH_LONG).show();
					}
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
				Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
			}
		});

	}

	public void onDetach() {
		super.onDetach();

		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
