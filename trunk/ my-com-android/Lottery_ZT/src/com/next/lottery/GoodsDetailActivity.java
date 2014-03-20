package com.next.lottery;

import java.util.ArrayList;
import java.util.List;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.GoodsDetailSaleInfoFragment;
import com.next.lottery.fragment.HomeFragmentTopKVFragment;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.listener.OnPageScrolledListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 商品详情界面
 * 
 * @author gfan
 * 
 */

public class GoodsDetailActivity extends BaseActivity {
	@ViewInject(R.id.app_top_title_tv_centre)
	private TextView tvTitle;
	@ViewInject(R.id.activity_goods_detail_layout)
	private LinearLayout contentLayout;
	private List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_detail_layout);
		ViewUtils.inject(this);
		tvTitle.setText("宝贝详情");

		initData();

	}

	private void initData() {
		try {
			/* KV图测试数据 */
			List<Integer> KVlist = new ArrayList<Integer>();
			KVlist.add(R.drawable.icon_goods_detail_kv_test);
			KVlist.add(R.drawable.icon_goods_detail_kv_test);
			KVlist.add(R.drawable.icon_goods_detail_kv_test);
			KVlist.add(R.drawable.icon_goods_detail_kv_test);
			
			
			OnClickTypeListener onClickTypeListener = new OnClickTypeListener() {
				@Override
				public void onClickType(Bundle bundle) {
					// AreacodeFragmentUtil.dealWithClickType(context, bundle);
				}
			};

			if (contentLayout != null) {
				contentLayout.removeAllViews();
				FragmentManager fragmentManager1 = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction1 = fragmentManager1
						.beginTransaction();
				for (int i = 0; i < fragments.size(); i++) {
					fragmentTransaction1.remove(fragments.get(i));
				}
				fragmentTransaction1.commit();
				fragments.clear();
				fragmentManager1.popBackStack();

				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();

				// KV图
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
				fKV.setHeightWightRadio(300);
				fragmentTransaction.add(R.id.activity_goods_detail_layout, fKV);
				fragments.add(fKV);
				
				GoodsDetailSaleInfoFragment fGoodsSaleInfo = new GoodsDetailSaleInfoFragment();
				
				fragmentTransaction.add(R.id.activity_goods_detail_layout, fGoodsSaleInfo);
				fragments.add(fGoodsSaleInfo);
				
				

				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}