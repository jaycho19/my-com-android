package com.next.lottery;

import java.util.ArrayList;
import java.util.List;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.beans.SKUBean;
import com.next.lottery.beans.SKUEntity;
import com.next.lottery.dialog.ShoppingSelectSKUDialog;
import com.next.lottery.fragment.GoodsDetailInteractiveAndSelectParamsFragment;
import com.next.lottery.fragment.GoodsDetailSaleInfoFragment;
import com.next.lottery.fragment.GoosDetailBottomFragment;
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
	@ViewInject(R.id.app_top_title_iv_left)
	private TextView tvBack;
	
	
	@ViewInject(R.id.activity_goods_detail_layout)
	private LinearLayout contentLayout;
	private List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_detail_layout);
		ViewUtils.inject(this);
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		tvTitle.setText("宝贝详情");
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

					ULog.i("onclick");
					ShoppingSelectSKUDialog.show(GoodsDetailActivity.this,
							getTestSKUBean());
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

				// 商品销售详情
				GoodsDetailSaleInfoFragment fGoodsSaleInfo = new GoodsDetailSaleInfoFragment();
				fragmentTransaction.add(R.id.activity_goods_detail_layout,
						fGoodsSaleInfo);
				fragments.add(fGoodsSaleInfo);

				// 服务，选择颜色 分类尺码
				GoodsDetailInteractiveAndSelectParamsFragment fGoodsInteractAndParams = new GoodsDetailInteractiveAndSelectParamsFragment();
				fragmentTransaction.add(R.id.activity_goods_detail_layout,
						fGoodsInteractAndParams);
				fragments.add(fGoodsInteractAndParams);

				GoosDetailBottomFragment fGoodSBottomBar = new GoosDetailBottomFragment(
						onClickTypeListener);
				fragmentTransaction.add(R.id.activity_goods_detail_layout,
						fGoodSBottomBar);
				fragments.add(fGoodSBottomBar);

				fragmentTransaction.commit();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private SKUBean getTestSKUBean() {
		ArrayList<SKUEntity> all = new ArrayList<SKUEntity>();
		for (int i = 0; i < 2; i++) {
			SKUEntity skuEntity = new SKUEntity();
			skuEntity.setSkuName("颜色");
			ArrayList<String> al = new ArrayList<String>();
			for (int j = 0; j < 18; j++)
				al.add("红色" + j);
			skuEntity.setSkuTypesList(al);
			all.add(skuEntity);
		}
		SKUBean skuBean = new SKUBean();
		skuBean.setSkuList(all);
		return skuBean;
	}
	@OnClick(R.id.app_top_title_iv_left)
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			finish();
			break;

		default:
			break;
		}
	}

}