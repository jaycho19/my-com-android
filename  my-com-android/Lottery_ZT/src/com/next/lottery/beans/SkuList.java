package com.next.lottery.beans;

import java.util.ArrayList;

/**
 * 
 * @author gfan
 * 
 */
public class SkuList {
	private String pid;
	private String pname;
	private ArrayList<SKUItem> values;
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
	public ArrayList<SKUItem> getValues() {
		return values;
	}
	public void setValues(ArrayList<SKUItem> values) {
		this.values = values;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("   pid = ").append(pid).append("\n");
		sb.append("   pname = ").append(pname).append("\n");
		for(SKUItem s : values){
			sb.append(s.toString());
		}
		return sb.toString();
	}
	
	
	
	
	
}
