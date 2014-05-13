package com.next.lottery.menu;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.next.lottery.R;

/***
 * 侧边栏菜单类
 * 
 * @author dongfang
 * 
 */
public class RibbonMenuView extends LinearLayout {

	private View view;

	public RibbonMenuView(Context context) {
		super(context);
		load();
	}

	public RibbonMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		load();
	}

	private void load() {
		if (isInEditMode())
			return;
		inflateLayout();
		initUi();
	}

	private void inflateLayout() {
		try {
			view = LayoutInflater.from(getContext()).inflate(R.layout.ribbon_menu, this, true);
		} catch (Throwable e) {
			e.printStackTrace();
			// LogUtils.e(e.toString());
		}
	}

	private void initUi() {

		// rbmListView = (ListView) findViewById(R.id.rbm_listview);
		// rbmOutsideView = (View) findViewById(R.id.rbm_outside_view);
		//
		// rbmOutsideView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// hideMenu();
		//
		// }
		// });
		//
		// rbmListView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//
		// if (callback != null)
		// callback.RibbonMenuItemClick(menuItems.get(position).id);
		//
		// hideMenu();
		// }
		//
		// });

	}

	// public void setMenuClickCallback(iRibbonMenuCallback callback) {
	// this.callback = callback;
	// }

	public void setBackgroundResource(int resource) {
		// rbmListView.setBackgroundResource(resource);

	}

	public void showMenu() {
		// rbmOutsideView.setVisibility(View.VISIBLE);

		view.setVisibility(View.VISIBLE);
		view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rbm_in_from_left));

	}

	public void hideMenu() {

		// rbmOutsideView.setVisibility(View.GONE);
		view.setVisibility(View.GONE);
		view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rbm_out_to_left));

	}

	public void toggleMenu() {
		if (view.getVisibility() == View.GONE) {
			showMenu();
		}
		else {
			hideMenu();
		}
	}

	private String resourceIdToString(String text) {

		if (!text.contains("@")) {
			return text;
		}
		else {

			String id = text.replace("@", "");

			return getResources().getString(Integer.valueOf(id));

		}

	}

	public boolean isMenuVisible() {
		// return view.getVisibility() == View.VISIBLE;
		return view.isShown();
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		if (ss.bShowMenu)
			showMenu();
		else
			hideMenu();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);

		ss.bShowMenu = isMenuVisible();

		return ss;
	}

	static class SavedState extends BaseSavedState {
		boolean bShowMenu;

		SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			bShowMenu = (in.readInt() == 1);
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(bShowMenu ? 1 : 0);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

}
