package com.dongfang.yzsj.bean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 搜索结果列表
 * 
 * @author dongfang
 */
public class SeachListData implements Parcelable {
	private int total;
	private List<Movie> objs;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Movie> getObjs() {
		return objs;
	}

	public void setObjs(List<Movie> objs) {
		this.objs = objs;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("total = ").append(total).append("\n");
		for (int i = 0, length = objs.size(); i < length; i++) {
			sb.append("listData ").append(i).append(" = ").append(objs.get(i).toString()).append("\n");
		}
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(total);
		dest.writeList(objs);
	}

	public static final Parcelable.Creator<SeachListData> CREATOR = new Parcelable.Creator<SeachListData>() {

		@Override
		public SeachListData createFromParcel(Parcel in) {
			SeachListData data = new SeachListData();
			data.setTotal(in.readInt());
			in.readList(data.getObjs(), Movie.class.getClassLoader());
			return data;
		}

		@Override
		public SeachListData[] newArray(int size) {
			return new SeachListData[size];
		}
	};

}
