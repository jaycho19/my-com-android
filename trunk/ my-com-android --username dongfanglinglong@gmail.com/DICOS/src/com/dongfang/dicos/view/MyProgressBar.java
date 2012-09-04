package com.dongfang.dicos.view;

import com.dongfang.dicos.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyProgressBar extends LinearLayout {

	private ProgressBar	progressBar;
	private TextView	progressText;

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context);
	}

	public MyProgressBar(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.setOrientation(LinearLayout.VERTICAL);
		View convertView = LayoutInflater.from(context).inflate(R.layout.progressbar_load, null);

		progressBar = (ProgressBar) convertView.findViewById(R.id.load_progressbar);
		progressText = (TextView) convertView.findViewById(R.id.load_textview);

		progressText.setText((progressBar.getProgress() * 100 / progressBar.getMax()) + "%");

		addView(convertView);
	}

	public synchronized void setProgress(int progress) {
		if (progressBar.getProgress() != progress) {
			progressBar.setProgress(progress);
			progressText.setText((progressBar.getProgress() * 100 / progressBar.getMax()) + "%");
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
