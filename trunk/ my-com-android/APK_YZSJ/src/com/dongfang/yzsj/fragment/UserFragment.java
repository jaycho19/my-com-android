package com.dongfang.yzsj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.df.util.ULog;
import com.dongfang.yzsj.R;

/**
 * 个人中心
 * 
 * @author dongfang
 * 
 */
public class UserFragment extends Fragment {
	public static final String TAG = "UserFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.d(TAG, "onCreateView(...)");
		View view = inflater.inflate(R.layout.fragment_user, container, false);
		// if (User.isLogined(getActivity())) {
		initTabhostItems(view, inflater);
		// }
		// else {
		// ((MainActivity) getActivity()).showLoginFragment();
		// }
		return view;
	}

	private void initTabhostItems(View view, LayoutInflater inflater) {
		FragmentTabHost fgtHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
		fgtHost.setup(getActivity(), getChildFragmentManager(), R.id.tabcontent);

		TextView tab1 = (TextView) inflater.inflate(R.layout.fragment_user_tab, null);
		TextView tab2 = (TextView) inflater.inflate(R.layout.fragment_user_tab, null);
		TextView tab3 = (TextView) inflater.inflate(R.layout.fragment_user_tab, null);
		tab1.setText("个人收藏");
		tab2.setText("我的订阅");
		tab3.setText("播放记录");

		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab3), HistoryFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), SubscibeFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab1), FavoriteFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), LoginFragment.class, null);

		final LinearLayout.LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
		lp.setMargins(10, 10, 10, 10);
		for (int i = 0, length = fgtHost.getTabWidget().getChildCount(); i < length; i++) {
			fgtHost.getTabWidget().getChildAt(i).setLayoutParams(lp);
		}

		// fgtHost.setCurrentTabByTag("2");

		// fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator("1"), LoginFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator("2"), LiveFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator("3"), VODFragment.class, null);
	}

	@Override
	public void onStart() {
		super.onStart();
		ULog.d(TAG, "onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();
		ULog.d(TAG, "onResume()");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
