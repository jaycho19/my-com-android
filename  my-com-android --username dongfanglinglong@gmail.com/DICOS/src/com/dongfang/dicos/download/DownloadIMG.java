package com.dongfang.dicos.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

public class DownloadIMG extends Thread {
	public static final String	tag	= "DownloadIMG";

	private Context				context;
	private Handler				hanlder;
	private String				imageUrl;
	private String				imageName;
	private int					array_param;

	public DownloadIMG(Context context, Handler handler, String imageUrl, String imageName, int array_param) {
		this.context = context;
		this.hanlder = handler;
		this.imageUrl = imageUrl;
		this.imageName = imageName;
		this.array_param = array_param;
	}

	@Override
	public void run() {
		ULog.d(tag, "run");
		if (Util.isNetworkAvailable(context)) {
			ULog.d(tag, "array_param = " + array_param + " , url = " + imageUrl);
			saveFileByURL(imageUrl, context.getCacheDir()+ "/" + imageName, hanlder, array_param);
		} else {
			Message msg = new Message();
			msg.what = ComParams.HANDLER_IMAGE_DOWNLOAD_NONET;
			msg.arg1 = array_param;
			hanlder.sendMessage(msg);
		}

	}

	private String saveFileByURL(String url, String filename, Handler handler, int array_param) {

		InputStream ins = null;

		if (Integer.parseInt(Build.VERSION.SDK) >= 14) {
			ins = getInputStreamFromURL(handler, url, 0);
		} else {
			ins = getInputStreamFromURLV2(handler, url, 0);
		}

		if (null == ins)
			return null;

		// File file = new File(ComParams.FILESAVEPATH_IMG);
		// if (!file.exists()) {
		// file.mkdir();
		// }
		return saveFile(ins, filename, handler, array_param);
	}

	private InputStream getInputStreamFromURL(Handler handler, String urlStr, int length) {
		HttpURLConnection urlConn = null;
		try {
			urlConn = (HttpURLConnection) (new URL(urlStr)).openConnection(java.net.Proxy.NO_PROXY);
			if (0 < length)
				urlConn.setRequestProperty("RANGE", "bytes=" + length + "-");

			urlConn.setReadTimeout(30 * 1000);
			urlConn.setConnectTimeout(30 * 1000);
			urlConn.setRequestMethod("POST");
			urlConn.setDoInput(true);
			urlConn.setDefaultUseCaches(false);
			urlConn.setChunkedStreamingMode(0);
			urlConn.connect();

			int code = ((HttpURLConnection) urlConn).getResponseCode();
			ULog.d(tag, "code = " + code);
			if (code > 299 || code < HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_NO_CONTENT) {
				return null;
			}

			if (null != handler) {
				Message msg = new Message();
				msg.what = ComParams.HANDLER_IMAGE_DOWNLOAD_START;
				msg.arg1 = urlConn.getContentLength();
				handler.sendMessage(msg);
			}

			return urlConn.getInputStream();
		} catch (Exception e) {
			ULog.d(tag, "Exception = " + e.getMessage());
			if (null != handler) {
				handler.sendEmptyMessage(ComParams.HANDLER_IMAGE_DOWNLOAD_ERROR);
			}
		}
		return null;
	}

	private InputStream getInputStreamFromURLV2(Handler handler, String urlStr, long length) {
		URLConnection urlConn = null;
		try {
			urlConn = (new URL(urlStr)).openConnection();
			if (0 < length)
				urlConn.setRequestProperty("RANGE", "bytes=" + length + "-");

			urlConn.setReadTimeout(30 * 1000);
			urlConn.setConnectTimeout(30 * 1000);
			int code = ((HttpURLConnection) urlConn).getResponseCode();

			ULog.d(tag, "code = " + code);
			if (code > 299 || code < HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_NO_CONTENT) {
				return null;
			}
			if (null != handler) {
				Message msg = new Message();
				msg.what = ComParams.HANDLER_IMAGE_DOWNLOAD_START;
				msg.arg1 = urlConn.getContentLength();
				handler.sendMessage(msg);
			}

			return urlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			ULog.d(tag, "getInputStreamFromURL Exception" + e.toString());
		}
		return null;
	}

	private String saveFile(InputStream ins, String filename, Handler handler, int array_param) {
		if (TextUtils.isEmpty(filename))
			return filename;
		try {
			ULog.d(tag, "filename = " + filename);

			FileOutputStream outs = new FileOutputStream(filename);
			byte[] b = new byte[ComParams.BUF_SIZE];

			int i = 0;
			int totle = 0;
			int num = ins.read(b);
			while (num != -1) {
				outs.write(b, 0, num);
				num = ins.read(b);
				ULog.d(tag, filename + " " + (totle += num));
				if (10 < i++) {
					i = 0;
					if (null != handler) {
						Message msg = new Message();
						msg.what = ComParams.HANDLER_IMAGE_DOWNLOAD_ING;
						msg.arg1 = totle;
						handler.sendMessage(msg);
					}
				}
			}

			outs.flush();
			ins.close();
			outs.close();

			// ÖØÃüÃû
			(new File(filename)).renameTo(new File(filename + "e"));

			if (null != handler) {
				handler.sendEmptyMessage(ComParams.HANDLER_IMAGE_DOWNLOAD_END);
			}

		} catch (Exception e) {
			ULog.d(tag, "Exception = " + e.getMessage());
			if (null != handler) {
				handler.sendEmptyMessage(ComParams.HANDLER_IMAGE_DOWNLOAD_ERROR);
			}
		}
		return filename;
	}
}
