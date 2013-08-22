package com.dongfang.view.imagegallery;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.df.util.ULog;
import com.dongfang.yzsj.bean.HomeSliderItem;
import com.dongfang.yzsj.utils.Util;
import com.lidroid.xutils.BitmapUtils;

public class ImageGalleyAdapter1 extends PagerAdapter {
	public static final String TAG = ImageGalleyAdapter1.class.getSimpleName();
	protected Context context;
	protected List<HomeSliderItem> list;
	private int mCount;

	// private int mPosition;
	// private ImageGallery imageGallery;

	public ImageGalleyAdapter1(Context context, List<HomeSliderItem> list) {
		this.context = context;
		this.list = list;
		mCount = list.size();
	}

	/*
	 * public ImageAdapter1(Context context, List<RecommendData> list, ImageGallery imageGallery) { this.context =
	 * context; this.list = list; this.imageGallery = imageGallery; mCount = list.size(); }
	 */

	@Override
	public int getCount() {
		if (list.size() > 1) {
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

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
//		ULog.d(TAG, "destroyItem --> position = " + position);
		ULog.d(TAG, " object ? = null " + (null == object));
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

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
//		ULog.d(TAG, "instantiateItem --> position = " + position + "; mCount = " + mCount);

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
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		linearLayout.setPadding(1, 1, 1, 1);
		ULog.d(TAG, "instantiateItem --> position = " + position + "; mCount = " + mCount);
		ImageView fling_image = new ImageView(context);

		BitmapUtils.create(context).display(fling_image, list.get(position).PC_MEDIA_POSTER_HORIZONTAL_BIG);
		fling_image.setLayoutParams(new LayoutParams(-1, -1));

		fling_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		fling_image.setId(position);
		linearLayout.addView(fling_image);
		linearLayout.setFocusable(true);
		linearLayout.setClickable(true);
		container.addView(linearLayout);
		return linearLayout;
	}

	protected class MyOnClickListener implements OnClickListener {

		public MyOnClickListener(String str) {}

		@Override
		public void onClick(View v) {
			ULog.d(TAG, v.toString());

		}
	}
}