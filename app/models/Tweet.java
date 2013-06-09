package models;

import java.sql.Time;

public class Tweet {

	private String text;
	private String user;
	private Time dateTime;
	
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
		System.out.println(dateTime);
		return dateTime.toString();
	}
	
	public void setDateTime(Time dateTime) {
		this.dateTime = dateTime;
	}
	
}
