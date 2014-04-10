package com.next.lottery.db.bean;

import com.lidroid.xutils.db.annotation.Id;

public class SkulistDbBean  {
	@Id(column="_id")
	private int   _id;
	private String pid;
	private String pname;
	private String vid; // 编号
	private String vname; // 编号说明，不如 蓝色，M号等
	private int    itemId;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
}
