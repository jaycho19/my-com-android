package com.dongfang.dicos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.net.thread.IPArea;
import com.dongfang.dicos.util.Analysis;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 开启应用前ACTIVITY
 * 
 * @author dongfang
 * */

public class FirstpageActivity extends Activity {
	public static final String	TAG	= "FirstpageActivity";

	private Context				context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstpage);
		context = this;

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		new LoadingTask().execute("");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// new Handler().postDelayed(gotoMain, 1000);
	}

	class LoadingTask extends AsyncTask<String, String, Bundle> {

		@Override
		protected Bundle doInBackground(String... params) {
			new IPArea(context, Util.getPhoneNumber(context)).start();

			Bundle data = new Bundle();
			SharedPreferences setConfig = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
					Activity.MODE_PRIVATE);
//			setConfig
//					.edit()
//					.putString(ComParams.SHAREDPREFERENCES_CRUUENTSEASON_IMG_URLS,
//							"[\"http:\\/\\/www.dicos.com.cn\\/  images\\/  app\\/action\\/9_1348134247.jpg\"]")
//					.commit();
			String[] aImgUrl = null;

			if (Util.isNetworkAvailable(context)) {
				String cruuentSeasonImgUrls = new HttpActions(context).getCurrentSeasonImgUrl();
				ULog.i(TAG, cruuentSeasonImgUrls);
				aImgUrl = Analysis.analysisCurrentSeasonImgUrl(cruuentSeasonImgUrls);

				if (null != aImgUrl && aImgUrl.length > 0) {
					ULog.i(TAG, aImgUrl[0]);
					data.putStringArray(ComParams.CURRENT_SEASON_IMG_ARRARY, aImgUrl);
					setConfig.edit()
							.putString(ComParams.SHAREDPREFERENCES_CRUUENTSEASON_IMG_URLS, cruuentSeasonImgUrls)
							.commit();
				} else {
					aImgUrl = Analysis.analysisCurrentSeasonImgUrl(setConfig.getString(
							ComParams.SHAREDPREFERENCES_CRUUENTSEASON_IMG_URLS, ""));
					if (null != aImgUrl && aImgUrl.length > 0) {
						data.putStringArray(ComParams.CURRENT_SEASON_IMG_ARRARY, aImgUrl);
					}
				}
			} else {
				aImgUrl = Analysis.analysisCurrentSeasonImgUrl(setConfig.getString(
						ComParams.SHAREDPREFERENCES_CRUUENTSEASON_IMG_URLS, ""));
				if (null != aImgUrl && aImgUrl.length > 0) {
					data.putStringArray(ComParams.CURRENT_SEASON_IMG_ARRARY, aImgUrl);
				}
			}

			return data;
		}

		protected void onPostExecute(Bundle result) {
			super.onPostExecute(result);

			Intent intent = new Intent(FirstpageActivity.this, DicosMainActivity.class);
			if (result.containsKey(ComParams.CURRENT_SEASON_IMG_ARRARY)) {
				intent.putExtra(ComParams.CURRENT_SEASON_IMG_ARRARY,
						result.getStringArray(ComParams.CURRENT_SEASON_IMG_ARRARY));
			}
			FirstpageActivity.this.startActivity(intent);
			finish();

		}

	}

	// Runnable gotoMain = new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// // Toast.makeText(Firstpage.this,
	// // "goto main page!!", Toast.LENGTH_LONG);
	//
	// Intent intent = new Intent(FirstpageActivity.this,
	// DicosMainActivity.class);
	// FirstpageActivity.this.startActivity(intent);
	// finish();
	// }
	// };

}
