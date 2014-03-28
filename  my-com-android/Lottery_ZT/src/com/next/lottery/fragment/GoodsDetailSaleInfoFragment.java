package com.next.lottery.fragment;

import java.util.List;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.listener.OnClickTypeListener;

/**
 * 宝贝详情页 价格 销售量fragment
 * 
 * @author gfan
 * 
 */
public class GoodsDetailSaleInfoFragment extends BaseFragment {
	protected static String TAG = GoodsDetailSaleInfoFragment.class.getSimpleName();

	private OnClickTypeListener onClickTypeListener;
	private List<String> list;

	@ViewInject(R.id.fragment_goods_detail_title)
	private TextView goodsTitle;
	@ViewInject(R.id.fragment_goods_detail_goods_new_price)
	private TextView goodsNewPrice;
	@ViewInject(R.id.fragment_goods_detail_goods_pri_price)
	private TextView goodsPriPrice;
	@ViewInject(R.id.fragment_goods_detail_goods_sale_num)
	private TextView goodsSaleNum;
	@ViewInject(R.id.fragment_goods_detail_dofavour_tv)
	private TextView dofavourTv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_detail_sale_info, container, false);
		ViewUtils.inject(this, view);
		initView(view);
		return view;
	}

	private void initView(View view) {
		goodsPriPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 添加删除线

		try {

		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}

	public void setData(List<String> list, OnClickTypeListener onClickTypeListener) {
		this.list = list;
		this.onClickTypeListener = onClickTypeListener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}