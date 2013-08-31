package com.dongfang.yzsj.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailContent implements Parcelable {
	private int CLIP_COUNT; // 集数
	private String id; // 内容id
	private String MEDIA_ACTORS; // 演员
	private String MEDIA_INTRO; // 详情
	private String MEDIA_LENGTH; // 视频长度
	private String MEDIA_NAME; // 名称
	private String PC_MEDIA_POSTER_BIG; // 图片
	private List<DetailContentClip> CLIP_BANDWITHS;//

	private DetailContent() {
		CLIP_BANDWITHS = new ArrayList<DetailContentClip>();
	}

	public int getCLIP_COUNT() {
		return CLIP_COUNT;
	}

	public void setCLIP_COUNT(int cLIP_COUNT) {
		CLIP_COUNT = cLIP_COUNT;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMEDIA_ACTORS() {
		return MEDIA_ACTORS;
	}

	public void setMEDIA_ACTORS(String mEDIA_ACTORS) {
		MEDIA_ACTORS = mEDIA_ACTORS;
	}

	public String getMEDIA_INTRO() {
		return MEDIA_INTRO;
	}

	public void setMEDIA_INTRO(String mEDIA_INTRO) {
		MEDIA_INTRO = mEDIA_INTRO;
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

	public String getPC_MEDIA_POSTER_BIG() {
		return PC_MEDIA_POSTER_BIG;
	}

	public void setPC_MEDIA_POSTER_BIG(String pC_MEDIA_POSTER_BIG) {
		PC_MEDIA_POSTER_BIG = pC_MEDIA_POSTER_BIG;
	}

	public List<DetailContentClip> getCLIP_BANDWITHS() {
		return CLIP_BANDWITHS;
	}

	public void setCLIP_BANDWITHS(List<DetailContentClip> cLIP_BANDWITHS) {
		CLIP_BANDWITHS = cLIP_BANDWITHS;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
		sb.append("-- CLIP_COUNT          = ").append(CLIP_COUNT).append("\n");
		sb.append("-- id                  = ").append(id).append("\n");
		sb.append("-- MEDIA_ACTORS        = ").append(MEDIA_ACTORS).append("\n");
		sb.append("-- MEDIA_INTRO         = ").append(MEDIA_INTRO).append("\n");
		sb.append("-- MEDIA_LENGTH        = ").append(MEDIA_LENGTH).append("\n");
		sb.append("-- MEDIA_NAME          = ").append(MEDIA_NAME).append("\n");
		sb.append("-- PC_MEDIA_POSTER_BIG = ").append(PC_MEDIA_POSTER_BIG).append("\n");

		for (int i = 0, length = CLIP_BANDWITHS.size(); i < length; i++) {
			sb.append("CLIP_BANDWITHS ").append(i).append(" = ").append(CLIP_BANDWITHS.get(i).toString()).append("\n");
		}
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(CLIP_COUNT);
		dest.writeString(id);
		dest.writeString(MEDIA_ACTORS);
		dest.writeString(MEDIA_INTRO);
		dest.writeString(MEDIA_LENGTH);
		dest.writeString(MEDIA_NAME);
		dest.writeString(PC_MEDIA_POSTER_BIG);
		dest.writeTypedList(CLIP_BANDWITHS);
	}

	public static final Parcelable.Creator<DetailContent> CREATOR = new Parcelable.Creator<DetailContent>() {

		@Override
		public DetailContent createFromParcel(Parcel in) {
			DetailContent data = new DetailContent();
			data.CLIP_COUNT = in.readInt();
			data.id = in.readString();
			data.MEDIA_ACTORS = in.readString();
			data.MEDIA_INTRO = in.readString();
			data.MEDIA_LENGTH = in.readString();
			data.MEDIA_NAME = in.readString();
			data.PC_MEDIA_POSTER_BIG = in.readString();
			// data.CLIP_BANDWITHS = in. (DetailContentClip.CREATOR);
			in.readTypedList(data.CLIP_BANDWITHS, DetailContentClip.CREATOR);
			return data;
		}

		@Override
		public DetailContent[] newArray(int size) {
			return new DetailContent[size];
		}
	};

}
