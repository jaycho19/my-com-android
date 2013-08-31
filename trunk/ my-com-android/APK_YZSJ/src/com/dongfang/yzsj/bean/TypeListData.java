package com.dongfang.yzsj.bean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeListData implements Parcelable {
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
		StringBuilder sb = new StringBuilder("\n");
		sb.append("-- total = ").append(total).append("\n");
		for (int i = 0, length = objs.size(); i < length; i++) {
			sb.append("-- objs ").append(i).append(" = ").append(objs.get(i).toString()).append("\n");
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
		dest.writeTypedList(objs);
	}

	public static final Parcelable.Creator<TypeListData> CREATOR = new Parcelable.Creator<TypeListData>() {
		@Override
		public TypeListData createFromParcel(Parcel in) {
			TypeListData data = new TypeListData();
			data.total = in.readInt();
			in.readTypedList(data.objs, Movie.CREATOR);
			return data;
		}

		@Override
		public TypeListData[] newArray(int size) {
			return new TypeListData[size];
		}
	};

}
