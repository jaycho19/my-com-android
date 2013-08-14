package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/** 每个频道的视频信息@author dongfang */
public class Movie implements Parcelable {
	public String MEDIA_ACTORS; // 演员
	public String MEDIA_LENGTH; // 视频长度
	public String MEDIA_NAME; // 视频名称
	public String MEDIA_PIC_RECOM2;
	// public String PAD_MEDIA_POSTER_BIG;
	public String PC_MEDIA_POSTER_BIG;
	// public String PC_MEDIA_POSTER_HORIZONTAL_BIG;
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
		dest.writeString(PC_MEDIA_POSTER_BIG);
		dest.writeString(id);
	}

	public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
		@Override
		public Movie createFromParcel(Parcel in) {
			Movie data = new Movie();
			data.MEDIA_ACTORS = in.readString();
			data.MEDIA_LENGTH = in.readString();
			data.MEDIA_NAME = in.readString();
			data.MEDIA_PIC_RECOM2 = in.readString();
			data.PC_MEDIA_POSTER_BIG = in.readString();
			data.id = in.readString();
			return data;
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" -- -- MEDIA_ACTORS = " + MEDIA_ACTORS).append("\n");
		sb.append(" -- -- MEDIA_LENGTH = " + MEDIA_LENGTH).append("\n");
		sb.append(" -- -- MEDIA_NAME = " + MEDIA_NAME).append("\n");
		sb.append(" -- -- MEDIA_PIC_RECOM2 = " + MEDIA_PIC_RECOM2).append("\n");
		sb.append(" -- -- PC_MEDIA_POSTER_BIG = " + PC_MEDIA_POSTER_BIG).append("\n");
		sb.append(" -- -- id = " + id).append("\n");
		return sb.toString();
	}

}
