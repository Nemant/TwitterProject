package models;

public class UserData {

	private String userName;
	private int totalTweets;
	private int totalRT;
	private int avgRT;
	private int followers;
	private int friends;
	private String location;
	private String timezone;
	
	public UserData(String userName, int totalTweets, int totalRT, int avgRT, int followers, int friends, String location, String timezone){
		this.userName = userName;
		this.totalTweets = totalTweets;
		this.totalRT = totalRT;
		this.avgRT = avgRT;
		this.followers = followers;
		this.friends = friends;
		this.location = location;
		this.timezone = timezone;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getTotalTweets() {
		return totalTweets;
	}
	public void setTotalTweets(int totalTweets) {
		this.totalTweets = totalTweets;
	}
	public int getTotalRT() {
		return totalRT;
	}
	public void setTotalRT(int totalRT) {
		this.totalRT = totalRT;
	}
	public int getAvgRT() {
		return avgRT;
	}
	public void setAvgRT(int avgRT) {
		this.avgRT = avgRT;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public int getFriends() {
		return friends;
	}
	public void setFriends(int friends) {
		this.friends = friends;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	

}
