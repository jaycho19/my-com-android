package com.dongfang.view;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.df.util.ULog;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.utils.Util;

/**
 * 图片滑动
 * 
 * @author dongfang
 * 
 */
public class ImageGallery extends LinearLayout {
	public static final String	TAG					= ImageGallery.class.getSimpleName();

	/** 一次显示一张图片 */
	public static final int		IMAGE_VIEW_TYPE_1	= 1;
	/** 一次显示二张图片 */
	public static final int		IMAGE_VIEW_TYPE_2	= 2;
	/** 一次显示三张图片 */
	public static final int		IMAGE_VIEW_TYPE_3	= 3;

	private ViewPager			viewPager;
	private LinearLayout		ll_fling_desc_image;
	private TextView			tv_fling_desc;

	private List<Object>		list;

	private Context				context;
	/** 需要显示的图片的类型 */
	private int					imageViewType		= -1;

	public ImageGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public ImageGallery(Context context, List<Object> list, int imageViewType) {
		super(context);
		this.imageViewType = imageViewType;
		this.context = context;
		init();
		setList();
	}

	/** 初始化 */
	public void init() {
		View view = LayoutInflater.from(context).inflate(R.layout.image_gallery, null);
		viewPager = (ViewPager) view.findViewById(R.id.imageGallery_viewpage);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Util.getWindowWidth(context) * 290 / 480));
		viewPager.setLayoutParams(layoutParams);
		ll_fling_desc_image = (LinearLayout) view.findViewById(R.id.imageGallery_ll_fling_desc_image);
		tv_fling_desc = (TextView) view.findViewById(R.id.imageGallery_tv_fling_desc);
		addView(view);
		// viewPager.setCurrentItem(1);
	}

	public void setList(int imageViewType, List<Object> list) {
		this.imageViewType = imageViewType;
		setList();
	}

	public void setList() {
		if (null == list || null == list.get(0))
			return;

		int num = -1;
		if (IMAGE_VIEW_TYPE_1 == imageViewType)
			num = 1;
		else if (IMAGE_VIEW_TYPE_2 == imageViewType)
			num = 2;
		else if (IMAGE_VIEW_TYPE_3 == imageViewType)
			num = 3;

		final int size = list.size() / num;

		if (0 < size) {
			ll_fling_desc_image.removeAllViews();
			if (1 == size) {
				ll_fling_desc_image.setVisibility(View.GONE);
				// tv_fling_desc.setText(list.get(0).getTitle());
			}
			else if (1 < size) {
				ll_fling_desc_image.setVisibility(View.VISIBLE);
				for (int i = 0; i < size; i++) {
					// ULog.i(TAG, "--- " + list.get(i).toString());
					ImageView image = new ImageView(context);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
					params.setMargins(3, 0, 3, 0);
					ll_fling_desc_image.addView(image, params);
					if (i == 0) {
						image.setBackgroundResource(R.drawable.image_gralley_dot01);
						/* 用于显示文字 */
						// tv_fling_desc.setText(list.get(0).getTitle());
					}
					else {
						image.setBackgroundResource(R.drawable.image_gralley_dot02);
					}
				}
			}

			if (IMAGE_VIEW_TYPE_1 == imageViewType) {
				// list.add(list.get(list.size() -1));
				// list.add(0, list.get(0));
				/*
				 * if (context instanceof RecommendedActivity || context instanceof ComplexActivity) {
				 * viewPager.setAdapter(new ImageAdapter1(context, list, this)); } else {
				 */
				// viewPager.setAdapter(new ImageAdapter1(context, list));
				// }
				// viewPager.setCurrentItem(list.size() * 100);
			}
			// else if (IMAGE_VIEW_TYPE_2 == imageViewType) {
			// viewPager.setAdapter(new ImageAdapter2(context, list));
			// }
			// else if (IMAGE_VIEW_TYPE_3 == imageViewType) {
			// viewPager.setAdapter(new ImageAdapter3(context, list));
			// }

			viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					ULog.d(TAG, "onPageSelected arg0 = " + arg0);
					changeDesc(arg0 % list.size());
					// if (arg0 == 0) {
					// viewPager.setCurrentItem(list.size() - 1, false);
					// }
					// else

					// if (arg0 == list.size() - 1) {
					// viewPager.setCurrentItem(0, false);
					// }
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// if (listener != null) {
					// listener.OnPageScrolled();
					// }
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {}
			});
		}
	}

	/** 修改说明 */
	public void changeDesc(int position) {
		for (int i = 0, length = ll_fling_desc_image.getChildCount(); i < length; i++) {
			ll_fling_desc_image.getChildAt(i).setBackgroundResource(R.drawable.image_gralley_dot02);
		}
		ll_fling_desc_image.getChildAt(position).setBackgroundResource(R.drawable.image_gralley_dot01);
		// tv_fling_desc.setText(list.get(position * imageViewType).getTitle());
		// Util.flipStats(StatsDef.ID_FLIP_KV, position, null == list.get(position) ? "" :
		// list.get(position).getTitle());
	}

	public int getCurrentIndex() {
		return viewPager.getCurrentItem();
	}
}