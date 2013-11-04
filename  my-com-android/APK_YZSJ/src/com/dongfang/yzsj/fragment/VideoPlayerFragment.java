package com.dongfang.yzsj.fragment;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.mediaplayer.Mediaplayer;
import com.dongfang.mediaplayer.Mediaplayer.OnCurrentPositionUpdateListener;
import com.dongfang.mediaplayer.Mediaplayer.OnLoadingListener;
import com.dongfang.mediaplayer.Mediaplayer.OnMPClickListener;
import com.dongfang.mediaplayer.Mediaplayer.OnMPDoubleClickListener;
import com.dongfang.mediaplayer.Mediaplayer.OnSurfaceCreatedListener;
import com.dongfang.mediaplayer.Mediaplayer.OnSwitchListener;
import com.dongfang.mediaplayer.Mediaplayer.VIDEOSIZESTATE;
import com.dongfang.mediaplayer.VideoView;
import com.dongfang.mediaplayer.constants.Constants;
import com.dongfang.mediaplayer.windows.BasePopupWindow;
import com.dongfang.mediaplayer.windows.BasePopupWindow.LandPopUpWinWorkingListener;
import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.RequestCallBack;
import com.dongfang.net.http.ResponseInfo;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.yzsj.PlayerActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;

/**
 * 视频播放界面Fragment
 * 
 * @author yanghua
 * 
 */
@SuppressLint("ValidFragment")
public class VideoPlayerFragment extends Fragment implements OnSurfaceCreatedListener, OnMPClickListener,
		OnMPDoubleClickListener {
	private final String TAG = VideoPlayerFragment.class.getSimpleName();

	private Context context;
	private FrameLayout fragview;
	private BasePopupWindow basePop;

	private Mediaplayer mediaPlayer;
	// private static VideoView mVideoView;

	private LinearLayout loadingBar;
	private AnimationDrawable loadingAnim;

	// private int mSaveMediaPosition = 0;
	/** 是否播放器被挂起状�? */
	private boolean isOnMediaSaveState = false;

	private boolean hasFocus;
	private boolean resumeFocusNot;

	private boolean mediaIsPrepared;

	private boolean mIsLandscape;
	private int mPortScreenHeight = Constants.SCREEN_HEIGHT_PORTRAIT;
	private float mPortScreenHeightPer = (float) 0.5;

	public static int mPortScreenPaddingTopPx = 0;

	private final int hidePopupwindow = 0;
	private final int showMediaLoding = 1;
	private final int hideMediaLoding = 2;

	/** 几秒钟之后popupwin自动消失 */
	private int popupwindowDissmissDelay = 5;
	/** 控制缓冲�?popupwin 是否自动弹出的标�? */
	private boolean mFromUserClick;

	public static final int SHOW_VOICE_POPUPWINDOW_ADJUST_VOLUME = 100;
	public static final int ADJUST_LOWER = 101;
	public static final int ADJUST_RAISE = 102;

	// private static Bundle bundle;
	private static String mUri;
	private VPFragmentOnClickListener mExternalOnClickListener;
	private VPFragmentPopupWinAutoDissmissOrShowListener mExternalPopupWinAutoDissmissOrShowListener;

	/** 让播放器做的事情 */
	public static enum MEDIA_TODO {
	PLAY, PAUSE, STOP;
	}

	public static VideoPlayerFragment newInstance(String uri) {
		VideoPlayerFragment vf = new VideoPlayerFragment();
		mUri = uri;
		return vf;
	}

	// @Override
	// public void setArguments(Bundle args) {
	// super.setArguments(args);
	//
	// this.bundle = args;
	// ULog.i( "--> setArguments bundle = "+bundle.toString());
	// }

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		ULog.i("--> onAttach");
		context = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		ULog.i("--> onCreate");
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ULog.i("--> onViewCreated");

		// 缓冲�?..
		// loadingStart(-1);

		// IntentFilter filter = new IntentFilter(
		// "android.media.VOLUME_CHANGED_ACTION");
		// getActivity().registerReceiver(voice_changed_receiver, filter);
	}

	@Override
	public void onStart() {
		super.onStart();
		ULog.i("--> onStart");
	}

	@Override
	public void onSurfaceViewCreated() {
		ULog.d("VideoPlayerFragment onSurfaceViewCreated()");
		mediaPlayer.playNewVideo(mUri);
	}

	@Override
	public void onResume() {
		super.onResume();
		ULog.i("--> VideoPlayerFragment onResume()");
		// if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
		if (hasFocus == false) {
			resumeFocusNot = true;
			return;
		}
		else {
			resumeFocusNot = false;
		}
		// }
		if (isOnMediaSaveState == true) {
			if (mediaPlayer.isSurfaceViewCreated() == false) {
				initMediaPlayerVideoView();
			}
			else {
				onSurfaceViewCreated();
			}
			isOnMediaSaveState = false;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		ULog.i("--> onPause");
		addHistory(mediaPlayer.getCurrentPosition() / 1000);

		mediaPlayer.releaseMediaPlayer();
		isOnMediaSaveState = true;
	}

	@Override
	public void onStop() {
		super.onStop();
		ULog.i("--> onStop");
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		ULog.d("--> VideoPlayerFragment onWindowFocusChanged()");
		this.hasFocus = hasFocus;
		if (!hasFocus) {
			dissmissAllPopupWin();
		}
		// 为了修复2.3�?2.2版本，锁屏点亮屏�?未开锁之后会直接调用onResume�? // 修复�?��播放器界面没有焦点时，就�?��播放视频的BUG
		if (resumeFocusNot == true && hasFocus && isOnMediaSaveState == true) {
			onResume();
			return;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			boolean isPlaying = mediaPlayer.isPlaying();
			if (!hasFocus && isPlaying) {
				mediaPlayer.pause();
			}
			else if (hasFocus && !isPlaying) {
				mediaPlayer.start();
			}
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ULog.i("--> onDestroy");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ULog.i("--> onActivityCreated");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ULog.i("--> onDestroyView");

		// getActivity().unregisterReceiver(voice_changed_receiver);
		cancelHideAllPopupWindDelay();
		if (basePop != null) {
			basePop.dissmissAll();
		}

	}

	@Override
	public void onDetach() {
		super.onDetach();
		ULog.i("--> onDetach");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.i("--> onCreateView");
		fragview = (FrameLayout) inflater.inflate(R.layout.fragment_videoview_player, container, false);

		// initVideoParams();
		initView();
		initMediaPlayer();
		setupData();
		return fragview;
	}

	// private void initVideoParams() {
	//
	// ULog.i( "mPlayData.playType = " + mPlayData.getPlayType() + "contentId--> " + mPlayData.getContentId()
	// + "nplayUrl = " + mPlayData.getPlayUrlNowSelect() + "bundle = " + bundle.toString());
	// }

	/** 初始化view */
	public void initView() {
		mediaPlayer = Mediaplayer.getInstance();
		initMediaPlayerVideoView();
		loadingBar = (LinearLayout) fragview.findViewById(R.id.video_loading_bar);
		ImageView imageView = (ImageView) loadingBar.findViewById(R.id.iv_loading);
		loadingAnim = (AnimationDrawable) imageView.getBackground();
		if (mIsLandscape) {
			initLandScapePopupWin();
		}
		else {}

	}

	private void initMediaPlayerVideoView() {

		// mediaPlayer.setOnTouchListener(this);
		mediaPlayer.setOnClickListener(this);
		mediaPlayer.setOnMPDoubleClickListener(this);
		mediaPlayer.setOnSurfaceCreatedListener(this);
		// mVideoView.setSurfaceViewCreatedListener(this);

		mediaPlayer.setContentView((VideoView) fragview.findViewById(R.id.videoview));
		setScreenOrientation(mIsLandscape);
	}

	public void initLandScapePopupWin() {

		basePop = BasePopupWindow.getInstance(context);
		basePop.initBasePopupWindow(context);
		basePop.setLandPopUpWinWorkingListener(new LandPopUpWinWorkingListener() {

			@Override
			public void onWorking() {
				hideAllPopupWinDelay();
			}
		});
	}

	private void destroyLandScapePopupWin() {
		if (basePop != null) {
			basePop.dissmissAll();
		}
	}

	private void setupData() {
		getWindowStatusBarHeight();
	}

	/**
	 * 获得屏幕状态栏的高度
	 */
	private void getWindowStatusBarHeight() {
		Rect frame = new Rect();
		this.getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		if (frame.top == 0) {
			Class<?> c = null;
			Object obj = null;
			Field field = null;
			int x = 0, sbar = 0;
			try {
				c = Class.forName("com.android.internal.R$dimen");
				obj = c.newInstance();
				field = c.getField("status_bar_height");
				x = Integer.parseInt(field.get(obj).toString());
				sbar = getResources().getDimensionPixelSize(x);
			} catch (Exception e1) {
				// Log.e("get status bar height fail");
				e1.printStackTrace();
			}
			Constants.STATUSBARHEIGHT = sbar;
		}
		else {
			Constants.STATUSBARHEIGHT = frame.top;
		}

		ULog.i("Constants.STATUSBARHEIGHT = " + Constants.STATUSBARHEIGHT);

	}

	/** 初始化MediaPlayer控制 */
	@SuppressLint("NewApi")
	private void initMediaPlayer() {

		// 暂停媒体播放
		context.sendBroadcast(new Intent("com.android.music.musicservicecommand").putExtra("command", "pause"));

		// mediaPlayer.setOnBufferingUpdateListener(new
		// OnBufferingUpdateListener() {
		// @Override
		// public void onBufferingUpdate(android.media.MediaPlayer mp, int
		// percent) {}
		// });
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(android.media.MediaPlayer mp) {
				// 判断是否是连续剧，如果是连续剧并且不是最后一集，播放下一集?
				// if (mPlayData.isEpisode()) {
				// autoPlayNextEpisode();
				// }
				// else {
				// ((Activity) context).finish();
				// }

				addHistory(mp.getCurrentPosition() / 1000);
			}
		});
		mediaPlayer.setOnCurrentPositionUpdateListener(new OnCurrentPositionUpdateListener() {
			@Override
			public void onCurrentPositionUpdate(int currentPosition) {}
		});
		mediaPlayer.setOnErrorListener(new OnErrorListener() {
			@Override
			public boolean onError(android.media.MediaPlayer mp, int what, int extra) {
				return false;
			}
		});
		mediaPlayer.setOnInfoListener(new OnInfoListener() {
			@Override
			public boolean onInfo(android.media.MediaPlayer mp, int what, int extra) {
				String test = "";
				switch (what) {
				case MediaPlayer.MEDIA_INFO_BUFFERING_START:
					test = "努力加载中...";
					break;
				case MediaPlayer.MEDIA_INFO_BUFFERING_END:
					break;
				case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
					test = "视频画面解码中，请稍等。";
					break;
				default:
					break;
				}
				if (test.length() == 0) {

				}
				else {
					Toast.makeText(context, test, Toast.LENGTH_SHORT).show();
				}

				return false;
			}
		});
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(android.media.MediaPlayer paramMediaPlayer) {
				ULog.v("onPrepared");

				mediaIsPrepared = true;
				if (mIsLandscape) {
					// showLandFirstLayerPopupWindows();
				}
				else {
					// showPortFirstLayerPopupWindows();
				}

			}
		});
		// mediaPlayer.setOnSeekCompleteListener(new OnSeekCompleteListener() {
		// @Override
		// public void onSeekComplete(android.media.MediaPlayer mp) {}
		// });
		// mediaPlayer.setOnVideoSizeChangedListener(new
		// OnVideoSizeChangedListener() {
		// @Override
		// public void onVideoSizeChanged(android.media.MediaPlayer mp, int
		// width, int height) {}
		// });
		mediaPlayer.setOnTimeOutListener(new Mediaplayer.OnTimeOutListener() {
			@Override
			public void onTimeOut() {}
		});
		// mediaPlayer.setOnVideoSizeChangedListener(new
		// OnVideoSizeChangedListener() {
		// @Override
		// public void onVideoSizeChanged(android.media.MediaPlayer mp, int
		// width, int height) {}
		// });
		mediaPlayer.setOnLoadingListener(new OnLoadingListener() {
			@Override
			public void onLoading(int percent) {
				// loadingStart();
				Message message = myHandler.obtainMessage(showMediaLoding);
				message.arg1 = percent;
				myHandler.sendMessage(message);
			}

			@Override
			public void onLoaded() {
				// loadingCompleted();
				myHandler.sendEmptyMessage(hideMediaLoding);
			}
		});
		mediaPlayer.setOnSwitchListener(new OnSwitchListener() {
			@Override
			public void onSwitch(SurfaceHolder holder1, SurfaceHolder holder2, int width, int height) {}

			@Override
			public void onSmall(SurfaceHolder holder) {}

			@Override
			public void onResizeCurrent(int width, int height) {}

			@Override
			public void onLarge(SurfaceHolder holder, int width, int height) {}
		});
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
		// mediaPlayer.setOnTimedTextListener(new OnTimedTextListener() {
		// @Override
		// public void onTimedText(MediaPlayer mp, TimedText text) {
		// }
		// });
		// }
	}

	/**
	 * 显示Land PopupWindow
	 */
	private void showLandFirstLayerPopupWindows() {
		ULog.d("showFirstLayerPopupWindows");

		// if (mediaIsPrepared == false) {
		// return;
		// }
		if (basePop.isFirstLayerShowing() == false) {
			// popupwindow显示，开始计时进行自动消�? hideAllPopupWinDelay();
			basePop.firstLayerShow();
		}
		else {
			cancelHideAllPopupWindDelay();
			dissmissAllPopupWin();
		}

	}

	/**
	 * 参数 单位 �?�? *
	 * 
	 * @param time
	 *            seconds
	 */
	public void setPopupWinDissDelayTime(int time) {
		if (time > 1)
			popupwindowDissmissDelay = time;
	}

	/**
	 * Dissmiss all PopupWindow
	 */
	public void dissmissAllPopupWin() {
		ULog.d("dissmissAllPopupWin");
		if (mIsLandscape) {
			basePop.dissmissAll();
		}
		else {}

	}

	/**
	 * show or dissmiss the firstlayer PopupWindow
	 */
	public void showOrDissmissFirstLayerPopupWin() {

		if (loadingBar == null) {
			return;
		}
		if (loadingBar.isShown() && firstLayerPopupWinIsShowing()) {
			// 在缓冲中 关闭 popupwin 才有�? mFromUserClick = true;
		}
		else if (loadingBar.isShown() && firstLayerPopupWinIsShowing() == false) {
			mFromUserClick = false;
		}

		if (mIsLandscape) {
			showLandFirstLayerPopupWindows();
		}
		else {

		}
	}

	/**
	 * atuo always show the firstlayer PopupWindow
	 */
	private void showFirstLayerPopupWinAlways() {

		hideAllPopupWinDelay();
		// 用户操作了的话，此函数就无效不用做任何事情返回
		if (mFromUserClick || !hasFocus) {
			return;
		}
		if (mIsLandscape) {
			if (basePop.isFirstLayerShowing() == false) {
				if (mExternalPopupWinAutoDissmissOrShowListener != null) {
					mExternalPopupWinAutoDissmissOrShowListener.popupWinAutoChanged(true);
				}
				basePop.firstLayerShow();
			}

		}
	}

	/**
	 * IS the firstlayer PopupWindow showing
	 */
	public boolean firstLayerPopupWinIsShowing() {
		if (mIsLandscape) {
			return basePop.isFirstLayerShowing();
		}
		else {
			return false;
		}

	}

	public void setVPFragmentOnClickListener(VPFragmentOnClickListener mListener) {
		mExternalOnClickListener = mListener;

	}

	public void setVPFragmentPopupWinAutoDissmissOrShowListener(VPFragmentPopupWinAutoDissmissOrShowListener mListener) {
		mExternalPopupWinAutoDissmissOrShowListener = mListener;

	}

	/**
	 * 播放器fragment点击接口
	 * 
	 * @author Administrator
	 * 
	 */
	public abstract static interface VPFragmentOnClickListener {
		public abstract boolean onClick(View v);

	}

	/**
	 * 播放器上popupwin自动消失自动显示通知接口
	 * 
	 * @author Administrator
	 * 
	 */
	public abstract static interface VPFragmentPopupWinAutoDissmissOrShowListener {
		public abstract boolean popupWinAutoChanged(boolean isShow);

	}

	@Override
	public boolean onClick(View v) {
		if (loadingBar.isShown() && firstLayerPopupWinIsShowing()) {
			// 在缓冲中 关闭 popupwin 才有�? mFromUserClick = true;
		}
		else if (loadingBar.isShown() && firstLayerPopupWinIsShowing() == false) {
			mFromUserClick = false;
		}
		if (mExternalOnClickListener != null) {
			mExternalOnClickListener.onClick(v);
		}
		else {
			showOrDissmissFirstLayerPopupWin();
		}
		return false;
	}

	@Override
	public void onDoubleClick(View v) {
		if (!mIsLandscape) {
			return;
		}
		switch (mediaPlayer.getVideoSizeRatio()) {
		case ORIGINAL:
			mediaPlayer.setVideoSizeRatio(VIDEOSIZESTATE.FULL);
			break;
		case FULL:
			mediaPlayer.setVideoSizeRatio(VIDEOSIZESTATE.ORIGINAL);
			break;
		default:
			break;
		}
		basePop.videoScaleChanged();

	}

	/**
	 * PopupWin自动延迟消失
	 */
	private void hideAllPopupWinDelay() {
		cancelHideAllPopupWindDelay();
		myHandler.sendEmptyMessageDelayed(hidePopupwindow, popupwindowDissmissDelay * 1000);
	}

	/**
	 * 取消PopupWin自动延迟消失
	 */
	private void cancelHideAllPopupWindDelay() {
		myHandler.removeMessages(hidePopupwindow);
	}

	public void loadingStart(int percent) {
		ULog.i("--> loadingStart = percent =" + percent);
		String Text = "";
		loadingAnim.start();
		loadingBar.setVisibility(View.VISIBLE);
		TextView loadingPercent = (TextView) fragview.findViewById(R.id.video_loading_percent);
		if (percent == -1) {
			Text = getString(R.string.video_loading_start);
		}
		else if (percent > 100) {
			Text = getString(R.string.video_loading_pecent, 100);
		}
		else {
			Text = getString(R.string.video_loading_pecent, percent);
		}
		loadingPercent.setText(Text);

		showFirstLayerPopupWinAlways();

		// if (mediaPlayer != null && mediaPlayer.isPlaying()) {
		// mediaPlayer.pause();
		// }

	}

	public void loadingCompleted() {
		ULog.i("--> loadingCompleted");
		if (loadingBar.getVisibility() == View.VISIBLE) {

			loadingBar.setVisibility(View.GONE);
			loadingAnim.stop();

			showFirstLayerPopupWinAlways();

			mFromUserClick = false;

			// if (mediaPlayer != null && mediaPlayer.isPlaying() == false) {
			// mediaPlayer.start();
			// }

		}

	}

	/**
	 * 设置竖屏可以用的高度
	 * 
	 * @param height
	 */
	public void setPortScreenHeightCanDraw(int height) {
		mPortScreenHeight = height;
	}

	/**
	 * 设置竖屏可以用的高度的百分比
	 * 
	 * @param percent
	 */
	public void setPortScreenHeightPercent(float percent) {
		mPortScreenHeightPer = percent;
	}

	/**
	 * 设置竖屏播放器顶部偏移量
	 * 
	 * @param px
	 */
	public void setPortScreenPaddingTopPx(int px) {
		mPortScreenPaddingTopPx = px;
	}

	/**
	 * 让播放器�?��，或者暂停等操作
	 * 
	 * @param what
	 */
	public void letMediaPlayDo(MEDIA_TODO what) {
		ULog.d("--> VideoPlayerFragment letMediaPlayDo()");

		if (what == MEDIA_TODO.PAUSE) {
			mediaPlayer.pause();
		}
		else if (what == MEDIA_TODO.PLAY) {
			mediaPlayer.start();
		}
	}

	/**
	 * 设置是否是横屏显�? *
	 * 
	 * @param mIsLandscape
	 */
	public void setScreenOrientation(boolean mIsLandscape) {

		this.mIsLandscape = mIsLandscape;
		if (mediaPlayer == null) {
			return;
		}
		if (mIsLandscape) {
			mediaPlayer.setFixedSize(Constants.SCREEN_WIDTH_LANDSCAPE, Constants.SCREEN_HEIGHT_LANDSCAPE, mIsLandscape);
		}
		else {
			mediaPlayer.setFixedSize(Constants.SCREEN_WIDTH_PORTRAIT, (int) (mPortScreenHeight * mPortScreenHeightPer),
					mIsLandscape);
		}

	}

	// /**
	// * 保存数据
	// */
	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// ULog.d("tag", "onSaveInstanceState");
	// if (data == null) {
	// data = new Bundle();
	// }
	// data.putInt("playTime", mPlayData.getPlayTime());
	// data.putBundle(ComParams.PLAY_KEY_BUNDLEDATA, bundle);
	// if (null != outState) {
	// outState.putBundle(ComParams.PLAY_KEY_BUNDLEDATA, data);
	// }
	// super.onSaveInstanceState(outState);
	// }

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	/**
	 * 从外部传进来事件
	 * 
	 * @param what
	 * @param arg1
	 */
	public void sendMessage(int what, int arg1) {
		Message message = myHandler.obtainMessage(what);
		message.arg1 = arg1;
		myHandler.sendMessage(message);
	}

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case hidePopupwindow:
				if (mExternalPopupWinAutoDissmissOrShowListener != null) {
					mExternalPopupWinAutoDissmissOrShowListener.popupWinAutoChanged(false);
				}
				dissmissAllPopupWin();
				break;
			case SHOW_VOICE_POPUPWINDOW_ADJUST_VOLUME:
				if (hasFocus) {
					if (mIsLandscape) {
						basePop.sendMessage(msg.what, msg.arg1);
					}
					else {}
				}
				break;
			case showMediaLoding:
				loadingStart(msg.arg1);
				break;
			case hideMediaLoding:
				loadingCompleted();
				break;

			}
		}

	};

	/** 增加播放历史 */
	private void addHistory(final int position) {
		if (ComParams.MOVIE_TYPE_Live == PlayerActivity.movieType) // 直播不需要历史记录
			return;
		ULog.d("addHistory");

		StringBuilder url = new StringBuilder(ComParams.HTTP_HISTORY_ADD);
		url.append("contentId=").append(PlayerActivity.contentId);
		url.append("&").append("clipId=").append(PlayerActivity.clipId);
		url.append("&").append("position=").append(position);
		url.append("&").append("token=").append(User.getToken(getActivity()));
		url.append("&").append("userTelephone=").append(User.getPhone(getActivity()));

		ULog.i(url.toString());
		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				ULog.d("onSuccess  --" + responseInfo.result);

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.d("onFailure  --" + msg);

			}
		});
	}

}
