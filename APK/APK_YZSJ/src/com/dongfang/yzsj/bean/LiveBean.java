package com.dongfang.yzsj.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/** 直播数据 @author dongfang */
public class LiveBean implements Parcelable {
	public static final String TAG = "LiveBean";
	private int total;
	private List<HomeLivesItem> lives;

	public LiveBean() {
		total = 0;
		lives = new ArrayList<HomeLivesItem>();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<HomeLivesItem> getLives() {
		return lives;
	}

	public void setLives(List<HomeLivesItem> lives) {
		this.lives = lives;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(total);
		dest.writeList(lives);
	}

	public static final Parcelable.Creator<LiveBean> CREATOR = new Parcelable.Creator<LiveBean>() {

		@Override
		public LiveBean createFromParcel(Parcel in) {
			LiveBean data = new LiveBean();
			data.setTotal(in.readInt());
			in.readList(data.getLives(), HomeLivesItem.class.getClassLoader());
			return data;
		}

		@Override
		public LiveBean[] newArray(int size) {
			return new LiveBean[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("total = " + total).append("\n");
		for (HomeLivesItem item : lives) {
			sb.append("lives = " + item.toString()).append("\n");
		}
		return sb.toString();
	}
}
