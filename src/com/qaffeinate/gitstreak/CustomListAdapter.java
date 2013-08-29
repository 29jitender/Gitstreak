package com.qaffeinate.gitstreak;

import java.util.ArrayList;
import java.util.Locale;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {

	private ArrayList<Item> listData;
	private ArrayList<Item> arraylist;


	private LayoutInflater layoutInflater;

	public CustomListAdapter(Context context, ArrayList<Item> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
		
		this.arraylist = new ArrayList<Item>();
        this.arraylist.addAll(listData);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
 			convertView = layoutInflater.inflate(R.layout.people_first_list, null);
			holder = new ViewHolder();
			holder.headlineView = (TextView) convertView.findViewById(R.id.people_first_name);
 			holder.location = (TextView) convertView.findViewById(R.id.people_first_location);
			holder.imageItem = (ImageView) convertView.findViewById(R.id.follow_image);

 			convertView.setTag(holder);
	 

		Item newsItem = (Item) listData.get(position);

		holder.headlineView.setText(newsItem.getHeadline());
  		holder.location.setText(newsItem.getstreak());
		if (holder.imageItem != null) {
  			String imageUrl = newsItem.geturl();
			AQuery aq = new AQuery(convertView);

			aq.id(holder.imageItem).image(imageUrl, true, true, 0, 0, new BitmapAjaxCallback(){

		        @Override
		        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
 		                iv.setImageBitmap(bm);
		                    
		        }
		        
		});
			
			
 		}
 
		return convertView;
	}
	 
	static class ViewHolder {
		TextView headlineView;
 		TextView location;
 
		ImageView imageItem;
	}
	
	 
}
