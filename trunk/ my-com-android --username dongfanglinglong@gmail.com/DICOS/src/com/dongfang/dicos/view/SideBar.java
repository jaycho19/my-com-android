package com.dongfang.dicos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SectionIndexer;

import com.dongfang.dicos.util.ULog;

/**
 * 城市搜索列表右侧提示栏目
 * 
 * @author dongfang
 * */
public class SideBar extends View {
	private static final String	tag				= "SideBar";

	/** 提示栏目中的字符 */
	private char[]				aCharacter;
	private SectionIndexer		sectionIndexter	= null;
	private ListView			list;
	private int					m_nItemHeight	= 0;

	public SideBar(Context context) {
		super(context);
		init();
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		ULog.d(tag, "init");
		aCharacter = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		ULog.d(tag, "h = " + this.getHeight() + " e = " + (this.getHeight() / aCharacter.length));
	}

	public void setListView(ListView _list) {
		list = _list;
		sectionIndexter = (SectionIndexer) _list.getAdapter();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ULog.d(tag, "onTouchEvent");

		int i = (int) event.getY();
		int idx = i / m_nItemHeight;
		if (idx >= aCharacter.length) {
			idx = aCharacter.length - 1;
		} else if (idx < 0) {
			idx = 0;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			if (sectionIndexter == null) {
				sectionIndexter = (SectionIndexer) list.getAdapter();
			}
			int position = sectionIndexter.getPositionForSection(aCharacter[idx]);
			if (position == -1) {
				return true;
			}
			list.setSelection(position);
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// ULog.d(tag, "onDraw h = " + this.getHeight() + " e = " + (this.getHeight() / aCharacter.length));

		if (0 == m_nItemHeight) {
			m_nItemHeight = this.getHeight() / aCharacter.length - 1;
		}

		Paint paint = new Paint();
		paint.setColor(0xFFA6A9AA);
		paint.setTextSize(20);
		paint.setTextAlign(Paint.Align.CENTER);
		float widthCenter = getMeasuredWidth() / 2;
		for (int i = 0; i < aCharacter.length; i++) {
			canvas.drawText(String.valueOf(aCharacter[i]), widthCenter, m_nItemHeight + (i * m_nItemHeight), paint);
		}
		super.onDraw(canvas);
	}
}