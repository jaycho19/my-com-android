package com.dongfang.dicos.view;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.dongfang.dicos.R;

public class FlingAdapter extends BaseAdapter {
	private static String	TAG	= "FlingAdapter";
	private List<String>	list;
	private Context			context;

	public FlingAdapter(Context context, List<String> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "getView() position = " + position);

		return new GalleryViewItem(context, position, list);

	}

	private class GalleryViewItem extends TableLayout {

		public GalleryViewItem(Context context) {
			super(context);
		}

		public GalleryViewItem(Context context, int position, List<String> list) {
			super(context);
			Log.d(TAG, "GalleryViewItem() position = " + position);
			this.setOrientation(LinearLayout.VERTICAL);

			this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.FILL_PARENT));

			MyImageView fling_image = new MyImageView(context);
			fling_image.setImage(list.get(position));

//			switch (position) {
//			case 0:
//				fling_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.image0));
//				break;
//			case 1:
//				fling_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.image1));
//				break;
//			case 2:
//				fling_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.image2));
//				break;
//			case 3:
//				fling_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.image3));
//				break;
//			case 4:
//				fling_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.image4));
//				break;
//			case 5:
//				fling_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.image5));
//				break;
//			case 6:
//				fling_image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.image6));
//				break;
//			}

			this.addView(fling_image, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.FILL_PARENT));

		}
	}

}
