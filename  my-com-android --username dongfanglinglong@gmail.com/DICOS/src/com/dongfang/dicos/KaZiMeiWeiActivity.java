package com.dongfang.dicos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.dongfang.dicos.kzmw.LoginActivity;
import com.dongfang.dicos.kzmw.LotteryActivity;
import com.dongfang.dicos.more.CityListActivity;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 咔滋美味页面
 * 
 * @author dongfang
 * */
public class KaZiMeiWeiActivity extends Activity implements OnClickListener, ViewFactory {

	public static final String	tag			= "KaZiMeiWeiActivity";

	private TextView			tvTitle;

	/** 登录按钮 */
	private Button				bLogin;
	/** 签到按钮 */
	private Button				bSigne;
	/** Ipad 抽奖 */
	private Button				bIpad3;

	/** 图片Flipper */
	private ImageSwitcher		vfInfo;

	/** 图片列表 */
	private int[]				array_img_name;					// 所有图片的文件名称
	private int					array_param	= 0;					// 当前显示图片下标

	private GestureDetector		gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kazimeiwei);

		tvTitle = (TextView) findViewById(R.id.textview_kzmw_top_title);
		tvTitle.setOnClickListener(this);

		bLogin = (Button) findViewById(R.id.button_kzmw_login);
		bLogin.setOnClickListener(this);

		bSigne = (Button) findViewById(R.id.button_kzmw_signe);
		bSigne.setOnClickListener(this);

		bIpad3 = (Button) findViewById(R.id.button_kzmw_ipad3);
		bIpad3.setOnClickListener(this);

		gestureDetector = new GestureDetector(new MyGestureDetector());

		vfInfo = (ImageSwitcher) findViewById(R.id.imageswitcher_kzmw_info);
		vfInfo.setFactory(this);
		vfInfo.setOnTouchListener(new MyOnTouchListener());
		// vfInfo.setOnTouchListener(new MyOnTouchListener());
		// vfInfo.setInAnimation(this, R.anim.push_left_in);
		// vfInfo.setOutAnimation(this, R.anim.push_left_out);

		// FrameLayout scrollview_kzmw_info = (FrameLayout)
		// findViewById(R.id.framelayout_kzmw_info);
		// scrollview_kzmw_info.setOnTouchListener(new MyOnTouchListener());

		array_img_name = new int[] { R.drawable.image0, R.drawable.image1, R.drawable.image2, R.drawable.image3,
				R.drawable.image4, R.drawable.image5, R.drawable.image6 };

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		ULog.d(tag, "onStart");
		showImage();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		/** 登录状态，不显示登录按钮 */
		ULog.d(tag, "onResume = " + bLogin.isShown());
		if (Util.isLogin(this)) {
			bLogin.setVisibility(View.GONE);
		} else {
			bLogin.setVisibility(View.VISIBLE);
		}

		if (TextUtils.isEmpty(ComParams.IPAREA)) {
			Util.iniIPArea(this);
		}

		if (ComParams.IPAREA.length() > 5)
			tvTitle.setText(ComParams.IPAREA.substring(0, 5) + "***");
		else if (ComParams.IPAREA.length() > 0)
			tvTitle.setText(ComParams.IPAREA);
	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		Intent intent;
		switch (v.getId()) {
		case R.id.textview_kzmw_top_title:
			intent = new Intent(KaZiMeiWeiActivity.this, CityListActivity.class);
			startActivity(intent);
			break;
		case R.id.button_kzmw_login:
			if (!Util.isNetworkAvailable(KaZiMeiWeiActivity.this)) {
				Util.showDialogSetNetWork(KaZiMeiWeiActivity.this);
			} else if (Util.isLogin(KaZiMeiWeiActivity.this)) {
				Toast.makeText(KaZiMeiWeiActivity.this, "您已登录", Toast.LENGTH_LONG).show();
			} else {
				intent = new Intent(KaZiMeiWeiActivity.this, LoginActivity.class);
				startActivity(intent);
			}

			break;
		case R.id.button_kzmw_signe:
			if (!Util.isLogin(KaZiMeiWeiActivity.this)) {
				Util.showDialogLogin(KaZiMeiWeiActivity.this);
			} else {
				intent = new Intent(KaZiMeiWeiActivity.this, StoreSearchActivity.class);
				intent.putExtra("visibility", true);
				startActivity(intent);

			}
			break;
		case R.id.button_kzmw_ipad3:
			intent = new Intent(KaZiMeiWeiActivity.this, LotteryActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public View makeView() {
		ULog.d(tag, "makeView");
		ImageView i = new ImageView(this);
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT));
		return i;
	}

	private void showImage() {
		ULog.d(tag, "showImage " + array_img_name.length + " " + array_param + " " + array_img_name[array_param]);
		Drawable drawable = getResources().getDrawable(array_img_name[array_param]);
		if (null != drawable) {
			vfInfo.setImageDrawable(drawable);
		} else {
			vfInfo.setImageDrawable(null);
		}
	}

	private void showPrevious() {
		ULog.d(tag, "showPrevious");
		if (array_param > 0) {
			array_param--;
			vfInfo.setInAnimation(KaZiMeiWeiActivity.this, R.anim.back_enter);
			vfInfo.setOutAnimation(KaZiMeiWeiActivity.this, R.anim.back_exit);
			showImage();
		} else if (0 == array_param) {

			array_param = array_img_name.length - 1;
			vfInfo.setInAnimation(KaZiMeiWeiActivity.this, R.anim.back_enter);
			vfInfo.setOutAnimation(KaZiMeiWeiActivity.this, R.anim.back_exit);
			showImage();
		}
	}

	private void showNext() {
		ULog.d(tag, "showNext");
		array_param++;
		if (array_param < array_img_name.length) {
			vfInfo.setInAnimation(KaZiMeiWeiActivity.this, R.anim.anim_enter);
			vfInfo.setOutAnimation(KaZiMeiWeiActivity.this, R.anim.anim_exit);
			showImage();

		} else if (array_param >= array_img_name.length) {
			array_param = 0;
			vfInfo.setInAnimation(KaZiMeiWeiActivity.this, R.anim.anim_enter);
			vfInfo.setOutAnimation(KaZiMeiWeiActivity.this, R.anim.anim_exit);
			showImage();
		}
	}

	private class MyOnTouchListener implements View.OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			// ULog.d(tag, "onTouch " + v.toString());
			return gestureDetector.onTouchEvent(event);
		}
	};

	private class MyGestureDetector extends SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			// ULog.d(tag, "e1.getX() = " + e1.getX() + ", e2.getX() = " +
			// e2.getX() + ", velocityX = " + velocityX);
			if (e1.getX() - e2.getX() > 120 && Math.abs(velocityX) > 200) {
				showNext();
			} else if (e2.getX() - e1.getX() > 120 && Math.abs(velocityX) > 200) {
				showPrevious();
			}
			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// ULog.d(tag, "onDown " + e.toString());
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// ULog.d(tag, "onShowPress " + e.toString());
			super.onShowPress(e);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// ULog.d(tag, "onLongPress " + e.toString());
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			ULog.d(tag, "onSingleTapConfirmed " + e.toString());
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// ULog.d(tag, "onSingleTapUp " + e.toString());
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			// ULog.d(tag, "onScroll " + e1.toString());
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

	}

}
