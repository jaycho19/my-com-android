package com.next.lottery.beans;

import java.util.ArrayList;

/**
 * search goods bean 
 * 
 * @author gfan
 * 
 */
public class SearchGoosBean {
	private int totalCount;// 8,
	private int nowPage;// 0,
	private int  nowSize;// 0,
	private ArrayList<SearchItem> items;
	
	
	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public int getNowPage() {
		return nowPage;
	}


	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}


	public int getNowSize() {
		return nowSize;
	}


	public void setNowSize(int nowSize) {
		this.nowSize = nowSize;
	}


	public ArrayList<SearchItem> getItems() {
		return items;
	}


	public void setItems(ArrayList<SearchItem> items) {
		this.items = items;
	}


	public class SearchItem{
		private String id ;
		private String title;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
	}
	
}
