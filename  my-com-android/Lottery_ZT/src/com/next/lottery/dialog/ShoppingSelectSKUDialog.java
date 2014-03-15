package com.next.lottery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.LineLayout;
import com.next.lottery.R;
import com.next.lottery.beans.SKUBean;
import com.next.lottery.beans.SKUEntity;

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

	public static void show(Context context, SKUBean bean) {
		if (null != dialog && dialog.isShowing())
			return;
		dialog = new ShoppingSelectSKUDialog(context, R.style.SelectSKUDialog);
		dialog.getWindow().setGravity(Gravity.BOTTOM);
		dialog.setContentView(R.layout.dialog_shopping_select_sku);
		dialog.setCancelable(true);
		init(context, bean);

		dialog.show();
	}

	private static void init(Context context, SKUBean bean) {
		LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.dialog_select_sku_content_ll);
		if (null != bean)
		{
			ULog.e("null != bean + " + bean.getSkuList().size());
		}
		
		for (SKUEntity sku : bean.getSkuList()) {
			ULog.e(sku.getSkuName());
			View view = LayoutInflater.from(context).inflate(R.layout.dialog_shopping_select_sku_item, null);
			TextView skuName = (TextView) view.findViewById(R.id.dialog_shopping_select_sku_item_tv_skuname);
			skuName.setText(sku.getSkuName());
			LineLayout l = (LineLayout) view.findViewById(R.id.dialog_shopping_select_sku_item_linelayout);
			for (String s : sku.getSkuTypesList()) {
				TextView tv = (TextView) LayoutInflater.from(context).inflate(
						R.layout.dialog_shopping_select_sku_item_element, null);
				tv.setText(s);
				l.addView(tv);
			}

			ll.addView(view);
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
