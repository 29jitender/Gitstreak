package com.qaffeinate.gitstreak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Followers extends Activity {
	String userid;
	 String result;
	 GridView gridView;
	 List<Integer> passposition = new ArrayList<Integer>();
	 HashMap<String, String> name_url = new HashMap<String, String>();

	 ArrayList<String> pass_userid = new ArrayList<String>();
	 Button go;
		ArrayList<Itemgrid> gridArray = new ArrayList<Itemgrid>();
		 CustomGridViewAdapter customGridAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_followers);
		Intent in = getIntent();
		userid=in.getStringExtra("userid"); 	 
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
		getuser_data();
		go = (Button) findViewById(R.id.follower_go); 
		go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.i("name url",name_url+"");
				Intent myIntent = new Intent(Followers.this ,Streak_main.class);//refreshing
				
 				myIntent.putExtra("userid", pass_userid);
 				myIntent.putExtra("name_url", name_url);

 				myIntent.putExtra("myid", userid);
               startActivity(myIntent);	
				
			}
		 });
		
	}
	public void getuser_data(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.github.com/users/"+userid,  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
            	TextView user_name =(TextView) findViewById(R.id.follower_username);
        		TextView user_email =(TextView) findViewById(R.id.follower_useremail);
        		TextView user_location =(TextView) findViewById(R.id.follower_userlocation); 
        		try {
        			JSONObject obj1 =new JSONObject(response);
        			String name = obj1.getString("name");
        			String email = obj1.getString("email");
        			String location = obj1.getString("location");
        			String imageurl = obj1.getString("gravatar_id");
        			name_url.put(userid, "http://gravatar.com/avatar/"+imageurl);
        			 

         			user_name.setText(name);
        			user_email.setText(email);
        			user_location.setText(location);
        			
        			AQuery aq = new AQuery(Followers.this);
        			 
        			ImageOptions options = new ImageOptions();
        			options.round = 15;
        			options.memCache=true;
        			options.fileCache=true;
        			
        			aq.id(R.id.follower_userimage).image("http://gravatar.com/avatar/"+imageurl,options);
        			
        			 		 
        			
        			getfollow();        			
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		
             }
        });

    }
	public void getfollow(){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.github.com/users/"+userid+"/following",  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
            	try {
					JSONArray array1 =new JSONArray(response);
					for(int i=0;i<array1.length();i++){
						JSONObject obj1 = array1.getJSONObject(i);
						String name= obj1.getString("login");
						String imageurl = obj1.getString("gravatar_id");
						name_url.put(name, "http://gravatar.com/avatar/"+imageurl);
 						gridArray.add(new Itemgrid("http://gravatar.com/avatar/"+imageurl,name));

								
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 				 
				gridView = (GridView) findViewById(R.id.gridView1); 
				gridView.setAdapter(customGridAdapter);		
				gridView.setOnItemClickListener(itemClickListener);
            }
        });

    }
 

	  
	 
	 
	 private OnItemClickListener itemClickListener = new OnItemClickListener() {
		   
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Itemgrid item = gridArray.get(position);

				if(passposition.contains(position)){
					
					passposition.remove(passposition.indexOf(position));
					pass_userid.remove(pass_userid.indexOf(item.getTitle()));
					
 				}
				else{
					passposition.add(position);

					pass_userid.add(item.getTitle());

				}
				if(passposition.isEmpty()){
					go.setVisibility(View.GONE);
				}
				else{
					go.setVisibility(View.VISIBLE);
				}
				customGridAdapter.check=passposition;
				gridView.setAdapter(customGridAdapter);
 				 //gridView.smoothScrollToPositionFromTop(position,arg1.getBottom());

 				//gridView.smoothScrollToPosition(position);
				 gridView.setSelection(position);	
				//gridView.requestFocus();
			//	gridView.scrollTo(0, arg1.getId());
       			//Toast.makeText(getApplicationContext(), pass_userid+"", Toast.LENGTH_SHORT).show();
				
			}
		};
	 
	 
	  

}
