package com.dongfang.view.imagegallery;

import java.util.List;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.BitmapUtils;

public class ImageGalleyAdapter2 extends ImageGalleyAdapter1 {
	public static final String TAG = ImageGalleyAdapter2.class.getName();

	public ImageGalleyAdapter2(Context context, List list) {
		super(context, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telecom.video.view.ImageAdapter1#getCount()
	 */
	@Override
	public int getCount() {
		return super.getCount() / 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telecom.video.view.ImageAdapter1#instantiateItem(android.view.ViewGroup , int)
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LinearLayout ll = new LinearLayout(context);
		ll.setHorizontalGravity(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		for (int i = 0; i < 2; i++) {
			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(lp);
			imageView.setOnClickListener(new MyOnClickListener("0", "0"));
			BitmapUtils.create(context).display(imageView,
					"http://img3.douban.com/view/photo/albumicon/public/p1785901007.jpg");
			ll.addView(imageView, i);
		}
		container.addView(ll);
		return ll;
	}

}
