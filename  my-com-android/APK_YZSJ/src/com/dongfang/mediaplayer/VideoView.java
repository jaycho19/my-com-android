package com.dongfang.mediaplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * @author yanghua 2013-4-18
 * 
 */
public class VideoView extends SurfaceView {
	private static final String	TAG					= VideoView.class.getName();

	private SurfaceHolder		holder;
	private Mediaplayer			mMediaplayer		= Mediaplayer.getInstance();

	public VideoView(Context context) {
		super(context);
		init();
	}

	public VideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public VideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {

		holder = getHolder();
		holder.addCallback(mMediaplayer);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}

}
