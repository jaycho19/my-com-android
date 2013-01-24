package com.dongfang.dicos.net.thread;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dongfang.dicos.mydicos.DykAdapter;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ULog;

/**  @author dongfang  */
public class GetDykCardTask extends AsyncTask<String, Integer, Bundle> {
	private final String		TAG			= GetDykCardTask.class.getSimpleName();
	private Context				context;
	private ProgressDialog		progressDialog;
	private DykAdapter			dykadapter;

	public static final String	LIVELIST	= "LiveList";

	public GetDykCardTask(Context context, DykAdapter dykadapter) {
		this.context = context;
		this.dykadapter = dykadapter;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "", "努力获取数据中...");
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (GetDykCardTask.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
					GetDykCardTask.this.cancel(true);
			}
		});
	}

	@Override
	protected Bundle doInBackground(String... params) {
		Bundle b = new Bundle();
		try {
			String str = "";
			JSONObject json;
			// str = new HttpActions(context).addDykCards("1234567890111110");
			// json = new JSONObject(str);
			// ULog.d(TAG, "s      = " + json.opt("s"));
			// ULog.d(TAG, "msg    = " + json.opt("msg"));

			str = new HttpActions(context).getDykCards();
			ULog.d(TAG, "str = " + str);
			json = new JSONObject(str);
			if (1 == json.optInt("s", 0)) {
				JSONArray jsonArray = json.getJSONArray("cno");
				if (null != jsonArray && jsonArray.length() > 0) {
					ULog.e(TAG, jsonArray.toString());
					int length = jsonArray.length();

					String[] sa = new String[jsonArray.length()];
					for (int i = 0; i < length; i++) {
						sa[i] = jsonArray.optString(i);
					}
					b.putStringArray("cno", sa);
				}
			}
		} catch (Exception e) {
			b.putString("msg", e.getMessage());
		}
		return b;
	}

	@Override
	protected void onPostExecute(Bundle result) {
		super.onPostExecute(result);
		if (progressDialog != null)
			progressDialog.dismiss();

		if (result.containsKey("cno")) {
			dykadapter.setArray(result.getStringArray("cno"));
			dykadapter.notifyDataSetChanged();
		}

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		ULog.i(TAG, "--> onCancelled");
		if (progressDialog != null)
			progressDialog.dismiss();

	}

}
