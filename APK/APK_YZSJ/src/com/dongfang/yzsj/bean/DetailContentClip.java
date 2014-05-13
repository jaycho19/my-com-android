package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 详情页码流信息
 * 
 * @author dongfang
 * 
 */
public class DetailContentClip implements Parcelable {
	private String name; // 清晰度名称
	private String code; // 清晰度编号

	private DetailContentClip() {
		name = "";
		code = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(code);
	}

	public static final Parcelable.Creator<DetailContentClip> CREATOR = new Parcelable.Creator<DetailContentClip>() {

		@Override
		public DetailContentClip createFromParcel(Parcel in) {
			DetailContentClip data = new DetailContentClip();
			data.name = in.readString();
			data.code = in.readString();
			return data;
		}

		@Override
		public DetailContentClip[] newArray(int size) {
			return new DetailContentClip[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
		sb.append(" -- -- name = ").append(name).append("\n");
		sb.append(" -- -- code = ").append(code).append("\n");
		return sb.toString();
	}
}
