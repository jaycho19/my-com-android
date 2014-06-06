package com.dongfang.daohang.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.dongfang.daohang.R;

/**
 * 添加记录点的对话框
 * 
 * @author dongfang
 *
 */
public class RecordAlert {

	public static Dialog show(final Context context, String str) {
		final Dialog dlg = new Dialog(context, R.style.CustomProgressDialog);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_record, null);
		dlg.setContentView(layout);

		OnRecordListener listener = new OnRecordListener(dlg, str);

		layout.findViewById(R.id.dialog_record_cancel).setOnClickListener(listener);
		layout.findViewById(R.id.dialog_record_recorded).setOnClickListener(listener);
		layout.findViewById(R.id.dialog_record_share_mm).setOnClickListener(listener);
		layout.findViewById(R.id.dialog_record_share_sms).setOnClickListener(listener);

		return dlg;
	}

	public static class OnRecordListener implements View.OnClickListener {
		Dialog dlg;
		String str;

		public OnRecordListener(Dialog dialog, String str) {
			dlg = dialog;
			this.str = str;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.dialog_record_cancel:
				dlg.dismiss();
				break;
			case R.id.dialog_record_recorded:
				break;
			case R.id.dialog_record_share_mm:
				break;
			case R.id.dialog_record_share_sms:
				break;
			}

		}

	}

}
