package com.next.lottery.view;

import java.util.ArrayList;
import java.util.List;

import com.dongfang.utils.ULog;
import com.next.lottery.R;
import com.next.lottery.utils.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 横向滑动布局，子菜单
 * 
 */
public class MyHorizontalScrollView extends FrameLayout {
	public static final String TAG = MyHorizontalScrollView.class
			.getSimpleName();

	/** 子菜单之间的padding值 */
	private static int PADDING = 2;

	private Context context;
	private HorizontalScrollView horScrollView;
	private LinearLayout itemLayout;
	private ArrayList<String> itemList = new ArrayList<String>();
	private List<ItemClickListener> itemClickList = new ArrayList<ItemClickListener>();
	private View conView;
	private int textColor = R.color.black;

	public LinearLayout getContainer() {
		return itemLayout;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public MyHorizontalScrollView(Context context, AttributeSet attr) {
		super(context, attr);
		this.context = context;
		initView(context);
		PADDING = (int) (2 * Util.getWindowDensity(context));
	}

	public void setBackground(int resid) {
		conView.setBackgroundResource(resid);
	}

	private void initView(Context context) {
		conView = LayoutInflater.from(context).inflate(
				R.layout.my_horizontalscrollview, null);
		horScrollView = (HorizontalScrollView) conView
				.findViewById(R.id.horizontal_scroll_horscrollview);
		itemLayout = (LinearLayout) conView
				.findViewById(R.id.horizontal_scroll_item_layout);
		itemLayout.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT, (int) (38 * Util
						.getWindowDensity(context))));

		addView(conView);
	}

	private OnItemListener clickListener = new OnItemListener();

	class OnItemListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			ULog.d("v.getId() = " + v.getId());
			for (int i = 0, length = itemLayout.getChildCount(); i < length; i++) {
				if (null != v && v.getId() == itemLayout.getChildAt(i).getId()) {
					if (itemClickList != null && itemClickList.get(i) != null) {
						itemClickList.get(i).onClick(v);
					}
				}
			}
		}
	}

	public void selectItem(int position, boolean shouldChangeContent) {
		View v = itemLayout.getChildAt(position);
		if (shouldChangeContent && itemClickList != null
				&& itemClickList.get(position) != null) {
			itemClickList.get(position).onClick(v);
		}
	}

	public void scrollTo(int x, int y) {
		horScrollView.scrollTo(x, y);
	}

	public void refresh(ArrayList<String> dataArrayList) {
		this.itemList = dataArrayList;
		if (itemList != null) {
			itemLayout.removeAllViews();
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			for (int i = 0; i < itemList.size(); i++) {
				TextView itemView = (TextView) View.inflate(context,
						R.layout.my_horizontalscrollview_item, null);
				itemView.setText(itemList.get(i));
//				itemView.setTextColor(textColor);
				itemView.setLayoutParams(params);
				itemView.setOnClickListener(clickListener);
				itemView.setId(10000 - i);
				itemLayout.addView(itemView);
			}
		}

		int count = itemLayout.getChildCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				View child = itemLayout.getChildAt(i);
				child.setId(i);
				if (i == 0) {
					LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					p.setMargins(PADDING, 0, 0, 0);
					child.setLayoutParams(p);
				} else if (i == count - 1 && i > 0) {
					LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					p.setMargins(PADDING, 0,
							(int) (12 * Util.getWindowDensity(context)), 0);
					child.setLayoutParams(p);
				}
			}
		}
	}

	public void setItemList(ArrayList<String> itemList) {
		this.itemList = itemList;
		refresh(itemList);
	}

	public void setItemClickListener(List<ItemClickListener> itemClickList) {
		this.itemClickList = itemClickList;
	}

	public interface ItemClickListener {
		void onClick(View v);
	}

	public void setOnTouchListener(OnTouchListener listener) {
		horScrollView.setOnTouchListener(listener);
	}
}