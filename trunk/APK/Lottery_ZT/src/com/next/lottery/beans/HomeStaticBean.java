package com.next.lottery.beans;

import java.util.ArrayList;

public class HomeStaticBean {
	private int				areaCode;
	private ArrayList<Data>	data		= new ArrayList<Data>();
	private Category		category	= new Category();

	public HomeStaticBean() {
		super();
	}

	public HomeStaticBean(int areaCode, ArrayList<Data> data, Category category) {
		this.areaCode = areaCode;
		this.data = data;
		this.category = category;

	}

	public int getCode() {
		return areaCode;
	}

	public void setCode(int code) {
		this.areaCode = code;
	}

	public ArrayList<Data> getData() {
		return data;
	}

	public void setData(ArrayList<Data> data) {
		this.data = data;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public class Data {
		private String		title;
		private String		cover;
		private int			clickType;
		private ClickParam	clickParam;
		private String		name;

		public Data() {
			super();
		}

		public Data(String title, String cover, int clickType, ClickParam clickParam, String name) {
			this.title = title;
			this.cover = cover;
			this.clickType = clickType;
			this.clickParam = clickParam;
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getCover() {
			return cover;
		}

		public void setCover(String cover) {
			this.cover = cover;
		}

		public int getClickType() {
			return clickType;
		}

		public void setClickType(int clickType) {
			this.clickType = clickType;
		}

		public ClickParam getClickParam() {
			return clickParam;
		}

		public void setClickParam(ClickParam clickParam) {
			this.clickParam = clickParam;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("title:").append(title);
			sb.append("cover:").append(cover);
			sb.append("clickType:").append(clickType);
			sb.append("clickParam:").append(clickParam);
			sb.append("name:").append(name);
			return sb.toString();
		}
	}

	public class ClickParam {
		private String	itemid;
		private String	categoryId;
		private String	keyword;

		public ClickParam() {
			super();
		}

		public ClickParam(String itemid, String categoryid, String keyword) {
			this.itemid = itemid;
			this.categoryId = categoryid;
			this.keyword = keyword;
		}

		public String getItemid() {
			return itemid;
		}

		public void setItemid(String itemid) {
			this.itemid = itemid;
		}

		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
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
			sb.append("itemid:").append(itemid);
			sb.append("categoryId-->").append(categoryId);
			sb.append("keyword-->").append(keyword);
			return sb.toString();
		}
	}

	public class Category {
		private String			name;
		private ArrayList<Tags>	tags;
		private More			more;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public More getMore() {
			return more;
		}

		public void setMore(More more) {
			this.more = more;
		}

		public ArrayList<Tags> getTags() {
			return tags;
		}

		public void setTags(ArrayList<Tags> tags) {
			this.tags = tags;
		}

	}

	public class Tags {
		private String		clickType;	// : 1,
		private String		name;
		private ClickParam	clickParam;

		public String getClickType() {
			return clickType;
		}

		public void setClickType(String clickType) {
			this.clickType = clickType;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ClickParam getClickParam() {
			return clickParam;
		}

		public void setClickParam(ClickParam clickParam) {
			this.clickParam = clickParam;
		}
	}

	public class More {
		private String		clickType;	// : 1,
		private String		name;
		private ClickParam	clickParam;

		public String getClickType() {
			return clickType;
		}

		public void setClickType(String clickType) {
			this.clickType = clickType;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ClickParam getClickParam() {
			return clickParam;
		}

		public void setClickParam(ClickParam clickParam) {
			this.clickParam = clickParam;
		}

	}
}
