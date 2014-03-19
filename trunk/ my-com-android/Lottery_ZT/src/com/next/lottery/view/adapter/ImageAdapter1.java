package com.next.lottery.view.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dongfang.utils.ULog;

public class ImageAdapter1 extends PagerAdapter {
	public static final String		TAG	= ImageAdapter1.class.getSimpleName();
	protected Context				context;
	protected List<Integer>	list;
	private int						mCount;
	// private int mPosition;
	//private ImageGallery			imageGallery;

	public ImageAdapter1(Context context, List<Integer> list) {
		this.context = context;
		this.list = list;
		mCount = list.size();
	}

/*	public ImageAdapter1(Context context, List<RecommendData> list, ImageGallery imageGallery) {
		this.context = context;
		this.list = list;
		this.imageGallery = imageGallery;
		mCount = list.size();
	}*/

	@Override
	public int getCount() {
		if (list.size() > 1) {
			// return mCount + 1;
			return Integer.MAX_VALUE;
		}
		else {
			return mCount;
		}
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup,
	 * int, java.lang.Object)
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ULog.d("destroyItem --> position = " + position);
		ULog.d(" object ? = null " + (null == object));
		if (null != object)
			container.removeView((View) object);

		// position = position % list.size();
		// ULog.d(TAG, "destroyItem     --> position = " + position);
		// if (null != container.getChildAt(position))
		// container.removeViewAt(position);

		// int newPosition = mPosition - 2;
		// newPosition = (newPosition < 0) ? list.size() + newPosition :
		// newPosition;
		// ULog.d(TAG, "destroyItem  --> newPosition = " + newPosition);
		// if (null != container.getChildAt(newPosition))
		// container.removeViewAt(newPosition);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup
	 * , int)
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ULog.d("instantiateItem --> position = " + position + "; mCount = " + mCount);

		if (position >= list.size()) {
			position = position % list.size();
			mCount++;
		}
		// if (position < mPosition && mCount > (list.size() + 1)) {
		// // position = -position;
		// mCount--;
		// }

		// mPosition = position;

		if (null == list.get(position)) {
			list.remove(position);
			position = 0;
		}
		LinearLayout  linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		linearLayout.setPadding((int)0.5f, (int)0.5f, (int)0.5f,(int)0.5f);
		ULog.d("instantiateItem --> position = " + position + "; mCount = " + mCount);
		ImageView fling_image = new ImageView(context);
		// fling_image.setImage("http://img3.douban.com/view/photo/photo/public/p1784571177.jpg");
		fling_image.setImageResource(list.get(position));
		fling_image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		//fling_image.setBackgroundResource(R.drawable.bg_selector);
		/*if (imageGallery != null) {
			fling_image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					imageGallery.getOnClickTypeListener().onClickType(Util.setClickTypeData(list.get(v.getId())));
				}
			});

		}
		else {
			fling_image.setOnClickListener(new MyOnClickListener(""));
		}*/
		fling_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				AreacodeFragmentUtil.dealWithClickType(context, Util.setClickTypeData(list.get(v.getId())));
			}
		});
		fling_image.setId(position);
		linearLayout.addView(fling_image);
		//linearLayout.setBackgroundResource(R.drawable.bg_selector);
		linearLayout.setFocusable(true);
		linearLayout.setClickable(true);
		container.addView(linearLayout);
	//	container.addView(fling_image);
		return linearLayout;
	}

	protected class MyOnClickListener implements OnClickListener {

		public MyOnClickListener(String str) {}

		@Override
		public void onClick(View v) {
		}
	}
}