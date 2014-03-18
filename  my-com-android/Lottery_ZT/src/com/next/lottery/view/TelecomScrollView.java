package com.next.lottery.view;

import com.next.lottery.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


public class TelecomScrollView extends ScrollView {

	public TelecomScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.defaultScrollViewStyle);

	}

	public TelecomScrollView(Context context) {
		this(context, null, R.attr.defaultScrollViewStyle);

	}

	public TelecomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

}
