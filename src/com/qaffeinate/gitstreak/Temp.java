package com.qaffeinate.gitstreak;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Temp extends Activity {
	  ListView lv1;
    int diff = 0; 
    int timercount=0;
    BigDecimal roundinc;
    ArrayList<Item> image_details = new ArrayList<Item>();

    private static int FRAME_TIME_MS ;
    private static final String KEY = "i";
    private TextView animatedText;
    boolean isRunning = false;
    CustomListAdapter adapter;
    BigDecimal margin=new BigDecimal(0.0f);

    String[] value={"400","330","250", "40","33","25","17","12","7","1","1","1","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};    // background updating
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
//                   GradientDrawable gd = new GradientDrawable(
//                           GradientDrawable.Orientation.TOP_BOTTOM,
//                           new int[] {Color.argb(i, 255,102, 0),Color.argb(i*5, 255, 102, 0)});
//                   gd.setCornerRadius(0f);
//                   
//                   rel.setBackgroundDrawable(gd);
                  rel.setBackgroundColor(Color.argb(255, i, 102, 255-i));  
                   animatedText.setLayoutParams(params);
 
                   
           	

             } catch (Exception err) {
            }
        }

    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streak_main);
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
         start(Integer.parseInt(value[0]),Integer.parseInt(value[value.length-1])-1);      
          

    }

        
    
    
    public void start(final int fromValue,final int toValue) {
         Thread background = new Thread(new Runnable() {
            public void run() {
                try {
                	
                    for (int i = fromValue; i >= toValue && isRunning; i--) {
                     
                    	for(int j=0;j<value.length;j++){
 
                    		if(i==Integer.parseInt(value[j])){
                    			
                    			if(timercount<value.length-1){
                                	diff=Integer.parseInt(value[timercount])-Integer.parseInt(value[timercount+1]);
                                    if(diff==0){
											FRAME_TIME_MS=1000;
				                        	Log.i("frame","pause");
				                        	 roundinc =new BigDecimal(Float.toString((float) (80)));

				                             margin=margin.add(roundinc); 
                                    }
                                    else{
 											FRAME_TIME_MS=1000/diff;
                                    }
                                	                    	
                                    runOnUiThread(new Runnable() {
                                    	  public void run() {
                                    			Item data = new Item();
                                                data.setHeadline(""+timercount);    
                                            data.setstreak(value[timercount]+"");
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
                            roundinc =new BigDecimal(Float.toString((float) (80/(diff*1.00))));

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