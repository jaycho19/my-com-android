package com.dongfang.yzsj.fragment.adp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.MediaBean;

/**
 * 收藏adapter
 * 
 * @author dongfang
 * 
 */
public class FavoriteAdp extends BaseAdapter {

	public static final String	TAG	= FavoriteAdp.class.getSimpleName();

	private List<MediaBean>		list;
	private Context				context;

	public FavoriteAdp(Context context, List<MediaBean> list) {
		this.list = list;
		this.context = context;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_favorite_adp_item, null);
			holder = new ViewHolder();
			holder.iv_placard = (ImageView) convertView.findViewById(R.id.favorite_imageView_placard);
			holder.tv_title = (TextView) convertView.findViewById(R.id.favorite_tv_title);
			holder.tv_actor = (TextView) convertView.findViewById(R.id.favorite_tv_actor);
			holder.tv_des = (TextView) convertView.findViewById(R.id.favorite_tv_des);
			holder.btn_cancel = (TextView) convertView.findViewById(R.id.favorite_tv_cancel);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		final MediaBean mediaBean = list.get(position);

		// BitmapUtils.create(context).display(holder.iv_placard, mediaBean.getImageUrl(), 105, 137,
		// CompressFormat.PNG);
		holder.tv_des.setText(mediaBean.getDes());
		holder.tv_title.setText(mediaBean.getTitle());
		return convertView;
	}

	/** 列表item内容 */
	private class ViewHolder {
		ImageView	iv_placard; // 海报，截图
		TextView	tv_title;
		TextView	tv_actor;
		TextView	tv_des;
		TextView	btn_cancel;
	}

}
