package com.next.lottery.beans;

import java.util.ArrayList;

import com.next.lottery.beans.OrderBean.ActivityInfo;

/**
 * 订单价格计算
 * 
 * @author gfan
 * 
 */
public class CalculateOrderListBean {

	private int						itemPrice;
	private ArrayList<Items>		items;
	private int						price;
	private UserDiscount			userDiscount;
	private ArrayList<ActivityInfo>	activitys;
	private ArrayList<Coupons>		coupons;
	private TraFee					traFee;

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public ArrayList<Items> getItems() {
		return items;
	}

	public void setItems(ArrayList<Items> items) {
		this.items = items;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public UserDiscount getUserDiscount() {
		return userDiscount;
	}

	public void setUserDiscount(UserDiscount userDiscount) {
		this.userDiscount = userDiscount;
	}

	public ArrayList<ActivityInfo> getActivitys() {
		return activitys;
	}

	public void setActivitys(ArrayList<ActivityInfo> activitys) {
		this.activitys = activitys;
	}

	public ArrayList<Coupons> getCoupons() {
		return coupons;
	}

	public void setCoupons(ArrayList<Coupons> coupons) {
		this.coupons = coupons;
	}

	public TraFee getTraFee() {
		return traFee;
	}

	public void setTraFee(TraFee traFee) {
		this.traFee = traFee;
	}

	public class Items {
		private String	itemId;		// "1",
		private String	itemName;	// "连衣裙",
		private int		skuId;		// 1,
		private int		count;		// 2,
		private String	picUrl;
		private int		price;

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public int getSkuId() {
			return skuId;
		}

		public void setSkuId(int skuId) {
			this.skuId = skuId;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}
	}

	public class UserDiscount {
		private int	discount;
		private int	uerDiscountPrice;

		public int getDiscount() {
			return discount;
		}

		public void setDiscount(int discount) {
			this.discount = discount;
		}

		public int getUerDiscountPrice() {
			return uerDiscountPrice;
		}

		public void setUerDiscountPrice(int uerDiscountPrice) {
			this.uerDiscountPrice = uerDiscountPrice;
		}

	}

	public class Coupons {
		private int		id;
		private String	name;
		private int		couponId;
		private String	couponCode;
		private int		discountLine;
		private int		discountValue;
		private int		discountType;
		private int		disPrice;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getCouponId() {
			return couponId;
		}

		public void setCouponId(int couponId) {
			this.couponId = couponId;
		}

		public String getCouponCode() {
			return couponCode;
		}

		public void setCouponCode(String couponCode) {
			this.couponCode = couponCode;
		}

		public int getDiscountLine() {
			return discountLine;
		}

		public void setDiscountLine(int discountLine) {
			this.discountLine = discountLine;
		}

		public int getDiscountValue() {
			return discountValue;
		}

		public void setDiscountValue(int discountValue) {
			this.discountValue = discountValue;
		}

		public int getDiscountType() {
			return discountType;
		}

		public void setDiscountType(int discountType) {
			this.discountType = discountType;
		}

		public int getDisPrice() {
			return disPrice;
		}

		public void setDisPrice(int disPrice) {
			this.disPrice = disPrice;
		}

	}

	public class TraFee {
		private int	deliveryModeId;			// 2,
		private int	userDeliveryAddressId;	// 1,
		private int	traFee;					// 500

		public int getDeliveryModeId() {
			return deliveryModeId;
		}

		public void setDeliveryModeId(int deliveryModeId) {
			this.deliveryModeId = deliveryModeId;
		}

		public int getUserDeliveryAddressId() {
			return userDeliveryAddressId;
		}

		public void setUserDeliveryAddressId(int userDeliveryAddressId) {
			this.userDeliveryAddressId = userDeliveryAddressId;
		}

		public int getTraFee() {
			return traFee;
		}

		public void setTraFee(int traFee) {
			this.traFee = traFee;
		}
	}

}
