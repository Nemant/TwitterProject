package models;

import java.util.Date;

public class Tweet {

	private String text;
	private String user;
	private Date dateTime;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getDateTime() {
		return dateTime.toString();
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
}
