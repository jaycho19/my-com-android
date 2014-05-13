package com.dongfang.dicos.lottery;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ULog;

public class PrizeInfoAdapter extends BaseAdapter {
	private static final String	tag	= "PrizeInfoAdapter";
	/** JSON 格式的门店信息列表 */
	private String[]			aMobileArray;

	private Context				context;

	public PrizeInfoAdapter(Context context) {
		this.context = context;

	}

	public void setArray(String[] storeArray) {
		this.aMobileArray = new String[storeArray.length];
		for (int i = 0; i < storeArray.length; i++) {
			try {
				aMobileArray[i] = new JSONObject(storeArray[i]).getString(Actions.ACTIONS_KEY_MOBILE);
			} catch (JSONException e) {
				ULog.d(tag, e.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getCount() {
		return (null == aMobileArray) ? 0 : aMobileArray.length;
	}

	@Override
	public Object getItem(int position) {
		if (-1 < position && position < getCount())
			return aMobileArray[position];
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		PrizeInfoElement prizeInfoElement = null;

		if (null == convertView) {
			ULog.d(tag, "new list element1");
			convertView = LayoutInflater.from(context).inflate(R.layout.prizeinfo_row, null);
			ULog.d(tag, "new list element2");

			prizeInfoElement = new PrizeInfoElement();

			prizeInfoElement.tvMobile1 = (TextView) convertView.findViewById(R.id.textview_prizeinfo_1);
			prizeInfoElement.tvMobile2 = (TextView) convertView.findViewById(R.id.textview_prizeinfo_2);
			prizeInfoElement.tvMobile3 = (TextView) convertView.findViewById(R.id.textview_prizeinfo_3);

			ULog.d(tag, "new list element6");
			convertView.setTag(prizeInfoElement);

		} else {
			ULog.d(tag, "new list element7");
			prizeInfoElement = (PrizeInfoElement) convertView.getTag();
		}

		ULog.d(tag, "new list element3");

		int p = position;

		if (p + 2 < aMobileArray.length) {
			prizeInfoElement.initElement(aMobileArray[p], aMobileArray[p + 1], aMobileArray[p + 2]);
		} else if (p + 1 < aMobileArray.length) {
			prizeInfoElement.initElement(aMobileArray[p], aMobileArray[p + 1], "");
		} else {
			prizeInfoElement.initElement(aMobileArray[p], "", "");
		}

		return convertView;
	}

}
