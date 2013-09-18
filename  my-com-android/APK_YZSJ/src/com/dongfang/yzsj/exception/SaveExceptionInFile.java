package com.dongfang.yzsj.exception;

import java.io.FileOutputStream;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dongfang.utils.ULog;

public class SaveExceptionInFile extends AsyncTask<Bundle, Integer, Bundle> {

	public static final String TAG = SaveExceptionInFile.class.getSimpleName();

	private Context context;

	public SaveExceptionInFile(Context context) {
		this.context = context;
	}

	@Override
	protected Bundle doInBackground(Bundle... params) {
		Bundle bundle = params[0];
		if (bundle != null) {
			String filename = bundle.getString(ExpKeys.EXP_FILENAME);
			String log = bundle.getString(ExpKeys.EXP_LOG);
			ULog.d(TAG, " ---> savelog");
			try {
				FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
				outStream.write(log.getBytes());
				ULog.d(TAG, "write success " + filename);
				outStream.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}