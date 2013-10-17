package com.dongfang.view.imagegallery;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

import com.dongfang.utils.ULog;
import com.dongfang.view.MyImageView;
import com.dongfang.yzsj.LoginActivity;
import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
import com.dongfang.yzsj.bean.HomeSliderItem;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;

/**
 * 首页一张 kv图 适配器
 * 
 * @author dongfang
 * 
 */
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
		// ULog.d( "destroyItem --> position = " + position);
		ULog.d( " object ? = null " + (null == object));
		if (null != object)
			container.removeView((View) object);

		// position = position % list.size();
		// ULog.d( "destroyItem     --> position = " + position);
		// if (null != container.getChildAt(position))
		// container.removeViewAt(position);

		// int newPosition = mPosition - 2;
		// newPosition = (newPosition < 0) ? list.size() + newPosition :
		// newPosition;
		// ULog.d( "destroyItem  --> newPosition = " + newPosition);
		// if (null != container.getChildAt(newPosition))
		// container.removeViewAt(newPosition);

	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// ULog.d( "instantiateItem --> position = " + position + "; mCount = " + mCount);

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
		// ULog.d( "instantiateItem --> position = " + position + "; mCount = " + mCount);

		HomeSliderItem kv = list.get(position);
		MyImageView fling_image = new MyImageView(context);
		fling_image.setImage(kv.getMEDIA_PIC_RECOM2(), true);
		fling_image.setLayoutParams(new LayoutParams(-1, -1));

		fling_image.setOnClickListener(new MyOnClickListener(kv.getChannelId(), kv.getId()));

		fling_image.setId(position);
		linearLayout.addView(fling_image);
		linearLayout.setFocusable(true);
		linearLayout.setClickable(true);
		container.addView(linearLayout);
		return linearLayout;
	}

	protected class MyOnClickListener implements OnClickListener {
		private String contentId;
		private String channelId;

		public MyOnClickListener(String channelId, String contentId) {
			this.contentId = contentId;
			this.channelId = channelId;
		}

		@Override
		public void onClick(View v) {
			ULog.d( v.toString());

			if (User.isLogined(context)) {
				new ToDetailAsyncTask(context, channelId, contentId).execute();
			}
			else {
				toLogin(channelId, contentId);
			}
		}

	}

	/** 跳转到登陆页面 */
	private void toLogin(String channelId, String contentId) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra(ComParams.INTENT_TODO, ToDetailAsyncTask.TAG);
		intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CHANNELID, channelId);
		intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID, contentId);
		((Activity) context).startActivity(intent);
	}
}