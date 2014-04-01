package com.next.lottery.fragment;

import java.util.ArrayList;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.R;
import com.next.lottery.beans.BaseGateWayInterfaceEntity;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.adapter.ShoppingCartsFragmentAdapter;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.view.TabPageIndicator;

/***
 * 购物车
 * 
 * @author gfan
 * 
 */
public class ShoppingCartNewFragment extends BaseFragment {

	private ArrayList<ShopCartsInfo>		shopCartslist	= new ArrayList<ShopCartsInfo>();
	private ProgressDialog					progDialog;
	private ShoppingCartsFragmentAdapter	fragmentAdapter;
	@ViewInject(R.id.fragment_live_main_viewPager)
	private ViewPager						mViewPager;
	@ViewInject(R.id.fragment_live_main_indicator)
	private TabPageIndicator				mIndicator;
	private View							view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.i("ShoppingCartNewFragment onCreateView");
//		if (view == null) {
		View view = inflater.inflate(R.layout.fragment_live_main, container, false);
			ViewUtils.inject(this, view);

			// 缓存的rootView需要判断是否已经被加过parent，
			// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
			progDialog = ProgressDialog.show(getActivity());
			progDialog.setCancelable(true);
			getShopCartList();
//		}
//		else {
//			ViewGroup parent = (ViewGroup) view.getParent();
//			if (parent != null) {
//				parent.removeView(view);
//			}
//		}
		return view;
	}

	public void setViewPager(ArrayList<ShopCartsInfo> shopCartslist) {
		try {
			fragmentAdapter = new ShoppingCartsFragmentAdapter(getChildFragmentManager(), shopCartslist);
			fragmentAdapter.notifyDataSetChanged();
			mViewPager.setAdapter(fragmentAdapter);
			mIndicator.setBackgroundResource(R.color.whitesmoke);
			mIndicator.setViewPager(mViewPager);
			mIndicator.notifyDataSetChanged();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getShopCartList() {
		String url = HttpActions.GetShopCartsList();
		ULog.i("addShopCarts url = " + url);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.i(responseInfo.result);

				BaseGateWayInterfaceEntity<ArrayList<ShopCartsInfo>> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseGateWayInterfaceEntity<ArrayList<ShopCartsInfo>>>() {}.getType());
				if (null != bean && bean.getCode() == 0) {

					shopCartslist = bean.getInfo();
					setViewPager(shopCartslist);
				}
				else {
					Toast.makeText(getActivity(), bean.getMsg(), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
			}
		});

	}

	@OnClick({})
	public void onClick(View v) {
		ULog.i("id = " + v.getId());
		switch (v.getId()) {
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ULog.i("onDestroy");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		ULog.i("onDestroyView");
	}
}
