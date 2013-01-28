package com.dongfang.dicos.net.thread;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.dongfang.dicos.mydicos.DykAdapter;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ULog;

/** @author dongfang */
public class AddDykCardTask extends AsyncTask<String, Integer, Bundle> {
	private final String		TAG			= GetDykCardTask.class.getSimpleName();
	private Context				context;
	private ProgressDialog		progressDialog;
	private DykAdapter			dykadapter;
	private EditText			etDykNum;

	public static final String	LIVELIST	= "LiveList";

	public AddDykCardTask(Context context, DykAdapter dykadapter,EditText etDykNum) {
		this.context = context;
		this.dykadapter = dykadapter;
		this.etDykNum = etDykNum;
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
				if (AddDykCardTask.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
					AddDykCardTask.this.cancel(true);
			}
		});
	}

	@Override
	protected Bundle doInBackground(String... params) {
		Bundle b = new Bundle();
		try {
			String str = "";
			JSONObject json;
			str = new HttpActions(context).addDykCards(params[0]);
			json = new JSONObject(str);

			int s = json.optInt("s", 0);
			ULog.d(TAG, "s      = " + str);
			ULog.d(TAG, "msg    = " + json.opt("msg"));

			if (1 == s) {
				b.putInt("s", 1);
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
			}
			else {
				b.putInt("s", s);
				b.putString("msg", json.optString("msg", ""));
			}

		} catch (Exception e) {
			b.putString("error", e.getMessage());
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
			etDykNum.setText("");
		}
		
		if (result.containsKey("msg") || result.containsKey("s")) {
			if (1 == result.getInt("s")) {
				Toast.makeText(context, "德意卡号添加成功", Toast.LENGTH_LONG).show();
			}

			else if (!TextUtils.isEmpty(result.getString("msg"))) {
				Toast.makeText(context, result.getString("msg"), Toast.LENGTH_LONG).show();
			}
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
