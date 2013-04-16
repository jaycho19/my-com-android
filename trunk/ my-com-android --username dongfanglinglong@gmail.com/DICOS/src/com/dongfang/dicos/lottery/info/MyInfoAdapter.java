package com.dongfang.dicos.lottery.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ULog;

public class MyInfoAdapter extends BaseAdapter {
	private static final String	tag	= "MyInfoAdapter";
	/** JSON 格式的门店信息列表 */
	private String[]			aJSONStoreArray;

	private Context				context;

	public MyInfoAdapter(Context context) {
		this.context = context;

	}

	public void setArray(String[] storeArray) {
		this.aJSONStoreArray = storeArray;
	}

	@Override
	public int getCount() {
		return (null == aJSONStoreArray) ? 0 : aJSONStoreArray.length;
	}

	@Override
	public Object getItem(int position) {
		if (-1 < position && position < getCount())
			return aJSONStoreArray[position];
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		MyInfoElement myInfoElement = null;

		if (null == convertView) {
			ULog.d(tag, "new list element1");
			convertView = LayoutInflater.from(context).inflate(R.layout.myinfo_row, null);
			ULog.d(tag, "new list element2");

			myInfoElement = new MyInfoElement();

			myInfoElement.tvCode = (TextView) convertView.findViewById(R.id.textview_myinfo_row_code);
			myInfoElement.tvAmount = (TextView) convertView.findViewById(R.id.textview_myinfo_row_amount);
			myInfoElement.tvDate = (TextView) convertView.findViewById(R.id.textview_myinfo_row_date);
			myInfoElement.tvStatus = (TextView) convertView.findViewById(R.id.textview_myinfo_row_statue);

			ULog.d(tag, "new list element6");
			convertView.setTag(myInfoElement);

		} else {
			ULog.d(tag, "new list element7");
			myInfoElement = (MyInfoElement) convertView.getTag();
		}

		ULog.d(tag, "new list element3");

		myInfoElement.initElement(aJSONStoreArray[position]);

		return convertView;
	}

}
