package com.dongfang.apad.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 属性为横条的时候，进度条高度只为5
 * 
 * @author dongfang
 * 
 */
public class MyProgressBar extends LinearLayout {
	public static final String	TAG	= MyProgressBar.class.getSimpleName();
	private ProgressBar			progressBar;
	private TextView			progressText;

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public MyProgressBar(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		this.setOrientation(LinearLayout.VERTICAL);
		// ULog.i(TAG, attrs.getStyleAttribute() + "");

		LayoutParams layoutparams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1);
		setLayoutParams(layoutparams);

		progressBar = new ProgressBar(context, attrs);

		if (android.R.attr.progressBarStyleHorizontal == attrs.getStyleAttribute()) {

			progressText = new TextView(context);
			progressText.setTextSize(14);
			progressText.setTextColor(android.graphics.Color.GREEN);
			progressText.getPaint().setFakeBoldText(true);
			progressText.setGravity(Gravity.CENTER_HORIZONTAL);
			progressText.setText("0%");
			addView(progressText, layoutparams);
			addView(progressBar, new LayoutParams(LayoutParams.FILL_PARENT, 5, 1));
		}
		else {
			addView(progressBar, layoutparams);
		}
	}

	public synchronized void setProgress(int progress) {
		if (null != progressText && progressBar.getProgress() != progress) {
			progressBar.setProgress(progress);
			progressText.setText((progress * 100 / progressBar.getMax()) + "%");
		}
	}

	public synchronized int getProgress() {
		return progressBar.getProgress();
	}

	public synchronized void setSecondaryProgress(int secondaryProgress) {

		if (progressBar.getSecondaryProgress() != secondaryProgress) {
			progressBar.setSecondaryProgress(secondaryProgress);
		}
	}

	public synchronized int getSecondaryProgress() {
		return progressBar.getSecondaryProgress();
	}

	public synchronized int getMax() {
		return progressBar.getMax();
	}

	public synchronized void setMax(int max) {
		progressBar.setMax(max);
	}

}
