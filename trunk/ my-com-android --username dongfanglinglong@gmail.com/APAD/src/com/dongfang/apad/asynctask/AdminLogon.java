package com.dongfang.apad.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.dongfang.apad.net.HttpActions;
import com.dongfang.apad.util.DFException;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.view.MyProgressDialog;
import com.google.gson.JsonObject;

/**
 * 上报测试结果
 * 
 * @author dongfang
 * 
 * 
 */
public class AdminLogon extends AsyncTask<String, String, String> {
	public static final String	TAG	= AdminLogon.class.getSimpleName();
	private MyProgressDialog	progressDialog;
	private Context				context;

	// private Handler handler;

	public AdminLogon(Context context, Handler handler) {
		this.context = context;
		// this.handler = handler;
	}

	public AdminLogon(Context context) {
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
			JsonObject entity = new JsonObject();
			entity.addProperty("TYPE", "AdminLogon");
			JsonObject detail = new JsonObject();
			detail.addProperty("NAME", "test");
			detail.addProperty("PASSWORD", "test");
			entity.add("DETAIL", detail);

//			StringBuilder sb = new StringBuilder();
//			sb.append("{TYPE:QueryUser,DETAIL:{NAME:test,PASSWORD:test}}");

			result = httpActions.getResponse(null, "AdminLogon", entity.toString());
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
