package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.next.lottery.R;
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
	protected static String			TAG	= HomeFragmentTopKVFragment.class.getSimpleName();

	private OnClickTypeListener		onClickTypeListener;
	private OnPageScrolledListener	onPageScrolledListener;

	private ImageGallery			imageGallery;
	protected List<Integer>	list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_top_kv, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		try {
			imageGallery = (ImageGallery) view.findViewById(R.id.fragment_recommend_kv_ig);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT, (int) (Util.getWindowWidth(getActivity()) * 170 / 480));
			imageGallery.setLayoutParams(layoutParams);
			imageGallery.setList(ImageGallery.IMAGE_VIEW_TYPE_1, list);
			imageGallery.setOnPageScrolledListener(onPageScrolledListener);
			imageGallery.setOnClickTypeListener(onClickTypeListener);
		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}

	public void setData( List<Integer>	list, OnClickTypeListener onClickTypeListener,
			OnPageScrolledListener onPageScrolledListener) {
		this.list = list;
		this.onClickTypeListener = onClickTypeListener;
		this.onPageScrolledListener = onPageScrolledListener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}