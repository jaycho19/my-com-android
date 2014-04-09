package com.next.lottery.fragment;

import java.util.List;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.MainActivity;
import com.next.lottery.R;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.GoodsBean;
import com.next.lottery.beans.UserBean;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.utils.User;
import com.next.lottery.utils.Util;

/**
 * 宝贝详情页 价格 销售量fragment
 * 
 * @author gfan
 * 
 */
public class GoodsDetailSaleInfoFragment extends BaseFragment {
	protected static String		TAG	= GoodsDetailSaleInfoFragment.class.getSimpleName();

	private OnClickTypeListener	onClickTypeListener;
	private GoodsBean goodsBean;

	@ViewInject(R.id.fragment_goods_detail_title)
	private TextView			goodsTitle;
	@ViewInject(R.id.fragment_goods_detail_goods_new_price)
	private TextView			goodsNewPrice;
	@ViewInject(R.id.fragment_goods_detail_goods_pri_price)
	private TextView			goodsPriPrice;
	@ViewInject(R.id.fragment_goods_detail_goods_sale_num)
	private TextView			goodsSaleNum;
	@ViewInject(R.id.fragment_goods_detail_dofavour_tv)
	private TextView			dofavourTv;
	
	private ProgressDialog progDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_detail_sale_info, container, false);
		ViewUtils.inject(this, view);
		initView(view);
		return view;
	}

	private void initView(View view) {
		try {
		goodsPriPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 添加删除线
		goodsTitle.setText(goodsBean.getTitle());
		
		goodsNewPrice.setText(Util.fen2Yuan(goodsBean.getPrice()));
		goodsPriPrice.setText(Util.fen2Yuan(goodsBean.getPrice()));
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}

	public void setData(GoodsBean goodsBean, OnClickTypeListener onClickTypeListener) {
		this.goodsBean = goodsBean;
		this.onClickTypeListener = onClickTypeListener;
	}

	@OnClick(R.id.fragment_goods_detail_dofavour_tv)
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragment_goods_detail_dofavour_tv:
			doAddFavour();
			
			break;

		default:
			break;
		}
	}

	private void doAddFavour() {
		String url = HttpActions.AddShopCarts();
		ULog.d("doAddFavour url = " + url);
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