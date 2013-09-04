package com.dongfang.yzsj.fragment.adp;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.df.util.ULog;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.OrderProduct;
import com.dongfang.yzsj.interf.OrderResult;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 收藏adapter
 * 
 * @author dongfang
 * 
 */
public class OrderAdp extends BaseAdapter {
	public static final String TAG = OrderAdp.class.getSimpleName();

	private com.dongfang.view.ProgressDialog progDialog;
	private List<OrderProduct> list;
	private Context context;

	private OrderResult orderResult;

	public OrderResult getOrderResult() {
		return orderResult;
	}

	public void setOrderResult(OrderResult orderResult) {
		this.orderResult = orderResult;
	}

	public OrderAdp(Context context, List<OrderProduct> list) {
		this.list = list;
		this.context = context;
		progDialog = com.dongfang.view.ProgressDialog.show(context);
		progDialog.setCancelable(true);
	}

	public List<OrderProduct> getList() {
		return list;
	}

	public void setList(List<OrderProduct> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_order_adp_item, null);
			holder = new ViewHolder();
			holder.productName = (TextView) convertView.findViewById(R.id.order_tv_product_name);
			holder.productPrice = (TextView) convertView.findViewById(R.id.order_tv_product_price);
			holder.productType = (TextView) convertView.findViewById(R.id.order_tv_product_type);
			holder.productDesc = (TextView) convertView.findViewById(R.id.order_tv_product_desc);
			holder.btn_order = (TextView) convertView.findViewById(R.id.order_tv_sub);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		final OrderProduct product = list.get(position);
		holder.productName.setText(product.getServiceProductName());
		holder.productPrice.setText(product.getPrice() + " 元");
		holder.productType.setText(product.getType());
		holder.productDesc.setText(product.getDescription());
		holder.btn_order.setOnClickListener(new MyOnClickListener(product.getPayProductNo(), position));

		return convertView;
	}

	/** 列表item内容 */
	private class ViewHolder {
		TextView productName; // 产品名称
		TextView productPrice; // 价格
		TextView productType; // 类型
		TextView productDesc; // 描述
		TextView btn_order; // 订购按钮

	}

	private class MyOnClickListener implements OnClickListener {
		private String productId;
		private int position;

		public MyOnClickListener(String productId, int position) {
			this.productId = productId;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			ULog.d(TAG, v.toString());
			StringBuilder url = new StringBuilder(ComParams.HTTP_ORDER_PRODUCT);
			url.append("userId=").append(User.getPhone(context));
			url.append("&").append("productId=").append(productId);

			ULog.i(TAG, url.toString());

			new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {

				@Override
				public void onSuccess(String result) {
					ULog.d(TAG, "onSuccess  --" + result);
					progDialog.dismiss();

					try {
						JSONObject json = new JSONObject(result);
						if (json.getBoolean("success")) {
							Toast.makeText(context, "订购成功", Toast.LENGTH_LONG).show();

							if (null != orderResult) {
								orderResult.successed();
							}

						}
						else {
							Toast.makeText(context, json.getJSONArray("error").getString(0), Toast.LENGTH_LONG).show();
							if (null != orderResult) {
								orderResult.failed();
							}

						}
					} catch (JSONException e) {
						if (null != orderResult) {
							orderResult.failed();
						}
						Toast.makeText(context, "订购失败~~~~(>_<)~~~~ ", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}

				@Override
				public void onStart() {
					ULog.i(TAG, "RequestCallBack.onStart");
					progDialog.show();
				}

				@Override
				public void onFailure(Throwable error, String msg) {
					ULog.i(TAG, "RequestCallBack.onFailure");
					progDialog.dismiss();
				}
			});
		}
	}

}
