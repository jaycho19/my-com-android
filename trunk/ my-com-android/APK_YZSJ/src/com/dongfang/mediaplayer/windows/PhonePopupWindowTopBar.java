package com.dongfang.mediaplayer.windows;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dongfang.mediaplayer.Mediaplayer;
import com.dongfang.mediaplayer.Mediaplayer.VIDEOSIZESTATE;
import com.dongfang.mediaplayer.constants.Constants;
import com.dongfang.yzsj.R;

/**
 * 
 * @author dongfang
 * 
 */
public class PhonePopupWindowTopBar extends PopupWindow implements OnClickListener {
	public static final String	TAG					= PhonePopupWindowTopBar.class.getName();
	private Context				mContext;
	private LayoutInflater		mInflater;
	private final float			mPercentOfWindow	= (float) 0.15;

	private TextView			mVideoTitle;
	private Button				btnBack;
	private BasePopupWindow		basePop				= BasePopupWindow.getInstance();
	private Mediaplayer			mMediaplayer		= Mediaplayer.getInstance();

	public PhonePopupWindowTopBar(Context context) {
		super(context);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		View contentView = mInflater.inflate(R.layout.popupwin_top, null);
		initView(contentView);
		setupView();
		this.setContentView(contentView);
	}

	private void initView(View view) {
		this.setTouchable(true);
		// this.setFocusable(true);
		// this.setOutsideTouchable(true);
		// this.setAnimationStyle(R.style.bottom_popupwin_anim_style);
		view.setFocusableInTouchMode(true);

		mVideoTitle = (TextView) view.findViewById(R.id.txt_video_title);
		btnBack = (Button) view.findViewById(R.id.btn_video_back);
	}

	private void setupView() {

		btnBack.setOnClickListener(this);
	}

	public void show() {
		show(0, 0);
	}

	public void show(int xOffset, int yOffset) {
		this.setWidth(Constants.SCREEN_WIDTH_LANDSCAPE);
		this.setHeight((int) (Constants.SCREEN_HEIGHT_LANDSCAPE * mPercentOfWindow));

		this.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.video_top_bg));

		this.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.TOP, xOffset, yOffset);
		this.update();
		setVideoScale();

	}

	public void setVideoScale() {
		String currentScale = null;
		if (mMediaplayer.getVideoSizeRatio() == VIDEOSIZESTATE.ORIGINAL) {
			currentScale = mContext.getString(R.string.video_scale_original);
		}
		else if (mMediaplayer.getVideoSizeRatio() == VIDEOSIZESTATE.FULL) {
			currentScale = mContext.getString(R.string.video_scale_full);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_video_back:
			basePop.sendMessage(BasePopupWindow.PRESS_BACK_BTN, null);
			break;
		}
	}

}
