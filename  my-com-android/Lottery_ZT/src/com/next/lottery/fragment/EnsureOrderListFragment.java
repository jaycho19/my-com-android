package com.next.lottery.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.dongfang.views.MyImageView;
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
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.R;
import com.next.lottery.alipay.AlipayConfig;
import com.next.lottery.alipay.AlipayUtil;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.adapter.EnsureOrderListViewAdapter;
import com.next.lottery.fragment.adapter.ShoppingCartAllAdapter;
import com.next.lottery.nets.HttpActions;

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
	@ViewInject(R.id.fragment_ensure_order_item_ll)
	private LinearLayout				itemll;

	private ArrayList<ShopCartsInfo>	orderlist	= new ArrayList<ShopCartsInfo>();
	private ProgressDialog				progDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ensure_order_list_layout, container, false);
		ViewUtils.inject(this, view);
		tvTitle = (TextView) view.findViewById(R.id.app_top_title_tv_centre);
		initView();
		return view;
	}

	private void initView() {
		tvTitle.setText("确定订单");

		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		for (int i = 0; i < orderlist.size(); i++) {
			View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ensure_order_listview_item,
					null);
//			MyImageView img = (MyImageView) itemView.findViewById(R.id.fragment_shoppingcart_all_adp_item_iv);
			TextView tvNum = (TextView) itemView.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_number);
			itemll.addView(itemView);
		}

		// for (int i = 0; i < 2; i++) {
		// ShopCartsInfo item = new ShopCartsInfo();
		// item.setPrice("" + 1000);
		// orderlist.add(item);
		// }

		/*
		 * EnsureOrderListViewAdapter allAdapter = new
		 * EnsureOrderListViewAdapter(getActivity(), orderlist);
		 * listView.setAdapter(allAdapter);
		 */
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
			creatOrder();
			break;

		default:
			break;
		}
	}

	/* 生成订单 */
	private void creatOrder() {
		String url = HttpActions.creatOrder(getActivity());
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

				BaseEntity<String> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseEntity<String>>() {}.getType());
				if (null != bean && bean.getCode() == 0) {

					AlipayUtil.doPayment(getActivity());
					Toast.makeText(getActivity(), bean.getInfo(), Toast.LENGTH_LONG).show();
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

}
