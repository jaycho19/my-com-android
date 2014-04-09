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

	public GoosDetailBottomFragment(OnClickTypeListener onClickTypeListener) {
		this.OnClickTypeListener = onClickTypeListener;
	}

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
		String url = HttpActions.AddShopCarts();
		ULog.d("addShopCarts url = " + url);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d(responseInfo.result);
				
				BaseEntity bean = new Gson().fromJson(responseInfo.result, BaseEntity.class);
				if (null != bean && bean.getCode() == 0) {
					
					Toast.makeText(getActivity(), "添加成功!", Toast.LENGTH_LONG).show();
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

}
