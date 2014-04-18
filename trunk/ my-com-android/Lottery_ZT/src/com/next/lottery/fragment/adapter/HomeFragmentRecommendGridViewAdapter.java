package com.next.lottery.fragment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.views.MyImageView;
import com.lidroid.xutils.BitmapUtils;
import com.next.lottery.R;
import com.next.lottery.beans.HomeStaticBean.Data;
import com.next.lottery.utils.Util;

public class HomeFragmentRecommendGridViewAdapter extends BaseAdapter {

	private Context				context;

	private LayoutInflater		inflater;

	private List<Data>	list;
	private BitmapUtils bitmapUtils ;


	private static final String	TAG							= "HomeFragmentRecommendGridViewAdapter";
	private static final int	IMAGE_ITEM_SPACING			= 3;							// px
	private static final int	MARGIN_LEFT					= 8;							// dp
	private static final int	MARGIN_RIGHT				= 8;							// dp
	private static final int	MARGIN_BOTTOM				= 3;							// dp
	private static final int	TEXT_TITLE_FONT_SIZE		= 15;							// sp
	private static final int	TEXT_DES_FONT_SIZE			= 12;							// sp
	private static final int	IMAGE_WIDTH					= 140;							//px
	private static final int	IMAGE_HEIGHT				= 110;							// px
	private static final int	COLUMN						= 3;
	private static final int	GRIDVIEW_HORIZONAL_SPACE	= 6;							// dp

	public HomeFragmentRecommendGridViewAdapter(Context context, ArrayList<Data> mGridData) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = mGridData;
		bitmapUtils = new BitmapUtils(context);
		ULog.i("list-->"+mGridData.toString());
	}

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
	public View getView(int position, View convertView, ViewGroup arg2) {
		InfoHolder infoHolder = null;
		Data data = list.get(position);
		ULog.i("data-->"+data);
		
		if (convertView == null) {
			convertView = inflater.from(context).inflate(R.layout.fragment_home_recommend_item, null);
			infoHolder = new InfoHolder();
			infoHolder.tag = (ImageView) convertView.findViewById(R.id.icon_item);
			infoHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
			infoHolder.describe = (TextView) convertView.findViewById(R.id.tv_describe);
			convertView.setTag(infoHolder);
		}
		else {
			infoHolder = (InfoHolder) convertView.getTag();
		}
		infoHolder.title.setText(data.getName());
		measureItemImage(infoHolder.tag);
		bitmapUtils.display(infoHolder.tag, data.getCover());
		return convertView;
	}

	class InfoHolder {
		ImageView	tag;
		TextView	title;
		TextView	describe;
	}

	public void measureItemImage(View view) {
		// TODO Auto-generated method stub
		ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
		int gridviewMargin = Util.dip2px(context, MARGIN_LEFT + MARGIN_RIGHT);
		int gridviewHorizonalSpac = Util.dip2px(context, GRIDVIEW_HORIZONAL_SPACE) * (COLUMN - 1);
		int gridviewImageSpace = 2 * COLUMN * IMAGE_ITEM_SPACING;
		int imageWidth = (Util.getWindowWidth(context) - gridviewHorizonalSpac - gridviewMargin - gridviewImageSpace) / 3;
		int imageHeight = imageWidth * IMAGE_HEIGHT / IMAGE_WIDTH;
		params.width = imageWidth;
		params.height = imageHeight;
		view.setLayoutParams(params);
	}
}
