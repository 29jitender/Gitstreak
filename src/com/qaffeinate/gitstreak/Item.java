package com.qaffeinate.gitstreak;

public class Item {
	private String headline;
	 
	private String streak;
	private String userid;
	private String url;
 
	public String geturl(){
		return url;
	}
	public void seturl(String url){
		this.url=url;
	}
	
	
	public String getstreak(){
		return streak;
	}
	public void setstreak(String streak){
		this.streak=streak;
	}
	
	 

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	 

	 

	@Override
	public String toString() {
		return headline+","+userid;
	}
}
