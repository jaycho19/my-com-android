package com.next.lottery.beans;

import java.util.ArrayList;

public class GoodsBean {
	private String					id;					// "9",
	private String					source;				// "tb",
	private String					sourceId;			// "37336713264",
	private String					sourceUrl;			// "http;////item.taobao.com/item.htm?id=37336713264&spm=2014.12129701.0.0",
	private int						merId;				// 1,
	private String					title;				// "NINE WEST玖熙女装 2014春装新款纯色圆领短袖修身气质连衣裙",
	private int						type;				// 0,
	private int						transportId;		// 0,
	private String					categoryId;		// 0,
	private int						length;				// 0,
	private int						width;				// 0,
	private int						height;				// 0,
	private int						costPrice;			// 0,
	private int						price;				// 149000,
	private int						marketPrice;		// 0,
	private int						stockNum;			// 0,
	private int						isCanVat;			// 0,
	private int						isImported;			// 0,
	private int						isHealthProduct;	// 0,
	private int						isShelfLife;		// 0,
	private int						isSerialNo;			// 0,
	private String					params;				// "20418023;//157305307;//主图来源;//自主实拍图;13021751;//213924712;//货号;//3051321703;20608;//6384766;//风格;//通勤;18073285;//29919;//通勤;//淑女;10142888;//3386071;//组合形式;//单件;122216349;//29967;//裙长;//短裙;122216348;//29445;//袖长;//短袖;20663;//29447;//领型;//圆领;2917380;//53607756;//袖型;//连袖;20677;//95284510;//腰型;//宽松腰;31611;//103422;//衣门襟;//套头;20603;//29454;//图案;//纯色;20000;//31888;//品牌;//Nine West/玖熙;20551;//20213;//面料;//其他;13328588;//145656297;//成分含量;//95%以上;20021;//113357;//材质;//聚酯纤维;20017;//494072160;//适用年龄;//25-29周岁;122216347;//199870733;//年份季节;//2013年春季;1627207;//28338;//颜色分类;//蓝色;1627207;//28341;//颜色分类;//黑色;1627207;//28326;//颜色分类;//红色;1627207;//3232480;//颜色分类;//粉红色;1627207;//28335;//颜色分类;//绿色;20509;//28381;//尺码;//XXS;20509;//28313;//尺码;//XS;20509;//28314;//尺码;//S;20509;//28315;//尺码;//M;20509;//28316;//尺码;//L;20509;//28317;//尺码;//XL",
	private String					paramsName;			// "",
	private String					paramsAlias;		// "1627207;//28335;//004草绿/黑色;1627207;//28341;//002黑色/白色;1627207;//3232480;//003红色/深红;1627207;//28326;//005红色/黑色;1627207;//28338;//001蓝色/黑色;20509;//28381;//2;20509;//28313;//4;20509;//28315;//8;20509;//28314;//6;20509;//28317;//12;20509;//28316;//10",
	private int						isCheckCode;		// 0,
	private int						status;				// 0,
	private int						isFenxiao;			// 0,
	private int						isXinpin;			// 0,
	private int						subStock;			// 0,
	private String					picUrl;				// "http;////img03.taobaocdn.com/bao/uploaded/i3/T19p9DFCtcXXXXXXXX_!!0-item_pic.jpg",
	private int						postFee;			// 500,
	private int						expressFee;			// 0,
	private int						emsFee;				// 0,
	private int						hasInvoice;			// 1,
	private int						hasWarranty;		// 0,
	private int						violation;			// 0,
	private int						codPostageId;		// 0,
	private int						sellPromise;		// 0,
	private int						isEdit;				// 0,
	private long					createTime;			// 1396319029000,
	private long					lastUpdateTime;		// 1396405437000,

	private ArrayList<KVImageBean>	image;
	private ArrayList<SKUBean2>	sku;
	private long _version_;
	private ArrayList<SkuList>	skuList;
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id).append("\n");
		sb.append("source=").append(source).append("\n");
		sb.append("sourceId=").append(sourceId).append("\n");
		sb.append("sourceUrl=").append(sourceUrl).append("\n");
		sb.append("merId=").append(merId).append("\n");
		sb.append("title=").append(title).append("\n");
		sb.append("type=").append(type).append("\n");
		sb.append("categoryId=").append(categoryId).append("\n");
		sb.append("transportId=").append(transportId).append("\n");
		sb.append("length=").append(length).append("\n");
		sb.append("width=").append(width).append("\n");
		sb.append("height=").append(height).append("\n");
		sb.append("costPrice=").append(costPrice).append("\n");
		sb.append("price=").append(price).append("\n");
		sb.append("marketPrice=").append(marketPrice).append("\n");
		sb.append("stockNum=").append(stockNum).append("\n");
		sb.append("isCanVat=").append(isCanVat).append("\n");
		sb.append("isImported=").append(isImported).append("\n");
		sb.append("isHealthProduct=").append(isHealthProduct).append("\n");
		sb.append("isShelfLife=").append(isShelfLife).append("\n");
		sb.append("isSerialNo=").append(isSerialNo).append("\n");
		sb.append("params=").append(params).append("\n");
		sb.append("paramsName=").append(paramsName).append("\n");
		sb.append("paramsAlias=").append(paramsAlias).append("\n");
		sb.append("isCheckCode=").append(isCheckCode).append("\n");
		sb.append("status=").append(status).append("\n");
		sb.append("isFenxiao=").append(isFenxiao).append("\n");
		sb.append("isXinpin=").append(isXinpin).append("\n");
		sb.append("subStock=").append(subStock).append("\n");
		sb.append("picUrl=").append(picUrl).append("\n");
		sb.append("postFee=").append(postFee).append("\n");
		sb.append("expressFee=").append(expressFee).append("\n");
		sb.append("emsFee=").append(emsFee).append("\n");
		sb.append("hasInvoice=").append(hasInvoice).append("\n");
		sb.append("hasWarranty=").append(hasWarranty).append("\n");
		sb.append("violation=").append(violation).append("\n");
		sb.append("codPostageId=").append(codPostageId).append("\n");
		sb.append("sellPromise=").append(sellPromise).append("\n");
		sb.append("isEdit=").append(isEdit).append("\n");
		sb.append("createTime=").append(createTime).append("\n");
		sb.append("lastUpdateTime=").append(lastUpdateTime).append("\n");
		sb.append("_version_=").append(_version_).append("\n");
		for (SKUBean2 s : sku) {
			sb.append("sku -> " + s.toString());
		}
		for (SkuList s : skuList) {
			sb.append("skuList -> " + s.toString());
		}
		return sb.toString();
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public int getMerId() {
		return merId;
	}
	public void setMerId(int merId) {
		this.merId = merId;
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
	public int getTransportId() {
		return transportId;
	}
	public void setTransportId(int transportId) {
		this.transportId = transportId;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(int costPrice) {
		this.costPrice = costPrice;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(int marketPrice) {
		this.marketPrice = marketPrice;
	}
	public int getStockNum() {
		return stockNum;
	}
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	public int getIsCanVat() {
		return isCanVat;
	}
	public void setIsCanVat(int isCanVat) {
		this.isCanVat = isCanVat;
	}
	public int getIsImported() {
		return isImported;
	}
	public void setIsImported(int isImported) {
		this.isImported = isImported;
	}
	public int getIsHealthProduct() {
		return isHealthProduct;
	}
	public void setIsHealthProduct(int isHealthProduct) {
		this.isHealthProduct = isHealthProduct;
	}
	public int getIsShelfLife() {
		return isShelfLife;
	}
	public void setIsShelfLife(int isShelfLife) {
		this.isShelfLife = isShelfLife;
	}
	public int getIsSerialNo() {
		return isSerialNo;
	}
	public void setIsSerialNo(int isSerialNo) {
		this.isSerialNo = isSerialNo;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getParamsName() {
		return paramsName;
	}
	public void setParamsName(String paramsName) {
		this.paramsName = paramsName;
	}
	public String getParamsAlias() {
		return paramsAlias;
	}
	public void setParamsAlias(String paramsAlias) {
		this.paramsAlias = paramsAlias;
	}
	public int getIsCheckCode() {
		return isCheckCode;
	}
	public void setIsCheckCode(int isCheckCode) {
		this.isCheckCode = isCheckCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsFenxiao() {
		return isFenxiao;
	}
	public void setIsFenxiao(int isFenxiao) {
		this.isFenxiao = isFenxiao;
	}
	public int getIsXinpin() {
		return isXinpin;
	}
	public void setIsXinpin(int isXinpin) {
		this.isXinpin = isXinpin;
	}
	public int getSubStock() {
		return subStock;
	}
	public void setSubStock(int subStock) {
		this.subStock = subStock;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public int getPostFee() {
		return postFee;
	}
	public void setPostFee(int postFee) {
		this.postFee = postFee;
	}
	public int getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(int expressFee) {
		this.expressFee = expressFee;
	}
	public int getEmsFee() {
		return emsFee;
	}
	public void setEmsFee(int emsFee) {
		this.emsFee = emsFee;
	}
	public int getHasInvoice() {
		return hasInvoice;
	}
	public void setHasInvoice(int hasInvoice) {
		this.hasInvoice = hasInvoice;
	}
	public int getHasWarranty() {
		return hasWarranty;
	}
	public void setHasWarranty(int hasWarranty) {
		this.hasWarranty = hasWarranty;
	}
	public int getViolation() {
		return violation;
	}
	public void setViolation(int violation) {
		this.violation = violation;
	}
	public int getCodPostageId() {
		return codPostageId;
	}
	public void setCodPostageId(int codPostageId) {
		this.codPostageId = codPostageId;
	}
	public int getSellPromise() {
		return sellPromise;
	}
	public void setSellPromise(int sellPromise) {
		this.sellPromise = sellPromise;
	}
	public int getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public ArrayList<KVImageBean> getImage() {
		return image;
	}
	public void setImage(ArrayList<KVImageBean> image) {
		this.image = image;
	}
	public ArrayList<SKUBean2> getSku() {
		return sku;
	}
	public void setSku(ArrayList<SKUBean2> sku) {
		this.sku = sku;
	}
	public long get_version_() {
		return _version_;
	}
	public void set_version_(long _version_) {
		this._version_ = _version_;
	}
	public ArrayList<SkuList> getSkuList() {
		return skuList;
	}
	public void setSkuList(ArrayList<SkuList> skuList) {
		this.skuList = skuList;
	}
	/*
	 * "{"id":82,"isMain":0,"path":"http://img03.taobaocd
	 * n.com/bao/uploaded/i3/T19p9DFCtcXXXXXXXX_!!0-item_pic.jpg",
	 * "position":0,"properties":"","type":0}" ],
	 */
	public class KVImageBean {
		
		private int h;
		private int w;
		private String		id;
		private String imageId;
		private int isMain;
		private String name;
		private String	path;
		private int		position;
		private String	properties;
		private int		type;
		private int		size;
		
		public int getH() {
			return h;
		}
		public void setH(int h) {
			this.h = h;
		}
		public int getW() {
			return w;
		}
		public void setW(int w) {
			this.w = w;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getImageId() {
			return imageId;
		}
		public void setImageId(String imageId) {
			this.imageId = imageId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		public int getIsMain() {
			return isMain;
		}
		public void setIsMain(int isMain) {
			this.isMain = isMain;
		}
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getProperties() {
			return properties;
		}
		public void setProperties(String properties) {
			this.properties = properties;
		}

	}
	
	
	
	
	
	
	/*{"costPrice":0,"id":12,"itemId":9,"marketPrice":0,"price":149000,
		"skuAttr":"1627207:28335;20509:28381"
		,"skuAttrname":"1627207:28335:颜色分类:绿色;20509:28381:尺码:XXS","status":1,"stockNum":0}
*/
//	public class SkuBean{
//		private int costPrice;
//		private int itemId;
//		private int marketPrice;
//		private long  price;
//		private String skuAttr;
//		private String skuAttrname;
//		private int status;
//		private int stockNum;
//		public int getCostPrice() {
//			return costPrice;
//		}
//		public void setCostPrice(int costPrice) {
//			this.costPrice = costPrice;
//		}
//		public int getItemId() {
//			return itemId;
//		}
//		public void setItemId(int itemId) {
//			this.itemId = itemId;
//		}
//		public int getMarketPrice() {
//			return marketPrice;
//		}
//		public void setMarketPrice(int marketPrice) {
//			this.marketPrice = marketPrice;
//		}
//		public long getPrice() {
//			return price;
//		}
//		public void setPrice(long price) {
//			this.price = price;
//		}
//		public String getSkuAttr() {
//			return skuAttr;
//		}
//		public void setSkuAttr(String skuAttr) {
//			this.skuAttr = skuAttr;
//		}
//		public String getSkuAttrname() {
//			return skuAttrname;
//		}
//		public void setSkuAttrname(String skuAttrname) {
//			this.skuAttrname = skuAttrname;
//		}
//		public int getStatus() {
//			return status;
//		}
//		public void setStatus(int status) {
//			this.status = status;
//		}
//		public int getStockNum() {
//			return stockNum;
//		}
//		public void setStockNum(int stockNum) {
//			this.stockNum = stockNum;
//		}
//	}
	/*pid: "1627207",
	pname: "颜色分类",
	values: [*/
//	public class SkuList{
//		private String pid;
//		private String pname;
//		private ArrayList<SKUItem> values;
//		public String getPid() {
//			return pid;
//		}
//		public void setPid(String pid) {
//			this.pid = pid;
//		}
//		public String getPname() {
//			return pname;
//		}
//		public void setPname(String pname) {
//			this.pname = pname;
//		}
//		public ArrayList<SKUItem> getValues() {
//			return values;
//		}
//		public void setValues(ArrayList<SKUItem> values) {
//			this.values = values;
//		}
//	}
}
