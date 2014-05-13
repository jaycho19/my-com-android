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

public class SignHistoryAdapter extends BaseAdapter {

	private static final String	tag	= "SignHistoryAdapter";

	/** JSON 格式的门店信息列表 */
	private String[]			aJSONSignHistoryArray;

	private Context				context;
	private Handler				handler;

	public SignHistoryAdapter(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;

	}

	public void setArray(String[] array) {
		this.aJSONSignHistoryArray = array;
	}

	@Override
	public int getCount() {
		return (null == aJSONSignHistoryArray) ? 0 : aJSONSignHistoryArray.length;
	}

	@Override
	public Object getItem(int position) {
		if (-1 < position && position < getCount())
			return aJSONSignHistoryArray[position];
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		SignHistoryElement signHistoryElement;
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(R.layout.signhistory_row, null);
			signHistoryElement = new SignHistoryElement();

			signHistoryElement.tvStoreName = (TextView) convertView.findViewById(R.id.textview_signhistory_storename);
			signHistoryElement.tvSignTime = (TextView) convertView.findViewById(R.id.textview_signhistory_time);

			convertView.setTag(signHistoryElement);

		} else {
			ULog.d(tag, "new list element7");
			signHistoryElement = (SignHistoryElement) convertView.getTag();
		}

		signHistoryElement.initElement(aJSONSignHistoryArray[position]);

		return convertView;
	}

}
