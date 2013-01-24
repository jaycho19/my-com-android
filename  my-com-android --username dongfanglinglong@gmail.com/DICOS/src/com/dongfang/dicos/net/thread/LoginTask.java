package com.dongfang.dicos.net.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class LoginTask extends AsyncTask<String, Integer, Bundle> {

	public static final String	TAG	= "LoginThread";

	private ProgressDialog		progressDialog;

	private Handler				handler;
	private Context				context;
	private String				phoneNumber;

	public LoginTask(Context context, Handler handler, String phoneNumber) {
		this.handler = handler;
		this.context = context;
		this.phoneNumber = phoneNumber;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "", "µÇÂ¼ÖÐ...");
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (LoginTask.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
					LoginTask.this.cancel(true);
			}
		});
	}

	@Override
	protected Bundle doInBackground(String... params) {
		String result = new HttpActions(context).login(phoneNumber);
		ULog.d(TAG, result);
		Bundle data = new Bundle();

		try {
			JSONObject js = new JSONObject(result.substring(result.indexOf("=") + 1));
			data.putString(Actions.ACTIONS_KEY_ACT, js.getString(Actions.ACTIONS_KEY_ACT));
			data.putString(Actions.ACTIONS_KEY_RESULT, js.getString(Actions.ACTIONS_KEY_RESULT));
		} catch (JSONException e) {
			ULog.e(TAG, e.toString());
		}

		Message msg = new Message();
		msg.setData(data);
		msg.what = ComParams.HANDLER_RESULT_LOGIN;
		handler.sendMessage(msg);
		return null;
	}

	@Override
	protected void onPostExecute(Bundle result) {
		super.onPostExecute(result);
		if (progressDialog != null)
			progressDialog.dismiss();

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		ULog.i(TAG, "--> onCancelled");
		if (progressDialog != null)
			progressDialog.dismiss();

	}

}
