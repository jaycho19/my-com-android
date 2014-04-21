package com.next.lottery.view;

import java.util.ArrayList;
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

import com.dongfang.utils.ULog;
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.R;
import com.next.lottery.beans.GoodsBean.KVImageBean;
import com.next.lottery.beans.HomeStaticBean.Data;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.listener.OnPageScrolledListener;
import com.next.lottery.view.adapter.ImageAdapter1;
import com.next.lottery.view.adapter.ImageAdapter2;

/**
 * 图片滑动
 * 
 * @author dongfang
 * 
 */
public class ImageGallery extends LinearLayout {
	public static final String		TAG					= ImageGallery.class.getSimpleName();

	/** 一次显示一张图片 */
	public static final int			IMAGE_VIEW_TYPE_1	= 1;
	/** 一次显示二张图片 */
	public static final int			IMAGE_VIEW_TYPE_2	= 2;
	/** 一次显示三张图片 */
	public static final int			IMAGE_VIEW_TYPE_3	= 3;

	private ViewPager				viewPager;
	private LinearLayout			ll_fling_desc_image;

	private Context					context;
	private List<Data>				listFromHome;
	private ArrayList<KVImageBean>	listFromDetail;
	private OnPageScrolledListener	listener;
	private OnClickTypeListener		onClickTypeListener;
	/** 需要显示的图片的类型 */
	private int						imageViewType		= -1;

	public ImageGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.listFromHome = null;
		init();
	}

	public ImageGallery(Context context, List<Data> list, int imageViewType) {
		super(context);
		this.imageViewType = imageViewType;
		this.context = context;
		this.listFromHome = list;

		init();
		setList();
	}

	public void setOnPageScrolledListener(OnPageScrolledListener listener) {
		this.listener = listener;
	}

	public void setOnClickTypeListener(OnClickTypeListener onClickTypeListener) {
		this.onClickTypeListener = onClickTypeListener;
	}

	public OnClickTypeListener getOnClickTypeListener() {
		return onClickTypeListener;
	}

	/** 初始化 */
	public void init() {
		View view = LayoutInflater.from(context).inflate(R.layout.image_gallery, null);
		viewPager = (ViewPager) view.findViewById(R.id.vp_main);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		viewPager.setLayoutParams(layoutParams);
		ll_fling_desc_image = (LinearLayout) view.findViewById(R.id.ll_fling_desc_image);
		addView(view);
		// viewPager.setCurrentItem(1);
	}

	public void setList(int imageViewType, ArrayList<Data> arrayList) {
		this.imageViewType = imageViewType;
		this.listFromHome = arrayList;
		setList();
	}

	public void setGoodsDetailList(int imageViewType, ArrayList<KVImageBean> arrayList) {
		this.imageViewType = imageViewType;
		this.listFromDetail = arrayList;
		setList();
	}

	public void setList() {
		if ((null == listFromDetail || null == listFromDetail.get(0))
				&& (null == listFromHome || null == listFromHome.get(0)))
			return;

		int num = -1;
		if (IMAGE_VIEW_TYPE_1 == imageViewType)
			num = 1;
		else if (IMAGE_VIEW_TYPE_2 == imageViewType)
			num = 2;
		else if (IMAGE_VIEW_TYPE_3 == imageViewType)
			num = 3;
		 int size=0;
		if (context instanceof GoodsDetailActivity)
			size = listFromDetail.size() / num;
		else
		    size = listFromHome.size() / num;

		if (0 < size) {
			ll_fling_desc_image.removeAllViews();
			if (1 == size) {
				ll_fling_desc_image.setVisibility(View.GONE);
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
						image.setBackgroundResource(R.drawable.icon_yuan_selected);
					}
					else {
						image.setBackgroundResource(R.drawable.icon_yuan);
					}
				}
			}

			if (IMAGE_VIEW_TYPE_1 == imageViewType) {
				if (context instanceof GoodsDetailActivity)
					viewPager.setAdapter(new ImageAdapter2(context, listFromDetail, onClickTypeListener));
				else
					viewPager.setAdapter(new ImageAdapter1(context, listFromHome, onClickTypeListener));
				viewPager.setCurrentItem(listFromHome.size() * 100);
			}

			viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					ULog.d("onPageSelected arg0 = " + arg0);
					changeDesc(arg0 % listFromHome.size());
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					if (listener != null) {
						listener.OnPageScrolled();
					}
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {}
			});
		}
	}

	/** 修改说明 */
	public void changeDesc(int position) {
		for (int i = 0, length = ll_fling_desc_image.getChildCount(); i < length; i++) {
			ll_fling_desc_image.getChildAt(i).setBackgroundResource(R.drawable.icon_yuan);
		}
		ll_fling_desc_image.getChildAt(position).setBackgroundResource(R.drawable.icon_yuan_selected);
		// tv_fling_desc.setText(list.get(position * imageViewType).getTitle());
	}

	public int getCurrentIndex() {
		return viewPager.getCurrentItem();
	}

}