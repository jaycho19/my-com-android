package com.dongfang.dicos.lottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ULog;

public class PrizeProvideAdapter extends BaseAdapter {
	private static final String	tag	= "PrzieProvideAdapter";
	/** JSON 格式的门店信息列表 */
	private String[]			aMobileArray;

	private Context				context;

	public PrizeProvideAdapter(Context context) {
		this.context = context;

	}

	public void setArray(String[] storeArray) {
		this.aMobileArray = storeArray;
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

		PrizeProvideElement prizeProviderElement = null;

		if (null == convertView) {
			ULog.d(tag, "new list element1");
			convertView = LayoutInflater.from(context).inflate(R.layout.prizeprovide_row, null);
			ULog.d(tag, "new list element2");

			prizeProviderElement = new PrizeProvideElement();

			prizeProviderElement.tvCompanyName = (TextView) convertView
					.findViewById(R.id.textview_prizeprovide_companyname);
			prizeProviderElement.tvAddress = (TextView) convertView.findViewById(R.id.textview_prizeprovide_address);
			prizeProviderElement.tvTel = (TextView) convertView.findViewById(R.id.textview_prizeprovide_tel);

			ULog.d(tag, "new list element6");
			convertView.setTag(prizeProviderElement);

		} else {
			ULog.d(tag, "new list element7");
			prizeProviderElement = (PrizeProvideElement) convertView.getTag();
		}

		ULog.d(tag, "new list element3");
		prizeProviderElement.initElement(aMobileArray[position]);

		return convertView;
	}

}
