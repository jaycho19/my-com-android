package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/** 直播 @author dongfang */
public class LivesItem implements Parcelable {
	public String MEDIA_NAME;
	// public String PC_MEDIA_POSTER_HORIZONTAL_BIG;
	public String PHONE_MEDIA_POSTER_SMALL;
	public String id;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(MEDIA_NAME);
		dest.writeString(PHONE_MEDIA_POSTER_SMALL);
		dest.writeString(id);
	}

	public static final Parcelable.Creator<LivesItem> CREATOR = new Parcelable.Creator<LivesItem>() {
		@Override
		public LivesItem createFromParcel(Parcel in) {
			LivesItem data = new LivesItem();
			data.MEDIA_NAME = in.readString();
			data.PHONE_MEDIA_POSTER_SMALL = in.readString();
			data.id = in.readString();
			return data;
		}

		@Override
		public LivesItem[] newArray(int size) {
			return new LivesItem[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" -- MEDIA_NAME = " + MEDIA_NAME).append("\n");
		sb.append(" -- PHONE_MEDIA_POSTER_SMALL = " + PHONE_MEDIA_POSTER_SMALL).append("\n");
		sb.append(" -- id = " + id).append("\n");
		return sb.toString();
	}
}
