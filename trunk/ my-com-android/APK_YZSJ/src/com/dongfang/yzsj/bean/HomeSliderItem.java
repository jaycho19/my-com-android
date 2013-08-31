package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/** kv图 */
public class HomeSliderItem implements Parcelable {
	// public String MEDIA_ACTORS;
	// public String MEDIA_LENGTH;
	// public String PAD_MEDIA_POSTER_BIG;// 740*360
	// public String PC_MEDIA_POSTER_BIG; // 330*456
	// public String PC_MEDIA_POSTER_HORIZONTAL_BIG; // 450*360
	private String MEDIA_NAME;
	private String MEDIA_PIC_RECOM2; // 640*200
	private String id;
	private String channelId;

	public String getMEDIA_NAME() {
		return MEDIA_NAME;
	}

	public void setMEDIA_NAME(String mEDIA_NAME) {
		MEDIA_NAME = mEDIA_NAME;
	}

	public String getMEDIA_PIC_RECOM2() {
		return MEDIA_PIC_RECOM2;
	}

	public void setMEDIA_PIC_RECOM2(String mEDIA_PIC_RECOM2) {
		MEDIA_PIC_RECOM2 = mEDIA_PIC_RECOM2;
	}

	public String getId() {
		return TextUtils.isEmpty(id) ? "0" : id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelId() {
		return TextUtils.isEmpty(channelId) ? "0" : channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
		// sb.append(" -- MEDIA_ACTORS = ").append(MEDIA_ACTORS).append("\n");
		// sb.append(" -- MEDIA_LENGTH = ").append(MEDIA_LENGTH).append("\n");
		// sb.append(" -- PAD_MEDIA_POSTER_BIG = ").append(PAD_MEDIA_POSTER_BIG).append("\n");
		// sb.append(" -- PC_MEDIA_POSTER_BIG = ").append(PC_MEDIA_POSTER_BIG).append("\n");
		// sb.append(" -- PC_MEDIA_POSTER_HORIZONTAL_BIG = ").append(PC_MEDIA_POSTER_HORIZONTAL_BIG).append("\n");
		sb.append(" -- MEDIA_NAME       = ").append(MEDIA_NAME).append("\n");
		sb.append(" -- MEDIA_PIC_RECOM2 = ").append(MEDIA_PIC_RECOM2).append("\n");
		sb.append(" -- id               = ").append(id).append("\n");
		sb.append(" -- channelId        = ").append(channelId).append("\n");
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// dest.writeString(MEDIA_ACTORS);
		// dest.writeString(MEDIA_LENGTH);
		// dest.writeString(PAD_MEDIA_POSTER_BIG);
		// dest.writeString(PC_MEDIA_POSTER_BIG);
		// dest.writeString(PC_MEDIA_POSTER_HORIZONTAL_BIG);
		dest.writeString(MEDIA_NAME);
		dest.writeString(MEDIA_PIC_RECOM2);
		dest.writeString(id);
		dest.writeString(channelId);
	}

	public static final Parcelable.Creator<HomeSliderItem> CREATOR = new Parcelable.Creator<HomeSliderItem>() {
		@Override
		public HomeSliderItem createFromParcel(Parcel in) {
			HomeSliderItem data = new HomeSliderItem();
			// data.MEDIA_ACTORS = in.readString();
			// data.MEDIA_LENGTH = in.readString();
			// data.PAD_MEDIA_POSTER_BIG = in.readString();
			// data.PC_MEDIA_POSTER_BIG = in.readString();
			// data.PC_MEDIA_POSTER_HORIZONTAL_BIG = in.readString();
			data.MEDIA_NAME = in.readString();
			data.MEDIA_PIC_RECOM2 = in.readString();
			data.id = in.readString();
			data.channelId = in.readString();
			return data;
		}

		@Override
		public HomeSliderItem[] newArray(int size) {
			return new HomeSliderItem[size];
		}
	};

}
