package com.dongfang.dicos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.dongfang.dicos.kzmw.Category;
import com.dongfang.dicos.kzmw.LoginActivity;
import com.dongfang.dicos.kzmw.LotteryActivity;
import com.dongfang.dicos.more.CityListActivity;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.Analysis;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;
import com.dongfang.dicos.view.FlingAdapter;
import com.dongfang.dicos.view.FlingGallery;
import com.dongfang.dicos.view.MyHorizontalScrollView;
import com.dongfang.dicos.view.SubMenuItem;
import com.dongfang.dicos.view.SubMenuLayout;

/**
 * 咔滋美味页面
 * 
 * @author dongfang
 * */
public class KaZiMeiWeiActivity extends Activity implements OnTouchListener, OnClickListener, ViewFactory {

	public static final String		tag					= "KaZiMeiWeiActivity";

	private TextView				tvTitle;

	/** 登录按钮 */
	private Button					bLogin;
	/** 签到按钮 */
	private Button					bSigne;
	/** Ipad 抽奖 */
	private Button					bIpad3;

	// /** 图片Flipper */
	// private ImageSwitcher vfInfo;
	//
	// /** 图片列表 */
	// private int[] array_img_name; // 所有图片的文件名称
	// private int array_param = 0; // 当前显示图片下标

	// private GestureDetector gestureDetector;

	private FlingGallery			mGallery			= null;
	private LinearLayout			ll_fling_view		= null;
	private LinearLayout			ll_fling_desc_image	= null;
	public List<String>				flingViewList		= new ArrayList<String>();
	public List<SubMenuItem>		subMenuItemList		= new ArrayList<SubMenuItem>();

	private MyHorizontalScrollView	myHorizontalScrollView;
	private SubMenuLayout			subMenuLayout		= null;

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

		// gestureDetector = new GestureDetector(new MyGestureDetector());

		// vfInfo = (ImageSwitcher) findViewById(R.id.imageswitcher_kzmw_info);
		// vfInfo.setFactory(this);
		// vfInfo.setOnTouchListener(new MyOnTouchListener());
		// vfInfo.setOnTouchListener(new MyOnTouchListener());
		// vfInfo.setInAnimation(this, R.anim.push_left_in);
		// vfInfo.setOutAnimation(this, R.anim.push_left_out);

		// FrameLayout scrollview_kzmw_info = (FrameLayout)
		// findViewById(R.id.framelayout_kzmw_info);
		// scrollview_kzmw_info.setOnTouchListener(new MyOnTouchListener());

		myHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.my_horizontal_scroll_view_kazm);
		subMenuLayout = (SubMenuLayout) findViewById(R.id.submenulayout);

		ll_fling_view = (LinearLayout) findViewById(R.id.ll_fling_view);
		ll_fling_desc_image = (LinearLayout) findViewById(R.id.ll_fling_desc_image);

		new getInfoAsyncTask().execute("");
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

	/** 初始化子菜单 */
	public void initSubMenuLayout(Category category) {
		subMenuLayout.setTVListText(category.getMenuList());
		int size = category.getMenuList().size();
		MyOnClickListener[] onClickList = new MyOnClickListener[size];
		for (int i = 0; i < size; i++) {
			onClickList[i] = new MyOnClickListener(category.getMenuList().get(i).key);
		}
		subMenuLayout.setOnClickListener(onClickList);

	}

	class MyOnClickListener implements OnClickListener {
		String	cateId;

		MyOnClickListener(String cateId) {
			this.cateId = cateId;
		}

		@Override
		public void onClick(View v) {
			ULog.e(tag, "MyOnClickListener");
			new getInfoTypeAsyncTask().execute(cateId);

		}

	}

	public void initFlingView(final List<String> flingViewList) {
		ll_fling_view = (LinearLayout) findViewById(R.id.ll_fling_view);
		ll_fling_desc_image = (LinearLayout) findViewById(R.id.ll_fling_desc_image);

		int size = flingViewList.size();
		ll_fling_desc_image.removeAllViews();
		for (int i = 0; i < size; i++) {
			ImageView image = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
			params.setMargins(5, 0, 5, 0);
			ll_fling_desc_image.addView(image, params);
			if (i == 0) {
				image.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.fling_point_focsed));
			} else {
				image.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.fling_point_unfocsed));
			}
		}

		if (mGallery != null) {
			mGallery.setAdapter(new FlingAdapter(this, flingViewList));
			mGallery.refreshDrawableState();
		} else {
			mGallery = new FlingGallery(this, null, ll_fling_desc_image);
			mGallery.setPaddingWidth(0);
			mGallery.setAdapter(new FlingAdapter(this, flingViewList));
			mGallery.setIsGalleryCircular(false);
		}

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		ll_fling_view.setOrientation(LinearLayout.HORIZONTAL);
		ll_fling_view.removeAllViews();
		ll_fling_view.addView(mGallery, layoutParams);
		ll_fling_view.setOnTouchListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Util.showExitDialog(this);

			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.ll_fling_view:
			return mGallery.onGalleryTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

	/** 初始化卡滋美味页面菜单数据 */
	class getInfoAsyncTask extends AsyncTask<String, String, Category> {
		ProgressDialog	progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = ProgressDialog.show(KaZiMeiWeiActivity.this, "", "数据加载中...");

		}

		@Override
		protected Category doInBackground(String... params) {
			Category category = null;
			SharedPreferences setConfig = getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
					Activity.MODE_PRIVATE);
			if (Util.isNetworkAvailable(KaZiMeiWeiActivity.this)) {
				String kzmwInfo = new HttpActions(KaZiMeiWeiActivity.this).getKaZiMeiWeiInfo();

				category = Analysis.analysisKZMWInfo(kzmwInfo);

				if (null != category) {
					setConfig.edit().putString(ComParams.SHAREDPREFERENCES_KZME_IFNO, kzmwInfo).commit();
				} else {
					category = Analysis
							.analysisKZMWInfo(setConfig.getString(ComParams.SHAREDPREFERENCES_KZME_IFNO, ""));
				}
			} else {
				category = Analysis.analysisKZMWInfo(setConfig.getString(ComParams.SHAREDPREFERENCES_KZME_IFNO, ""));
			}
			return category;
		}

		@Override
		protected void onPostExecute(Category result) {
			super.onPostExecute(result);

			progressDialog.dismiss();

			if (null == result) {
				Toast.makeText(KaZiMeiWeiActivity.this, "获取数据失败", Toast.LENGTH_LONG).show();
			} else {
				initSubMenuLayout(result);
				myHorizontalScrollView.setVisibility(View.VISIBLE);
				initFlingView(result.getImgUrls());

			}

		}

	}

	/** 卡滋美味二级菜单 */
	class getInfoTypeAsyncTask extends AsyncTask<String, String, Category> {

		ProgressDialog	progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = ProgressDialog.show(KaZiMeiWeiActivity.this, "", "数据加载中...");

		}

		@Override
		protected Category doInBackground(String... params) {
			ULog.i(tag, "cateId = " + params[0]);
			Category category = null;
			SharedPreferences setConfig = getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
					Activity.MODE_PRIVATE);
			String[] imgUrls = null;
			if (Util.isNetworkAvailable(KaZiMeiWeiActivity.this)) {
				String kzmwInfo_type = new HttpActions(KaZiMeiWeiActivity.this).getKaZiMeiWei_type(params[0]);
				ULog.i(tag, "kzmwInfo_type = " + kzmwInfo_type);

				imgUrls = Analysis.analysisKZMWInfo_type(kzmwInfo_type);

				if (null != imgUrls && imgUrls.length > 0) {
					category = new Category();
					category.setImageUrls(Arrays.asList(imgUrls));
					setConfig.edit()
							.putString(ComParams.SHAREDPREFERENCES_KZME_IFNO_TYPE_CATE + params[0], kzmwInfo_type)
							.commit();
				} else {
					imgUrls = Analysis.analysisKZMWInfo_type(setConfig.getString(
							ComParams.SHAREDPREFERENCES_KZME_IFNO_TYPE_CATE + params[0], ""));
					if (null != imgUrls && imgUrls.length > 0) {
						category = new Category();
						category.setImageUrls(Arrays.asList(imgUrls));
					}
				}
			} else {
				imgUrls = Analysis.analysisKZMWInfo_type(setConfig.getString(
						ComParams.SHAREDPREFERENCES_KZME_IFNO_TYPE_CATE + params[0], ""));
				if (null != imgUrls && imgUrls.length > 0) {
					category = new Category();
					category.setImageUrls(Arrays.asList(imgUrls));
				}
			}

			return category;
		}

		@Override
		protected void onPostExecute(Category result) {
			super.onPostExecute(result);

			progressDialog.dismiss();

			if (null == result) {
				Toast.makeText(KaZiMeiWeiActivity.this, "获取数据失败", Toast.LENGTH_LONG).show();
			} else {
				// initSubMenuLayout(result);
				// myHorizontalScrollView.setVisibility(View.VISIBLE);

				ULog.d(tag, result.getImgUrls().get(0));

				initFlingView(result.getImgUrls());
			}

		}

	}

	// private void showImage() {
	// ULog.d(tag, "showImage " + array_img_name.length + " " + array_param +
	// " " + array_img_name[array_param]);
	// Drawable drawable =
	// getResources().getDrawable(array_img_name[array_param]);
	// if (null != drawable) {
	// vfInfo.setImageDrawable(drawable);
	// } else {
	// vfInfo.setImageDrawable(null);
	// }
	// }

	// private void showPrevious() {
	// ULog.d(tag, "showPrevious");
	// if (array_param > 0) {
	// array_param--;
	// vfInfo.setInAnimation(KaZiMeiWeiActivity.this, R.anim.back_enter);
	// vfInfo.setOutAnimation(KaZiMeiWeiActivity.this, R.anim.back_exit);
	// showImage();
	// } else if (0 == array_param) {
	//
	// array_param = array_img_name.length - 1;
	// vfInfo.setInAnimation(KaZiMeiWeiActivity.this, R.anim.back_enter);
	// vfInfo.setOutAnimation(KaZiMeiWeiActivity.this, R.anim.back_exit);
	// showImage();
	// }
	// }
	//
	// private void showNext() {
	// ULog.d(tag, "showNext");
	// array_param++;
	// if (array_param < array_img_name.length) {
	// vfInfo.setInAnimation(KaZiMeiWeiActivity.this, R.anim.anim_enter);
	// vfInfo.setOutAnimation(KaZiMeiWeiActivity.this, R.anim.anim_exit);
	// showImage();
	//
	// } else if (array_param >= array_img_name.length) {
	// array_param = 0;
	// vfInfo.setInAnimation(KaZiMeiWeiActivity.this, R.anim.anim_enter);
	// vfInfo.setOutAnimation(KaZiMeiWeiActivity.this, R.anim.anim_exit);
	// showImage();
	// }
	// }

	// private class MyOnTouchListener implements View.OnTouchListener {
	// public boolean onTouch(View v, MotionEvent event) {
	// // ULog.d(tag, "onTouch " + v.toString());
	// return gestureDetector.onTouchEvent(event);
	// }
	// };

	// private class MyGestureDetector extends SimpleOnGestureListener {
	//
	// @Override
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// // ULog.d(tag, "e1.getX() = " + e1.getX() + ", e2.getX() = " +
	// // e2.getX() + ", velocityX = " + velocityX);
	// if (e1.getX() - e2.getX() > 120 && Math.abs(velocityX) > 200) {
	// showNext();
	// } else if (e2.getX() - e1.getX() > 120 && Math.abs(velocityX) > 200) {
	// showPrevious();
	// }
	// return true;
	// }
	//
	// @Override
	// public boolean onDown(MotionEvent e) {
	// // ULog.d(tag, "onDown " + e.toString());
	// return true;
	// }
	//
	// @Override
	// public void onShowPress(MotionEvent e) {
	// // ULog.d(tag, "onShowPress " + e.toString());
	// super.onShowPress(e);
	// }
	//
	// @Override
	// public void onLongPress(MotionEvent e) {
	// // ULog.d(tag, "onLongPress " + e.toString());
	// }
	//
	// @Override
	// public boolean onSingleTapConfirmed(MotionEvent e) {
	// ULog.d(tag, "onSingleTapConfirmed " + e.toString());
	// return false;
	// }
	//
	// @Override
	// public boolean onSingleTapUp(MotionEvent e) {
	// // ULog.d(tag, "onSingleTapUp " + e.toString());
	// return true;
	// }
	//
	// @Override
	// public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	// float distanceY) {
	// // ULog.d(tag, "onScroll " + e1.toString());
	// return super.onScroll(e1, e2, distanceX, distanceY);
	// }
	//
	// }

}
