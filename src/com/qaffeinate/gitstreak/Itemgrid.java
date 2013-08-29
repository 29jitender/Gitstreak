package com.qaffeinate.gitstreak;


/**
 * 
 * @author manish.s
 *
 */

public class Itemgrid {
 	String title;
	private String url;

	public Itemgrid(String string, String title) {
		super();
		this.url = string;
		this.title = title;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	

}
