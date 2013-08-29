package com.qaffeinate.gitstreak;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Streak_main extends Activity {
	 ArrayList<String> useridlist = new ArrayList<String>();
	 String myid;
	 int length=0;
	 int marginpixel=120;
     Map<String, Double> map = new HashMap<String, Double>();
     ValueComparator bvc =  new ValueComparator(map);
     TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
		ArrayList<TreeMap<String, Double>> streakdata = new ArrayList<TreeMap<String, Double>>();
	    ArrayList<Item> image_details = new ArrayList<Item>();
	    ArrayList<String> user_name=new ArrayList<String>();
	    ArrayList<String> user_value=new ArrayList<String>();
		 HashMap<String, String> name_url = new HashMap<String, String>();



		 ListView lv1;
		    int diff = 0; 
		    int timercount=0;
		    BigDecimal roundinc;

		    private static int FRAME_TIME_MS ;
		    private static final String KEY = "i";
		    private TextView animatedText;
		    boolean isRunning = false;
		    CustomListAdapter adapter;
		    BigDecimal margin=new BigDecimal(0.0f);

 		    Handler handler = new Handler() {
		        public void handleMessage(Message msg) {
		            try {  
		 
		            	   final int i= msg.getData().getInt(KEY);
		                   
		                   animatedText.setText(""+i);
		                   BigDecimal scaled = margin.setScale(0, RoundingMode.HALF_UP);
		                     RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)animatedText.getLayoutParams();
		                   params.setMargins(30, scaled.intValueExact(), 0, 0); //substitute parameters for left, top, right, bottom
		                   RelativeLayout rel =(RelativeLayout)findViewById(R.id.streak_layout);
//		                    
//		                   GradientDrawable gd = new GradientDrawable(
//		                           GradientDrawable.Orientation.TOP_BOTTOM,
//		                           new int[] {Color.argb(i, 255,102, 0),Color.argb(i*5, 255, 102, 0)});
//		                   gd.setCornerRadius(0f);
//		                   
//		                   rel.setBackgroundDrawable(gd);
		                  rel.setBackgroundColor(Color.argb(255, i, 102, 255-i));  
		                   animatedText.setLayoutParams(params);
		                   
		                   if(i==Integer.parseInt(user_value.get(user_value.size()-1))){//reached end

		                	   animatedText.setVisibility(View.GONE);
		                	   
		                   }

		             } catch (Exception err) {
		            }
		        }

		    };
		
		
		
 	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_streak_main);
		Intent in = getIntent();
		myid=in.getExtras().getString("myid");
		name_url = (HashMap<String, String>)in.getSerializableExtra("name_url");
		useridlist=(ArrayList<String>)in.getSerializableExtra("userid");
		useridlist.add(myid);
		length=useridlist.size();
		for(int i=0;i<useridlist.size();i++){
			String id =useridlist.get(i);
			getstreak(id);			
		}
        animatedText = (TextView) findViewById(R.id.streak);

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 0.0f);//1.0f
        animation.setDuration(50);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(1000);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 1f);
      
		    lv1 = (ListView) findViewById(R.id.custom_list);
		adapter=new CustomListAdapter(this, image_details);
		lv1.setAdapter(adapter);
	 
		lv1.setLayoutAnimation(controller);
		
	}

	public void getstreak(final String userid){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://github.com/users/"+userid+"/contributions_calendar_data",  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
				ArrayList<Integer> streak_value = new ArrayList<Integer>();
				try {
					JSONArray array1 =new JSONArray(response);
					for(int i =0;i<array1.length();i++){
						JSONArray array2 =array1.getJSONArray(i);
							String value=array2.getString(1);
							
							streak_value.add(Integer.parseInt(value));
					}			
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Double count=0.0;
	             streak: for(int i=streak_value.size()-2;i>0;i--){
	            	if(streak_value.get(i)==0){
	            		break streak;            		
	            	}
	            	else{
	            		count=count+1;
 
	            	}
	            	
	            		            	
	            }
	            
	            map.put(userid, count);  
             	 
             	if(length==map.size()){
             		sorted_map.putAll(map);
             		streakdata.add(sorted_map);
                	 Set<String> keys = sorted_map.keySet();
                	 for(String key: keys){
                         user_name.add(key);

                		 user_value.add(Math.round(map.get(key))+"");
                     }                


                    start(Integer.parseInt(user_value.get(0)),Integer.parseInt(user_value.get(user_value.size()-1)));   
             	}
  	            	
 
            }
        });

    }
	 
    public void start(final int fromValue,final int toValue) {
         Thread background = new Thread(new Runnable() {
            public void run() {
                try {
                	
                     for (int i = fromValue; i >= toValue && isRunning; i--) {
                     
                    	
                    	for(int j=0;j<user_value.size();j++){
 
                    		if(i==Integer.parseInt(user_value.get(j))){
                    			if(timercount<user_value.size()-1){
                                	diff=Integer.parseInt(user_value.get(timercount))-Integer.parseInt(user_value.get((timercount+1)));
                                	 if(diff==0){
											FRAME_TIME_MS=1000;
 				                        	 roundinc =new BigDecimal(Float.toString((float) (marginpixel)));

				                             margin=margin.add(roundinc); 
                                 }
                                 else{
											FRAME_TIME_MS=1000/diff;
                                 }
                             	            
                                	                    	
                                	  runOnUiThread(new Runnable() {
                                    	  public void run() {
                                	
                                	Item data = new Item();
                                    data.setHeadline(user_name.get(timercount)+"");    
                                    data.setstreak(user_value.get(timercount)+"");
                                    data.seturl(name_url.get((user_name.get(timercount))));
                                    image_details.add(data);
                                    timercount=timercount+1;
                                    
                                    adapter.notifyDataSetChanged();
                                    lv1.smoothScrollToPosition(timercount);

                            	  }
                            	});
                        	
                        
                             }
                    			else{
                    				
                     				
                    			}
                    		}  
                    		 
 
                    	}                     
                        
                    	Thread.sleep(FRAME_TIME_MS);
 
                        Bundle data= new Bundle();
                        data.putInt(KEY, i);
                        data.putInt("margin", fromValue-i);
 
                        Message message = handler.obtainMessage();
                        message.setData(data);
                        handler.sendMessage(message);

                        if(diff>0){
                            roundinc =new BigDecimal(Float.toString((float) (marginpixel/(diff*1.00))));

                            margin=margin.add(roundinc); 

                        }
 
                    }
                }
                catch (Throwable t) {
                }
            }
        });
         
        isRunning = true;
        background.start();
    }


}
