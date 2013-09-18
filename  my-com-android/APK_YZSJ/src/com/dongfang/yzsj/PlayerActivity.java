package com.dongfang.yzsj;

import java.net.InetAddress;
import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dongfang.utils.ULog;
import com.dongfang.view.MyMedioControl;
import com.dongfang.view.MyVideoView;
import com.dongfang.yzsj.params.ComParams;

public class PlayerActivity extends BaseActivity implements MediaPlayer.OnCompletionListener,
		MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
	private final String tag = "MediaPlayerActivity";
	private static PlayerActivity mediaPlayerActivity = null;
	// private int currentLoadingTime = 0;// 当前加载所花的时间
	private Display display;
	private MyVideoView mVideoView; // 视频控件
	private MyMedioControl mMedioControl; // 控制栏
	private ImageView loadingImage; // 进入MEDIAPLAYER ACTIVITY 时的展示图片 private AudioManager mAudioManager;
	private String voiceVolume = "voice";
	private Boolean isExit = false;
	private boolean isFirstLoadingVideo = true;
	/** 用以计算两次按下back键的时间差 */
	private long lastTimeMillis = -1;

	/** 答题互动中，当前显示题目的顺序标号 */
	private int current_question_number = 0;

	/** 答题互动中的标题 */
	private TextView textview_title;
	/** 答题互动中的右上角关闭按钮 */
	private Button button_close;

	/** 答题互动中的题目 */
	private TextView textview_questions;
	/** 答题互动中的选项A */
	private Button button_a;
	/** 答题互动中的选项B */
	private Button button_b;
	/** 答题互动中的选项C */
	private Button button_c;
	/** 答题互动中的选项D */
	private Button button_d;

	private Button[] button_array;

	/** 答题互动中的中奖用户 */
	private ViewFlipper viewFlipper_prizes;
	/** 中奖用户title */
	private TextView textview_winning_users_title;

	private Intent intent;
	// private ConnectManager connectManager;

	private String videoURL;
	private Bundle data;
	/** 视频播放类型包括local,program,live */
	public String playMode;
	/** 播放视频的来源，有一下几种："isfrompage","isfromrecommend" and "externalpages" */
	private String fromPlace;
	/** 播放退出返回是需要返回到的详情页地址 */
	private String detailURL;

	/** 内容id，用于互动答题 */
	private String contentId;
	private int curpositon = -1;
	private int loadPage0 = 0; // 加载网页广告状态
	private Handler handler;

	// private WakeLock wakeLock;
	/** 是否退出 */
	private boolean isBack; // 是否要退出,初始化为false
	private int playListCount = 0;
	/** 视频列表 */
	ArrayList<String> getPlayList;
	private Boolean isExecute = false;
	/**
	 * 显示的异常dialog的状态，默认-1，下载的视频未找到：DialogFactory.DOWNLOADED_VIDEO_NOT_FOUND， 播放错误：DialogFactory.PLAY_ERROR
	 */
	private int exceptionDialogState = -1;
	private int currentPosition = 0;
	private boolean mIsPrepared = false;

	/* 测试蓝牙耳机 qzp */
	private BluetoothReceiver mBlutoothReceiver = null;

	class BluetoothReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			ULog.d(tag, "BluetoothReceiver onReceive intent Action = " + action);
			// When discovery finds a device
			if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
				ULog.d(tag, "ACTION_ACL_DISCONNECTED");
				setPauseOrPlay();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ULog.d("tag", "onCreate");
		super.onCreate(savedInstanceState);

		// 捕获异常，写入到sdcard/TYSX.log文件
		// CrashHandler.getInstance().init(getApplicationContext());

		/**
		 * 1.实例化控件 2.接受intent数据，初始化本地参数（播放模式：live等） 3.videoview + webview 载入url 4.videoview.start();
		 */

		/**
		 * 1.实例化控件
		 */
		setContentView(R.layout.activity_player);
		mediaPlayerActivity = this;
		try {
			mBlutoothReceiver = new BluetoothReceiver();
			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
			registerReceiver(mBlutoothReceiver, filter);
		} catch (Exception e) {
			ULog.e(tag, "BluetoothReceiver Exception " + e.toString());
		}

		isBack = false;
		handler = new MediaHandler();
		mVideoView = (MyVideoView) findViewById(R.id.surface_view);
		loadingImage = (ImageView) findViewById(R.id.video_loadingImage);

		// mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		display = getWindowManager().getDefaultDisplay();

		mMedioControl = new MyMedioControl(this, mVideoView, handler);

		mVideoView.setOnCompletionListener(this);
		mVideoView.setOnPreparedListener(this);
		mVideoView.setOnErrorListener(this);

		/**
		 * 2.接收intent传递进来的参数
		 */
		intent = this.getIntent();
		data = intent.getBundleExtra(ComParams.PLAY_KEY_VIEWINFO);
		initViewInfo(savedInstanceState);
		mIsPrepared = false;

		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(mScreenOffReceiver, filter);
	}

	@Override
	protected void onStart() {
		ULog.d("tag", "onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		ULog.d("tag", "onResume");

		if (null != mMedioControl) {
			mMedioControl.setOnPause(false);
		}

		super.onResume();
	}

	/**
	 * 保存数据
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		ULog.d("tag", "onSaveInstanceState");
		if (null == data) {
			data = new Bundle();
			data.putString(ComParams.PLAY_KEY_VIDEOURL, videoURL);
			data.putString(ComParams.PLAY_KEY_DETAILURL, detailURL);
			data.putString(ComParams.PLAY_KEY_FROMPLACE, fromPlace);
			data.putString(ComParams.PLAY_KEY_PLAYMODE, playMode);
		}
		data.putInt(ComParams.PLAY_KEY_CURPOSITON, currentPosition);

		ULog.d(tag, "onSaveInstanceState  getCurrentPosition   = " + currentPosition);

		if (null != outState) {
			outState.putBundle(ComParams.PLAY_KEY_BUNDLEDATA, data);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		ULog.d("tag", "onPause");
		super.onPause();
		ULog.d(tag, "onPause End VideoState:" /* + mVideoView.getCurrentViewState() */);
	}

	@Override
	protected void onDestroy() {
		ULog.d("tag", "onDestroy top");
		super.onDestroy();
		isExit = true;
		MyVideoView.mPlayFlag = true;
		try {
			if (null != mMedioControl) {
				mMedioControl.hideVideoControllerWhenQuit();// 直接调用dismissController造成控制栏对话框泄漏
				mMedioControl.destroyDrawingCache();
				mMedioControl = null;
			}
			if (null != mVideoView) {
				mVideoView.destroyDrawingCache();
				mVideoView = null;
			}
			mediaPlayerActivity = null;
			if (handler != null) {
				handler.removeMessages(ComParams.GET_PALYlIST);
				handler = null;
			}

		} catch (Throwable e) {
			ULog.e(tag, " " + e.toString());
		}
		if (mScreenOffReceiver != null) {
			try {
				unregisterReceiver(mScreenOffReceiver);
			} catch (Exception e) {
				ULog.e(TAG, "unregisterReceiver mBatInfoReceiver failure :" + e.getCause());
			}
		}
		try {
			unregisterReceiver(mBlutoothReceiver);
		} catch (Exception e) {
			ULog.e(tag, "BluetoothReceiver unregisterReceiver Exception " + e.toString());
		}

		ULog.d(tag, "onDestroy end");
	}

	@Override
	protected void onStop() {
		ULog.d("tag", "onStop");
		super.onStop();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		ULog.d("tag", "onConfigurationChanged");
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * 准备就绪，即将播放
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {
		ULog.d(tag, "onPrepared");
		mIsPrepared = true;
		ULog.d(tag, (null != mp) ? "onPrepared MediaPlayer is not null" : "onPrepared MediaPlayer is null");
		handler.sendEmptyMessage(ComParams.PREPARESTATE);
	}

	/**
	 * 播放完毕
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		ULog.d(tag, "onCompletion Head VideoState:" + mVideoView.getCurrentViewState());
		ULog.d(tag, "连播视频个数：" + (null == getPlayList ? 0 : getPlayList.size()));
		if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYING
				|| mVideoView.getCurrentViewState() == MyVideoView.STATE_PREPARED) {
			setCurpositon(-1);
			mVideoView.destroyDrawingCache();
			mMedioControl.setButtonBackgroundonCompletion();
			mMedioControl.show(0);
		}
		if (ComParams.PLAY_VIEW_EXTERNALPAGE.equals(fromPlace)) {
			ULog.d(tag, "onCompletion PLAY_VIEW_EXTERNALPAGE: " + fromPlace);
			// 第三方界面
			loadingImage.setVisibility(View.VISIBLE);
		}
		// }
		ULog.d(tag, "onCompletion End VideoState:" + mVideoView.getCurrentViewState());
	}

	private static int error_time = 0;
	private static int error_what = -1;
	private static int error_extra = -1;

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		ULog.e(tag, "onError what = " + what + ", extra = " + extra);

		/**
		 * 初次进入， 就报错的现象， 是否显示web界面
		 */
		// TODO...

		/**
		 * 记录连续1000次。退出
		 */
		if (error_time < 1000L) {
			error_time++;
		}
		else {
			error_time = 0;

			// XT928 特殊处理
			if (1 == what && 0 == extra) {
				return true;
			}

			// ME811 特殊处理
			if ((1 == what && -1 == extra) || (-2147483648 == what && 0 == extra)) {
				return true;
			}

			mVideoView.setCurrentViewState(MyVideoView.STATE_ERROR, "onError(MediaPlayer mp, int what, int extra)");
			mVideoView.stop();
			handler.sendEmptyMessage(ComParams.PLAY_ERROR_RECEIVE);
		}

		/**
		 * toast提示
		 */
		// try{
		// if ( (error_what!=what || error_extra!=extra) )
		// Toast.makeText(MediaPlayerActivity.this, "("+what+","+extra+")", 1000).show();
		// }catch (Exception e){
		// e.printStackTrace();
		// }

		// error_what = what;
		// error_extra = extra;

		if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
			mp.release();
		}

		return true;
	}

	/**
	 * 获取播放的媒体信息
	 * 
	 * @param savedInstanceState
	 */
	private void initViewInfo(Bundle savedInstanceState) {
		ULog.d(tag, "initViewInfo");
		if (null != data) {
			videoURL = data.getString(ComParams.PLAY_KEY_VIDEOURL);
			detailURL = data.getString(ComParams.PLAY_KEY_DETAILURL);
			fromPlace = data.getString(ComParams.PLAY_KEY_FROMPLACE);
			playMode = data.getString(ComParams.PLAY_KEY_PLAYMODE);
			contentId = data.getString(ComParams.PLAY_KEY_CONTENTID);
		}

		if (savedInstanceState != null) {

			data = savedInstanceState.getBundle(ComParams.PLAY_KEY_BUNDLEDATA);
			videoURL = data.getString(ComParams.PLAY_KEY_VIDEOURL);
			detailURL = data.getString(ComParams.PLAY_KEY_DETAILURL);
			fromPlace = data.getString(ComParams.PLAY_KEY_FROMPLACE);
			playMode = data.getString(ComParams.PLAY_KEY_PLAYMODE);
			contentId = data.getString(ComParams.PLAY_KEY_CONTENTID);

			// curpositon = data.getInt(ComParams.PLAY_KEY_CURPOSITON);
			setCurpositon(data.getInt(ComParams.PLAY_KEY_CURPOSITON));
			ULog.e(tag, "oncreat()  initViewInfo  curpositon  = " + getCurpositon());

		}
		// videoURL = "/sdcard/downloads/templete.3gp";
		// detailURL = "http://www.baidu.com";
		// playMode = ComParams.PLAY_VIEW_LOCAL;
		// fromPlace = ComParams.PLAY_VIEW_ISFROMPAGE;
		// adPage = new String[] { "http://topit.com" };
	}

	/**
	 * 异步加载视频的播放地址
	 * */
	class MediaPlayerAsync extends AsyncTask<Void, Void, String> {

		protected String doInBackground(Void... params) {
			// TODO IP address retrieve
			videoURL = parseDomainToIPAddress(videoURL);
			ULog.d(tag, "after parseDomainToIPAddress-->" + videoURL);

			/** 针对SAMSUNG-N719/I939D [BUG #491] **/
			try {
				if (!playMode.equalsIgnoreCase(ComParams.PLAY_VIEW_LIVE)
				/* && !playMode.equalsIgnoreCase(ComParams.PLAY_VIEW_SCHEDULE) */) {
					if (isFirstLoadingVideo == false && null != mVideoView) {
						mVideoView.stopPlayback();
						mVideoView = null;
					}
					isFirstLoadingVideo = false;
					mVideoView = (MyVideoView) findViewById(R.id.surface_view);
				}
			} catch (Exception e) {
				ULog.e(tag, "loadingVideo exception: " + e.toString());
			}
			return videoURL;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			try {
				ULog.d(tag, "MediaPlayerAsync onPreExecute()");
				mMedioControl.mLandCurrentTime.setText("连接中..");
				mMedioControl.mLandProgress.setVisibility(View.VISIBLE);
				mMedioControl.setPlayMode(playMode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onPostExecute(String result) {
			ULog.d(tag, "onPostExecute result-->" + result);
			super.onPostExecute(result);
			try {
				if (!TextUtils.isEmpty(result)) {
					mVideoView.setCurrentViewState(MyVideoView.STATE_IDLE, "loadingVideo()");
					// 测试地址："http://vslb.tv189.cn/2011/10/29/bf/0b813af1a04c30a4d60239e7f27577450p-0000.flv.mp4"
					mVideoView.setVideoPath(result);
					mVideoView.requestFocus();
					mVideoView.start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * rtsp://vod01.v.vnet.mobi/2013/01/10/Q300_2002567986.3gp?sid=30432319&..... <br>
	 * -->rtsp://118.85.193.195/2013/01/10/Q300_2002567986.3gp?sid=30432319&.....
	 * 
	 * @param videoUrl
	 * @return
	 */
	private String parseDomainToIPAddress(String videoUrl) {
		String tmpVideoUrl = videoUrl;
		ULog.d(tag, "before parseDomainToIPAddress-->" + videoUrl);
		if (tmpVideoUrl.startsWith("rtsp://") || tmpVideoUrl.startsWith("http://")) {
			String strPrefix = "";
			if (tmpVideoUrl.startsWith("rtsp://")) {
				strPrefix = "rtsp://";
			}
			else {
				strPrefix = "http://";
			}
			try {
				tmpVideoUrl = tmpVideoUrl.replace(strPrefix, "");
				tmpVideoUrl = tmpVideoUrl.substring(0, tmpVideoUrl.indexOf("/"));
				InetAddress address = null;
				address = InetAddress.getByName(tmpVideoUrl);
				String IPAdd = address.getHostAddress();
				ULog.d(tag, "tmpVideoUrl-->" + tmpVideoUrl + "  IPAdd-->" + IPAdd);
				tmpVideoUrl = videoUrl.replace(tmpVideoUrl, IPAdd);
				return tmpVideoUrl;
			} catch (Exception e) {
				ULog.e(tag, e.toString());
				ULog.e(tag,
						"get exception when parse video url, do nothing and pass the url to mediaplayer directly!!!");
				return videoUrl;
			}
		}
		else {
			ULog.d(tag, "video url is strange, do nothing and pass the url to mediaplayer directly!!!");
			return videoUrl;
		}

	}

	boolean isback = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_MENU:
			if (null == mMedioControl) {
				return false;
			}
			if (mMedioControl.isControllerShowing()) {
				mMedioControl.hideVideoController();
			}
			else {
				mMedioControl.show();
			}
			return true;
		case KeyEvent.KEYCODE_BACK:
			if (isback == false) {
				isback = true;
				handler.sendEmptyMessage(ComParams.PLAY_CONTROL_BACK);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showDialog() {
		mMedioControl.setControllerButtonEnabled(false);
		mMedioControl.mLandProgress.setVisibility(View.GONE);
		mMedioControl.mLandCurrentTime.setText("00:00");
		// DialogFactory mFactory = new DialogFactory(PlayerActivity.this);
		// mFactory.setBtnClickListener(new DialogBtnClickListener() {
		// @Override
		// public void btnOnClickListener(View paramView) {
		// handler.sendEmptyMessage(ComParams.PLAY_CONTROL_BACK);
		// }
		// });
		// mFactory.showExceptionDialog(DialogFactory.PLAY_ERROR);
	}

	public class MediaHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			ULog.d(tag, "MediaHandler msg.what = " + msg.what);
			switch (msg.what) {
			case ComParams.PREPARESTATE:
				if (isExit == true) {
					return;
				}
				else {
					setPrepareState();
				}
				break;
			case ComParams.PLAY_ERROR_RECEIVE:
				if (isExit == false) {
					// exceptionDialogState = DialogFactory.PLAY_ERROR;
					showDialog();
					exceptionDialogState = -1;
				}
				break;
			case ComParams.PLAY_CONTROL_VIEWTIMEOUT:
				ULog.d(tag, "Timeout isBack:" + isBack);
				// 加载视频超时
				if (!isBack) {
					if (mVideoView != null) {
						mVideoView.stop();
					}
					mVideoView.setCurrentViewState(MyVideoView.STATE_PLAYBACK_COMPLETED, "Timeout");
					// exceptionDialogState = DialogFactory.PLAY_ERROR;
					showDialog();
					exceptionDialogState = -1;
				}
				break;
			case ComParams.PLAY_CONTROL_BACK:
				// 退出视频播放：
				ULog.d(tag, "control_back");
				isBack = true;
				if (mVideoView != null) {
					/**
					 * 视频stopPlayback()函数，视频流不流畅时，容易卡住，先注释掉 但当视频不流畅时，退出时，视频还是会去stopPlayback()函数。
					 */
					mVideoView.setCurrentViewState(MyVideoView.STATE_PLAYBACK_COMPLETED, "CONTROL_BACK");
				}

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						finish();
					}
				}, 1000);

				break;
			case ComParams.PLAY_CONTROL_REPLAY:
				/**
				 * 重新播放： 播放完毕点播放按钮，或者点推荐页的【重播】
				 */
				ULog.d(tag, "PLAY_CONTROL_REPLAY");
				mMedioControl.setControllerButtonEnabled(false);
				mMedioControl.mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_selector);
				// loadingVideo();
				new MediaPlayerAsync().execute();
				break;
			case ComParams.PLAY_CONTROL_GONEWEBVIEW:
				ULog.d(tag, "MediaHandler PLAY_CONTROL_GONEWEBVIEW");
				try {
					ULog.d(tag, (null != mVideoView) ? "mVideoView is not null" : "mVideoView is null");
					ULog.d(tag, (null != mMedioControl) ? "mMedioControl is not null" : "mMedioControl is null");
					ULog.d(tag, (mVideoView.isPlaying()) ? "mVideoView is play" : "mVideoView is not play");
					ULog.d(tag, (mVideoView.getCurrentPosition() > 0) ? "mVideoView current position is >0"
							: "VideoView current position is <0");

					if (null != mVideoView && null != mMedioControl && mVideoView.isPlaying()
							&& mVideoView.getCurrentPosition() > 0) {
						ULog.d(tag,
								"GoneWebView gone mVideoView.getCurrentPosition() = " + mVideoView.getCurrentPosition());
						mVideoView.setCurrentViewState(MyVideoView.STATE_PLAYING,
								"MediaHandler:ComParams.PLAY_CONTROL_GONEWEBVIEW");
					}
					else {
						ULog.d(tag, "GoneWebView continue");
						this.sendEmptyMessageDelayed(ComParams.PLAY_CONTROL_GONEWEBVIEW, 1000);
					}
				} catch (Exception e) {
					ULog.d(tag, "MediaHandler exception " + e.toString());
				}
				break;
			case ComParams.PLAY_PALYlIST:
				ULog.d(tag, "MediaHandler PLAY_PALYlIST");
				// loadingVideo();
				new MediaPlayerAsync().execute();
				break;
			case ComParams.SHOW_DIALOGE:
				showDialog();
				break;
			}
		}
	}

	//
	public boolean sendMessage(Message msg) {
		return this.handler.sendMessage(msg);
	}

	public boolean sendEmptyMessage(int what) {
		return this.handler.sendEmptyMessage(what);
	}

	public boolean post(Runnable r) {
		return this.handler.post(r);
	}

	public void setPauseOrPlay() {
		this.mMedioControl.setPauseOrPlay();
	}

	public int getPlayerWidth() {
		return this.display.getWidth();

	}

	public int getPlayerHeight() {
		return this.display.getHeight();
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public int getCurpositon() {
		return curpositon;
	}

	public void setCurpositon(int curposition) {
		this.curpositon = curposition;
	}

	public String getDetailURL() {
		return detailURL;
	}

	public boolean getStatePrepared() {
		return mIsPrepared;
	}

	public void setPrepareState() {
		ULog.d(tag, "setPrepareState");
		if (mVideoView == null) {
			return;
		}
		// ULog.d(tag, "onPrepared  Head VideoState:" + mVideoView.getCurrentViewState());
		if (null != data && data.getInt(ComParams.PLAY_KEY_CURPOSITON) > 1000) {
			mVideoView.seekTo(data.getInt(ComParams.PLAY_KEY_CURPOSITON));
			data.remove(ComParams.PLAY_KEY_CURPOSITON);
		}

		// ULog.d(tag, "onPrepared  Head VideoState:" + mVideoView.getCurrentViewState());

		/**
		 * 1.setCurrentViewState(STATE_PREPARED) 2.启动自动隐藏控制栏 3.隐藏webvew
		 */
		if ((mVideoView.getCurrentViewState() == MyVideoView.STATE_PREPARED || mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYING)
				&& !mVideoView.isError()) {
			ULog.d(tag, "onPrepared  Head VideoState:" + mVideoView.getCurrentViewState());
			/**
			 * 1.视频处于就蓄状态 2.视频处理播放状态
			 */
			mVideoView.setCurrentViewState(MyVideoView.STATE_PLAYING, "onPrepared(MediaPlayer mp)");
			mVideoView.setPerPosition(1);
			if (loadingImage.isShown())
				loadingImage.setVisibility(View.GONE);
			handler.sendEmptyMessageDelayed(ComParams.PLAY_CONTROL_GONEWEBVIEW, 100);
			mMedioControl.mLandCurrentTime.setText("00:00");
			mMedioControl.mLandProgress.setVisibility(View.GONE);
			mMedioControl.mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_stop_btn_selector);
			ULog.d(tag, "mVideoView.getDuration()------>" + mVideoView.getDuration());
		}
		if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PAUSED && getCurpositon() > 0) {
			ULog.d(tag, "onPrepared1  Head VideoState:" + mVideoView.getCurrentViewState());
			// 进入其他应用(进入主页)，又返回，视频又自动 加载完成的情况
			setCurpositon(-1);
			// curpositon = -1;
			// mVideoView.pause();
		}
		ULog.d(tag, "onPrepared  End VideoState:" + mVideoView.getCurrentViewState());

		// 点播视频循环播放
		if (!playMode.equalsIgnoreCase(ComParams.PLAY_VIEW_LIVE) && isExecute == false) {
			ULog.d(tag, "onPrepared  getPlayList enable");
			handler.sendEmptyMessageDelayed(ComParams.GET_PALYlIST, 10000);
		}
		ULog.d(tag, "onPrepared  End VideoState:" + mVideoView.getCurrentViewState());
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		ULog.i(tag, "--> onWindowFocusChanged hasFocus = " + hasFocus);

		try {
			if (null != mVideoView && !hasFocus /* && mVideoView.isPlaying() */) {
				ULog.d(tag, "--> onWindowFocusChanged   focus false");
				// mVideoView.mCurrentViewOnFocus = false;
				VideoPause();

			}
			else if (null != mVideoView && hasFocus /* && !mVideoView.isPlaying() */) {
				ULog.d(tag, "--> onWindowFocusChanged  focus true");
				ULog.d(tag, "--> onWindowFocusChanged  getCurrentViewState " + mVideoView.getCurrentViewState());
				// mVideoView.mCurrentViewOnFocus = true;
				VideoStart();
			}
			else if (null == mVideoView && hasFocus) {
				ULog.d(tag, "--> onWindowFocusChanged  focus true - videoview is null");
				MyVideoView.mPlayFlag = false;
				VideoStart();
			}
		} catch (Exception e) {
			ULog.d(tag, "onWindowFocusChanged exception " + e.toString());
			e.printStackTrace();
		}
		super.onWindowFocusChanged(hasFocus);
	}

	private void VideoPause() {
		ULog.e(tag, "VideoPause");

		try {
			ULog.d(tag, "VideoPause Head VideoState:" + mVideoView.getCurrentViewState());

			if (null != mVideoView) {
				ULog.d(tag, "<<<<VideoPause currentPosition:" + currentPosition);
				currentPosition = mVideoView.getCurrentPosition();
				ULog.d(tag, ">>>>VideoPause currentPosition:" + currentPosition);
			}
			// }catch (Exception e){
			// ULog.d(tag, "VideoPause exception: " + e.toString());
			// e.printStackTrace();
			// }

			mMedioControl.setOnPause(true);
			if (null == data) {
				data = new Bundle();
			}
			data.putInt(ComParams.PLAY_KEY_CURPOSITON, currentPosition);

			/**
			 * 1. 取消状态栏显示 2. 取消屏幕常亮 3. 设置当前视频状态viewCurrentStatus
			 */

			ULog.d(tag, "VideoPause isBack = " + isBack);
			ULog.d(tag, "VideoPause playMode = " + playMode);
			ULog.d(tag, "VideoPause CurrentViewState = " + mVideoView.getCurrentViewState());

			if (!isBack) {
				if (playMode.equalsIgnoreCase(ComParams.PLAY_VIEW_LIVE)) {
					// 直播
					if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYING
							|| mVideoView.getCurrentViewState() == MyVideoView.STATE_PREPARED) {
						mVideoView.stop(MyVideoView.STATE_PAUSE_SHOWWEBVIEW);
						mMedioControl.show(0);
						mMedioControl.setControllerButtonEnabled(false);
						mMedioControl.setButtonBackgroundonCompletion();
						mVideoView.setCurrentViewState(MyVideoView.STATE_PLAYBACK_COMPLETED, "VideoPause()");
					}
				}
				else {
					// 点播
					if (mVideoView.getCurrentViewState() == MyVideoView.STATE_PLAYING
							|| mVideoView.getCurrentViewState() == MyVideoView.STATE_PREPARED) {
						/**
						 * 1.视频处于播放状态 2.视频处于就蓄状态
						 */
						mVideoView.pause(MyVideoView.STATE_PAUSE_SHOWWEBVIEW);
						mMedioControl.setControllerButtonEnabled(false);
						mMedioControl.setButtonBackgroundonCompletion();
						mMedioControl.show(0);
					}
				}
			}

			if (!playMode.equalsIgnoreCase(ComParams.PLAY_VIEW_LIVE)
					&& !playMode.equalsIgnoreCase(ComParams.PLAY_VIEW_SCHEDULE)) {
				mMedioControl.dismissController();
				mMedioControl.setVideoView(null);
				mVideoView.setOnCompletionListener(null);
				mVideoView.setOnPreparedListener(null);
				mVideoView.setOnErrorListener(null);
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							if (mVideoView != null) {
								mVideoView.stopPlayback();
								mVideoView = null;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}

		} catch (Exception e) {
			ULog.d(tag, "VideoPause exception: " + e.toString());
			e.printStackTrace();
		}
	}

	private void VideoStart() {
		ULog.e(tag, "VideoStart");
		if (null == mVideoView) {
			mVideoView = (MyVideoView) findViewById(R.id.surface_view);

			mMedioControl.setVideoView(mVideoView);
			mMedioControl.setOnPause(false);
			mMedioControl.show();
			mVideoView.setOnCompletionListener(this);
			mVideoView.setOnPreparedListener(this);
			mVideoView.setOnErrorListener(this);

			mVideoView.setCurrentViewState(MyVideoView.STATE_PAUSED, "VideoStart()1");
			mVideoView.setVideoPath(videoURL);
			mVideoView.requestFocus();
			if (null != data) {
				// curpositon = data.getInt(ComParams.PLAY_KEY_CURPOSITON);
				setCurpositon(data.getInt(ComParams.PLAY_KEY_CURPOSITON));
			}
			if (getCurpositon() > 0) {
				mVideoView.seekTo(getCurpositon());
				ULog.e(tag, "curpositon---->" + getCurpositon());
				mMedioControl.mLandCurrentTime.setText(mMedioControl.stringForTime(getCurpositon()));
				// mMedioControl.mLandTotalTime.setText(mMedioControl.stringForTime(curpositon));
			}
			else {
				mMedioControl.mLandCurrentTime.setText("00:00");
			}

			mMedioControl.mLandPlayOrStopBtn.setBackgroundResource(R.drawable.land_play_btn_normal);
		}

		if (null != mVideoView && (mVideoView.getCurrentViewState() == MyVideoView.STATE_PAUSED)) {
			// 如果视频被暂停挡住
			if (data != null) {
				int curpositontmp = data.getInt(ComParams.PLAY_KEY_CURPOSITON);
				if (curpositontmp > 1000) {
					setCurpositon(curpositontmp);
					ULog.d(tag, "VideoStart  curpositon   = " + getCurpositon());
					mVideoView.seekTo(getCurpositon());
				}
			}

			if (null == mMedioControl) {
				return;
			}
			mMedioControl.hideVideoController();
			mMedioControl.show(0);
		}

		/**
		 * 1.判断视频是否被取消播放，如果是，强制退出
		 */
		if (null != mVideoView) {
			mVideoView.setCurrentViewState(MyVideoView.STATE_ERROR, "VideoStart()2");
			handler.sendEmptyMessage(ComParams.PLAY_CONTROL_BACK);
		}
	}

	@Override
	protected void setBaseValues() {
		this.TAG = PlayerActivity.class.getName();
	}

	public static PlayerActivity getInstance() {
		return mediaPlayerActivity;
	}

	private final BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context context, final Intent intent) {
			ULog.d(TAG, "Screen off " + intent.getAction());
			final String action = intent.getAction();
			if (!playMode.equalsIgnoreCase(ComParams.PLAY_VIEW_LIVE)
					&& !playMode.equalsIgnoreCase(ComParams.PLAY_VIEW_SCHEDULE)) {
				if (Intent.ACTION_SCREEN_OFF.equals(action)) {
					// 退出当前Activity...
					if (mediaPlayerActivity != null && mediaPlayerActivity.hasWindowFocus()) {
						if (mMedioControl != null && mVideoView != null) {
							mMedioControl.dismissController();
							mMedioControl.setVideoView(null);
							ULog.e(TAG, "Screen off video pause");
							VideoPause();
						}
					}
					else {
						ULog.e(TAG, "mediaPlayerActivity is null");
					}
				}
				else if (Intent.ACTION_SCREEN_ON.equals(action)) {
					if (mediaPlayerActivity != null && mediaPlayerActivity.hasWindowFocus()) {
						if (null != mVideoView) {
							ULog.d(tag, "--> mScreenOffReceiver  focus true");
							ULog.d(tag,
									"--> mScreenOffReceiver  getCurrentViewState " + mVideoView.getCurrentViewState());
							// mVideoView.mCurrentViewOnFocus = true;
							VideoStart();
						}
						else if (null == mVideoView) {
							ULog.d(tag, "--> mScreenOffReceiver  focus true - videoview is null");
							MyVideoView.mPlayFlag = false;
							VideoStart();
						}
					}
				}
			}
		}
	};
}