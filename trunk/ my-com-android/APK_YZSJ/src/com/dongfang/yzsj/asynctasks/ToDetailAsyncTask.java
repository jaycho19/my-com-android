package com.dongfang.yzsj.asynctasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.BasicHttpContext;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.HttpHandler;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.LogUtils;
import com.dongfang.utils.ULog;
import com.dongfang.yzsj.DetailsActiivity;
import com.dongfang.yzsj.LoginActivity;
import com.dongfang.yzsj.bean.DetailBean;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;

/**
 * 跳转到视频详情页
 * 
 * @author dongfang
 * 
 */
public class ToDetailAsyncTask extends AsyncTask<String, String, DetailBean> {

	public static final String TAG = "ToDetailAsyncTask";

	private Context context;
	private String conntentId; // 视频id
	private String channelId; // 频道id
	private com.dongfang.view.ProgressDialog progDialog;

	public ToDetailAsyncTask(Context context, String channelId, String conntentId) {
		this.context = context;
		this.channelId = channelId;
		this.conntentId = conntentId;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progDialog = com.dongfang.view.ProgressDialog.show(context);
		progDialog.show();
		progDialog.setCancelable(true);
		progDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				ToDetailAsyncTask.this.cancel(true);
			}
		});

	}

	@Override
	protected DetailBean doInBackground(String... params) {
		DetailBean bean = null;

		if (!TextUtils.isEmpty(conntentId)) {

			StringBuilder sb = new StringBuilder(ComParams.HTTP_DETAIL);
			sb.append("token=").append(User.getToken(context)).append("&");
			sb.append("phone=").append(User.getPhone(context)).append("&");
			sb.append("channelId=").append(channelId).append("&");
			sb.append("contentId=").append(conntentId);
			ULog.i(TAG, sb.toString());

			try {
				bean = new com.google.gson.Gson().fromJson(httpGet(sb.toString()), DetailBean.class);
			} catch (Exception jdr) {
				jdr.printStackTrace();
			}
		}

		return bean;
	}

	@Override
	protected void onPostExecute(DetailBean result) {
		super.onPostExecute(result);

		if (null != progDialog)
			progDialog.cancel();

		if (null == result) {
			Toast.makeText(context, "无法获取该节目信息~~~~(>_<)~~~", Toast.LENGTH_LONG).show();
			return;
		}

		else if (!result.isSuccess()) {
			if ("Error Token".equals(result.getError0())) {
				User.saveToken(context, "");
				Toast.makeText(context, "请先进行登录 ^_^", Toast.LENGTH_LONG).show();

			}
			else {
				Toast.makeText(context, result.getError0(), Toast.LENGTH_LONG).show();
			}

			return;
		}

		if (context instanceof DetailsActiivity) {
			((DetailsActiivity) context).finish();
		}

		Intent intent = new Intent(context, DetailsActiivity.class);
		intent.putExtra(ComParams.INTENT_MOVIEDETAIL_BEAN, result);
		context.startActivity(intent);

		if (context instanceof LoginActivity) {
			((Activity) context).finish();
		}

	}

	@Override
	protected void onCancelled() {
		if (null != progDialog)
			progDialog.cancel();
		super.onCancelled();
	}

	/** 读取网络信息 */
	private String httpGet(String url) {
		String result = null;
		try {

			result = new HttpUtils().sendSync(HttpRequest.HttpMethod.GET, url).readString();
			LogUtils.d(result);

			//
			// HttpResponse response = new HttpUtils().getHttpClient().execute(
			// new HttpRequest(HttpRequest.HttpMethod.GET, url), new BasicHttpContext());
			//
			// if (null != response && response.getStatusLine().getStatusCode() < 300) {
			// HttpEntity entity = response.getEntity();
			// String charset = "utf-8";
			// if (entity.getContentType() != null) {
			// HeaderElement[] values = entity.getContentType().getElements();
			// if (values.length > 0) {
			// NameValuePair param = values[0].getParameterByName("charset");
			// if (param != null) {
			// charset = ((TextUtils.isEmpty(param.getValue())) ? charset : param.getValue());
			// }
			// }
			// }
			// result = read(entity, charset);
			// }
		} catch (Exception e) {
			LogUtils.e(e.toString());
		}

		return result;
	}

	private String read(HttpEntity entity, String charset) {
		// long current = 0L;
		StringBuilder sb = new StringBuilder();
		InputStream ins = null;
		try {
			ins = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, charset));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				// current += line.getBytes(charset).length;
			}
		} catch (Exception e) {
			e.printStackTrace();
			sb.delete(0, sb.length());
		} finally {
			if (ins != null)
				try {
					ins.close();
				} catch (Exception localException) {}
		}
		return sb.toString();
	}
}
