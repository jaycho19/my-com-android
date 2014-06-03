package com.dongfang.daohang.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class CollectItem implements Parcelable {

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

	public static final Parcelable.Creator<CollectItem> CREATOR = new Creator<CollectItem>() {

		@Override
		public CollectItem createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CollectItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}

	};

}
