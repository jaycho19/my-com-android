package com.dongfang.dicos.view;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dongfang.dicos.R;
import com.dongfang.dicos.dl.DownloadIMG;
import com.dongfang.dicos.kzmw.KeyValue;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

public class SubMenuLayout extends LinearLayout {

	private final String			TAG				= "SubMenuLayout";

	private Context					context;
	// /** TextView 对象列表 */
	// private SubMenuItem[] tvList;

	/** 二级菜单，图片菜单列表 */
	private ImageView[]				ivList;

	/** 显示的图片 */
	private List<KeyValue>			menulist;

	private int						length			= 0;

	private Handler					handler			= null;

	private static SubMenuLayout	submenuLayout	= null;

	public static SubMenuLayout getSubMenuLayout(Context context) {
		if (null == submenuLayout)
			submenuLayout = new SubMenuLayout(context);
		return submenuLayout;

	}

	public SubMenuLayout(Context context) {
		super(context);
		this.context = context;
		handler = new MyHandler();
		// initSubMenuLayout();
	}

	public SubMenuLayout(Context context, AttributeSet paramAttributeSet) {
		super(context, paramAttributeSet);
		this.context = context;
		handler = new MyHandler();
		// initSubMenuLayout();
	}

	// private void initSubMenuLayout() {
	// String[] tvListText = getResources().getStringArray(R.array.sub_manu);
	// length = tvListText.length;
	// // tvList = new SubMenuItem[length];
	// ivList = new ImageView[length];
	//
	// for (int i = 0; i < length; i++) {
	// ivList[i] = new ImageView(context);
	// String filename = menulist[i]
	// // Drawable d = Drawable.createFromPath(context.getCacheDir()+"/" +
	// default_icon.getDefaultPicURL());
	// // if ()
	// //ivList[i].setBackgroundDrawable(d);
	//
	//
	// // tvList[i] = (SubMenuItem)
	// LayoutInflater.from(context).inflate(R.layout.textview_sub, null);
	// // tvList[i].setText(tvListText[i]);
	// // tvList[i].setid(3 + "");
	// //this.addView(tvList[i]);
	//
	// }
	// }

	public void setTVListText(List<KeyValue> list) {
		menulist = list;
		length = list.size();
		refresh();
	}

	public void refresh() {
		removeAllViews();
		ivList = new ImageView[length];
		for (int i = 0; i < length; i++) {
			String url = menulist.get(i).blur_img;
			String furl = menulist.get(i).focus_img;
			String imageName = url.substring(url.lastIndexOf("/") + 1);
			String fimageName = furl.substring(furl.lastIndexOf("/") + 1);

			ivList[i] = new ImageView(context);

			ivList[i].setOnTouchListener(new MyTouchListener(i));

			if (new File(context.getCacheDir() + "/" + imageName + "e").exists()) {
				final Drawable d = Drawable.createFromPath(context.getCacheDir() + "/" + imageName + "e");
				if (null != d) {
					ivList[i].setBackgroundDrawable(d);
					// final Drawable fd =
					// Drawable.createFromPath(context.getCacheDir() + "/" +
					// fimageName + "e");
					// if (new File(context.getCacheDir() + "/" + fimageName +
					// "e").exists() && fd != null) {
					// ivList[i].setOnTouchListener(new OnTouchListener() {
					// @Override
					// public boolean onTouch(View v, MotionEvent event) {
					// if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// ULog.i(TAG, "touch");
					// v.setBackgroundDrawable(fd);
					// } else if (event.getAction() == MotionEvent.ACTION_UP) {
					// v.setBackgroundDrawable(d);
					// }
					// return false;
					// }
					// });
					// }
				} else {
					ivList[i].setBackgroundResource(R.drawable.submenu_def);
				}
			} else {
				ivList[i].setBackgroundResource(R.drawable.submenu_def);
				new DownloadIMG(context, handler, url, imageName, i).start();
				new DownloadIMG(context, handler, furl, fimageName + "f", i).start();
			}

			double d = Util.getWindowWidth(context) / 480.0;
			this.addView(ivList[i], new LayoutParams((int) (96 * d), (int) (48 * d)));
		}
	}

	public void setOnClickListener(OnClickListener[] clickListener) {
		for (int i = 0, l = Math.min(length, clickListener.length); i < l; i++) {
			ivList[i].setOnClickListener(clickListener[i]);
		}
	}

	/**
	 * 初始化图片子菜单
	 * 
	 * @param i
	 */
	private void initImageMenu(int i) {
		String url = menulist.get(i).blur_img;
		String imageName = url.substring(url.lastIndexOf("/") + 1);

		ivList[i] = new ImageView(context);
		if (new File(context.getCacheDir(), imageName + "e").exists()) {
			ULog.i(TAG, "11111");
			final Drawable d = Drawable.createFromPath(context.getCacheDir() + "/" + imageName + "e");
			if (null != d) {
				ULog.i(TAG, "22222");
				ivList[i].setBackgroundDrawable(d);
				String furl = menulist.get(i).focus_img;
				String fimageName = furl.substring(furl.lastIndexOf("/") + 1);
				final Drawable fd = Drawable.createFromPath(context.getCacheDir() + "/" + fimageName + "e");
				if (new File(context.getCacheDir(), fimageName + "e").exists() && fd != null) {
					ULog.i(TAG, "33333");
					ivList[i].setOnTouchListener(new OnTouchListener() {
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							if (event.getAction() == MotionEvent.ACTION_DOWN) {
								v.setBackgroundDrawable(fd);
							} else if (event.getAction() == MotionEvent.ACTION_UP) {
								v.setBackgroundDrawable(d);
							}
							return false;
						}
					});
				}
			}
			ivList[i].refreshDrawableState();
		}
		this.refreshDrawableState();
	}

	class MyTouchListener implements OnTouchListener {

		private int	index	= 0;

		public MyTouchListener(int i) {
			index = i;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE ) {
				String furl = menulist.get(index).focus_img;
				String fimageName = furl.substring(furl.lastIndexOf("/") + 1);
				final Drawable fd = Drawable.createFromPath(context.getCacheDir() + "/" + fimageName + "fe");
				if (new File(context.getCacheDir() + "/" + fimageName + "fe").exists() && fd != null) {
					ivList[index].setBackgroundDrawable(fd);
				}
			} else {
				String url = menulist.get(index).blur_img;
				String imageName = url.substring(url.lastIndexOf("/") + 1);
				if (new File(context.getCacheDir() + "/" + imageName + "e").exists()) {
					ivList[index].setBackgroundDrawable(Drawable.createFromPath(context.getCacheDir() + "/" + imageName
							+ "e"));
				}
			}
			return false;
		}

	}

	@SuppressLint("HandlerLeak")
	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ComParams.HANDLER_IMAGE_DOWNLOAD_END:
				// ULog.i(TAG, "ComParams.HANDLER_IMAGE_DOWNLOAD_END");
				int i = msg.arg1;
				if (i > -1 && i < length) {
					// initImageMenu(i);
					refresh();
				}
				break;
			}
		}
	}

}