package com.dongfang.apad;

import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.ULog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author dongfang
 * 
 */
public class StartActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void setBaseValues() {
		TAG = StartActivity.class.getSimpleName();
	}

	private TextView	btnBack;
	private TextView	btnPageName;
	private TextView	tvTitle;
	private ImageView	imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);

		btnBack = (TextView) findViewById(R.id.tv_back);
		btnBack.setOnClickListener(this);
		
		btnPageName = (TextView) findViewById(R.id.tv_page_name);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		imageView = (ImageView) findViewById(R.id.imageView);

		Intent intent = getIntent();
		btnPageName.setText(intent.getIntExtra(ComParams.ACTIVITY_PAGENAME, R.string.app_name));
		tvTitle.setText(intent.getIntExtra(ComParams.ACTIVITY_PAGENAME, R.string.app_name));
		imageView.setImageResource(intent.getIntExtra(ComParams.ACTIVITY_IMAGE_SRC_ID, R.drawable.card_notice));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		ULog.d(TAG, v.toString());
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}
	}

}
