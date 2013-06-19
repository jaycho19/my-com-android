package com.dongfang.apad.view;

import java.io.File;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dongfang.apad.R;
import com.dongfang.apad.dl.DownloadInfo;
import com.dongfang.apad.dl.DownloadTask;
import com.dongfang.apad.dl.OnDownloadListener;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.DFException;

/**
 * KV 图类
 * 
 * @author dongfang
 * 
 */
public class MyImageView extends RelativeLayout {
	public static final String	TAG	= MyImageView.class.getSimpleName();

	private Context				context;
	private ImageView			imageView;
	private MyProgressBar		myProgressBar;

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyImageView(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		this.context = context;

		View convertView = LayoutInflater.from(context).inflate(R.layout.my_iamgeview, null);
		// myProgressBar = (MyProgressBar)
		// convertView.findViewById(R.id.imageview_myprogressbar);
		imageView = (ImageView) convertView.findViewById(R.id.imageview_show);
		this.setGravity(Gravity.CENTER);

		addView(convertView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	public void setImage(String url) {
		if (TextUtils.isEmpty(url))
			return;

		if (!url.startsWith("http://"))
			url = ComParams.URL_BASE_IMGAGE + url;
		DownloadInfo dlinfo = new DownloadInfo(context, url,true);
		if (new File(dlinfo.filePath).exists()) {
			Drawable defaultDraw = Drawable.createFromPath(dlinfo.filePath);
			if (null != defaultDraw) {
				imageView.setBackgroundDrawable(defaultDraw);
				// myProgressBar.setVisibility(View.GONE);
			}else{
				new File(dlinfo.filePath).delete();		//删除下载好的无法使用的图片
			}
		}
		else {
			// 下载图片
			new DownloadTask(context, dlinfo, new OnDownloadListener() {
				@Override
				public void updateProcess(DownloadInfo dlInfo) {}

				@Override
				public void preDownload(DownloadInfo dlInfo) {}

				@Override
				public void finishDownload(DownloadInfo dlInfo) {
					// myProgressBar.setVisibility(View.GONE);
					Drawable defaultDraw = Drawable.createFromPath(dlInfo.filePath);
					if (null != defaultDraw) {
						imageView.setBackgroundDrawable(defaultDraw);
					}
					// myProgressBar.setVisibility(View.GONE);
					imageView.refreshDrawableState();
					imageView.getBackground().setCallback(null);
				}

				@Override
				public void errorDownload(DownloadInfo dlInfo, DFException e) {}
			}).execute();
		}
	}

	public void setImageDrawable(Drawable drawable) {
		imageView.setBackgroundDrawable(drawable);
	}

	public void setBackgroundResource(int resid) {
		imageView.setBackgroundResource(resid);
	}

}