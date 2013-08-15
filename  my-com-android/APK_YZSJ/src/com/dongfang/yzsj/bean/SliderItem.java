package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/** kv图 */
public class SliderItem implements Parcelable {
	public String MEDIA_ACTORS;
	public String MEDIA_LENGTH;
	public String MEDIA_NAME;
	public String MEDIA_PIC_RECOM2; // 640*200
//	public String PAD_MEDIA_POSTER_BIG;// 740*360
//	public String PC_MEDIA_POSTER_BIG; // 330*456
//	public String PC_MEDIA_POSTER_HORIZONTAL_BIG; // 450*360
	public String id;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(MEDIA_ACTORS);
		dest.writeString(MEDIA_LENGTH);
		dest.writeString(MEDIA_NAME);
		dest.writeString(MEDIA_PIC_RECOM2);
//		dest.writeString(PAD_MEDIA_POSTER_BIG);
//		dest.writeString(PC_MEDIA_POSTER_BIG);
		dest.writeString(id);
	}

	public static final Parcelable.Creator<SliderItem> CREATOR = new Parcelable.Creator<SliderItem>() {
		@Override
		public SliderItem createFromParcel(Parcel in) {
			SliderItem data = new SliderItem();
			data.MEDIA_ACTORS = in.readString();
			data.MEDIA_LENGTH = in.readString();
			data.MEDIA_NAME = in.readString();
			data.MEDIA_PIC_RECOM2 = in.readString();
//			data.PAD_MEDIA_POSTER_BIG = in.readString();
//			data.PC_MEDIA_POSTER_BIG = in.readString();
			data.id = in.readString();
			return data;
		}

		@Override
		public SliderItem[] newArray(int size) {
			return new SliderItem[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" -- MEDIA_ACTORS = " + MEDIA_ACTORS).append("\n");
		sb.append(" -- MEDIA_LENGTH = " + MEDIA_LENGTH).append("\n");
		sb.append(" -- MEDIA_NAME = " + MEDIA_NAME).append("\n");
		sb.append(" -- MEDIA_PIC_RECOM2 = " + MEDIA_PIC_RECOM2).append("\n");
		sb.append(" -- MEDIA_PIC_RECOM2 = " + MEDIA_PIC_RECOM2).append("\n");
//		sb.append(" -- PAD_MEDIA_POSTER_BIG = " + PAD_MEDIA_POSTER_BIG).append("\n");
//		sb.append(" -- PC_MEDIA_POSTER_BIG = " + PC_MEDIA_POSTER_BIG).append("\n");
		sb.append(" -- id = " + id).append("\n");
		return sb.toString();
	}

}
