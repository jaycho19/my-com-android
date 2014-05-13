package com.next.lottery.beans;

import java.util.ArrayList;

/**
 * my order bean
 * 
 * @author gfan
 * 
 */
public class OrderBean {

	private int total;
	private ArrayList<OrderEntity> data;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ArrayList<OrderEntity> getData() {
		return data;
	}

	public void setData(ArrayList<OrderEntity> orderList) {
		this.data = orderList;
	}

	public class OrderEntity {
		private String orderNo; // ;//
								// "201403261395813267",
		private String userId; // "3",
		private int merId; // 1,
		private String buyerMsg; // "",
		private String buyerComeFrom; // "",
		private String buyerIp; // "127.0.0.1",
		private int buyerArea; // 0,
		private int type; // 1,
		private String refOrderNo; // "",
		private int sumOldPrice; // 500000,
		private int sumRealPrice; // 0,
		private int backPrice; // 0,
		private int traFee; // 500,
		private int sellerCodFee; // 0,
		private int payPrice; // 398700,
		private int offsetPrice; // 0,
		private int price; // 398700,
		private int isLgtype; // 2,
		private int credits; // 0,
		private int sumWeight; // 0,
		private int deliveryModeId; // 2,
		private int branchId; // 0,
		private int payModeId; // 1,
		private String payOrderNo; // "",
		private String payBackOrderNo; // "",
		private String payTime; // "1970-01-01 08;//00;//00",
		private int codStatus; // 2,
		private int status; // 1,
		private int invoiceType; // 1,
		private int isAppraise; // private int 2,
		private String endTime; // "1970-01-01 08;//00;//00",
		private String closeReason; // "订单错误",
		private String appraiseTime; // "1970-01-01 08;//00;//00",
		private int refundId; // 0,
		private int isDelete; // 2,
		private String creater; // "3",
		private String createTime; // "2014-03-26 13;//54;//27",
		private String lastUpdater; // "3",
		private String lastUpdateTime; // "2014-03-26 13;//54;//27",

		private UserDeliveryAddress userDeliveryAddres;
		private ArrayList<ActivityInfo> activitys;
		private Invoice invoice;
		private ArrayList<Items> items;
		private ArrayList<String> coupons;
		private ArrayList<String> opers;
		private ArrayList<String> packs;

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
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

		public String getBuyerMsg() {
			return buyerMsg;
		}

		public void setBuyerMsg(String buyerMsg) {
			this.buyerMsg = buyerMsg;
		}

		public String getBuyerComeFrom() {
			return buyerComeFrom;
		}

		public void setBuyerComeFrom(String buyerComeFrom) {
			this.buyerComeFrom = buyerComeFrom;
		}

		public String getBuyerIp() {
			return buyerIp;
		}

		public void setBuyerIp(String buyerIp) {
			this.buyerIp = buyerIp;
		}

		public int getBuyerArea() {
			return buyerArea;
		}

		public void setBuyerArea(int buyerArea) {
			this.buyerArea = buyerArea;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getRefOrderNo() {
			return refOrderNo;
		}

		public void setRefOrderNo(String refOrderNo) {
			this.refOrderNo = refOrderNo;
		}

		public int getSumOldPrice() {
			return sumOldPrice;
		}

		public void setSumOldPrice(int sumOldPrice) {
			this.sumOldPrice = sumOldPrice;
		}

		public int getSumRealPrice() {
			return sumRealPrice;
		}

		public void setSumRealPrice(int sumRealPrice) {
			this.sumRealPrice = sumRealPrice;
		}

		public int getBackPrice() {
			return backPrice;
		}

		public void setBackPrice(int backPrice) {
			this.backPrice = backPrice;
		}

		public int getTraFee() {
			return traFee;
		}

		public void setTraFee(int traFee) {
			this.traFee = traFee;
		}

		public int getSellerCodFee() {
			return sellerCodFee;
		}

		public void setSellerCodFee(int sellerCodFee) {
			this.sellerCodFee = sellerCodFee;
		}

		public int getPayPrice() {
			return payPrice;
		}

		public void setPayPrice(int payPrice) {
			this.payPrice = payPrice;
		}

		public int getOffsetPrice() {
			return offsetPrice;
		}

		public void setOffsetPrice(int offsetPrice) {
			this.offsetPrice = offsetPrice;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public int getIsLgtype() {
			return isLgtype;
		}

		public void setIsLgtype(int isLgtype) {
			this.isLgtype = isLgtype;
		}

		public int getCredits() {
			return credits;
		}

		public void setCredits(int credits) {
			this.credits = credits;
		}

		public int getSumWeight() {
			return sumWeight;
		}

		public void setSumWeight(int sumWeight) {
			this.sumWeight = sumWeight;
		}

		public int getDeliveryModeId() {
			return deliveryModeId;
		}

		public void setDeliveryModeId(int deliveryModeId) {
			this.deliveryModeId = deliveryModeId;
		}

		public int getBranchId() {
			return branchId;
		}

		public void setBranchId(int branchId) {
			this.branchId = branchId;
		}

		public int getPayModeId() {
			return payModeId;
		}

		public void setPayModeId(int payModeId) {
			this.payModeId = payModeId;
		}

		public String getPayOrderNo() {
			return payOrderNo;
		}

		public void setPayOrderNo(String payOrderNo) {
			this.payOrderNo = payOrderNo;
		}

		public String getPayBackOrderNo() {
			return payBackOrderNo;
		}

		public void setPayBackOrderNo(String payBackOrderNo) {
			this.payBackOrderNo = payBackOrderNo;
		}

		public String getPayTime() {
			return payTime;
		}

		public void setPayTime(String payTime) {
			this.payTime = payTime;
		}

		public int getCodStatus() {
			return codStatus;
		}

		public void setCodStatus(int codStatus) {
			this.codStatus = codStatus;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getInvoiceType() {
			return invoiceType;
		}

		public void setInvoiceType(int invoiceType) {
			this.invoiceType = invoiceType;
		}

		public int getIsAppraise() {
			return isAppraise;
		}

		public void setIsAppraise(int isAppraise) {
			this.isAppraise = isAppraise;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getCloseReason() {
			return closeReason;
		}

		public void setCloseReason(String closeReason) {
			this.closeReason = closeReason;
		}

		public String getAppraiseTime() {
			return appraiseTime;
		}

		public void setAppraiseTime(String appraiseTime) {
			this.appraiseTime = appraiseTime;
		}

		public int getRefundId() {
			return refundId;
		}

		public void setRefundId(int refundId) {
			this.refundId = refundId;
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

		public ArrayList<ActivityInfo> getActivitys() {
			return activitys;
		}

		public void setActivitys(ArrayList<ActivityInfo> activitys) {
			this.activitys = activitys;
		}

		public UserDeliveryAddress getUserDeliveryAddres() {
			return userDeliveryAddres;
		}

		public void setUserDeliveryAddres(UserDeliveryAddress userDeliveryAddres) {
			this.userDeliveryAddres = userDeliveryAddres;
		}

		public Invoice getInvoice() {
			return invoice;
		}

		public void setInvoice(Invoice invoice) {
			this.invoice = invoice;
		}

		public ArrayList<Items> getItems() {
			return items;
		}

		public void setItems(ArrayList<Items> items) {
			this.items = items;
		}

		public ArrayList<String> getCoupons() {
			return coupons;
		}

		public void setCoupons(ArrayList<String> coupons) {
			this.coupons = coupons;
		}

		public ArrayList<String> getOpers() {
			return opers;
		}

		public void setOpers(ArrayList<String> opers) {
			this.opers = opers;
		}

		public ArrayList<String> getPacks() {
			return packs;
		}

		public void setPacks(ArrayList<String> packs) {
			this.packs = packs;
		}

	}

	/** 活动信息 */
	public class ActivityInfo {
		private String id;// "1",
		private int merId;// 1,
		private String name;// "满百元减10元",
		private String description;// "满了100元减10元",
		private int decreaseNum;// 1,
		private int discountLine;// 10000,
		private int discountValue;// 1000,
		private int discountType;// 1,
		private String startDate;// "2014-03-09 13：51：02",
		private String endDate;// "2015-07-23 13：51：09",
		private String categoryIds;// null,
		private String targetIds;// null,
		private int status;// 1,
		private int offsetPrice;// 1000
		private int disPrice;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getMerId() {
			return merId;
		}

		public void setMerId(int merId) {
			this.merId = merId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getDecreaseNum() {
			return decreaseNum;
		}

		public void setDecreaseNum(int decreaseNum) {
			this.decreaseNum = decreaseNum;
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

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getCategoryIds() {
			return categoryIds;
		}

		public void setCategoryIds(String categoryIds) {
			this.categoryIds = categoryIds;
		}

		public String getTargetIds() {
			return targetIds;
		}

		public void setTargetIds(String targetIds) {
			this.targetIds = targetIds;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getOffsetPrice() {
			return offsetPrice;
		}

		public void setOffsetPrice(int offsetPrice) {
			this.offsetPrice = offsetPrice;
		}

		public int getDisPrice() {
			return disPrice;
		}

		public void setDisPrice(int disPrice) {
			this.disPrice = disPrice;
		}

	}

	/** 用户地址 */
	public class UserDeliveryAddress {
		private String id;// "6",
		private String orderNo;// "201403261395813267",
		private String consignee;// "傅琛",
		private int areaId;// 0,
		private String address;// "中国 上海 上海 闸北区 共和新路 黄山路280弄8号503",
		private String phone;// "15901871159",
		private String tel;// "021-56900145",
		private String post;// "200010",
		private String email;// "fc0429@126.com",
		private String deliveryTime;// ""

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getConsignee() {
			return consignee;
		}

		public void setConsignee(String consignee) {
			this.consignee = consignee;
		}

		public int getAreaId() {
			return areaId;
		}

		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getPost() {
			return post;
		}

		public void setPost(String post) {
			this.post = post;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getDeliveryTime() {
			return deliveryTime;
		}

		public void setDeliveryTime(String deliveryTime) {
			this.deliveryTime = deliveryTime;
		}

	}

	/** 发票信息 */
	public class Invoice {
		private String id;// "5",
		private String orderNo;// "201403261395813267",
		private String title;// "发票title",
		private int type;// 0,
		private String content;// "发票内容",
		private int isDetail;// 2,
		private String zzName;// "",
		private String zzCode;// "",
		private String zzAddress;// "",
		private String zzTel;// "",
		private String zzBank;// "",
		private String zzAccount;// ""

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getIsDetail() {
			return isDetail;
		}

		public void setIsDetail(int isDetail) {
			this.isDetail = isDetail;
		}

		public String getZzName() {
			return zzName;
		}

		public void setZzName(String zzName) {
			this.zzName = zzName;
		}

		public String getZzCode() {
			return zzCode;
		}

		public void setZzCode(String zzCode) {
			this.zzCode = zzCode;
		}

		public String getZzAddress() {
			return zzAddress;
		}

		public void setZzAddress(String zzAddress) {
			this.zzAddress = zzAddress;
		}

		public String getZzTel() {
			return zzTel;
		}

		public void setZzTel(String zzTel) {
			this.zzTel = zzTel;
		}

		public String getZzBank() {
			return zzBank;
		}

		public void setZzBank(String zzBank) {
			this.zzBank = zzBank;
		}

		public String getZzAccount() {
			return zzAccount;
		}

		public void setZzAccount(String zzAccount) {
			this.zzAccount = zzAccount;
		}

	}

	/** 商品信息 */
	public class Items {
		private int id;// 14,
		private String orderNo;// "201403261395813267",
		private String itemId;// "1",
		private int type;// 1,
		private int skuId;// 1,
		private String snapshotPath;// "",
		private String itemImg;// "http：//t3.baidu.com/it/u=2,1049261941&fm=19&gp=0.jpg",
		private String itemName;// "连衣裙",
		private int count;// 2,
		private int sumRealPrice;// 200000,
		private int credits;// 0,
		private int weight;// 0,
		private int packId;// 0,
		private int isAppraise;// 2,
		private String appraiseTime;// "1970-01-01 08：00：00"

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getSkuId() {
			return skuId;
		}

		public void setSkuId(int skuId) {
			this.skuId = skuId;
		}

		public String getSnapshotPath() {
			return snapshotPath;
		}

		public void setSnapshotPath(String snapshotPath) {
			this.snapshotPath = snapshotPath;
		}

		public String getItemImg() {
			return itemImg;
		}

		public void setItemImg(String itemImg) {
			this.itemImg = itemImg;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getSumRealPrice() {
			return sumRealPrice;
		}

		public void setSumRealPrice(int sumRealPrice) {
			this.sumRealPrice = sumRealPrice;
		}

		public int getCredits() {
			return credits;
		}

		public void setCredits(int credits) {
			this.credits = credits;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public int getPackId() {
			return packId;
		}

		public void setPackId(int packId) {
			this.packId = packId;
		}

		public int getIsAppraise() {
			return isAppraise;
		}

		public void setIsAppraise(int isAppraise) {
			this.isAppraise = isAppraise;
		}

		public String getAppraiseTime() {
			return appraiseTime;
		}

		public void setAppraiseTime(String appraiseTime) {
			this.appraiseTime = appraiseTime;
		}

	}

}
