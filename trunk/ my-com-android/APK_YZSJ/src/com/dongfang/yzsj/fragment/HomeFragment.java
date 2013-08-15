package com.dongfang.yzsj.fragment;

import com.df.util.ULog;
import com.dongfang.view.ImageGallery;
import com.dongfang.yzsj.MainActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.HomeBean;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 首页
 * 
 * @author dongfang
 * 
 */
public class HomeFragment extends Fragment {
	public static final String TAG = "HomeFragment";

	private ImageGallery imageGallery;
	private TextView tvMarquee;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		initView(view);

		if (null != savedInstanceState) {
			ULog.d(TAG, savedInstanceState.toString());
		}
		else {
			ULog.d(TAG, "NULL");;
		}

		return view;
	}

	private void initView(View view) {
		tvMarquee = (TextView) view.findViewById(R.id.home_tv_marquee);
		imageGallery = (ImageGallery) view.findViewById(R.id.home_ig_slider);
		((MainActivity) getActivity()).getHomeBean();
		HomeBean bean = ((MainActivity) getActivity()).getHomeBean();
		
		
		tvMarquee.setText(bean.getMarquee());
		imageGallery.setList(ImageGallery.IMAGE_VIEW_TYPE_1, bean.getSlider());		
		
	}
}
