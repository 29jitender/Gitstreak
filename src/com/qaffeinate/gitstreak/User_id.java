package com.qaffeinate.gitstreak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class User_id extends Activity {
	String user;
	EditText userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_id);
		
		  userid =(EditText) findViewById(R.id.user_id_text);
		
		Button submit= (Button) findViewById(R.id.user_id_submit);
		
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(User_id.this ,Followers.class);//refreshing
				  user =userid.getText().toString();
  				myIntent.putExtra("userid", user);
                 startActivity(myIntent);	
				
			}
		 });
		
		
	}

	 

}
