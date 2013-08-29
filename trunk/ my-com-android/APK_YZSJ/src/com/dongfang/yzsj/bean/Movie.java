package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 首页每个频道的视频信息 <br>
 * 频道列表信息<br>
 * 搜索列表信息<dr>
 * 
 * @author dongfang
 */
public class Movie implements Parcelable {
	private String MEDIA_ACTORS; // 演员
	private String MEDIA_LENGTH; // 视频长度
	private String MEDIA_NAME; // 视频名称
	private String MEDIA_HOMETOWN; // 发布地
	private String MEDIA_INTRO; // 信息
	private String MEDIA_DIRECTORS; // 导员
	private String MEDIA_AGE;// 发布年
	// public String MEDIA_PIC_RECOM2;// 640*200
	// public String PAD_MEDIA_POSTER_BIG; // 740*360
	private String PC_MEDIA_POSTER_BIG; // 330*456
	// public String PC_MEDIA_POSTER_HORIZONTAL_BIG; // 464*329
	private String id;

	public String getMEDIA_ACTORS() {
		return MEDIA_ACTORS;
	}

	public void setMEDIA_ACTORS(String mEDIA_ACTORS) {
		MEDIA_ACTORS = mEDIA_ACTORS;
	}

	public String getMEDIA_LENGTH() {
		return MEDIA_LENGTH;
	}

	public void setMEDIA_LENGTH(String mEDIA_LENGTH) {
		MEDIA_LENGTH = mEDIA_LENGTH;
	}

	public String getMEDIA_NAME() {
		return MEDIA_NAME;
	}

	public void setMEDIA_NAME(String mEDIA_NAME) {
		MEDIA_NAME = mEDIA_NAME;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public String getMEDIA_HOMETOWN() {
		return MEDIA_HOMETOWN;
	}

	public void setMEDIA_HOMETOWN(String mEDIA_HOMETOWN) {
		MEDIA_HOMETOWN = mEDIA_HOMETOWN;
	}

	public String getMEDIA_INTRO() {
		return MEDIA_INTRO;
	}

	public void setMEDIA_INTRO(String mEDIA_INTRO) {
		MEDIA_INTRO = mEDIA_INTRO;
	}

	public String getMEDIA_DIRECTORS() {
		return MEDIA_DIRECTORS;
	}

	public void setMEDIA_DIRECTORS(String mEDIA_DIRECTORS) {
		MEDIA_DIRECTORS = mEDIA_DIRECTORS;
	}

	public String getMEDIA_AGE() {
		return MEDIA_AGE;
	}

	public void setMEDIA_AGE(String mEDIA_AGE) {
		MEDIA_AGE = mEDIA_AGE;
	}

	public String getPC_MEDIA_POSTER_BIG() {
		return PC_MEDIA_POSTER_BIG;
	}

	public void setPC_MEDIA_POSTER_BIG(String pC_MEDIA_POSTER_BIG) {
		PC_MEDIA_POSTER_BIG = pC_MEDIA_POSTER_BIG;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(MEDIA_ACTORS);
		dest.writeString(MEDIA_LENGTH);
		dest.writeString(MEDIA_NAME);
		dest.writeString(MEDIA_HOMETOWN);
		dest.writeString(MEDIA_INTRO);
		dest.writeString(MEDIA_DIRECTORS);
		dest.writeString(MEDIA_AGE);
		// dest.writeString(MEDIA_PIC_RECOM2);
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
			data.MEDIA_HOMETOWN = in.readString();
			data.MEDIA_INTRO = in.readString();
			data.MEDIA_DIRECTORS = in.readString();
			data.MEDIA_AGE = in.readString();
			// data.MEDIA_PIC_RECOM2 = in.readString();
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
		sb.append(" -- -- MEDIA_ACTORS    = " + MEDIA_ACTORS).append("\n");
		sb.append(" -- -- MEDIA_LENGTH    = " + MEDIA_LENGTH).append("\n");
		sb.append(" -- -- MEDIA_NAME      = " + MEDIA_NAME).append("\n");
		sb.append(" -- -- MEDIA_HOMETOWN  = " + MEDIA_HOMETOWN).append("\n");
		sb.append(" -- -- MEDIA_INTRO     = " + MEDIA_INTRO).append("\n");
		sb.append(" -- -- MEDIA_DIRECTORS = " + MEDIA_DIRECTORS).append("\n");
		sb.append(" -- -- MEDIA_AGE       = " + MEDIA_AGE).append("\n");
		// sb.append(" -- -- MEDIA_PIC_RECOM2 = " + MEDIA_PIC_RECOM2).append("\n");
		sb.append(" -- -- PC_MEDIA_POSTER_BIG = " + PC_MEDIA_POSTER_BIG).append("\n");
		sb.append(" -- -- id = " + id).append("\n");
		return sb.toString();
	}

}
