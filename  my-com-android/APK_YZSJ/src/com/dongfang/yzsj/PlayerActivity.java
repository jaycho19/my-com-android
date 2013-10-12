package com.dongfang.yzsj;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.dongfang.utils.ULog;
import com.dongfang.yzsj.fragment.VideoPlayerFragment;
import com.dongfang.yzsj.params.ComParams;

/**
 * 
 * @author yanghua
 * 
 */
public class PlayerActivity extends FragmentActivity {
	private static final String TAG = PlayerActivity.class.getSimpleName();

	private VideoPlayerFragment mVideoPlayer;
	private boolean mIsSavedInstanceState = false;
	private boolean hasFocus;
	private String url;
	
	
	public static String contentId;// 媒体id
	public static int clipId; // 集数

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_play);
		ULog.d(TAG, "onCreate");

		// if (savedInstanceState == null) {
		// ULog.d(TAG, "savedInstanceState is null");
		// // bundle = getIntent().getExtras();
		// url =
		// "http://vod02.v.vnet.mobi/mobi/vod/st02/2013/09/21/Q350_2009383882.3gp?sid=31776106&msisdn=18900000002&timestamp=20130922095552&H=0020&channelid=&nodeid=&videotype=2&encrypt=b6f65ce6ff49445c335169acca22b394&ua=30&nettype=2&imsi=null&spid=00000124";
		// creatVideoFragment(url);
		// }

		contentId = getIntent().getStringExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID);
		clipId = getIntent().getIntExtra(ComParams.INTENT_MOVIEDETAIL_CLIPID, 1);

		url = getIntent().getStringExtra(ComParams.PLAY_KEY_VIDEOURL);
		creatVideoFragment(url);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	protected void onDestroy() {
		ULog.d(TAG, "onDestroy");
		super.onDestroy();
	}

	/**
	 * 
	 * @param bundle
	 */
	private void creatVideoFragment(String uri) {
		ULog.d(TAG, "creatVideoFragment");
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (mVideoPlayer != null) {
			transaction.remove(mVideoPlayer);
			((FrameLayout) findViewById(R.id.player_view)).removeAllViews();
		}
		mVideoPlayer = VideoPlayerFragment.newInstance(uri);
		mVideoPlayer.setScreenOrientation(true);
		transaction.replace(R.id.player_view, mVideoPlayer, "A");
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.commit();
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN && event.getAction() != KeyEvent.ACTION_UP) {
			mVideoPlayer.sendMessage(VideoPlayerFragment.SHOW_VOICE_POPUPWINDOW_ADJUST_VOLUME,
					VideoPlayerFragment.ADJUST_LOWER);
			return true;
		}
		else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP && event.getAction() != KeyEvent.ACTION_UP) {
			mVideoPlayer.sendMessage(VideoPlayerFragment.SHOW_VOICE_POPUPWINDOW_ADJUST_VOLUME,
					VideoPlayerFragment.ADJUST_RAISE);
			return true;
		}
		else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
			finish();
			return true;
		}
		else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU && event.getAction() != KeyEvent.ACTION_UP) {
			mVideoPlayer.showOrDissmissFirstLayerPopupWin();
			return true;
		}
		else {
			return super.dispatchKeyEvent(event);
		}

	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		ULog.i(TAG, "--> onWindowFocusChanged hasFocus = " + hasFocus);
		this.hasFocus = hasFocus;
		if (hasFocus && mIsSavedInstanceState) {
			creatVideoFragment(url);
			mIsSavedInstanceState = false;
		}
		else {
			mVideoPlayer.onWindowFocusChanged(hasFocus);
		}

	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		ULog.i(TAG, "onConfigurationChanged");
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {}
		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {}
	}
	
	/** 视频播放 */
	public static void toPlay(Context context, String url, Bundle data) {
		if (ComParams.IS_PLAYER_ACTIVITY) {
			Intent intent = new Intent(context, PlayerActivity.class);
			intent.putExtra(ComParams.PLAY_KEY_VIDEOURL, url);
			intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID,
					data.getString(ComParams.INTENT_MOVIEDETAIL_CONNENTID));
			intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CLIPID, data.getInt(ComParams.INTENT_MOVIEDETAIL_CLIPID, 1));
			context.startActivity(intent);
		}
		else {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			String type = "video/*";
			Uri uri = Uri.parse(url);
			intent.setDataAndType(uri, type);
			context.startActivity(intent);
		}
	}

}
