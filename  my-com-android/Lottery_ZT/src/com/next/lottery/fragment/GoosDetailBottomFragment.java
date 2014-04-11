package com.next.lottery.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.GoodsBean;
import com.next.lottery.beans.UserBean;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.nets.HttpActions;

@SuppressLint("ValidFragment")
public class GoosDetailBottomFragment extends BaseFragment {

	@SuppressLint("ValidFragment")
	private OnClickTypeListener	OnClickTypeListener;
	private ProgressDialog progDialog;


	@ViewInject(R.id.btn_buy_now)
	private TextView			btnBuyNow;
	@ViewInject(R.id.btn_add_shopping_cart)
	private TextView			btnAddShopCart;
	private GoodsBean goodsBean;

	public GoosDetailBottomFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_detail_bottom_bar, container, false);
		ViewUtils.inject(this, view);
		
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		btnBuyNow.setOnClickListener(new itemClick());
		btnAddShopCart.setOnClickListener(new itemClick());
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	class itemClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_buy_now:
				OnClickTypeListener.onClickType(new Bundle());
				break;
			case R.id.btn_add_shopping_cart:
				addShopCarts();
//				Toast.makeText(getActivity(), "添加成功", 3000).show();
				break;

			default:
				break;
			}
		}

	}

	public void addShopCarts() {
//		Toast.makeText(getActivity(), "添加成功", 3000).show();
	}

	public void setData(GoodsBean goodsBean, OnClickTypeListener onClickTypeListener2) {
			this.goodsBean = goodsBean;
			this.OnClickTypeListener = onClickTypeListener2;
	}

}
