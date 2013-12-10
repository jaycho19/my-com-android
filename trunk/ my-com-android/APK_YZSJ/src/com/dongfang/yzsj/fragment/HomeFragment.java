package com.dongfang.yzsj.fragment;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.RequestCallBack;
import com.dongfang.net.http.ResponseInfo;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.utils.Util;
import com.dongfang.view.ImageGallery;
import com.dongfang.view.MyImageView;
import com.dongfang.yzsj.LoginActivity;
import com.dongfang.yzsj.MainActivity;
import com.dongfang.yzsj.MovieListActivity;
import com.dongfang.yzsj.PlayerActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
import com.dongfang.yzsj.bean.Channel;
import com.dongfang.yzsj.bean.HomeBean;
import com.dongfang.yzsj.bean.HomeChannelItem;
import com.dongfang.yzsj.bean.HomeLivesItem;
import com.dongfang.yzsj.bean.Movie;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;

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
	private com.dongfang.view.ProgressDialog progDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		progDialog = com.dongfang.view.ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		initView(inflater, view);

		if (null != savedInstanceState) {
			ULog.d(savedInstanceState.toString());
		}
		else {
			ULog.d("NULL");;
		}

		return view;
	}

	// 初始化view
	private void initView(LayoutInflater inflater, View view) {
		llHome = (LinearLayout) view.findViewById(R.id.ll_fragment_home);
		/** 首页各栏目大小布局 */
		int rparam_w = Util.getWindowWidth(getActivity()) * 50 / 540;
		RelativeLayout.LayoutParams rparam = new RelativeLayout.LayoutParams(rparam_w * 4, rparam_w);

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
				final HomeLivesItem live = bean.getLives().get(i);
				MyImageView imageView = new MyImageView(getActivity());
				imageView.setLayoutParams(lparam);
				imageView.setImage(live.PHONE_MEDIA_POSTER_SMALL);
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (User.isLogined(getActivity())) {
							toPlay(live.id);
						}
						else {
							toLogin(live.id);
						}

					}
				});

				llLievLine1.addView(imageView);
			}

			if (Math.min(length, 10) > 5) {
				for (int i = 5, l = Math.min(length, 10); i < l; i++) {
					final HomeLivesItem live = bean.getLives().get(i);
					MyImageView imageView = new MyImageView(getActivity());
					imageView.setLayoutParams(lparam);
					imageView.setImage(live.PHONE_MEDIA_POSTER_SMALL);
					imageView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (User.isLogined(getActivity())) {
								toPlay(live.id);
							}
							else {
								toLogin(live.id);
							}
						}
					});
					llLievLine2.addView(imageView);
				}
			}

			view.findViewById(R.id.iv_fragment_home_live_title).setLayoutParams(rparam);;

			// 更多
			view.findViewById(R.id.tv_fragment_home_live_more).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ULog.d(v.getId() + "");
					FragmentTabHost fgtHost = ((MainActivity) getActivity()).getFgtHost();
					fgtHost.setCurrentTabByTag("2");
				}
			});
		}

		// VIP 数据都是写死
		View viewVIP = inflater.inflate(R.layout.fragment_home_vip_item, null);
		int w = Util.getWindowWidth(getActivity()) / 3 - 10;
		int h = w * 90 / 179;
		LinearLayout.LayoutParams viplparams = new LinearLayout.LayoutParams(w, h);
		viplparams.setMargins(5, 5, 5, 5);
		ImageView iv_hs = (ImageView) viewVIP.findViewById(R.id.fragment_home_iv_huashu); // 华数
		ImageView iv_yp = (ImageView) viewVIP.findViewById(R.id.fragment_home_iv_youpengyingyue);// 优朋
		ImageView iv_bst = (ImageView) viewVIP.findViewById(R.id.fragment_home_iv_baishitong);// 百事通
		iv_hs.setLayoutParams(viplparams);
		iv_yp.setLayoutParams(viplparams);
		iv_bst.setLayoutParams(viplparams);

		iv_hs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Channel channel = new Channel();
				channel.setChannelId("15884642");
				channel.setId("15884642");
				channel.setName("华数专区");
				Intent intent = new Intent(getActivity(), MovieListActivity.class);
				intent.putExtra(ComParams.INTENT_MOVIELIST_CHANNEL, channel);
				getActivity().startActivity(intent);
			}
		});
		iv_yp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Channel channel = new Channel();
				channel.setChannelId("15884644");
				channel.setId("15884644");
				channel.setName("优朋专区");
				Intent intent = new Intent(getActivity(), MovieListActivity.class);
				intent.putExtra(ComParams.INTENT_MOVIELIST_CHANNEL, channel);
				getActivity().startActivity(intent);
			}
		});
		iv_bst.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Channel channel = new Channel();
				channel.setChannelId("15884646");
				channel.setId("15884646");
				channel.setName("百事通专区");
				Intent intent = new Intent(getActivity(), MovieListActivity.class);
				intent.putExtra(ComParams.INTENT_MOVIELIST_CHANNEL, channel);
				getActivity().startActivity(intent);
			}
		});

		viewVIP.findViewById(R.id.fragment_home_iv_vip_item_channelName).setLayoutParams(rparam);

		// 更多
		viewVIP.findViewById(R.id.fragment_home_tv_vip_item_more).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ULog.d(v.getId() + "");
				FragmentTabHost fgtHost = ((MainActivity) getActivity()).getFgtHost();
				fgtHost.setCurrentTabByTag("3");
			}
		});

		llHome.addView(viewVIP, llHome.getChildCount() - 1);

		// 频道
		if (null != bean.getChannelContents() && bean.getChannelContents().size() > 0) {
			LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(-1, -2);
			for (final HomeChannelItem item : bean.getChannelContents()) {
				View viewItem = inflater.inflate(R.layout.fragment_home_item, null);
				viewItem.findViewById(R.id.rl_fragment_home_item).setLayoutParams(lparam);
				MyImageView imageView = (MyImageView) viewItem.findViewById(R.id.iv_fragment_home_item_channelName);
				imageView.setLayoutParams(rparam);
				imageView.setImage(item.getChannel().getPoster());
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
				initViewHomeItem(inflater, llHomeItemContain, item);
				llHome.addView(viewItem, llHome.getChildCount() - 1);

			}
		}
	}

	// 每一个频道 fragment_home_item_image
	private void initViewHomeItem(LayoutInflater inflater, LinearLayout linearLayout, final HomeChannelItem item) {
		// fragment_home_item_image
		if (null != item.getMovies() && item.getMovies().size() > 0) {
			int w = Util.getWindowWidth(getActivity()) / 3 - 10;
			LinearLayout.LayoutParams lParam = new LinearLayout.LayoutParams(w, -2);
			lParam.setMargins(5, 5, 5, 5);
			LinearLayout.LayoutParams mivParam = new LinearLayout.LayoutParams(w, w * 456 / 330);

			for (final Movie movie : item.getMovies()) {
				View view = inflater.inflate(R.layout.fragment_home_item_image, null);
				view.setLayoutParams(lParam);
				MyImageView imageView = (MyImageView) view.findViewById(R.id.fragment_home_iv_item_myimage);
				imageView.setLayoutParams(mivParam);
				imageView.setImage(movie.getPC_MEDIA_POSTER_BIG());
				((TextView) view.findViewById(R.id.fragment_home_tv_item_myimage)).setText(movie.getMEDIA_NAME());
				view.setOnClickListener(new OnClickListener() {
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

				linearLayout.addView(view);
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

	/** 跳转到登陆页面,播放直播 */
	private void toLogin(String liveId) {
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		intent.putExtra(ComParams.INTENT_TODO, "TOPLAYR");
		intent.putExtra(ComParams.INTENT_MOVIEDETAIL_LIVEID, liveId);
		intent.putExtra(ComParams.INTENT_MOVIEDETAIL_TYPE, ComParams.MOVIE_TYPE_Live);
		startActivity(intent);
	}

	/**
	 * 获取播放地址，进入播放页面
	 * 
	 * @param conntentId
	 *            内容id
	 * @param band
	 *            码流类型
	 * @param clipId
	 *            第几集
	 */
	private void toPlay(final String conntentId) {
		StringBuilder url = new StringBuilder(ComParams.HTTP_PLAYURL);
		url.append("token=").append(User.getToken(getActivity()));
		url.append("&").append("phone=").append(User.getPhone(getActivity()));
		url.append("&").append("contentId=").append(conntentId);
		url.append("&").append("bandwidth=").append("Media_Url_Source");
		url.append("&").append("clipId=").append(1);

		ULog.i(url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d("onSuccess  --" + responseInfo.result);
				try {
					JSONObject json = new JSONObject(responseInfo.result);
					Bundle data = new Bundle();
					data.putString(ComParams.INTENT_MOVIEDETAIL_CONNENTID, conntentId);
					data.putInt(ComParams.INTENT_MOVIEDETAIL_CLIPID, 1);
					data.putInt(ComParams.INTENT_MOVIEDETAIL_TYPE, ComParams.MOVIE_TYPE_Live);
					PlayerActivity.toPlay(getActivity(), json.getString("url"), data);
					// Intent intent = new Intent(Intent.ACTION_VIEW);
					// String type = "video/*";
					// Uri uri = Uri.parse(json.getString("url"));
					// intent.setDataAndType(uri, type);
					// intent.setComponent(new
					// ComponentName("com.android.gallery3d","com.android.gallery3d.MovieActivity"));
					// startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onStart() {
				ULog.i("RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.i("RequestCallBack.onFailure");
				progDialog.dismiss();
			}
		});
	}

}
