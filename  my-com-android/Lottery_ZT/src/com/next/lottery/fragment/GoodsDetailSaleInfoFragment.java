package com.next.lottery.fragment;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.dongfang.views.MyImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.beans.SizeBean;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.utils.Util;

/**
 * 宝贝详情页 价格 销售量fragment
 * 
 * @author gfan
 * 
 */
public class GoodsDetailSaleInfoFragment extends BaseFragment {
	protected static String TAG = GoodsDetailSaleInfoFragment.class
			.getSimpleName();

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_detail_sale_info,
				container, false);
		ViewUtils.inject(this, view);
		initView(view);
		return view;
	}

	private void initView(View view) {
		try {

		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}

	public void setData(List<String> list,
			OnClickTypeListener onClickTypeListener) {
		this.list = list;
		this.onClickTypeListener = onClickTypeListener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}