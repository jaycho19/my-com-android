package com.next.lottery.dialog;

import java.util.ArrayList;
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
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.next.lottery.R;
import com.next.lottery.alipay.AlipayUtil;
import com.next.lottery.beans.SKUBean2;
import com.next.lottery.beans.SkuList;
import com.next.lottery.beans.SKUItem;
import com.next.lottery.listener.OnSkuResultListener;

/**
 * 
 * @author dongfang
 * 
 */
public class ShoppingSelectSKUDialog extends Dialog {
	private static ShoppingSelectSKUDialog	dialog;
	static DbUtils									db;

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

		db = DbUtils.create(context);
		init2(context, bean, onSkuResultListener);
		dialog.show();
	}

	private static void init1(final Context context, ArrayList<SkuList> arrayList,
			final OnSkuResultListener onSkuResultListener) {
		final ArrayList<SkuList> beanResult = init(context, arrayList, onSkuResultListener);

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
			final OnSkuResultListener onSkuResultListener) {
		final ArrayList<SkuList> beanResult = init(context, bean, onSkuResultListener);
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
			final OnSkuResultListener onSkuResultListener) {

		/** 构造回调实列 */
		final ArrayList<SkuList> EntityResult = new ArrayList<SkuList>();
		for (int i = 0; i < bean.size(); i++) {
			SkuList beanEntity = new SkuList();
			beanEntity.setPid(bean.get(i).getPid());
			beanEntity.setPname(bean.get(i).getPname());
			EntityResult.add(beanEntity);
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
						for (int i = 0; i < bean.size(); i++) {
							if (sku.getPname().equals(bean.get(i).getPname())) {
//									List<SKUBean2> skubean = db.findAll(Selector.from(SKUBean2.class));
//									List<SkuList> skulist = db.findAll(Selector.from(SkuList.class));
//									for (int j = 0; j < array.length; j++) {
//									tv.gett
//									}
									ULog.i("v getText"+tv.getText()+"id"+tv.getTag());
//									DbUtils.create(context).findAll(Selector.from(SKUBean2.class));
//								} catch (DbException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
								((LineLayout) ll.getChildAt(i == 0 ? i : 1).findViewById(
										R.id.dialog_shopping_select_sku_item_linelayout)).setNOEnable();
						}
						}

						/* 获取选中内容 */
						for (int i = 0; i < bean.size(); i++) {
							if (sku.getPname().equals(bean.get(i).getPname())) {
								ArrayList<SKUItem> values = new ArrayList<SKUItem>();

								SkuList beanEntity = new SkuList();
								// beanEntity.setPid(bean.get(i).getPid());
								// beanEntity.setPname(bean.get(i).getPname());
								values.add(bean.get(i).getValues()
										.get(((LineLayout) v.getParent()).getSelectPosition()));
								beanEntity.setValues(values);
								ULog.i(bean.get(i).getValues().get(((LineLayout) v.getParent()).getSelectPosition())
										.getName());
								EntityResult.get(i).setValues(values);
							}
						}
					}

				});
				l.addView(tv);
			}
			ll.addView(view, 0);
		}

		final TextView tvNumber = (TextView) dialog.findViewById(R.id.dialog_select_sku_tv_number);
		dialog.findViewById(R.id.dialog_select_sku_tv_addnumber).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chgNumber(tvNumber, 1);
				// beanResult.setNum(Integer.valueOf(tvNumber.getText().toString()));
			}
		});
		dialog.findViewById(R.id.dialog_select_sku_tv_reducenumber).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chgNumber(tvNumber, -1);
				// beanResult.setNum(Integer.valueOf(tvNumber.getText().toString()));
			}
		});

		return EntityResult;
	}

	private static void chgNumber(TextView tvNumber, int i) {
		int number = Integer.valueOf(tvNumber.getText().toString()) + i;
		number = number < 1 ? 1 : number;
		tvNumber.setText(Integer.toString(number));
	}

	public interface OnShopingSKUListener {
		public void ok(String phone, String authCode);
		// public void cancel();
	}

}
