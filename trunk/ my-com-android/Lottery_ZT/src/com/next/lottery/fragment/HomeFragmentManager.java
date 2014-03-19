package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import com.dongfang.v4.app.BaseActivity;
import com.next.lottery.R;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.listener.OnPageScrolledListener;
import com.next.lottery.utils.ComParams;
import com.next.lottery.view.PullToRefreshView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.Toast;

public class HomeFragmentManager {
	public final String TAG = HomeFragmentManager.class.getSimpleName();
	private Context context;
	private int contentRes;
	private int fragmentRes;
	private PullToRefreshView pullToRefreshView;
	private List<android.support.v4.app.Fragment> fragments = new ArrayList<android.support.v4.app.Fragment>();
	private FragmentManager mFragmentManager;
	private int addCount;
	private boolean LoadingData = false;
	private int mPosition = -1;

	private int votetype = 0;// 投票类型
	private int mcommenttype = 0;// 评论类型

	public HomeFragmentManager(Context context, int contentRes,
			int fragmentRes, PullToRefreshView pullToRefreshView) {
		this.context = context;
		this.contentRes = contentRes;
		this.fragmentRes = fragmentRes;
		this.pullToRefreshView = pullToRefreshView;
		this.mFragmentManager = ((FragmentActivity) context)
				.getSupportFragmentManager();
		;
	}

	public void removeAllFragment() {
		FragmentManager fragmentManager = ((FragmentActivity) context)
				.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
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

	public void addFragments() {
		FragmentTransaction fragmentTransaction = mFragmentManager
				.beginTransaction();
		OnClickTypeListener onClickTypeListener = new OnClickTypeListener() {
			@Override
			public void onClickType(Bundle bundle) {
				// AreacodeFragmentUtil.dealWithClickType(context, bundle);
			}
		};

		// 测试数据，一次性添加所有fragment
		int[] addFragmentIds = { ComParams.AREA_CODE_HOME_FRAGMENT_KV,
				/*ComParams.AREA_CODE_HOME_FRAGMENT_RECOMMEND,
				ComParams.AREA_CODE_HOME_FRAGMENT_SEASON_PANIC_BUYING,
				ComParams.AREA_CODE_HOME_FRAGMENT_SAIL_CHAMPION,
				ComParams.AREA_CODE_HOME_FRAGMENT_NEW_PRODUCT,
				ComParams.AREA_CODE_HOME_FRAGMENT_BOTTOM_KV */};
		
		//KV图测试数据
		List<Integer>	list = new ArrayList<Integer>();
		list.add(R.drawable.icon_home_fragment_kv_1);
		list.add(R.drawable.icon_home_fragment_kv_1);
		list.add(R.drawable.icon_home_fragment_kv_1);
		list.add(R.drawable.icon_home_fragment_kv_1);
		

		for (int i = 0; i < addFragmentIds.length; i++) {
			switch (addFragmentIds[i]) {
			case ComParams.AREA_CODE_HOME_FRAGMENT_KV:
				HomeFragmentTopKVFragment f = new HomeFragmentTopKVFragment();
				f.setData(list, onClickTypeListener, new OnPageScrolledListener() {
					@Override
					public void OnPageScrolled() {
						/*if (pullToRefreshView != null) {
							pullToRefreshView.needPull = false;
						}*/
					}
				});
				fragmentTransaction.add(fragmentRes, f);
				fragments.add(f);
				
				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_RECOMMEND:
				
				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_SEASON_PANIC_BUYING:
				
				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_SAIL_CHAMPION:
				
				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_NEW_PRODUCT:
				
				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_BOTTOM_KV:
				
				break;

			default:
				break;
			}
			
			
			
		}
	

		HomeFragmentBottemFragment hbf = new HomeFragmentBottemFragment();
		fragmentTransaction.add(fragmentRes, hbf);
		fragments.add(hbf);
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
