package com.next.lottery.dialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

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
import com.next.lottery.R;
import com.next.lottery.alipay.AlipayConfig;
import com.next.lottery.alipay.AlipayUtil;
import com.next.lottery.beans.SKUBean;
import com.next.lottery.beans.SKUEntity;
import com.next.lottery.beans.SKUItem;
import com.next.lottery.listener.OnSkuResultListener;

/**
 * 
 * @author dongfang
 * 
 */
public class ShoppingSelectSKUDialog extends Dialog {
	private static ShoppingSelectSKUDialog dialog;

	public ShoppingSelectSKUDialog(Context context, int theme) {
		super(context, theme);
	}

	private static void show(Context context, SKUBean bean, OnSkuResultListener onSkuResultListener) {
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
	
	public static void show1(Context context, SKUBean bean, OnSkuResultListener onSkuResultListener) {
		show(context,bean,onSkuResultListener);
	}
	
	public static void show2(Context context, SKUBean bean, OnSkuResultListener onSkuResultListener) {
		if (null != dialog && dialog.isShowing())
			return;
		dialog = new ShoppingSelectSKUDialog(context, R.style.SelectSKUDialog);
		dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
		dialog.setContentView(R.layout.dialog_shopping_select_sku_2);
		dialog.setCancelable(true);
		dialog.getWindow().setLayout(DeviceInfo.SCREEN_WIDTH_PORTRAIT, -2);
		init2(context, bean, onSkuResultListener);
		dialog.show();
	}

	private static void init1(final Context context, final SKUBean bean, final OnSkuResultListener onSkuResultListener) {
		final SKUBean beanResult = init(context, bean, onSkuResultListener);

		dialog.findViewById(R.id.dialog_select_sku_tv_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < beanResult.getSkuList().size(); i++) {
					if (null == beanResult.getSkuList().get(i)
							|| null == beanResult.getSkuList().get(i).getSkuTypesList()) {
						Toast.makeText(context, "请选择" + beanResult.getSkuList().get(i).getSkuName(), Toast.LENGTH_LONG)
								.show();
						return;
					}
				}
				onSkuResultListener.onSkuResult(beanResult);
				dialog.dismiss();
			}
		});

	}

	private static void init2(final Context context, final SKUBean bean, final OnSkuResultListener onSkuResultListener) {
		final SKUBean beanResult = init(context, bean, onSkuResultListener);
		dialog.findViewById(R.id.dialog_select_sku_tv_buy_now).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				onSkuResultListener.onSkuResult(beanResult);
				for (int i = 0; i < beanResult.getSkuList().size(); i++) {
					if (null == beanResult.getSkuList().get(i)
							|| null == beanResult.getSkuList().get(i).getSkuTypesList()) {
						Toast.makeText(context, "请选择" + beanResult.getSkuList().get(i).getSkuName(), Toast.LENGTH_LONG)
								.show();
						return;
					}
				}
				
				dialog.dismiss();
				try {
					String orderinfo = AlipayUtil.getOrderInfo(AlipayConfig.PARTNER, "lottery", AlipayConfig.DESCRIPTION, 1,
							2);
					String encodeSign = URLEncoder.encode(AlipayConfig.SIGN, "UTF-8");
					String info = orderinfo + "&sign=" + "\"" + encodeSign + "\"" + "&sign_type=" + "\"RSA\"";
					ULog.v("alipay orderInfo = " + info);
					
					AlipayUtil.PayHelper(context, info);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		dialog.findViewById(R.id.dialog_select_sku_tv_add_shopping_cart).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				onSkuResultListener.onSkuResult(beanResult);
				dialog.dismiss();
				Toast.makeText(context, "添加成功", 3000).show();
			}
		});

	}

	private static SKUBean init(final Context context, final SKUBean bean, final OnSkuResultListener onSkuResultListener) {

		/** 构造回调实列 */
		final SKUBean beanResult = new SKUBean();
		ArrayList<SKUEntity> EntityResult = new ArrayList<SKUEntity>();
		for (int i = 0; i < bean.getSkuList().size(); i++) {
			SKUEntity tempEnity = new SKUEntity();
			tempEnity.setSkuName(bean.getSkuList().get(i).getSkuName());
			EntityResult.add(tempEnity);
		}
		beanResult.setSkuList(EntityResult);

		((ScrollViewExtend) dialog.findViewById(R.id.dialog_select_sku_content_sl)).setAllow_match(false);
		LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.dialog_select_sku_content_ll);
		if (null != bean) {
			ULog.e("null != bean ;size = " + bean.getSkuList().size());
		}

		for (final SKUEntity sku : bean.getSkuList()) {
			ULog.e(sku.getSkuName());
			View view = LayoutInflater.from(context).inflate(R.layout.dialog_shopping_select_sku_item, null);
			TextView skuName = (TextView) view.findViewById(R.id.dialog_shopping_select_sku_item_tv_skuname);
			skuName.setText(sku.getSkuName());
			LineLayout l = (LineLayout) view.findViewById(R.id.dialog_shopping_select_sku_item_linelayout);
			for (SKUItem s : sku.getSkuTypesList()) {
				TextView tv = (TextView) LayoutInflater.from(context).inflate(
						R.layout.dialog_shopping_select_sku_item_element, null);
				tv.setText(s.getName());
				tv.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						int positon = 0;
						if (v.getParent() instanceof LineLayout) {
							((LineLayout) v.getParent()).setNOSelect();
						}
						v.setSelected(true);

						for (int i = 0; i < beanResult.getSkuList().size(); i++) {
							if (sku.getSkuName().equals(beanResult.getSkuList().get(i).getSkuName())) {
								ArrayList<SKUItem> skuTypesList = new ArrayList<SKUItem>();
								skuTypesList.add(bean.getSkuList().get(i).getSkuTypesList()
										.get(((LineLayout) v.getParent()).getSelectPosition()));
								ULog.i("getSkuTypesList-->"
										+ bean.getSkuList().get(i).getSkuTypesList()
												.get(((LineLayout) v.getParent()).getSelectPosition()));
								ULog.i("getSkuName-->" + bean.getSkuList().get(i).getSkuName());
								beanResult.getSkuList().get(i).setSkuTypesList(skuTypesList);
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
			}
		});
		dialog.findViewById(R.id.dialog_select_sku_tv_reducenumber).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chgNumber(tvNumber, -1);
			}
		});

		beanResult.setNum(Integer.valueOf(tvNumber.getText().toString()));

		return beanResult;
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
