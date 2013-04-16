package com.dongfang.dicos.more;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 城市列表中，列表的adapter
 * 
 * @author dongfang
 * */
public class CityListAdapter extends BaseAdapter implements SectionIndexer {
	private static final String	tag	= "CityListAdapter";
	private ArrayList<String>	stringArray;
	private Context				context;

	private Handler				handler;

	public CityListAdapter(Context context, ArrayList<String> stringArray, Handler handler) {
		this.stringArray = stringArray;
		this.context = context;
		this.handler = handler;
	}

	public void setList(ArrayList<String> list) {
		stringArray = list;
	}

	public int getCount() {
		return stringArray.size();
	}

	public Object getItem(int arg0) {
		return stringArray.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ULog.d(tag, "new list element0");

		CityListElement cityListElement = null;

		if (null == convertView) {

			ULog.d(tag, "new list element1");
			convertView = LayoutInflater.from(context).inflate(R.layout.citylist_listview_row, null);
			ULog.d(tag, "new list element2");

			cityListElement = new CityListElement();

			cityListElement.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.linearlayou_citylist_listview_row);
			cityListElement.header = (TextView) convertView.findViewById(R.id.textview_citylist_listview_row_header);
			cityListElement.cityName = (TextView) convertView.findViewById(R.id.textview_citylist_listview_row_content);

			ULog.d(tag, "new list element6");
			convertView.setTag(cityListElement);
		} else {
			ULog.d(tag, "new list element7");
			cityListElement = (CityListElement) convertView.getTag();
		}

		// LayoutInflater inflate = ((Activity) context).getLayoutInflater();
		// View view = (View) inflate.inflate(R.layout.citylist_listview_row,
		// null);
		// LinearLayout header = (LinearLayout)
		// view.findViewById(R.id.linearlayou_citylist_listview_row);
		// TextView textViewHeader = (TextView)
		// view.findViewById(R.id.textview_citylist_listview_row_header);
		String label = stringArray.get(position);
		char firstChar = label.toUpperCase().charAt(0);
		if (position == 0) {
			// setSection(header, label);
			cityListElement.header.setText(label.substring(0, 1));
		} else {
			String preLabel = stringArray.get(position - 1);
			char preFirstChar = preLabel.toUpperCase().charAt(0);
			if (firstChar != preFirstChar) {
				// setSection(header, label);
				cityListElement.header.setText(label.substring(0, 1));
			} else {
				cityListElement.linearLayout.setVisibility(View.GONE);
			}
		}
		// TextView textView = (TextView)
		// view.findViewById(R.id.textview_citylist_listview_row_content);
		cityListElement.cityName.setText(label.substring(1));
		cityListElement.cityName.setOnClickListener(new MyOnClickListener(label.substring(1)));

		return convertView;
	}

	class MyOnClickListener implements OnClickListener {
		private String	sIpArea;

		public MyOnClickListener(String s) {
			sIpArea = s;
		}

		@Override
		public void onClick(View v) {
			ComParams.IPAREA = sIpArea;
			Util.saveIpArea(context);
			Toast.makeText(context, "城市切换成:" + sIpArea, Toast.LENGTH_LONG).show();
			ULog.d(tag, "ComParams.IPAREA = " + ComParams.IPAREA);
			if (null != handler) {
				handler.sendEmptyMessage(ComParams.HANDLER_CHANGE_TITLE_CITYLIST_ACTIVITY);
				handler.sendEmptyMessageDelayed(ComParams.HANDLER_FINISH_CITYLIST_ACTIVITY, 1000);
			}
		}

	}

	// private void setSection(LinearLayout header, String label) {
	// TextView text = new TextView(context);
	// header.setBackgroundColor(0xffaabbcc);
	// text.setTextColor(Color.WHITE);
	// text.setText(label.substring(0, 1).toUpperCase());
	// text.setTextSize(20);
	// text.setPadding(5, 0, 0, 0);
	// text.setGravity(Gravity.CENTER_VERTICAL);
	// header.addView(text);
	// }

	public int getPositionForSection(int section) {
		if (section == 35) {
			return 0;
		}
		for (int i = 0; i < stringArray.size(); i++) {
			String l = stringArray.get(i);
			char firstChar = l.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	public int getSectionForPosition(int arg0) {
		return 0;
	}

	public Object[] getSections() {
		return null;
	}
}
