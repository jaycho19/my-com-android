package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import com.dongfang.v4.app.BaseActivity;
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.R;
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.listener.OnPageScrolledListener;
import com.next.lottery.utils.ComParams;
import com.next.lottery.view.PullToRefreshView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
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
				// 暂时跳入测试 详情界面
				context.startActivity(new Intent(context,
						GoodsDetailActivity.class));
			}
		};
		// 测试数据，一次性添加所有fragment
		int[] addFragmentIds = { ComParams.AREA_CODE_HOME_FRAGMENT_KV,
				ComParams.AREA_CODE_HOME_FRAGMENT_RECOMMEND,
				ComParams.AREA_CODE_HOME_FRAGMENT_SEASON_HOT_SALE,
				ComParams.AREA_CODE_HOME_FRAGMENT_SAIL_CHAMPION,
		
		 ComParams.AREA_CODE_HOME_FRAGMENT_NEW_PRODUCT,
		 ComParams.AREA_CODE_HOME_FRAGMENT_BOTTOM_KV
		 };

		// KV图测试数据
		List<Integer> KVlist = new ArrayList<Integer>();
		KVlist.add(R.drawable.icon_home_fragment_kv_1);
		KVlist.add(R.drawable.icon_home_fragment_kv_1);
		KVlist.add(R.drawable.icon_home_fragment_kv_1);
		KVlist.add(R.drawable.icon_home_fragment_kv_1);

		// 推荐测试数据
		List<String> Recommendlist = new ArrayList<String>();
		Recommendlist.add("限时抢购");
		Recommendlist.add("今日优惠");
		Recommendlist.add("猜你喜欢");

		// 销售冠军测试数据
		List<String> SaleChampionlist = new ArrayList<String>();
		SaleChampionlist.add("VeryModa2014年夏季新款废都元素精品凉鞋");
		SaleChampionlist.add("Jackjones2013年冬季新款帝都元素精品西裤");
		SaleChampionlist.add("Selected2012年秋季新款魔都元素精品上衣");
		SaleChampionlist.add("Only2011年春季新款霸都元素绝版拉风小秋裤");

		for (int i = 0; i < addFragmentIds.length; i++) {
			switch (addFragmentIds[i]) {
			case ComParams.AREA_CODE_HOME_FRAGMENT_KV:
				HomeFragmentTopKVFragment fKV = new HomeFragmentTopKVFragment();
				fKV.setData(KVlist, onClickTypeListener,
						new OnPageScrolledListener() {
							@Override
							public void OnPageScrolled() {
								/*
								 * if (pullToRefreshView != null) {
								 * pullToRefreshView.needPull = false; }
								 */
							}
						});
				fKV.setHeightWightRadio(160);
				fragmentTransaction.add(fragmentRes, fKV);
				fragments.add(fKV);

				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_RECOMMEND:
				HomeFragmentRecommendFragment fRecomend = new HomeFragmentRecommendFragment();
				fRecomend.setData(Recommendlist, onClickTypeListener);
				fragmentTransaction.add(fragmentRes, fRecomend);
				fragments.add(fRecomend);

				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_SEASON_HOT_SALE:

				HomeFrammentSeasonSaleFragment fHotSale = new HomeFrammentSeasonSaleFragment();
				fHotSale.setData(onClickTypeListener);
				fragmentTransaction.add(fragmentRes, fHotSale);
				fragments.add(fHotSale);
				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_SAIL_CHAMPION:
				HomeFragmentSaleChampionFragment fSaleChampion = new HomeFragmentSaleChampionFragment();
				fSaleChampion.setData(SaleChampionlist, onClickTypeListener);
				fragmentTransaction.add(fragmentRes, fSaleChampion);
				fragments.add(fSaleChampion);
				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_NEW_PRODUCT:
				HomeFragmentSaleChampionFragment fSaleChampion1 = new HomeFragmentSaleChampionFragment();
				fSaleChampion1.setData(SaleChampionlist, onClickTypeListener);
				fragmentTransaction.add(fragmentRes, fSaleChampion1);
				fragments.add(fSaleChampion1);
				break;
			case ComParams.AREA_CODE_HOME_FRAGMENT_BOTTOM_KV:
				HomeFragmentTopKVFragment fKV1 = new HomeFragmentTopKVFragment();
				fKV1.setData(KVlist, onClickTypeListener,
						new OnPageScrolledListener() {
							@Override
							public void OnPageScrolled() {
								/*
								 * if (pullToRefreshView != null) {
								 * pullToRefreshView.needPull = false; }
								 */
							}
						});
				fKV1.setHeightWightRadio(160);
				fragmentTransaction.add(fragmentRes, fKV1);
				fragments.add(fKV1);

				break;
			default:
				break;
			}

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
