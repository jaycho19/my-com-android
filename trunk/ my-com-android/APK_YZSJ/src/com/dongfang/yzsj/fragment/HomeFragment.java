package com.dongfang.yzsj.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.df.util.ULog;
import com.dongfang.utils.Util;
import com.dongfang.view.ImageGallery;
import com.dongfang.view.MyImageView;
import com.dongfang.yzsj.LoginActivity;
import com.dongfang.yzsj.MainActivity;
import com.dongfang.yzsj.MovieListActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
import com.dongfang.yzsj.bean.HomeBean;
import com.dongfang.yzsj.bean.HomeChannelItem;
import com.dongfang.yzsj.bean.Movie;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.lidroid.xutils.BitmapUtils;

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
	private LinearLayout llHome;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		initView(inflater, view);

		if (null != savedInstanceState) {
			ULog.d(TAG, savedInstanceState.toString());
		}
		else {
			ULog.d(TAG, "NULL");;
		}

		return view;
	}

	// 初始化view
	private void initView(LayoutInflater inflater, View view) {
		llHome = (LinearLayout) view.findViewById(R.id.ll_fragment_home);

		tvMarquee = (TextView) view.findViewById(R.id.home_tv_marquee);
		imageGallery = (ImageGallery) view.findViewById(R.id.home_ig_slider);

		((MainActivity) getActivity()).getHomeBean();
		HomeBean bean = ((MainActivity) getActivity()).getHomeBean();

		if (null == bean) {
			return;
		}
		// kv图
		tvMarquee.setText((TextUtils.isEmpty(bean.getMarquee()) ? "" : bean.getMarquee()));
		if (null != bean.getSlider() && bean.getSlider().size() > 0)
			imageGallery.setList(ImageGallery.IMAGE_VIEW_TYPE_1, bean.getSlider());
		// 直播
		if (null != bean.getLives() && bean.getLives().size() > 0) {
			int w = Util.getWindowWidth(getActivity()) / 5 - 10;
			LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(w, w);
			lparam.setMargins(5, 5, 5, 5);

			LinearLayout llLievLine1 = (LinearLayout) view.findViewById(R.id.ll_fragment_home_live_line1);
			LinearLayout llLievLine2 = (LinearLayout) view.findViewById(R.id.ll_fragment_home_live_line2);
			int length = bean.getLives().size();
			for (int i = 0, l = Math.min(length, 5); i < l; i++) {
				MyImageView imageView = new MyImageView(getActivity());
				imageView.setLayoutParams(lparam);
				imageView.setImage(bean.getLives().get(i).PHONE_MEDIA_POSTER_SMALL);
				// BitmapUtils.create(getActivity()).display(imageView,
				// bean.getLives().get(i).PHONE_MEDIA_POSTER_SMALL); // ,w,
				// w);
				llLievLine1.addView(imageView);
			}

			if (Math.min(length, 10) > 5) {
				for (int i = 5, l = Math.min(length, 10); i < l; i++) {
					ImageView imageView = new ImageView(getActivity());
					imageView.setLayoutParams(lparam);
					BitmapUtils.create(getActivity()).display(imageView,
							bean.getLives().get(i).PHONE_MEDIA_POSTER_SMALL, w, w);
					llLievLine2.addView(imageView);
				}
			}
			// 更多
			view.findViewById(R.id.tv_fragment_home_live_more).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ULog.d(TAG, v.getId() + "");
					FragmentTabHost fgtHost = ((MainActivity) getActivity()).getFgtHost();
					fgtHost.setCurrentTabByTag("2");
				}
			});
		}

		// 频道
		if (null != bean.getChannelContents() && bean.getChannelContents().size() > 0) {
			LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(-1, 50);
			RelativeLayout.LayoutParams rparam = new RelativeLayout.LayoutParams(200, 50);
			for (final HomeChannelItem item : bean.getChannelContents()) {
				View viewItem = inflater.inflate(R.layout.fragment_home_item, null);
				viewItem.findViewById(R.id.rl_fragment_home_item).setLayoutParams(lparam);
				ImageView imageView = (ImageView) viewItem.findViewById(R.id.iv_fragment_home_item_channelName);
				imageView.setLayoutParams(rparam);
				BitmapUtils.create(getActivity()).display(imageView, item.getChannel().getPoster());

				viewItem.findViewById(R.id.iv_fragment_home_item_more).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), MovieListActivity.class);
						intent.putExtra(ComParams.INTENT_MOVIELIST_CHANNEL, item.getChannel());
						getActivity().startActivity(intent);
					}
				});

				LinearLayout llHomeItemContain = (LinearLayout) viewItem
						.findViewById(R.id.ll_fragment_home_item_contain);
				initViewHomeItem(llHomeItemContain, item);
				llHome.addView(viewItem, llHome.getChildCount() - 1);

			}
		}
	}

	// 每一个频道
	private void initViewHomeItem(LinearLayout linearLayout, final HomeChannelItem item) {
		if (null != item.getMovies() && item.getMovies().size() > 0) {
			int w = Util.getWindowWidth(getActivity()) / 3 - 10;
			LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(w, w * 456 / 330);
			lparam.setMargins(5, 5, 5, 5);

			for (final Movie movie : item.getMovies()) {
				MyImageView imageView = new MyImageView(getActivity());
				imageView.setLayoutParams(lparam);
				imageView.setImage(movie.getPC_MEDIA_POSTER_BIG());
				// BitmapUtils.create(getActivity()).display(imageView, movie.getPC_MEDIA_POSTER_BIG(), w, w * 456 /
				// 330);
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (User.isLogined(getActivity())) {
							new ToDetailAsyncTask(getActivity(), item.getChannel().getChannelId(), movie.getId())
									.execute();
						}
						else {
							toLogin(item.getChannel().getChannelId(), movie.getId());
						}
					}
				});

				linearLayout.addView(imageView);
			}
		}
	}

	/** 可以保持中端的数据 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	 /** 跳转到登陆页面 */
	private void toLogin(String channelId, String contentId) {
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		intent.putExtra(ComParams.INTENT_TODO, ToDetailAsyncTask.TAG);
		intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CHANNELID, channelId);
		intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID, contentId);
		startActivity(intent);
	}

}
