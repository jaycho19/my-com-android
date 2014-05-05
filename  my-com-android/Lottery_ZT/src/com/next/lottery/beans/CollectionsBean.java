package com.next.lottery.beans;

import java.util.ArrayList;

/**
 * 收藏
 * 
 * @author gfan
 * 
 */
public class CollectionsBean {
	private int totalCount;// 6,
	private String nowPage;// "1",
	private String nowSize;// "3",
	private ArrayList<CollectionsEnity>  collects = new ArrayList<CollectionsEnity>();
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getNowPage() {
		return nowPage;
	}

	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}

	public String getNowSize() {
		return nowSize;
	}

	public void setNowSize(String nowSize) {
		this.nowSize = nowSize;
	}

	public ArrayList<CollectionsEnity> getCollects() {
		return collects;
	}

	public void setCollects(ArrayList<CollectionsEnity> collects) {
		this.collects = collects;
	}

	public class CollectionsEnity{
		private int id;// 7,
		private String userId;// "3",
		private int merId;// 1,
		private int itemId;// 45,
		private int price;// 129000,
		private int stockNum;// 0,
		private String title;// "133款NINE WEST玖熙正品厚底坡跟高跟凉鞋-301033709L*2",
		private String picUrl;// "http://img01.taobaocdn.com/bao/uploaded/i1/10328026524879122/T16ldYFitbXXXXXXXX_!!0-item_pic.jpg"
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public int getMerId() {
			return merId;
		}
		public void setMerId(int merId) {
			this.merId = merId;
		}
		public int getItemId() {
			return itemId;
		}
		public void setItemId(int itemId) {
			this.itemId = itemId;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public int getStockNum() {
			return stockNum;
		}
		public void setStockNum(int stockNum) {
			this.stockNum = stockNum;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getPicUrl() {
			return picUrl;
		}
		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}
	}
}
