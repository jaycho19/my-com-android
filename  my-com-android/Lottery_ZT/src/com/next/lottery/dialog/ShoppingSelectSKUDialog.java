package com.next.lottery.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.Dialog;
import android.content.Context;
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
import com.google.zxing.client.result.ProductParsedResult;
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
import com.next.lottery.beans.SKUBean2;
import com.next.lottery.beans.SkuList;
import com.next.lottery.beans.SKUItem;
import com.next.lottery.listener.OnSkuResultListener;
import com.next.lottery.nets.HttpActions;

/**
 * 
 * @author dongfang
 * 
 */
public class ShoppingSelectSKUDialog extends Dialog {
	private static ShoppingSelectSKUDialog	dialog;
	static DbUtils							db;
	private static ProgressDialog			progDialog;
	private static SKUBean2  skuBean = new SKUBean2();
	private static TextView tvNumber;

	public ShoppingSelectSKUDialog(Context context, int theme) {
		super(context, theme);
	}

	private static void show(Context context, ArrayList<SkuList> bean, OnSkuResultListener onSkuResultListener) {
		if (null != dialog && dialog.isShowing())
			return;
		dialog = new ShoppingSelectSKUDialog(context, R.style.SelectSKUDialog);
		dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
		dialog.setContentView(R.layout.dialog_shopping_select_sku);
		dialog.setCancelable(true);
		dialog.getWindow().setLayout(DeviceInfo.SCREEN_WIDTH_PORTRAIT, -2);
		init1(context, bean, onSkuResultListener);
		dialog.show();
	}

	public static void show1(Context context, ArrayList<SkuList> bean, OnSkuResultListener onSkuResultListener) {
		show(context, bean, onSkuResultListener);

	}

	public static void show2(Context context, ArrayList<SkuList> bean, OnSkuResultListener onSkuResultListener) {
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
			skubean = db.findAll(Selector.from(SKUBean2.class));
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
				onSkuResultListener.onSkuResult(beanResult);
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

				if (beanResult == null) {
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
				skuBean.setCostPrice(Integer.valueOf((String) tvNumber.getText())*skuBean.getPrice());
				creatOrder(context);
				// 弹出支付宝
				AlipayUtil.doPayment(context);
			}
		});
		dialog.findViewById(R.id.dialog_select_sku_tv_add_shopping_cart).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// onSkuResultListener.onSkuResult(beanResult);
				dialog.dismiss();
				Toast.makeText(context, "添加成功", 3000).show();
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
						List<Integer> indexPosition = new ArrayList<Integer>();

						for (int i = 0; i < (skubeanFromDB != null ? skubeanFromDB.size() : 0); i++) {
							int end = skubeanFromDB.get(i).getSkuAttr().indexOf(";");
							if ((skubeanFromDB.get(i).getSkuAttr().substring(0, end)).contains((CharSequence) tv
									.getTag())) {
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
						if (indexPosition.size() > 0)
							((LineLayout) ll.getChildAt(1)
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
			ll.addView(view, 0);
		}

		tvNumber = (TextView) dialog.findViewById(R.id.dialog_select_sku_tv_number);
		dialog.findViewById(R.id.dialog_select_sku_tv_addnumber).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chgNumber(context,tvNumber, 1);
				// beanResult.setNum(Integer.valueOf(tvNumber.getText().toString()));
			}
		});
		dialog.findViewById(R.id.dialog_select_sku_tv_reducenumber).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chgNumber(context,tvNumber, -1);
				// beanResult.setNum(Integer.valueOf(tvNumber.getText().toString()));
			}
		});

		return entityResult;
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
			} catch (DbException e) {
				e.printStackTrace();
			}
		}

	}

	private static void chgNumber(Context context ,TextView tvNumber, int i) {
		int number = Integer.valueOf(tvNumber.getText().toString()) + i;
		if (number>skuBean.getStockNum()) {
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
	private static void creatOrder(final Context context) {
		ArrayList<SKUBean2> skubeanList = new ArrayList<SKUBean2>();
		skubeanList.add(skuBean);
		String url = HttpActions.creatOrder(context,skubeanList);
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

				BaseEntity<String> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseEntity<String>>() {}.getType());
				if (null != bean && bean.getCode() == 0) {

					AlipayUtil.doPayment(context);
					Toast.makeText(context, bean.getInfo(), Toast.LENGTH_LONG).show();
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

}
