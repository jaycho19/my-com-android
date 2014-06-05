package com.dongfang.daohang.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class AreaBean implements Parcelable {

	// "areaId": "322",
	// "placeName": "测试商场",
	// "floor": "B3",
	// "floorId": "36",
	// "areaName": "9",
	// "areaType": "10"

	private String areaId;
	private String placeName;
	private String floor;
	private String floorId;
	private String areaName;
	private String areaType;

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("areaId=").append(areaId).append("\n");
		sb.append("placeName=").append(placeName).append("\n");
		sb.append("floor=").append(floor).append("\n");
		sb.append("floorId=").append(floorId).append("\n");
		sb.append("areaName=").append(areaName).append("\n");
		sb.append("areatype=").append(areaType).append("\n");
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(areaId);
		dest.writeString(placeName);
		dest.writeString(floor);
		dest.writeString(floorId);
		dest.writeString(areaName);
		dest.writeString(areaType);
	}

	public static final Parcelable.Creator<AreaBean> CREATOR = new Creator<AreaBean>() {

		@Override
		public AreaBean[] newArray(int size) {
			return new AreaBean[size];
		}

		// 将Parcel对象反序列化为ParcelableDate
		@Override
		public AreaBean createFromParcel(Parcel source) {
			AreaBean bean = new AreaBean();
			bean.areaId = source.readString();
			bean.placeName = source.readString();
			bean.floor = source.readString();
			bean.floorId = source.readString();
			bean.areaName = source.readString();
			bean.areaType = source.readString();
			return bean;
		}
	};

}
