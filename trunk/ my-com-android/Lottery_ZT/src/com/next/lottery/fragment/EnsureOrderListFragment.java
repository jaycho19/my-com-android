package com.next.lottery.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.next.lottery.EnsureOrderListActivity;
import com.next.lottery.R;
import com.next.lottery.alipay.AlipayUtil;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.CalculateOrderListBean;
import com.next.lottery.beans.CalculateOrderListBean.Items;
import com.next.lottery.beans.OrderBean;
import com.next.lottery.beans.OrderNoBean;
import com.next.lottery.beans.SKUBean2;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.utils.Util;

@SuppressLint("ValidFragment")
public class EnsureOrderListFragment extends BaseFragment {

	/*
	 * @ViewInject(R.id.fragment_ensure_order_listview) private ListView
	 * listView;
	 */
	// @ViewInject(R.id.app_top_title_tv_centre)
	private TextView					tvTitle;
	// @ViewInject(R.id.app_top_title_iv_left)
	// private TextView tvBack;
	// @ViewInject(R.id.app_top_title_iv_rigth)
	// private TextView tvRight;
	@ViewInject(R.id.btn_buy_now)
	private TextView					tvBuyNow;
	@ViewInject(R.id.fragment_ensure_order_bottom_list_tv)
	private TextView					tvBottomList;
	@ViewInject(R.id.fragment_ensure_order_delivery_money_tv)
	private TextView					tvTraFee;
	@ViewInject(R.id.fragment_ensure_order_item_ll)
	private LinearLayout				itemll;

	private ArrayList<ShopCartsInfo>	orderlist	= new ArrayList<ShopCartsInfo>();
	private ProgressDialog				progDialog;
	private BaseEntity<CalculateOrderListBean> calcuBean;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ensure_order_list_layout, container, false);
		ViewUtils.inject(this, view);
		tvTitle = (TextView) view.findViewById(R.id.app_top_title_tv_centre);
		
		
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
		CalculateOrder(getActivity(),orderlist);
		return view;
	}

	
	private void refreshView(){
		tvBottomList.setText("共计"+calcuBean.getInfo().getItems().size()+"件商品,"+"￥"+(calcuBean.getInfo().getPrice()/100)+"元");
		tvTraFee.setText(calcuBean.getInfo().getTraFee().getTraFee()/100+"元");
		for (Items cartsInfo : calcuBean.getInfo().getItems()) {
			View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ensure_order_listview_item,
					null);
//			MyImageView img = (MyImageView) itemView.findViewById(R.id.fragment_shoppingcart_all_adp_item_iv);
			TextView tvTitle = (TextView) itemView.findViewById(R.id.fragment_ensure_orderlist_item_show_title);
			TextView tvPrice = (TextView) itemView.findViewById(R.id.fragment_ensure_orderlist_item_show_price);
			TextView tvCount = (TextView) itemView.findViewById(R.id.fragment_ensure_orderlist_item_show_number);
//			tvTitle.setText(cartsInfo.getItemName());
			tvPrice.setText(cartsInfo.getPrice()/100+"");
			tvCount.setText(cartsInfo.getCount()+"");
			itemll.addView(itemView);
		}
	}

	public void setOrderlist(ArrayList<ShopCartsInfo> orderlist) {
		this.orderlist = orderlist;
		ULog.i(orderlist.toString());
	}

	@OnClick({ R.id.app_top_title_iv_left, R.id.app_top_title_iv_rigth, R.id.btn_buy_now })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			((EnsureOrderListActivity) getActivity()).finish();
			break;
		case R.id.app_top_title_iv_rigth:
			((EnsureOrderListActivity) getActivity()).showRight();
			break;
		case R.id.btn_buy_now:
			CreatOrder(getActivity());
			break;

		default:
			break;
		}
	}

	/* 生成订单 */
	private  void CreatOrder(final Context context) {
		// ArrayList<SKUBean2> skubeanList = new ArrayList<SKUBean2>();
		// skubeanList.add(skuBean);
		String url = HttpActions.creatOrder(context, calcuBean);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d(responseInfo.result);

				BaseEntity<OrderNoBean> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseEntity<OrderNoBean>>() {}.getType());
				if (null != bean && bean.getCode() == 0) {
					ULog.i(bean.getInfo().getOrderNo());
					AlipayUtil.doPayment(context);
				}
				else {
					Toast.makeText(context, bean.getMsg(), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		});

	}
	
	protected  void CalculateOrder(Context context,final ArrayList<ShopCartsInfo> orderlist2) {
		String url = HttpActions.CalcuLateOrderList(context, orderlist2);
		ULog.d("CalculateOrder url = " + url);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d(responseInfo.result);

			     calcuBean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseEntity<CalculateOrderListBean>>() {}.getType());
				if (null != calcuBean && calcuBean.getCode() == 0) {

//					CreatOrder(orderlist2, bean);
					ULog.i("price-->" + calcuBean.getInfo().getPrice());
					refreshView();
					// Toast.makeText(context, bean.getInfo().getPrice(),
					// Toast.LENGTH_LONG).show();
				}
				else {
					// Toast.makeText(context, bean.getMsg(),
					// Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
				// Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		});

	}

}
