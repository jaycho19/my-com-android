package com.dongfang.dicos.view;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dongfang.dicos.R;
import com.dongfang.dicos.dl.DownloadIMG;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.Util;

/**
 * 
 * @author dongfang
 * 
 */
public class MyImageView extends RelativeLayout {
	public static final String	tag	= "MyImageView";

	private Context				context;

	private Handler				handler;

	private ImageView			imageView;
	private MyProgressBar		myProgressBar;

	private String				imageName;

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

	/** �ж�ͼƬ�Ƿ�������� */
	public boolean isImgExsits() {
		return new File(context.getCacheDir() + "/" + imageName + "e").exists();
	}

	public void setImage(String url) {

		imageName = url.substring(url.lastIndexOf("/") + 1);

		if (new File(context.getCacheDir() + "/" + imageName + "e").exists()) {
			setImageBy(context.getCacheDir() + "/" + imageName + "e");
		} else {
			// �����߼�
			// ULog.d("MyImageView",
			// "new DownloadIMG(context, handler, url, imageName, 0).start()");
			new DownloadIMG(context, handler, url, imageName, 0).start();
		}

	}

	/**
	 * ���� {@linkplain this#imageView} ����ͼƬ
	 * 
	 * @param filePath
	 */
	public void setImageBy(String filePath) {
		// ULog.i(tag, filePath);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // ֵ��Ϊtrue��ô��������ʵ�ʵ�bitmap��Ҳ����������ڴ�ռ������ͱ����ڴ�����ˡ������������ǲ�ѯͼƬ����Ϣ�����оͰ���ͼƬ��С��Ϣ
		// ͨ�����bitmap��ȡͼƬ�Ŀ�͸�
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		// ��ʾ��Ļ���
		int windowWidth = Util.getWindowWidth(context);

		// ULog.d(tag, "windowWidth = " + windowWidth);

		if (bitmap != null) {
			// ULog.d(tag, "options.outWidth = " + options.outWidth);

			options.inSampleSize = 1; // ����1ʱ������û�н�������
			// ���ͼƬ��ʵ�ߴ����������Ļ�ߴ磬�ͽ��еȱ����ţ������ڴ�����
			if (options.outWidth >= windowWidth * 2) {
				options.inSampleSize = (int) Math.ceil(options.outWidth * 1.0 / windowWidth);
			}

			options.inJustDecodeBounds = false;
			imageView.setImageBitmap(BitmapFactory.decodeFile(filePath, options));

			// ULog.d(tag, "options.outWidth = " + options.outWidth);
			// ULog.d(tag, "imageView.getWidth() = " + imageView.getWidth());

			// �ȱ���������������Ӧ��Ļ��С�������ڴ治����С
			imageView.setLayoutParams(new RelativeLayout.LayoutParams(windowWidth, windowWidth * options.outHeight
					/ options.outWidth));

		} else {
			Drawable defaultDraw = Drawable.createFromPath(filePath);
			if (null != defaultDraw) {
				imageView.setBackgroundDrawable(defaultDraw);
				imageView.setLayoutParams(new RelativeLayout.LayoutParams(windowWidth, windowWidth
						* defaultDraw.getIntrinsicHeight() / defaultDraw.getIntrinsicWidth()));

				// this.setLayoutParams(new
				// AbsListView.LayoutParams(windowWidth, windowWidth
				// * defaultDraw.getIntrinsicHeight() /
				// defaultDraw.getIntrinsicWidth()));
			}
		}
		myProgressBar.setVisibility(View.GONE);
		imageView.refreshDrawableState();
	}

	class MyImageViewHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ComParams.HANDLER_IMAGE_DOWNLOAD_END:
				setImageBy(context.getCacheDir() + "/" + imageName + "e");
				break;
			case ComParams.HANDLER_IMAGE_DOWNLOAD_ING:
				// ULog.d("MyImageView", "downloading " + imageName + " = " +
				// msg.arg1);
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

	public void setImageBitmap(Bitmap newBitmap) {
		myProgressBar.setVisibility(View.GONE);
		imageView.setImageBitmap(newBitmap);

	}

}
