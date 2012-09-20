package com.dongfang.dicos.view;

import java.io.File;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dongfang.dicos.R;
import com.dongfang.dicos.dl.DownloadIMG;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;


/**
 * 
 * @author dongfang
 *
 */
public class MyImageView extends LinearLayout {
	private Context			context;

	private Handler			handler;

	private ImageView		imageView;
	private MyProgressBar	myProgressBar;

	private String			imageName;

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
		handler = new MyImageViewHandler();
		View convertView = LayoutInflater.from(context).inflate(R.layout.my_iamgeview, null);

		myProgressBar = (MyProgressBar) convertView.findViewById(R.id.imageview_myprogressbar);
		imageView = (ImageView) convertView.findViewById(R.id.imageview_show);

		addView(convertView);
	}

	public void setImage(String url) {

		imageName = url.substring(url.lastIndexOf("/") + 1);

		if (new File(context.getCacheDir() + "/" + imageName + "e").exists()) {
			Drawable defaultDraw = Drawable.createFromPath(context.getCacheDir() + "/" + imageName + "e");
			if (null != defaultDraw) {
				imageView.setBackgroundDrawable(defaultDraw);
				myProgressBar.setVisibility(View.GONE);
			}
		} else {
			// ÏÂÔØÂß¼­
			ULog.d("MyImageView", "new DownloadIMG(context, handler, url, imageName, 0).start()");
			new DownloadIMG(context, handler, url, imageName, 0).start();
		}

	}

	class MyImageViewHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ComParams.HANDLER_IMAGE_DOWNLOAD_END:
				myProgressBar.setVisibility(View.GONE);
				Drawable defaultDraw = Drawable.createFromPath(context.getCacheDir() + "/" + imageName + "e");
				if (null != defaultDraw) {
					imageView.setBackgroundDrawable(defaultDraw);
				}
				myProgressBar.setVisibility(View.GONE);
				imageView.refreshDrawableState();
				break;
			case ComParams.HANDLER_IMAGE_DOWNLOAD_ING:
				ULog.d("MyImageView", "downloading " + imageName + " = " + msg.arg1);
				myProgressBar.setProgress(msg.arg1);
				break;
			case ComParams.HANDLER_IMAGE_DOWNLOAD_START:
				myProgressBar.setMax(msg.arg1);
				break;
			case ComParams.HANDLER_IMAGE_DOWNLOAD_ERROR:
				break;
			case ComParams.HANDLER_IMAGE_DOWNLOAD_NONET:
				break;
			}
		}

	}

}
