package com.dongfang.dicos.store;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

/**
 * 门店查询显示列表信息adapter
 * 
 * @author dongfang
 */
public class StoreListAdapter extends BaseAdapter {

	private static final String	tag	= "StoreListAdapter";
	/** JSON 格式的门店信息列表 */
	private String[]			aJSONStoreArray;

	private Context				context;
	private Handler				handler;

	public StoreListAdapter(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;

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

		StoreListElement storeListElement = null;

		if (null == convertView) {
			ULog.d(tag, "new list element1");
			convertView = LayoutInflater.from(context).inflate(R.layout.storesearch_row, null);
			ULog.d(tag, "new list element2");

			storeListElement = new StoreListElement();

			storeListElement.ivDICOS_IC = (ImageView) convertView.findViewById(R.id.imageview_storesearch_dicos_icon);
			storeListElement.ivGoto = (ImageView) convertView.findViewById(R.id.imageview_storesearch_goto_detail);

			storeListElement.tvStoreAddress = (TextView) convertView
					.findViewById(R.id.textview_storesearch_row_storeaddress);
			storeListElement.tvStoreName = (TextView) convertView.findViewById(R.id.textview_storesearch_row_storename);

			ULog.d(tag, "new list element6");
			convertView.setTag(storeListElement);

		} else {
			ULog.d(tag, "new list element7");
			storeListElement = (StoreListElement) convertView.getTag();
		}

		ULog.d(tag, "new list element3");

		storeListElement.initElement(aJSONStoreArray[position]);

		ULog.d(tag, "new list element4");
		convertView.setOnClickListener(new gotoOnClickListener(aJSONStoreArray[position]));
		ULog.d(tag, "new list element5");

		return convertView;

	}

	class gotoOnClickListener implements OnClickListener {

		private String	json;

		public gotoOnClickListener(String js) {
			json = js;
		}

		@Override
		public void onClick(View v) {

			Message msg = new Message();
			msg.obj = json;
			msg.what = ComParams.HANDLER_INTENT_GOTO_STORE_DETAIL;

			if (null == handler) {
				ULog.d(tag, "HANDLER_INTENT_GOTO_STORE_DETAIL handler = null ");
			}

			handler.sendMessage(msg);
		}

	}

}
