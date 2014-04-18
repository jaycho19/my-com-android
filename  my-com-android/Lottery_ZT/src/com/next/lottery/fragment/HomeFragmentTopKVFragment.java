package com.next.lottery.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.beans.HomeStaticBean;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.listener.OnPageScrolledListener;
import com.next.lottery.utils.Util;
import com.next.lottery.view.ImageGallery;

/**
 * KV图 ==> 焦点推荐区的Fragment
 * 
 * @author gfan
 * 
 */
public class HomeFragmentTopKVFragment extends BaseFragment {
	protected static String TAG = HomeFragmentTopKVFragment.class
			.getSimpleName();

	private OnClickTypeListener onClickTypeListener;
	private OnPageScrolledListener onPageScrolledListener;
	@ViewInject(R.id.fragment_home_kv_ig)
	private ImageGallery imageGallery;
	protected HomeStaticBean bean;
	private int heightWightRadio =0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_top_kv, container,
				false);
		initView(view);
		ViewUtils.inject(this, view);
		return view;
	}
	
	public void setHeightWightRadio(int radio){
		heightWightRadio = radio;
	}

	private void initView(View view) {
		try {
			imageGallery = (ImageGallery) view
					.findViewById(R.id.fragment_home_kv_ig);
			@SuppressWarnings("deprecation")
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					(int) (Util.getWindowWidth(getActivity()) * heightWightRadio / 480));
			imageGallery.setLayoutParams(layoutParams);
			imageGallery.setList(ImageGallery.IMAGE_VIEW_TYPE_1, bean.getData());
			imageGallery.setOnPageScrolledListener(onPageScrolledListener);
			imageGallery.setOnClickTypeListener(onClickTypeListener);
		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}

	public void setData(HomeStaticBean homeBean,
			OnClickTypeListener onClickTypeListener,
			OnPageScrolledListener onPageScrolledListener) {
		this.bean = homeBean;
		this.onClickTypeListener = onClickTypeListener;
		this.onPageScrolledListener = onPageScrolledListener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}