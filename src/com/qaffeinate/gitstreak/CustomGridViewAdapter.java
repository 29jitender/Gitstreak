package com.qaffeinate.gitstreak;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
 
public class CustomGridViewAdapter extends ArrayAdapter<Itemgrid> {
	Context context;
	int layoutResourceId;
	View row;
	ArrayList<Itemgrid> data = new ArrayList<Itemgrid>();
	RecordHolder holder;
	 public static List<Integer> check = new ArrayList<Integer>();
	public CustomGridViewAdapter(Context context, int layoutResourceId,
			ArrayList<Itemgrid> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  row = convertView;
		  holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		Itemgrid item = data.get(position);
		holder.txtTitle.setText(item.getTitle());
		if (holder.imageItem != null) {
//			AQuery aq = new AQuery(row);
//			ImageOptions options = new ImageOptions();
//			options.round = 15;
//			options.memCache=true;
//			options.fileCache=true;
// 			aq.id(holder.imageItem).image(item.getUrl(), options);
			
			change(position);
			
			
 		}	
		
		return row;

	}
	
	public void change(int position){
		Itemgrid item = data.get(position);
		//notifyDataSetChanged();
		String imageUrl = item.getUrl();

			AQuery aq = new AQuery(row);
	 
			if(check.contains(position)){
				aq.id(holder.imageItem).image(imageUrl, true, true, 0, 0, new BitmapAjaxCallback(){

			        @Override
			        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
	 		                iv.setImageBitmap(getCroppedBitmap(bm));
			                    
			        }
			        
			});
			}
			else{
		aq.id(holder.imageItem).image(imageUrl, true, true, 0, 0, new BitmapAjaxCallback(){

		        @Override
		        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
 		                iv.setImageBitmap(getCroppedBitmap(toGrayscale(bm)));
		                    
		        }
		        
		});
			}
		
	}
	public Bitmap getCroppedBitmap(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	            bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
	            bitmap.getWidth() / 2, paint);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	    //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
	    //return _bmp;
	    return output;
	}
	
	public Bitmap toGrayscale(Bitmap bmpOriginal)
	{        
	    int width, height;
	    height = bmpOriginal.getHeight();
	    width = bmpOriginal.getWidth();    

	    Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	    Canvas c = new Canvas(bmpGrayscale);
	    Paint paint = new Paint();
	    ColorMatrix cm = new ColorMatrix();
	    cm.setSaturation(0);
	    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
	    paint.setColorFilter(f);
	    c.drawBitmap(bmpOriginal, 0, 0, paint);
	    return bmpGrayscale;
	}
	
	static class RecordHolder {
		TextView txtTitle;
		ImageView imageItem;

	}
}