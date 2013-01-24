package com.dongfang.dicos.mydicos;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ULog;

/** @author dongfang */
public class DykAdapter extends BaseAdapter {

	private static final String	tag	= DykAdapter.class.getSimpleName();

	/** JSON 格式的门店信息列表 */
	private String[]			aDykArray;

	private Context				context;

	public DykAdapter(Context context) {
		this.context = context;

	}

	public void setArray(String[] array) {
		this.aDykArray = array;
	}

	@Override
	public int getCount() {
		return (null == aDykArray) ? 0 : aDykArray.length;
	}

	@Override
	public Object getItem(int position) {
		if (-1 < position && position < getCount())
			return aDykArray[position];
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv;

		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(R.layout.dyk_row, null);
			tv = (TextView) convertView.findViewById(R.id.textview_dyk_no);
			convertView.setTag(tv);

		}
		else {
			ULog.d(tag, "new list element7");
			tv = (TextView) convertView.getTag();
		}

		tv.setText(aDykArray[position]);

		return convertView;
	}

}
