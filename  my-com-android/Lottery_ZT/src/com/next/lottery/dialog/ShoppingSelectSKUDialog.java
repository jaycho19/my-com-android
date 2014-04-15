package com.next.lottery.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.DeviceInfo;
import com.dongfang.v4.app.LineLayout;
import com.dongfang.views.ScrollViewExtend;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.next.lottery.R;
import com.next.lottery.alipay.AlipayUtil;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.CalculateOrderListBean;
import com.next.lottery.beans.OrderNoBean;
import com.next.lottery.beans.SKUBean2;
import com.next.lottery.beans.SKUItem;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.beans.SkuList;
import com.next.lottery.listener.OnSkuResultListener;
import com.next.lottery.nets.HttpActions;

/**
 * 
 * @author dongfang
 * 
 */
public class ShoppingSelectSKUDialog extends Dialog {
	private static ShoppingSelectSKUDialog	dialog;
	private static DbUtils					db;
	private static ProgressDialog			progDialog;
	private static SKUBean2					skuBean	= new SKUBean2();
	private static TextView					tvSelectNum;
	private static TextView					tvStockNum;
	private static String					title;

	public ShoppingSelectSKUDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * 
	 * @param context
	 * @param bean         颜色和大小 数据集合
	 * @param id    物品对应Id
	 * @param onSkuResultListener
	 */

	private static void show(Context context, ArrayList<SkuList> bean, String id, OnSkuResultListener onSkuResultListener) {
		if (null != dialog && dialog.isShowing() || bean.size() == 0)
			return;
		dialog = new ShoppingSelectSKUDialog(context, R.style.SelectSKUDialog);
		dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
		dialog.setContentView(R.layout.dialog_shopping_select_sku);
		dialog.setCancelable(true);
		dialog.getWindow().setLayout(DeviceInfo.SCREEN_WIDTH_PORTRAIT, -2);
		List<SKUBean2> skubean = null;
		try {
			db = DbUtils.create(context, context.getPackageName());
			skubean = db.findAll(Selector.from(SKUBean2.class).where("itemId", "=", id));
		} catch (DbException e) {
			e.printStackTrace();
		}
		init1(context, bean, onSkuResultListener);
		dialog.show();
	}

	public static void show1(Context context, ArrayList<SkuList> bean, String id, OnSkuResultListener onSkuResultListener) {
		show(context, bean, id, onSkuResultListener);

	}

	public static void setTitle(String titleString) {
		title = titleString;
	}

	public static void show2(Context context, ArrayList<SkuList> bean,String id, OnSkuResultListener onSkuResultListener) {
		if (null != dialog && dialog.isShowing())
			return;
		dialog = new ShoppingSelectSKUDialog(context, R.style.SelectSKUDialog);
		dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
		dialog.setContentView(R.layout.dialog_shopping_select_sku_2);
		dialog.setCancelable(true);
		dialog.getWindow().setLayout(DeviceInfo.SCREEN_WIDTH_PORTRAIT, -2);
		List<SKUBean2> skubean = null;
		try {
			db = DbUtils.create(context, context.getPackageName());
			skubean = db.findAll(Selector.from(SKUBean2.class).where("itemId", "=", Integer.parseInt(id)));
		} catch (DbException e) {
			e.printStackTrace();
		}
		init2(context, bean, onSkuResultListener, skubean);
		dialog.show();
	}

	private static void init1(final Context context, ArrayList<SkuList> arrayList,
			final OnSkuResultListener onSkuResultListener) {
		final ArrayList<SkuList> beanResult = init(context, arrayList, onSkuResultListener, null);

		dialog.findViewById(R.id.dialog_select_sku_tv_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < beanResult.size(); i++) {
					if (null == beanResult.get(i) || null == beanResult.get(i).getValues()) {
						Toast.makeText(context, "请选择" + "参数", Toast.LENGTH_LONG).show();
						return;
					}
				}
				int SelectNum = Integer.valueOf((String) tvSelectNum.getText());
				int StockNum = skuBean != null ? skuBean.getStockNum() : 0;
				onSkuResultListener.onSkuResult(beanResult, SelectNum < StockNum ? SelectNum : StockNum);
				dialog.dismiss();
			}
		});

	}

	private static void init2(final Context context, ArrayList<SkuList> bean,
			final OnSkuResultListener onSkuResultListener, List<SKUBean2> skubean) {
		final ArrayList<SkuList> beanResult = init(context, bean, onSkuResultListener, skubean);
		dialog.findViewById(R.id.dialog_select_sku_tv_buy_now).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (beanResult == null || skuBean == null) {
					return;
				}
				for (int i = 0; i < beanResult.size(); i++) {
					if (null == beanResult.get(i) || null == beanResult.get(i).getValues()) {
						Toast.makeText(context, "请选择" + "参数", Toast.LENGTH_LONG).show();
						return;
					}
				}
				dialog.dismiss();
				// 提交订单
				int SelectNum = Integer.valueOf((String) tvSelectNum.getText());
				int StockNum = skuBean != null ? skuBean.getStockNum() : 0;
				skuBean.setCostPrice((SelectNum < StockNum ? SelectNum : StockNum) * skuBean.getPrice());
				CalculateOrder(context);
			}
		});
		dialog.findViewById(R.id.dialog_select_sku_tv_add_shopping_cart).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// onSkuResultListener.onSkuResult(beanResult);
				addShopCarts(context);
				dialog.dismiss();
				Toast.makeText(context, "添加成功", 3000).show();
			}
		});

	}

	protected static void CalculateOrder(final Context context) {
		ArrayList<ShopCartsInfo> skubeanList = new ArrayList<ShopCartsInfo>();

		ShopCartsInfo info = new ShopCartsInfo();
		info.setItemId(String.valueOf(skuBean.getItemId()));
		info.setCount(skuBean.getCostPrice() / skuBean.getPrice());
		info.setSkuId(String.valueOf(skuBean.getId()));
		skubeanList.add(info);

		String url = HttpActions.CalcuLateOrderList(context, skubeanList);
		ULog.d("CalculateOrder url = " + url);
		progDialog = ProgressDialog.show(context);
		progDialog.setCancelable(true);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d(responseInfo.result);

				BaseEntity<CalculateOrderListBean> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseEntity<CalculateOrderListBean>>() {}.getType());
				if (null != bean && bean.getCode() == 0) {

					CreatOrder(context, bean);
					ULog.i("price-->" + bean.getInfo().getPrice());
					// Toast.makeText(context, bean.getInfo().getPrice(),
					// Toast.LENGTH_LONG).show();
				}
				else {
					// Toast.makeText(context, bean.getMsg(),
					// Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
				// Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		});

	}

	private static ArrayList<SkuList> init(final Context context, final ArrayList<SkuList> bean,
			final OnSkuResultListener onSkuResultListener, final List<SKUBean2> skubeanFromDB) {
		/** 构造回调实列 */
		final ArrayList<SkuList> entityResult = new ArrayList<SkuList>();
		for (int i = 0; i < bean.size(); i++) {
			SkuList beanEntity = new SkuList();
			beanEntity.setPid(bean.get(i).getPid());
			beanEntity.setPname(bean.get(i).getPname());
			entityResult.add(beanEntity);
		}
		((ScrollViewExtend) dialog.findViewById(R.id.dialog_select_sku_content_sl)).setAllow_match(false);

		tvStockNum = (TextView) dialog.findViewById(R.id.dialog_select_sku_stock_tv_number);

		final LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.dialog_select_sku_content_ll);
		if (null != bean) {
			ULog.e("null != bean ;size = " + bean.size());
		}

		for (final SkuList sku : bean) {
			ULog.d(sku.getPname());
			View view = LayoutInflater.from(context).inflate(R.layout.dialog_shopping_select_sku_item, null);
			TextView skuName = (TextView) view.findViewById(R.id.dialog_shopping_select_sku_item_tv_skuname);
			skuName.setText(sku.getPname());
			LineLayout l = (LineLayout) view.findViewById(R.id.dialog_shopping_select_sku_item_linelayout);
			for (final SKUItem s : sku.getValues()) {
				final TextView tv = (TextView) LayoutInflater.from(context).inflate(
						R.layout.dialog_shopping_select_sku_item_element, null);
				tv.setText(s.getName());
				tv.setTag(s.getId());
				tv.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						int positon = 0;
						if (v.getParent() instanceof LineLayout) {
							((LineLayout) v.getParent()).setNOSelect();
						}
						v.setSelected(true);
						/* 判断尺码/颜色 有无 */
						List<Integer> indexPosition = judgeSizeExists(bean, skubeanFromDB, tv);

						if (indexPosition.size() > 0)
							((LineLayout) ll.getChildAt(2)
									.findViewById(R.id.dialog_shopping_select_sku_item_linelayout))
									.setNOEnable(indexPosition);

						/* 获取选中内容 */
						for (int i = 0; i < bean.size(); i++) {
							if (sku.getPname().equals(bean.get(i).getPname())) {
								ArrayList<SKUItem> values = new ArrayList<SKUItem>();
								SkuList beanEntity = new SkuList();
								values.add(bean.get(i).getValues()
										.get(((LineLayout) v.getParent()).getSelectPosition()));
								beanEntity.setValues(values);
								entityResult.get(i).setValues(values);
							}
						}

						/* 获取相应库存 */
						getStackNum(entityResult);
					}

				});
				l.addView(tv);
			}
			ll.addView(view, 1);
		}

		tvSelectNum = (TextView) dialog.findViewById(R.id.dialog_select_sku_tv_number);
		dialog.findViewById(R.id.dialog_select_sku_tv_addnumber).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chgNumber(context, tvSelectNum, 1);
				// beanResult.setNum(Integer.valueOf(tvNumber.getText().toString()));
			}
		});
		dialog.findViewById(R.id.dialog_select_sku_tv_reducenumber).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chgNumber(context, tvSelectNum, -1);
				// beanResult.setNum(Integer.valueOf(tvNumber.getText().toString()));
			}
		});

		if (!TextUtils.isEmpty(title)) {
			TextView tvTitle = (TextView) dialog.findViewById(R.id.dialog_select_sku_tv_titile);
			tvTitle.setText(title);
		}
		return entityResult;
	}

	protected static List<Integer> judgeSizeExists(ArrayList<SkuList> bean, List<SKUBean2> skubeanFromDB, TextView tv) {
		List<Integer> indexPosition = new ArrayList<Integer>();
		for (int i = 0; i < (skubeanFromDB != null ? skubeanFromDB.size() : 0); i++) {
			int end = skubeanFromDB.get(i).getSkuAttr().indexOf(";");
			if ((skubeanFromDB.get(i).getSkuAttr().substring(0, end)).contains((CharSequence) tv.getTag())) {
				ULog.i(skubeanFromDB.get(i).getSkuAttrname());
				String attrname = skubeanFromDB.get(i).getSkuAttrname();
				String attrnameSubString = attrname.substring(0, attrname.lastIndexOf(":") - 3);// 减去3
				String id = attrnameSubString.substring(attrnameSubString.lastIndexOf(":") + 1);
				String size = attrname.substring(attrname.lastIndexOf(":") + 1);

				for (int j = 0; j < bean.get(0).getValues().size(); j++) {
					SKUItem item = bean.get(0).getValues().get(j);
					if (id.equalsIgnoreCase(item.getId())) {
						indexPosition.add(j);
						break;
					}
				}
			}
		}

		return indexPosition;

	}

	/* "1627207:28326;20509:28314" */
	protected static void getStackNum(ArrayList<SkuList> entityResult) {
		String itemColor = null;
		String itemSize = null;

		if (entityResult.get(0) != null & entityResult.get(0).getValues() != null) {
			itemSize = entityResult.get(0).getPid() + ":" + entityResult.get(0).getValues().get(0).getId();
		}
		if (entityResult.get(1) != null & entityResult.get(1).getValues() != null) {
			itemColor = entityResult.get(1).getPid() + ":" + entityResult.get(1).getValues().get(0).getId();
		}
		ULog.i(itemColor + ";" + itemSize);

		if (itemColor != null && itemSize != null) {
			String SkuAttrString = itemColor + ";" + itemSize;
			try {
				skuBean = db.findFirst(Selector.from(SKUBean2.class).where("skuAttr", "=", SkuAttrString));

				if (skuBean != null) {
					tvStockNum.setText("库存：" + skuBean.getStockNum());
				}
			} catch (DbException e) {
				e.printStackTrace();
			}
		}

	}

	private static void chgNumber(Context context, TextView tvNumber, int i) {
		int number = Integer.valueOf(tvNumber.getText().toString()) + i;
		if (number > skuBean.getStockNum()) {
			Toast.makeText(context, "库存不足！", Toast.LENGTH_LONG).show();
			return;
		}
		number = number < 1 ? 1 : number;
		tvNumber.setText(Integer.toString(number));
	}

	public interface OnShopingSKUListener {
		public void ok(String phone, String authCode);
		// public void cancel();
	}

	/* 生成订单 */
	private static void CreatOrder(final Context context, BaseEntity<CalculateOrderListBean> bean) {
		// ArrayList<SKUBean2> skubeanList = new ArrayList<SKUBean2>();
		// skubeanList.add(skuBean);
		String url = HttpActions.creatOrder(context, bean);
		ULog.d("addShopCarts url = " + url);
		progDialog = ProgressDialog.show(context);
		progDialog.setCancelable(true);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d(responseInfo.result);

				BaseEntity<OrderNoBean> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseEntity<OrderNoBean>>() {}.getType());
				if (null != bean && bean.getCode() == 0) {
					ULog.i(bean.getInfo().getOrderNo());
					AlipayUtil.doPayment(context);
				}
				else {
					Toast.makeText(context, bean.getMsg(), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		});

	}

	public static void addShopCarts(final Context context) {
		String url = HttpActions.AddShopCarts(skuBean);
		ULog.d("addShopCarts url = " + url);
		progDialog = ProgressDialog.show(context);
		progDialog.setCancelable(true);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d(responseInfo.result);

				BaseEntity bean = new Gson().fromJson(responseInfo.result, BaseEntity.class);
				if (null != bean && bean.getCode() == 0) {

					Toast.makeText(context, "添加成功!", Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(context, bean.getMsg(), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
			}
		});

	}

}
