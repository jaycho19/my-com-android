package com.dongfang.dicos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dongfang.dicos.CurrentSeasonDetail;
import com.dongfang.dicos.view.MyImageView;

public class ImgAdapter extends BaseAdapter {
	public static final String	tag	= "ImgAdapter";

	private String[]			aImgUrl;
	private Context				context;

	public ImgAdapter(Context context, String[] aImgUrl) {
		this.context = context;
		this.aImgUrl = aImgUrl;
	}

	@Override
	public int getCount() {
		return aImgUrl.length;
	}

	@Override
	public Object getItem(int position) {
		return aImgUrl[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final MyImageView myImgView = new MyImageView(context);

		// if (null == convertView) {
		// myImgView = new MyImageView(context);
		// convertView.setTag(myImgView);
		//
		// } else {
		// myImgView = (MyImageView) convertView.getTag();
		// }

		myImgView.setImage(aImgUrl[position]);

		myImgView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (myImgView.isImgExsits()) {
					Intent intent = new Intent(context, CurrentSeasonDetail.class);
					intent.putExtra("imgUrl", aImgUrl[position]);
					context.startActivity(intent);
				} else {
					myImgView.refreshDrawableState();
				}
			}
		});

		return myImgView;
	}
}
