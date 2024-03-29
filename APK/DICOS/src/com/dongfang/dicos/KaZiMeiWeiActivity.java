package com.dongfang.dicos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
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
import com.dongfang.dicos.kzmw.KeyValue;
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

	public static final String		tag						= "KaZiMeiWeiActivity";

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

	private FlingGallery			mGallery				= null;
	private LinearLayout			ll_fling_view			= null;
	private LinearLayout			ll_fling_desc_image		= null;
	public List<String>				flingViewList			= new ArrayList<String>();
	public List<SubMenuItem>		subMenuItemList			= new ArrayList<SubMenuItem>();

	private MyHorizontalScrollView	myHorizontalScrollView;
	private SubMenuLayout			subMenuLayout			= null;

	private GetInfoAsyncTask		getInfoAsyncTask		= null;
	private GetInfoTypeAsyncTask	getInfoTypeAsyncTask	= null;

	private ProgressDialog			progressDialog;

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

		getInfoAsyncTask = new GetInfoAsyncTask();
		getInfoAsyncTask.execute("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();

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
		}
		else {
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
			}
			else if (Util.isLogin(KaZiMeiWeiActivity.this)) {
				Toast.makeText(KaZiMeiWeiActivity.this, "您已登录", Toast.LENGTH_LONG).show();
			}
			else {
				intent = new Intent(KaZiMeiWeiActivity.this, LoginActivity.class);
				startActivity(intent);
			}

			break;
		case R.id.button_kzmw_signe:
			if (!Util.isLogin(KaZiMeiWeiActivity.this)) {
				Util.showDialogLogin(KaZiMeiWeiActivity.this);
			}
			else {
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
			onClickList[i] = new MyOnClickListener(category.getMenuList().get(i).key, i);
		}
		subMenuLayout.setOnClickListener(onClickList);

	}

	class MyOnClickListener implements OnClickListener {
		String	cateId;
		int		index;

		MyOnClickListener(String cateId, int index) {
			this.cateId = cateId;
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			ULog.e(tag, "MyOnClickListener");
			subMenuLayout.chageClicked(index);
			getInfoTypeAsyncTask = new GetInfoTypeAsyncTask();
			getInfoTypeAsyncTask.execute(cateId);
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
				image.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.fling_point_focsed_0));
			}
			else {
				image.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.fling_point_unfocsed_0));
			}
		}

		if (mGallery != null) {
			mGallery.setAdapter(new FlingAdapter(this, flingViewList));
			mGallery.refreshDrawableState();
		}
		else {
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
		ULog.d(tag, "4 keyCode = " + keyCode);

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (null != this.getInfoAsyncTask) {
				getInfoAsyncTask.cancel(true);
			}

			if (null != this.getInfoTypeAsyncTask) {
				getInfoTypeAsyncTask.cancel(true);
			}

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
	class GetInfoAsyncTask extends AsyncTask<String, String, Category> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = ProgressDialog.show(KaZiMeiWeiActivity.this, "", "数据加载中...", true);
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					ULog.d(tag, "OnCancelListener + getInfoTypeAsyncTask");

					getInfoAsyncTask.cancel(true);
				}
			});

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
				}
				else {
					category = Analysis
							.analysisKZMWInfo(setConfig.getString(ComParams.SHAREDPREFERENCES_KZME_IFNO, ""));
				}
			}
			else {
				category = Analysis.analysisKZMWInfo(setConfig.getString(ComParams.SHAREDPREFERENCES_KZME_IFNO, ""));
			}

			if (null != category && category.getMenuList().size() > 0) {
				String url, furl;
				String imageName, fimageName;
				for (KeyValue kv : category.getMenuList()) {
					url = kv.blur_img;
					furl = kv.focus_img;
					imageName = getCacheDir() + "/" + url.substring(url.lastIndexOf("/") + 1);
					fimageName = getCacheDir() + "/" + furl.substring(furl.lastIndexOf("/") + 1);

					if (!new File(imageName + "e").exists()) {
						saveFile(getInputStreamFromURL(url, 0), imageName);
					}
					if (!new File(fimageName + "fe").exists()) {
						saveFile(getInputStreamFromURL(furl, 0), fimageName + "f");
					}
				}
			}

			return category;
		}

		@Override
		protected void onPostExecute(Category result) {
			super.onPostExecute(result);

			// progressDialog.dismiss();

			if (null == result) {
				Toast.makeText(KaZiMeiWeiActivity.this, "获取数据失败", Toast.LENGTH_LONG).show();
			}
			else {

				// ULog.v(tag, "result = " + result.toString());

				initSubMenuLayout(result);
				myHorizontalScrollView.setVisibility(View.VISIBLE);
				// initFlingView(result.getImgUrls());

				subMenuLayout.chageClicked(0);
				new GetInfoTypeAsyncTask().execute(result.getMenuList().get(0).key);

			}
		}

		@Override
		protected void onCancelled() {
			progressDialog.dismiss();
			super.onCancelled();
		}

		private InputStream getInputStreamFromURL(String urlStr, int length) {
			HttpURLConnection urlConn = null;
			try {
				urlConn = (HttpURLConnection) (new URL(urlStr)).openConnection(java.net.Proxy.NO_PROXY);
				if (0 < length)
					urlConn.setRequestProperty("RANGE", "bytes=" + length + "-");

				urlConn.setReadTimeout(2000);
				urlConn.setConnectTimeout(2000);
				urlConn.setRequestMethod("GET");
				urlConn.setDoInput(true);
				urlConn.setDefaultUseCaches(false);
				urlConn.setChunkedStreamingMode(0);
				urlConn.connect();

				int code = ((HttpURLConnection) urlConn).getResponseCode();
				ULog.d(tag, "code = " + code);
				if (code > 299 || code < HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_NO_CONTENT) {
					return null;
				}

				return urlConn.getInputStream();
			} catch (Exception e) {
				ULog.d(tag, "Exception = " + e.getMessage());
			}
			return null;
		}

		/**
		 * 
		 * @param ins
		 *            输入流
		 * @param filename
		 *            文件名称 需要绝对路径
		 * @param handler
		 * @param array_param
		 * @return 返回文件名称 filename + "e"
		 */
		private String saveFile(InputStream ins, String filename) {
			if (TextUtils.isEmpty(filename))
				return filename;
			try {
				ULog.d(tag, "filename = " + filename);

				FileOutputStream outs = new FileOutputStream(filename);
				byte[] b = new byte[ComParams.BUF_SIZE];

				int num = ins.read(b);
				while (num != -1) {
					outs.write(b, 0, num);
					num = ins.read(b);
				}

				outs.flush();
				ins.close();
				outs.close();

				// 重命名
				(new File(filename)).renameTo(new File(filename + "e"));

			} catch (Exception e) {
				ULog.d(tag, "Exception = " + e.getMessage());
			}
			return filename + "e";
		}

	}

	/** 卡滋美味二级菜单 */
	class GetInfoTypeAsyncTask extends AsyncTask<String, String, Category> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if (null == progressDialog || !progressDialog.isShowing()) {
				progressDialog = ProgressDialog.show(KaZiMeiWeiActivity.this, "", "数据加载中...", true);
				progressDialog.setCancelable(true);
				progressDialog.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						ULog.d(tag, "OnCancelListener + getInfoTypeAsyncTask");
						getInfoTypeAsyncTask.cancel(true);
						progressDialog.cancel();
					}
				});
			}
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
				}
				else {
					imgUrls = Analysis.analysisKZMWInfo_type(setConfig.getString(
							ComParams.SHAREDPREFERENCES_KZME_IFNO_TYPE_CATE + params[0], ""));
					if (null != imgUrls && imgUrls.length > 0) {
						category = new Category();
						category.setImageUrls(Arrays.asList(imgUrls));
					}
				}
			}
			else {
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
			}
			else {
				// initSubMenuLayout(result);
				// myHorizontalScrollView.setVisibility(View.VISIBLE);
				ULog.d(tag, result.getImgUrls().get(0));
				initFlingView(result.getImgUrls());
			}

		}

		@Override
		protected void onCancelled() {
			progressDialog.dismiss();
			super.onCancelled();
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
