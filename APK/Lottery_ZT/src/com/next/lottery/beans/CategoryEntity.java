package com.next.lottery.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryEntity implements Parcelable{
	private String id;
	private String name;
	private int merId;
	private int sort;
	private String path;
	private int level;
	private String description;
	private int parentId;
	private String params;
	private String skus;
	private int status;
	private int isDelete;
	private String creater;
	private String createTime;
	private String lastUpdater;
	private String lastUpdateTime;
	private String keyword;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMerId() {
		return merId;
	}

	public void setMerId(int merId) {
		this.merId = merId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getSkus() {
		return skus;
	}

	public void setSkus(String skus) {
		this.skus = skus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdater() {
		return lastUpdater;
	}

	public void setLastUpdater(String lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id             = ").append(id).append("\n");
		sb.append("name           = ").append(name).append("\n");
		sb.append("merId          = ").append(merId).append("\n");
		sb.append("sort           = ").append(sort).append("\n");
		sb.append("path           = ").append(path).append("\n");
		sb.append("level          = ").append(level).append("\n");
		sb.append("description    = ").append(description).append("\n");
		sb.append("parentId       = ").append(parentId).append("\n");
		sb.append("params         = ").append(params).append("\n");
		sb.append("skus           = ").append(skus).append("\n");
		sb.append("status         = ").append(status).append("\n");
		sb.append("isDelete       = ").append(isDelete).append("\n");
		sb.append("creater        = ").append(creater).append("\n");
		sb.append("createTime     = ").append(createTime).append("\n");
		sb.append("lastUpdater    = ").append(lastUpdater).append("\n");
		sb.append("lastUpdateTime = ").append(lastUpdateTime).append("\n");
		sb.append("keyword = ").append(keyword).append("\n");
		return sb.toString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(merId);
		dest.writeInt(sort);
		dest.writeInt(level);
		dest.writeInt(parentId);
		dest.writeInt(status);
		dest.writeInt(isDelete);
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(path);
		dest.writeString(description);
		dest.writeString(params);
		dest.writeString(skus);
		dest.writeString(creater);
		dest.writeString(createTime);
		dest.writeString(lastUpdater);
		dest.writeString(lastUpdateTime);
		dest.writeString(keyword);
	}
	
	public static final Parcelable.Creator<CategoryEntity> CREATOR = new Parcelable.Creator<CategoryEntity>() {

		@Override
		public CategoryEntity createFromParcel(Parcel in) {
			CategoryEntity data = new CategoryEntity();
			data.setMerId(in.readInt());
			data.setSort(in.readInt());
			data.setLevel(in.readInt());
			data.setParentId(in.readInt());
			data.setStatus(in.readInt());
			data.setIsDelete(in.readInt());
			
			data.setId(in.readString());
			data.setName(in.readString());
			data.setPath(in.readString());
			data.setDescription(in.readString());
			data.setParams(in.readString());
			data.setSkus(in.readString());
			data.setCreater(in.readString());
			data.setCreateTime(in.readString());
			data.setLastUpdater(in.readString());
			data.setLastUpdateTime(in.readString());
			data.setKeyword(in.readString());
			return data;
		}

		@Override
		public CategoryEntity[] newArray(int size) {
			return new CategoryEntity[size];
		}
	};

}
