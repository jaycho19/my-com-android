package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import com.dongfang.v4.app.BaseActivity;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.view.PullToRefreshView;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.Toast;


public class HomeFragmentManager {
	public final String								TAG			= HomeFragmentManager.class.getSimpleName();
	private BaseActivity							context;
	private int										contentRes;
	private int										fragmentRes;
	private PullToRefreshView						pullToRefreshView;
	private List<android.support.v4.app.Fragment>	fragments	= new ArrayList<android.support.v4.app.Fragment>();
	private FragmentManager							mFragmentManager;
	private int										addCount;
	private boolean									LoadingData	= false;
	private int										mPosition	= -1;
	
	private int                                     votetype = 0;//投票类型
	private int                                     mcommenttype = 0;//评论类型

	public HomeFragmentManager(BaseActivity context, int contentRes, int fragmentRes,
			PullToRefreshView pullToRefreshView) {
		this.context = context;
		this.contentRes = contentRes;
		this.fragmentRes = fragmentRes;
		this.pullToRefreshView = pullToRefreshView;
		this.mFragmentManager = context.getSupportFragmentManager();;
	}


	public void removeAllFragment() {
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		for (int i = 0; i < fragments.size(); i++) {
			fragmentTransaction.remove(fragments.get(i));
		}
		fragments.clear();
		for (int i = 0; i < addCount; i++) {
			mFragmentManager.popBackStack();
		}
		addCount = 0;
		mPosition = -1;
		fragmentTransaction.commitAllowingStateLoss();
	}

	public void addFragments(int currentPage, int totalPage, List<Bundle> data) {
		FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
		addCount++;

		OnClickTypeListener onClickTypeListener = new OnClickTypeListener() {
			@Override
			public void onClickType(Bundle bundle) {
//				AreacodeFragmentUtil.dealWithClickType(context, bundle);
			}
		};

		if (currentPage == totalPage) {
			HomeFragmentBottemFragment hbf = new HomeFragmentBottemFragment();
			fragmentTransaction.add(fragmentRes, hbf);
			fragments.add(hbf);
		}
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commitAllowingStateLoss();
	}

	public int getFragmentCount() {
		if (mFragmentManager != null) {
			return mFragmentManager.getBackStackEntryCount();
		}
		return 0;
	}
}
