package com.dongfang.apad.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.dongfang.apad.net.HttpActions;
import com.dongfang.apad.util.DFException;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.view.MyProgressDialog;

/**
 * 上报测试结果
 * 
 * @author dongfang
 * 
 * 
 */
public class QueryUser extends AsyncTask<String, String, String> {
	public static final String	TAG	= QueryUser.class.getSimpleName();
	private MyProgressDialog	progressDialog;
	private Context				context;

	// private Handler handler;

	public QueryUser(Context context, Handler handler) {
		this.context = context;
		// this.handler = handler;
	}

	public QueryUser(Context context) {
		this.context = context;
	}

	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// progressDialog = MyProgressDialog.show(context, context.getString(R.string.loading_data));
	// progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
	// @Override
	// public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// if (progressDialog != null && progressDialog.isShowing()) {
	// progressDialog.dismiss();
	// GetCardInfo.this.cancel(true);
	// }
	// return true;
	// }
	// return false;
	// }
	// });
	// progressDialog.show();
	// progressDialog.setOnDismissListener(new OnDismissListener() {
	//
	// @Override
	// public void onDismiss(DialogInterface dialog) {
	// if (GetCardInfo.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
	// GetCardInfo.this.cancel(true);
	// }
	// });
	// }

	@Override
	protected String doInBackground(String... params) {
		HttpActions httpActions = new HttpActions(context);
		String result = null;
		try {
			JSONObject entity = new JSONObject();
			try {
				entity.put("TYPE", "QueryUser");
				JSONObject detail = new JSONObject();
				detail.put("CARDID", "项目编号");
				detail.put("IDCARDNUM", "用户编号");
				entity.put("DETAIL", detail.toString());
			} catch (JSONException e) {

			}

			result = httpActions.getResponse(null, "QueryUser", entity.toString());
		} catch (DFException e) {
			e.printStackTrace();
			ULog.e(TAG, e.getMessage());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {

		if (progressDialog != null)
			progressDialog.dismiss();

		if (!TextUtils.isEmpty(result)) {
			ULog.d(TAG, result);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onCancelled()
	 */
	@Override
	protected void onCancelled() {
		super.onCancelled();
		ULog.i(TAG, "--> onCancelled");
		if (progressDialog != null)
			progressDialog.dismiss();
	}

}
