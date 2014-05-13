package com.dongfang.view.imagegallery;

import java.util.List;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageGalleyAdapter3 extends ImageGalleyAdapter1 {
	public static final String TAG = ImageGalleyAdapter3.class.getName();

	public ImageGalleyAdapter3(Context context, List list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telecom.video.view.ImageAdapter1#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount() / 3;
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
		for (int i = 0; i < 3; i++) {
			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(lp);
			imageView.setOnClickListener(new MyOnClickListener("0", "0"));
			// BitmapUtils.create(context).display(imageView,
			// "http://img3.douban.com/view/photo/albumicon/public/p1785901007.jpg");
			ll.addView(imageView, i);
		}
		container.addView(ll);

		return ll;
	}

}
