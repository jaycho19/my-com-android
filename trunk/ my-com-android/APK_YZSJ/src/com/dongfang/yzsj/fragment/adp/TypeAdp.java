package com.dongfang.yzsj.fragment.adp;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.HomeMovie;
import com.dongfang.yzsj.utils.UtilOfTime;
import com.lidroid.xutils.BitmapUtils;

/**
 * 频道列表adp
 * 
 * @author dongfang
 */
public class TypeAdp extends BaseAdapter {

	public static final String TAG = TypeAdp.class.getSimpleName();

	private List<HomeMovie> list = null;
	private Context context;

	public TypeAdp(Context context) {
		this.context = context;
	}

	public List<HomeMovie> getList() {
		return list;
	}

	public void setList(List<HomeMovie> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return null == list ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return null == list ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_type_adp_item, null);
			holder = new ViewHolder();
			holder.iv_placard = (ImageView) convertView.findViewById(R.id.favorite_imageView_placard);
			holder.tv_title = (TextView) convertView.findViewById(R.id.favorite_tv_title);
			holder.tv_actor = (TextView) convertView.findViewById(R.id.favorite_tv_actor);
			holder.tv_length = (TextView) convertView.findViewById(R.id.favorite_tv_length);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		final HomeMovie movie = list.get(position);

		BitmapUtils.create(context).display(holder.iv_placard, movie.PC_MEDIA_POSTER_BIG, 105, 137);
		holder.tv_title.setText(movie.getMEDIA_NAME());
		holder.tv_actor.setText(movie.getMEDIA_ACTORS());
		String length = UtilOfTime.formatSeconds2Date(Long.valueOf(TextUtils.isEmpty(movie.getMEDIA_LENGTH()) ? "0"
				: movie.getMEDIA_LENGTH()));
		holder.tv_length.setText(length);
		return convertView;
	}

	/** 列表item内容 */
	private class ViewHolder {
		ImageView iv_placard; // 海报，截图
		TextView tv_title;
		TextView tv_actor;
		TextView tv_length;
	}

}
