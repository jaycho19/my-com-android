package com.dongfang.view;

import java.util.Formatter;
import java.util.Locale;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.yzsj.PlayerActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.params.ComParams;

public class MyMedioControl extends View implements View.OnClickListener {

	public String tag = "MyMedioControl";

	/** 默认隐藏控制栏pop的时间 */
	public static final int DEFAULTTIMEOUT = 3000; // 3s
	/** 默认快退快进时间 */
	public static final int DEFAULT_TIME_SEETTO = 15000; // 15s

	/**
	 * 用于控制隐藏controller的时间戳，如果在dismiss mPopControoler的线程中，如果 nHindNum的值和创建线程 时的值一样，那么执行dismiss操作，否则不执行。
	 */
	private static int nHindNum = 0;
	private static int nHindNum_Vol = 0;

	/** 当前视频加载圆圈 */
	public ProgressBar mLandProgress; // 当前视频加载圆圈
	/** 当前视频播放时间 */
	public TextView mLandCurrentTime; // 当前视频播放时间
	/** 视频资源长度 */
	public TextView mLandTotalTime; // 视频资源长度
	/** 视频控制条 */
	private SeekBar mLandVideoSeekBar; // 视频控制条

	/** 返回按钮 */
	private Button mLandBackBtn; // 返回按钮
	/** 快退 */
	private Button mLandRetreatBtn; // 快退
	/** pause or play */
	public Button mLandPlayOrStopBtn; // pause or play
	/** 快进 */
	private Button mLandAdvBtn; // 快进
	/** 全屏与默认切换 */
	private Button mLandFullBtn; // 全屏与默认切换

	/** {@link #mLandVideoSeekBar} 被滑动时，设置其为true，滑动结束设置为false */
	private boolean mDragging; // mLandVideoSeekBar 被滑动时，设置其为true，滑动结束设置为false
	/** {@link #mLandVolSeekBar} 被滑动时，设置其为true，滑动结束设置为false */
	private boolean mVol_Dragging; // mLandVolSeekBar 被滑动时，设置其为true，滑动结束设置为false

	/** 视频控制栏 */
	private PopupWindow mPopController; // 视频控制栏

	// 初始化类时，初始化数据
	private MyVideoView mVideoView;
	private Handler handler;
	private Handler controlHandler;

	private StringBuilder mFormatBuilder;
	private Formatter mFormatter;

	/** 默认视频宽度 */
	private int oldVideoWidth = 0; // 原来尺寸的视频宽度
	/** 默认视频高度 */
	private int oldVideoHeight = 0; // 原来尺寸的视频高度
	/** 是否被中断或者进入睡眠状态 */
	private boolean onpause = false; // 被中断或者进入睡眠状态

	/** 当前视频播放模式 ，默认为{@link ComParams#PLAY_VIEW_PROGRAM} 模式 */
	private String currentPlayMode = "program";

	// private boolean flag =false;

	public MyMedioControl(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		ULog.i(tag, "MyMedioControl(context, attrs, defStyle) ");
	}

	public MyMedioControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		ULog.i(tag, "MyMedioControl(context, attrs) ");
	}

	// 消除 handler引用
	private PlayerActivity mediaPlayerActivity = null;

	public MyMedioControl(PlayerActivity mediaPlayerActivity, MyVideoView myVideoView, Handler mHandler) {
		super(mediaPlayerActivity);
		mVideoView = myVideoView;
		handler = mHandler;
		controlHandler = new Handler();
		this.mediaPlayerActivity = mediaPlayerActivity;

		mFormatBuilder = new StringBuilder();
		mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

		init();
	}

	/** 初始化所有控件 */
	private void init() {
		ULog.i(tag, "init() ");
		LayoutInflater inflater = (LayoutInflater) mediaPlayerActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View videocon = inflater.inflate(R.layout.videocontrol, null);

		// ------------- 初始化视频控制pop ----------------
		mPopController = new PopupWindow(videocon, -1, LayoutParams.WRAP_CONTENT);

		mLandProgress = (ProgressBar) videocon.findViewById(R.id.video_pro);
		mLandCurrentTime = (TextView) videocon.findViewById(R.id.cur_time);
		mLandTotalTime = (TextView) videocon.findViewById(R.id.tol_time);
		mLandVideoSeekBar = (SeekBar) videocon.findViewById(R.id.video_seekbar);
		mLandVideoSeekBar.setMax(1000);
		mLandVideoSeekBar.setOnSeekBarChangeListener(new MyVideoProgressListener());

		mLandBackBtn = (Button) videocon.findViewById(R.id.land_back_btn);
		mLandRetreatBtn = (Button) videocon.findViewById(R.id.land_retr_btn);
		mLandPlayOrStopBtn = (Button) videocon.findViewById(R.id.land_stop_btn);
		mLandAdvBtn = (Button) videocon.findViewById(R.id.land_adv_btn);
		mLandFullBtn = (Button) videocon.findViewById(R.id.land_full_btn);

		mLandBackBtn.setOnClickListener(this);
		mLandRetreatBtn.setOnClickListener(this);
		mLandPlayOrStopBtn.setOnClickListener(this);
		mLandAdvBtn.setOnClickListener(this);
		mLandFullBtn.setOnClickListener(this);

	}

	/**
	 * dismiss mPopController and mPopVolView
	 */
	public void dismissController() {
		ULog.i(tag, "dismissController() ");
		hideVideoController();
	}

	public void showVideoController() {
		ULog.i(tag, "showVideoController() ");
		try {
			if (null != mVideoView && null != mPopController)
				mPopController.showAtLocation(mVideoView, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
		} catch (Throwable e) {}
	}

	/**
	 * dismiss mPopController
	 */
	public synchronized void hideVideoController() {
		ULog.i(tag, "hideVideoController() ");
		try {
			// if (isControllerShowing() == true&&mVideoView.getDuration()>0)//缓冲期间不隐藏控制栏
			if (mediaPlayerActivity.playMode != null && mediaPlayerActivity.playMode.equals(ComParams.PLAY_VIEW_LIVE)) {
				if (isControllerShowing() == true) {
					mPopController.dismiss();
				}
			}
			else {
				if (isControllerShowing() == true && mediaPlayerActivity != null
						&& mediaPlayerActivity.getStatePrepared() && mVideoView.getDuration() > 0) {
					mPopController.dismiss();
				}
			}
		} catch (Throwable e) {}
	}

	public synchronized void hideVideoControllerWhenQuit() {
		ULog.i(tag, "hideVideoControllerWhenQuit() ");
		try {
			if (isControllerShowing() == true)
				mPopController.dismiss();
		} catch (Throwable e) {}
	}

	/**
	 * 隐藏Progress
	 * */
	public void setProgressBarVisible() {
		if (mLandProgress.getVisibility() == View.VISIBLE) {
			mLandProgress.setVisibility(View.GONE);
		}
	}

	/**
	 * Indicate whether this {@link #mPopController} is showing on screen.
	 * 
	 * @return true if the {@link #mPopController} is showing, false otherwise
	 */
	public boolean isControllerShowing() {
		ULog.i(tag, "isControllerShowing() ");
		// ULog.i(tag, "isControllerShowing() mPopController " + ((mPopController != null)?"is not null":"is null"));
		// ULog.i(tag, "isControllerShowing() mPopController " +
		// ((mPopController.isShowing())?"is showing":"is not showing"));
		return (mPopController != null && mPopController.isShowing());
	}

	/**
	 * Display the {@link #mPopController} in screen which will be dismissed after {@value #DEFAULTTIMEOUT}
	 * milliseconds.<br>
	 */
	public void show() {
		ULog.i(tag, "show() ");
		show(DEFAULTTIMEOUT);
	}

	/**
	 * Display the {@link #mPopController} in screen which will be dismissed after iTime milliseconds.<br>
	 * But if the parametric iTime is not larger than 1, the {@link #mPopController} will not be dismissed forever;
	 * 
	 * @param iTime
	 */
	public void show(int iTime) {
		ULog.d(tag, "show(" + iTime + ")");
		if (mVideoView == null) {
			return;
		}
		showController();
		if (iTime > 1) {
			controlHandler.postDelayed(new HideControlPopup(++nHindNum), iTime);
		}

		// // 缓冲状态时，关闭控制栏部分按钮
		// if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PREPARED){
		// setControllerButtonEnabled(false);
		// }
		// else

		if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYING && mVideoView.getCurrentPosition() > 0) {
			setControllerButtonEnabled(true);
		}
		else if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PAUSED) {
			setControllerButtonEnabled(false);
			mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_play_btn_selector);
			mLandPlayOrStopBtn.setEnabled(true);
		}
		else if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYBACK_COMPLETED) {
			// ULog.d(tag, "show iscompleted .. " + mVideoView.IsCompleted());
			mLandVideoSeekBar.setProgress(0);
			mLandVideoSeekBar.setSecondaryProgress(0);
			mLandTotalTime.setText("00:00");
			mLandCurrentTime.setText("00:00");
			setControllerButtonEnabled(false);
			this.setButtonBackgroundonCompletion();
		}
		else {
			setControllerButtonEnabled(false);
		}
		// else if ( mVideoView.getCurrentViewState() ==
		// MyVideoView.STATE_PLAYING ){
		// mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_normal);
		// setControllerButtonEnabled(true);
		// }
	}

	/**
	 * 1、缓冲大于0 <br>
	 * 2、{@code MyMedioControl.isInPlaybackState() } 返回真<br>
	 * 3、满足以上两个条件，显示控制栏
	 */
	private void showController() {
		ULog.i(tag, "showController()");

		if (mVideoView.isInPlaybackState()) {
			setProgress();

			// ULog.d(tag, "showController()==> VideoState:"+mVideoView.getCurrentViewState());
			controlHandler.postDelayed(rChangeProgress, 1000);
			// mPopController.showAtLocation(mVideoView, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
			showVideoController();
			controlHandler.postDelayed(new HideControlPopup(++nHindNum), DEFAULTTIMEOUT);
		}
	}

	/**
	 * 控制栏按钮事件 音量seekbar和视频seekbar在拖动时候，不允许按钮事件
	 */

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick()");
		if (mDragging || mVol_Dragging)
			return;
		switch (v.getId()) {
		case R.id.land_back_btn: // 返回按钮
			handler.sendEmptyMessage(ComParams.PLAY_CONTROL_BACK);
			break;
		case R.id.land_retr_btn: // 快退按钮 ,直播不允许快退
			if (currentPlayMode.equals(ComParams.PLAY_VIEW_LIVE))
				break;
			int position = mVideoView.getCurrentPosition() - DEFAULT_TIME_SEETTO;
			Log.d("tag", " case position" + position);
			if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYING) {
				setControllerButtonEnabled(false);
				mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_disable);
				mVideoView.setCurrentViewState(MyVideoView.STATE_PREPARED, "onClick(View v)1");
				/*
				 * 修改了当播放进度小于快退的进度时，视频不会出现快退现象的bug 2013 1-21
				 */
				if (currentPlayMode.equals(ComParams.PLAY_VIEW_PROGRAM)) {
					if (position > 0) {
						mVideoView.seekTo(position);
						ULog.d("tag", "if mVideoView.seekTo" + mVideoView.getCurrentPosition());
					}
					else {
						// 播放进度小于快退的进度时,让视频从1秒钟的位置开发播放
						mVideoView.seekTo(1);
						ULog.d("tag", "else mVideoView.seekTo" + mVideoView.getCurrentPosition());
					}
				}
				if (currentPlayMode.equals(ComParams.PLAY_VIEW_LOCAL)) {
					mVideoView.seekTo(position);
				}
				/*
				 * int position = mVideoView.getCurrentPosition() - DEFAULT_TIME_SEETTO; mVideoView.seekTo(position);
				 */
			}
			else if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PAUSED) {
				/*
				 * int position = mVideoView.getCurrentPosition() - DEFAULT_TIME_SEETTO; mVideoView.seekTo(position);
				 */
				if (currentPlayMode.equals(ComParams.PLAY_VIEW_PROGRAM)) {
					if (position > 0) {
						mVideoView.seekTo(position);
					}
					else {
						// 播放进度小于快退的进度时,让视频从1秒钟的位置开发播放
						mVideoView.seekTo(1);
					}
				}
				if (currentPlayMode.equals(ComParams.PLAY_VIEW_LOCAL)) {
					mVideoView.seekTo(position);
				}
			}
			mVideoView.setDrag(true);
			break;
		case R.id.land_adv_btn: // 快进按钮 ,直播不允许快进

			if (currentPlayMode.equals(ComParams.PLAY_VIEW_LIVE))
				break;

			if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYING) {

				setControllerButtonEnabled(false);
				mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_disable);
				mVideoView.setCurrentViewState(MyVideoView.STATE_PREPARED, "onClick(View v)2");
				mVideoView.seekTo(mVideoView.getCurrentPosition() + DEFAULT_TIME_SEETTO);
			}
			else if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PAUSED) {
				mVideoView.seekTo(mVideoView.getCurrentPosition() - DEFAULT_TIME_SEETTO);
			}
			break;
		case R.id.land_stop_btn: // 播放暂停按钮 ,直播不允许暂停
			MyVideoView.mPlayFlag = true;
			// if (currentPlayMode.equals(ComParams.PLAY_VIEW_LIVE)){
			// //直播
			// handler.sendEmptyMessage(ComParams.PLAY_CONTROL_REPLAY);
			// }else{
			setPauseOrPlay();
			// }
			break;
		case R.id.land_full_btn: // 全屏播放按钮
			setVideoScale();
			break;
		}
		controlHandler.postDelayed(new HideControlPopup(++nHindNum), DEFAULTTIMEOUT);
	}

	// 暂停播放
	public void setPauseOrPlay() {
		ULog.d(tag, "setPauseOrPlay()");
		// ULog.d(tag, "setPauseOrPlay Head   mVideoView.IsCompleted()= " + mVideoView.IsCompleted() + ",isPlaying:"
		// + mVideoView.isPlaying());
		mLandPlayOrStopBtn.setEnabled(false);
		try {
			/*
			 * if (mVideoView.IsCompleted()) { handler.sendEmptyMessage(ComParams.PLAY_CONTROL_REPLAY); } else
			 */if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYING) {
				setControllerButtonEnabled(false);
				mVideoView.pause(MyVideoView.STATE_PAUSE_SHOWWEBVIEW);
				mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_play_btn_selector);
				mLandPlayOrStopBtn.setEnabled(true);
			}
			else {
				// 点播
				// setProgress();
				if (mVideoView.getCurrentViewState() == MyVideoView.STATE_ERROR) {
					// 进入主页再返回，如果加载出错
					mediaPlayerActivity.sendEmptyMessage(ComParams.PLAY_CONTROL_VIEWTIMEOUT);
				}
				else {
					if (mediaPlayerActivity.getCurpositon() > 0) {
						ULog.d(tag, "setPauseOrPlay Curpositon() > 0");
						// 进入主页后再返回
						mVideoView.seekTo(mediaPlayerActivity.getCurpositon());
						mVideoView.start();
						mVideoView.setCurrentViewState(MyVideoView.STATE_PREPARED, "setPauseOrPlay()");
						mediaPlayerActivity.setCurpositon(-1);
						setControllerButtonEnabled(false);
						mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_selector);
					}
					else {
						ULog.d(tag, "setPauseOrPlay Curpositon() <= 0");
						// 暂停状态进入播放
						mVideoView.start();
						setControllerButtonEnabled(true);
						mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_selector);
					}
					handler.sendEmptyMessage(ComParams.PLAY_CONTROL_GONEWEBVIEW);
					controlHandler.post(rChangeProgress);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mLandPlayOrStopBtn.setEnabled(true);
	}

	/**
	 * 直播的重新播放
	 */
	public void setLivePlay() {
		ULog.d(tag, "setLivePlay()");
		handler.sendEmptyMessage(ComParams.PLAY_CONTROL_REPLAY);
		mLandPlayOrStopBtn.setEnabled(false);
		mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_disable);
		mLandCurrentTime.setText("缓冲中" + mVideoView.getBufferPercentage() + "%");
	}

	/** 控制栏按钮状态 */
	public void setControllerButtonEnabled(boolean enabled) {
		ULog.d(tag, "setControllerButtonEnabled(" + enabled + ")");
		// 视频控制拖动条在拖动时，所以空间设置暂时无效
		if (mDragging)
			return;
		boolean enable_temp = enabled;
		// if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PAUSED)
		// enable_temp = false;
		ULog.d(tag, "setControllerButtonEnabled enabled = " + enabled + "  enable_temp = " + enable_temp);
		mLandPlayOrStopBtn.setEnabled(enable_temp);
		mLandVideoSeekBar.setEnabled(enable_temp);
		mLandRetreatBtn.setEnabled(enable_temp);
		mLandAdvBtn.setEnabled(enable_temp);
		mLandFullBtn.setEnabled(enable_temp);
		if (currentPlayMode.equals(ComParams.PLAY_VIEW_LIVE)) {
			mLandRetreatBtn.setEnabled(false);
			mLandAdvBtn.setEnabled(false);
			mLandVideoSeekBar.setEnabled(false);
			mLandPlayOrStopBtn.setEnabled(false);
		}
	}

	public void setPlayMode(String playMode) {
		ULog.d(tag, "setPlayMode(" + playMode + ")");
		if (playMode.toLowerCase().equals(ComParams.PLAY_VIEW_LIVE)) {
			currentPlayMode = ComParams.PLAY_VIEW_LIVE;
			if (mLandPlayOrStopBtn != null) {
				mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_disable);
				mLandPlayOrStopBtn.setEnabled(false);

			}
		}
		else if (playMode.toLowerCase().equals(ComParams.PLAY_VIEW_PROGRAM)) {
			currentPlayMode = ComParams.PLAY_VIEW_PROGRAM;
			if (mLandPlayOrStopBtn != null) {
				mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_selector);
				mLandPlayOrStopBtn.setEnabled(false);

			}
		}
		else if (playMode.toLowerCase().equals(ComParams.PLAY_VIEW_LOCAL)) {
			currentPlayMode = ComParams.PLAY_VIEW_LOCAL;
			if (mLandPlayOrStopBtn != null) {
				mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_selector);
				mLandPlayOrStopBtn.setEnabled(false);
			}
		}
		// ULog.d(tag, "setPlayMode currentPlayMode = " + currentPlayMode);
	}

	/**
	 * 初始化时间格式
	 * 
	 * @param timeMs
	 *            毫秒
	 * @return -:--:-- 格式的字符串
	 * */
	public String stringForTime(int timeMs) {
		ULog.d(tag, "stringForTime(" + timeMs + ")");

		int totalSeconds = timeMs / 1000;

		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		mFormatBuilder.setLength(0);
		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
		}
		else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}

	// 设置seekbar 和 时间
	private int setProgress() {
		ULog.d(tag, "setProgress()");
		if (mVideoView == null || mDragging || mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYBACK_COMPLETED
		/* || mVideoView.getCurrentViewState() == MyVideoView.STATE_PAUSED */) {
			return 0;
		}

		int position = -1;
		int duration = -1;
		try {
			position = mVideoView.getCurrentPosition();
			/**
			 * Call mVideoView.getDuration after video prepared, otherwise will cause error(-38,0)
			 */
			if (!mVideoView.getIsInterrupt() && !mVideoView.isError()) {
				if (mediaPlayerActivity.getStatePrepared()) {
					duration = mVideoView.getDuration();
				}
			}
			else {
				duration = mVideoView.getmPreDuration();
			}

		} catch (Exception e) {
			ULog.e(tag, "setProgress exception" + e.toString());
		}
		ULog.e(tag, "<<<<<<<<<<<<<<<<<<<  position " + position);
		ULog.e(tag, "<<<<<<<<<<<<<<<<<<<  duration " + duration);
		position = (position > duration) ? duration : position; // 防止视频当前显示进度时间超过总时间

		if (mLandVideoSeekBar != null) {

			if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PAUSED) {
				if (duration > 0) {
					int p = (int) (1000L * mVideoView.getPerPosition() / duration);
					mLandVideoSeekBar.setProgress(p);
				}
				if (!mVideoView.getIsInterrupt() && !mVideoView.isError()) {
					mLandCurrentTime.setText(stringForTime(mVideoView.getPerPosition()));
					mLandProgress.setVisibility(View.GONE);
				}
				else {
					mLandProgress.setVisibility(View.VISIBLE);
					mLandCurrentTime.setText("缓冲中...");
				}
				return 0;
			}

			if (mVideoView.getIsInterrupt()) {
				return 0;
			}

			if (duration > 0) {
				// use long to avoid overflow
				long pos = 1000L * position / duration;
				// ULog.d(tag, ">>>>>pos"+pos);
				mLandVideoSeekBar.setProgress((int) pos);
			}

			int buffer = mVideoView.getBufferPercentage();
			ULog.d(tag, "<<<<<<<<<<<<<<<<<<< setProgress()->CurrentPosition = " + position + " , Duration = "
					+ duration + ",VideoState:" + mVideoView.getCurrentViewState() + ",buffer:" + buffer);
			if (duration > 0 && position > 0) {
				// 防止出现mediaplayer重置工程中，duration出现超大值
				if (mVideoView.getmPreDuration() < 0 || duration == mVideoView.getmPreDuration()) {
					mVideoView.setmPreDuration(duration);
				}
				mVideoView.setmPrePosition(position);
			}

			/**
			 * 1.local播放模式不需要缓冲 3.视频缓冲值大于 0 小于100 4.在出错
			 */
			if (!currentPlayMode.equals(ComParams.PLAY_VIEW_LOCAL) && buffer >= 0 && buffer < 100) {
				mVideoView.setCurrentViewState(MyVideoView.STATE_PREPARED, "setProgress()1");
				mLandVideoSeekBar.setSecondaryProgress(buffer * 10);
				if (mLandCurrentTime != null) {
					/*
					 * mLandCurrentTime.setText(context.getResources().getString(R.string.video_buffer)
					 * .replaceFirst("%d", "" + percent));
					 */
					// mLandCurrentTime.setText(R.string.video_loading);
					mLandCurrentTime.setText("缓冲中...");
					mLandProgress.setVisibility(View.VISIBLE);
				}
				setControllerButtonEnabled(false);
				if (isControllerShowing()) {
					controlHandler.postDelayed(new HideControlPopup(++nHindNum), DEFAULTTIMEOUT);
				}
				else {
					// mPopController.showAtLocation(mVideoView, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
					showVideoController();
				}

			}
			else {
				if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PREPARED) {
					// 处于一直显示控制栏
					if (isControllerShowing()) {
						controlHandler.postDelayed(new HideControlPopup(++nHindNum), DEFAULTTIMEOUT);
					}
					else {
						// mPopController.showAtLocation(mVideoView, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
						showVideoController();
					}
				}

				if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PREPARED && buffer == 100) {
					mVideoView.setCurrentViewState(MyVideoView.STATE_PLAYING, "setProgress()2");
				}

				mLandVideoSeekBar.setSecondaryProgress(buffer * 10);
				// 播放总时长：1.控件不为空2.当前状态不为暂停
				if (mLandTotalTime != null && duration > 0) {
					ULog.e(tag, "duration:" + duration);
					mLandTotalTime.setText(stringForTime(duration));
				}

				if (mLandCurrentTime != null && position > 0) {
					ULog.e(tag, "mLandCurrentTime:" + position);
					// 点播
					mLandProgress.setVisibility(View.GONE);
					mLandCurrentTime.setText(stringForTime(position));
				}

				if (currentPlayMode.equals(ComParams.PLAY_VIEW_LIVE) && buffer == 100) {
					// 直播
					mLandProgress.setVisibility(View.GONE);
					mLandCurrentTime.setText("");
				}

				if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYING
						&& mVideoView.getCurrentPosition() > 0) {
					setControllerButtonEnabled(true);
					mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_selector);
				}
				else {
					setControllerButtonEnabled(false);
				}

				// mLandVideoSeekBar.setProgress(position * 1000 / duration);
				// if(mVideoView.isPlaying() &&
				// !MediaPlayerActivity.mWebView.isShown())
				// MediaPlayerActivity.mWebView.setVisibility(View.GONE);

			}
		}
		return position;
	}

	/**
	 * 全屏设置
	 */
	public void setVideoScale() {
		ULog.d(tag, "setVideoScale()");
		if (mVideoView.isFullScreen()) {
			mVideoView.setVideoScale(oldVideoWidth, oldVideoHeight);
			mLandFullBtn.setBackgroundResource(R.drawable.land_full_btn_selector);
			mVideoView.setFullScreen(false);
		}
		else {
			// 进入充满全屏
			if (oldVideoWidth == 0 && oldVideoHeight == 0) {
				oldVideoHeight = mVideoView.getMeasuredHeight();
				oldVideoWidth = mVideoView.getMeasuredWidth();
			}

			mVideoView.setVideoScale(mediaPlayerActivity.getPlayerWidth(), mediaPlayerActivity.getPlayerHeight());
			mLandFullBtn.setBackgroundResource(R.drawable.land_scale_btn_selector);
			mVideoView.setFullScreen(true);
		}
	}

	/** 视频播放完毕时对状态栏按钮的控制 */
	public void setButtonBackgroundonCompletion() {
		ULog.d(tag, "setButtonBackgroundonCompletion()");
		mLandPlayOrStopBtn.setEnabled(true);
		mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_play_btn_selector);
	}

	/** 视频 seekbar */
	class MyVideoProgressListener implements SeekBar.OnSeekBarChangeListener {
		private long duration = 0;
		private long newposition = 0;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			ULog.d(tag, "onProgressChanged time =" + progress);
			if (!fromUser) {
				return;
			}
			if (mediaPlayerActivity != null && mediaPlayerActivity.getStatePrepared())
				duration = mVideoView.getDuration();

			/**
			 * 把位置定为1毫秒处，可以使进度条到达0秒位置 2013 2-18 修改
			 * */
			if (progress == 0) {
				// 把位置定为1毫秒处，可以使进度条到达0秒位置
				newposition = 1;
			}
			else {
				newposition = (duration * progress) / 1000L;
			}
			// mVideoView.seekTo((int) newposition);
			if (mLandCurrentTime != null) {
				mLandCurrentTime.setText(stringForTime((int) newposition));
			}
			ULog.d(tag, "stringForTime((int) newposition)-->" + stringForTime((int) newposition));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			ULog.d(tag, "onStartTrackingTouch");
			mDragging = true;
			if (mediaPlayerActivity != null && mediaPlayerActivity.getStatePrepared())
				ULog.d(tag, "mVideoView.getDuration()-1->" + mVideoView.getDuration());
			// mVideoView.pause(); //如果视频不流畅时，此函数容易卡住，故此注释
			mLandPlayOrStopBtn.setEnabled(false);
			mLandRetreatBtn.setEnabled(false);
			mLandAdvBtn.setEnabled(false);
			mLandFullBtn.setEnabled(false);
			mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_disable);
			mVideoView.setDrag(true);
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			ULog.d(tag, "onStopTrackingTouch");
			mDragging = false;
			// 对视频进行进度进进行修改
			// setControllerButtonEnabled(false);
			// mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_disable);
			// mVideoView.start(); //如果视频不流畅时，此函数容易卡住，故此注释
			mVideoView.seekTo((int) newposition); // 这个顺序一定要在 mVideoView.start()之后，不然不会有缓冲显示
			ULog.d(tag, "newposition==>" + newposition);
			mVideoView.setCurrentViewState(MyVideoView.STATE_PREPARED, "onStopTrackingTouch(SeekBar seekBar)");
			show(0);
			controlHandler.postDelayed(new HideControlPopup(++nHindNum), DEFAULTTIMEOUT);
			if (mediaPlayerActivity != null && mediaPlayerActivity.getStatePrepared())
				ULog.d(tag, "mVideoView.getDuration()-2->" + mVideoView.getDuration());
		}

	}

	/**
	 * 每1000 毫秒修改当前视频播放进度
	 */
	private Runnable rChangeProgress = new Runnable() {

		@Override
		public void run() {
			/**
			 * 播放完毕\暂停播放\播放错误
			 */
			ULog.e(tag, "rChangeProgress Runnable");
			// if (mVideoView == null || MediaPlayerActivity.isCompletion ||
			// MediaPlayerActivity.onPause
			if (mVideoView == null || !mVideoView.isInPlaybackState())
				return;

			int state = mVideoView.getCurrentViewState();
			if (isControllerShowing() && (state == MyVideoView.STATE_PREPARED || state == MyVideoView.STATE_PLAYING)) {
				setProgress();
				controlHandler.postDelayed(rChangeProgress, 1000);
			}
			else if (isControllerShowing() && state == MyVideoView.STATE_PAUSED) {
				/**
				 * 问题：在使用有控制播放/停止按钮的耳机时，播放控制栏显示的情况下， 点击停止，控制栏状态不变
				 */
				setControllerButtonEnabled(false);
				mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_play_btn_selector);
				mLandPlayOrStopBtn.setEnabled(true);
			}
		}

	};

	/**
	 * 当视频处于播放状态、滑动视频进度条和音量进度条结束以及 控制栏处于显示状态，则dismiss mPopController and mPopVolView
	 */
	private class HideControlPopup implements Runnable {
		private int sNum = 0;

		public HideControlPopup(int nNum) {
			sNum = nNum;
		}

		@Override
		public void run() {
			ULog.d(tag, "HideControlPopup START");
			if (mVideoView == null) {
				return;
			}
			ULog.d(tag, "HideControlPopup sNum = " + sNum + "  nHindNum " + nHindNum + " state = " /*
																									 * + mVideoView.
																									 * getCurrentViewState
																									 * ()
																									 */);
			if (onpause)
				return;

			if (sNum == nHindNum && isControllerShowing()) {
				int current = mVideoView.getCurrentViewState();
				if (current == MyVideoView.STATE_PAUSED || current == MyVideoView.STATE_PLAYBACK_COMPLETED
						|| current == MyVideoView.STATE_ERROR || current == MyVideoView.STATE_PREPARED || mVol_Dragging
						|| mDragging) {
					controlHandler.postDelayed(new HideControlPopup(++nHindNum), DEFAULTTIMEOUT);
				}
				else {
					dismissController();
				}
			}
		}
	}

	/** 隐藏音量控制pop */

	/** 设置中断状态 */
	public void setOnPause(boolean b) {
		ULog.d(tag, "setOnPause(" + b + ")");
		onpause = b;
	}

	public boolean getOnPause() {
		return onpause;
	}

	public void setVideoView(MyVideoView v) {
		mVideoView = v;
	}
}
