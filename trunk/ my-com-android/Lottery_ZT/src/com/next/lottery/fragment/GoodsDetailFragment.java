package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.alipay.AlipayUtil;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.GoodsBean;
import com.next.lottery.beans.SKUBean;
import com.next.lottery.beans.SKUBean2;
import com.next.lottery.beans.SKUEntity;
import com.next.lottery.beans.SKUItem;
import com.next.lottery.beans.SkuList;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.dialog.ShoppingSelectSKUDialog;
import com.next.lottery.fragment.GoodsDetailInteractiveAndSelectParamsFragment;
import com.next.lottery.fragment.GoodsDetailSaleInfoFragment;
import com.next.lottery.fragment.GoosDetailBottomFragment;
import com.next.lottery.fragment.HomeFragmentTopKVFragment;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.listener.OnPageScrolledListener;
import com.next.lottery.listener.OnSkuResultListener;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.params.ComParams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.next.lottery.R;
import com.next.lottery.GoodsDetailActivity;

/**
 * 商品详情界面
 * 
 * @author gfan
 * 
 */

@SuppressLint("ValidFragment")
public class GoodsDetailFragment extends BaseFragment {
	private TextView		tvTitle;
	private Context			context;
	private ProgressDialog	progDialog;

	@ViewInject(R.id.activity_goods_detail_layout)
	private LinearLayout	contentLayout;
	private List<Fragment>	fragments	= new ArrayList<Fragment>();
	private GoodsBean		goodsBean;
	private DbUtils			dbUtils;

	public GoodsDetailFragment(GoodsDetailActivity goodsDetailActivity) {
		// TODO Auto-generated constructor stub
		this.context = goodsDetailActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_goods_detail_layout, container, false);
		ViewUtils.inject(this, view);
		this.dbUtils = DbUtils.create(context, context.getPackageName());
		initView(view);
		getDataFromInter();
		return view;
	}

	/* 通过接口获取详情数据 */
	private void getDataFromInter() {
		String[] fl = {};
		String url = HttpActions.GetGoodsDetaiBean(getActivity(), fl);
		ULog.d("GetGoodsDetaiBean url = " + url);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				// ULog.d(responseInfo.result);
				BaseEntity<GoodsBean> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseEntity<GoodsBean>>() {}.getType());
				ULog.d(bean.toString());
				if (null != bean && bean.getCode() == 0) {
					goodsBean = bean.getInfo();
					Collections.reverse(goodsBean.getSkuList());
					try {
						dbUtils.saveOrUpdateAll(goodsBean.getSku());
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					initData();
				}
				else {
					Toast.makeText(getActivity(), bean.getMsg(), Toast.LENGTH_LONG).show();
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

	/**
	 * 自己添加的 sku数据 并存入到数据库当中
	 */
	protected void getSkuTest() {

		try {
			dbUtils.dropTable(SKUBean2.class);
			// dbUtils.dropTable(SkuList.class);
			ArrayList<SKUBean2> skuBeanList = new ArrayList<SKUBean2>();
			for (int i = 0; i < 4; i++) {
				SKUBean2 skubean = new SKUBean2();
				skubean.setCostPrice(0);
				skubean.setPrice(149000);
				skubean.setItemId(9);
				skubean.setId(i);
				skubean.setMarketPrice(0);
				skubean.setStatus(0);
				skubean.setStockNum(i);

				if (i == 0) {
					skubean.setSkuAttr("1627207:28335;20509:28381");
					skubean.setSkuAttrname("1627207:28335:颜色分类:绿色;20509:28381:尺码:XXS");
					skuBeanList.add(skubean);

				}
				else if (i == 1) {
					skubean.setSkuAttr("1627207:3232480;20509:28381");
					skubean.setSkuAttrname("1627207:3232480:颜色分类:粉红色;20509:28313:尺码:XS");
					skuBeanList.add(skubean);
				}
				else if (i == 2) {
					skubean.setSkuAttr("1627207:28341;20509:28313");
					skubean.setSkuAttrname("1627207:28341:颜色分类:黑色;20509:28313:尺码:XS");
					skuBeanList.add(skubean);
				}
				else if (i == 3) {
					skubean.setSkuAttr("1627207:28341;20509:28313");
					skubean.setSkuAttrname("1627207:28341:颜色分类:红色;20509:28313:尺码:XS");
					skuBeanList.add(skubean);
				}
				else {
					skubean.setSkuAttr("1627207:28338;20509:28381");
					skubean.setSkuAttrname("1627207:28338:颜色分类:蓝色;20509:28381:尺码:XXS");
					skuBeanList.add(skubean);
				}

			}
			dbUtils.saveAll(skuBeanList);

		} catch (DbException e) {
			e.printStackTrace();
		}

	}

	private void initView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.app_top_title_tv_centre);
		tvTitle.setText("宝贝详情");
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
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
					ShoppingSelectSKUDialog.show2(context, goodsBean.getSkuList(), onSkuResultListener);
				}
			};

			if (contentLayout != null) {
				contentLayout.removeAllViews();
				FragmentManager fragmentManager1 = getChildFragmentManager();
				FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
				for (int i = 0; i < fragments.size(); i++) {
					fragmentTransaction1.remove(fragments.get(i));
				}
				fragmentTransaction1.commit();
				fragments.clear();
				fragmentManager1.popBackStack();

				FragmentManager fragmentManager = getChildFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

				// KV图
				HomeFragmentTopKVFragment fKV = new HomeFragmentTopKVFragment();
				fKV.setData(KVlist, onClickTypeListener, new OnPageScrolledListener() {
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
				fragmentTransaction.add(R.id.activity_goods_detail_layout, fGoodsSaleInfo);
				fGoodsSaleInfo.setData(goodsBean, onClickTypeListener);
				fragments.add(fGoodsSaleInfo);

				// 服务，选择颜色 分类尺码
				GoodsDetailInteractiveAndSelectParamsFragment fGoodsInteractAndParams = new GoodsDetailInteractiveAndSelectParamsFragment();
				fragmentTransaction.add(R.id.activity_goods_detail_layout, fGoodsInteractAndParams);
				fGoodsInteractAndParams.setData(onClickTypeListener);
				fragments.add(fGoodsInteractAndParams);

				GoosDetailBottomFragment fGoodSBottomBar = new GoosDetailBottomFragment(onClickTypeListener);
				fragmentTransaction.add(R.id.activity_goods_detail_layout, fGoodSBottomBar);
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
			ArrayList<SKUItem> al = new ArrayList<SKUItem>();
			for (int j = 0; j < 18; j++)
				al.add(new SKUItem("红色" + j));
			skuEntity.setSkuTypesList(al);
			all.add(skuEntity);
		}
		SKUBean skuBean = new SKUBean();
		skuBean.setSkuList(all);
		return skuBean;
	}

	@OnClick({ R.id.app_top_title_iv_left, R.id.app_top_title_iv_rigth })
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			((GoodsDetailActivity) context).finish();
			break;
		case R.id.app_top_title_iv_rigth:
			((GoodsDetailActivity) context).showRight();
			break;

		default:
			break;
		}
	}

	OnSkuResultListener	onSkuResultListener	= new OnSkuResultListener() {

												@Override
												public void onSkuResult(ArrayList<SkuList> beanResult) {

												}
											};
}