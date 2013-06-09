package models;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jfree.data.time.Second;
import java.util.LinkedList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;


public class Metrics {

	public void generateTimeLineForTweets() {
		generateTimeLine(EnumDataSet.TWEETS);
	}
	
	public void generateTimeLineForReTweets() {
		generateTimeLine(EnumDataSet.RETWEETS);
	}
	
	/**
	 * Generates timeline for tweet volume
	 * */
	private void generateTimeLine(EnumDataSet workingSet) {
		Calendar startTime = Calendar.getInstance();
		startTime.set(2013, 2, 2, 22, 36, 0);
		startTime.set(Calendar.MILLISECOND, 0);

		Calendar endTime = Calendar.getInstance();
		endTime.set(2013, 2, 21, 16, 10, 0);
		endTime.set(Calendar.MILLISECOND, 0);
		
		Calendar currTime = Calendar.getInstance();
		currTime.setTimeInMillis(startTime.getTimeInMillis());
		currTime.add(Calendar.MINUTE, 10);
		
		int count = 0;
		while (startTime.before(endTime)) {
			Set<String> uniqueUsers = new HashSet<String>();
			ResultSet rs = QueryDB.getDataInInterval(workingSet, startTime, currTime);
			try {
				while (rs.next()) {
					uniqueUsers.add(rs.getString(3));
					count++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			Store.updateInInterval(workingSet, startTime, currTime, count, uniqueUsers.size());
			
			startTime.setTimeInMillis(currTime.getTimeInMillis());
			currTime.add(Calendar.MINUTE, 10);
			count = 0;
		}
		
	}
	
	public static int[] getVolumeOfTweets() {
		return getVolume(EnumDataSet.TWEETS);
	}
	
	public static int[] getVolumeOfReTweets() {
		return getVolume(EnumDataSet.RETWEETS);
	}
	
	public static int[] getVolume(EnumDataSet workingset) {
		int[] returnArray;
		ResultSet rs = QueryDB.getAllVolume(workingset);
		
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		returnArray = new int[list.size()];
		
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = list.get(i).intValue();
		} 
		
		return returnArray;
	}
	
	public static int[] getTotalretweetsForTweet(){
		int[] returnArray;
		ResultSet rs = QueryDB.getRetweetsForTweet();
		HashMap<Integer, Integer> totalRetweetsCount = new HashMap<Integer, Integer>();

		int noOfReTweets = 0;
		try {
			while (rs.next()) {
				noOfReTweets = rs.getInt(1);
				
				if (totalRetweetsCount.containsKey(noOfReTweets)) {
					int count = totalRetweetsCount.get(noOfReTweets);
					count++;
					
					totalRetweetsCount.put(noOfReTweets, count);
				} else {
					totalRetweetsCount.put(noOfReTweets, 1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		returnArray = new int[noOfReTweets+1];
//
//		for (int i = 1; i < returnArray.length; i++) {
//			if (totalRetweetsCount.containsKey(i)) {
//				returnArray[i] = totalRetweetsCount.get(i);
//			} else {
//				returnArray[i] = 0;
//			}
//		}
		
		returnArray = new int[(noOfReTweets / 50) + 1];

		for (int i = 1; i <= noOfReTweets; i++) {
			if (totalRetweetsCount.containsKey(i)) {
				returnArray[i / 50] = returnArray[i / 50] + totalRetweetsCount.get(i);
			}
		}
		
		
		return returnArray;
	}
	
	public static int[] getTotalretweetsForUser(){
		int[] returnArray;
		ResultSet rs = QueryDB.getRetweetsForUser();
		HashMap<Integer, Integer> totalRetweetsCount = new HashMap<Integer, Integer>();

		int noOfReTweets = 0;
		try {
			while (rs.next()) {
				noOfReTweets = rs.getInt(1);
				
				if (totalRetweetsCount.containsKey(noOfReTweets)) {
					int count = totalRetweetsCount.get(noOfReTweets);
					count++;
					
					totalRetweetsCount.put(noOfReTweets, count);
				} else {
					totalRetweetsCount.put(noOfReTweets, 1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		returnArray = new int[noOfReTweets+1];
//
//		for (int i = 1; i < returnArray.length; i++) {
//			if (totalRetweetsCount.containsKey(i)) {
//				returnArray[i] = totalRetweetsCount.get(i);
//			} else {
//				returnArray[i] = 0;
//			}
//		}
		
		returnArray = new int[(noOfReTweets / 100) + 1];

		for (int i = 1; i <= noOfReTweets; i++) {
			if (totalRetweetsCount.containsKey(i)) {
				returnArray[i / 100] = returnArray[i / 100] + totalRetweetsCount.get(i);
			}
		}
		
		
		return returnArray;
	}
	
	
	public static int[] getAvgretweetsForUser(){
		int[] returnArray;
		ResultSet rs = QueryDB.getAvgRetweetsForUser();
		HashMap<Integer, Integer> totalRetweetsCount = new HashMap<Integer, Integer>();

		int noOfReTweets = 0;
		try {
			while (rs.next()) {
				noOfReTweets = rs.getInt(1);
				
				if (totalRetweetsCount.containsKey(noOfReTweets)) {
					int count = totalRetweetsCount.get(noOfReTweets);
					count++;
					
					totalRetweetsCount.put(noOfReTweets, count);
				} else {
					totalRetweetsCount.put(noOfReTweets, 1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		returnArray = new int[noOfReTweets+1];
//
//		for (int i = 1; i < returnArray.length; i++) {
//			if (totalRetweetsCount.containsKey(i)) {
//				returnArray[i] = totalRetweetsCount.get(i);
//			} else {
//				returnArray[i] = 0;
//			}
//		}
		
		returnArray = new int[(noOfReTweets / 30) + 1];

		for (int i = 1; i <= noOfReTweets; i++) {
			if (totalRetweetsCount.containsKey(i)) {
				returnArray[i / 30] = returnArray[i / 30] + totalRetweetsCount.get(i);
			}
		}
		
		
		return returnArray;
	}
	
	private void createChart(LinkedList<ReTweetInInterval> retweetsList ){

		int i = 0;
		TimeSeries c = new TimeSeries("Retweet frequency", Second.class);
		for (ReTweetInInterval reTweetInInterval: retweetsList) {
			if (i != 0) {
				Second regularTimePeriod =  new Second(reTweetInInterval.getEnd());

				c.add(regularTimePeriod, reTweetInInterval.getNoOfReTweets());
			}
			i++;
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(c);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"retweet frequency",
				"Date",
				"Population",
				dataset,
				true,
				true,
				false);
		try {
			ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 500, 300);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}

	}

	/**
	 * BUILD timeline_Table (hashmap) (take String[] users, Calendar start, Calendar end, min interval)
	 * */
	public void buildTimelineforUsers(String[] users, Calendar start, Calendar end, int intervalMin) {
		ResultSet rs = QueryDB.getRetweetsForUsersInInterval(users, start, end);
		int i = 1;
		Calendar currTime = Calendar.getInstance();

		Calendar beginInterval = Calendar.getInstance();
		beginInterval.setTimeInMillis(start.getTimeInMillis());
		Calendar endInterval = Calendar.getInstance();
		endInterval.setTimeInMillis(beginInterval.getTimeInMillis());
		endInterval.add(Calendar.MINUTE, intervalMin);

		LinkedList<ReTweetInInterval> retweetsList = new LinkedList<ReTweetInInterval>();
		int reTweetCount = 0;
		ReTweetInInterval prevTweet = new ReTweetInInterval("0", beginInterval, endInterval);
		ReTweetInInterval curTweet = new ReTweetInInterval("0", beginInterval, endInterval);
		// make beginInterval = currTime and keep moving, when there is a jump in currTime, fill in the middle stuff with crap
		try {
			while (rs.next()) {
				// Get time of current row
				currTime.setTimeInMillis(rs.getTimestamp(2).getTime());
				// Create current Tweet
				curTweet = new ReTweetInInterval(rs.getString(5), beginInterval, endInterval);
				// Still in same set of reTweet
				if (prevTweet.equals(rs.getString(5)) && (endInterval.after(currTime) || endInterval.equals(currTime))) {
					System.out.println("counting");

					reTweetCount++;
				} else if (prevTweet.equals(rs.getString(5)) && endInterval.before(currTime)) {
					System.out.println("changed time interval");
					System.out.println(i);
					i++;

					prevTweet.setNoOfReTweets(reTweetCount);
					retweetsList.add(prevTweet);
					reTweetCount = 1;
					//					beginInterval.setTimeInMillis(endInterval.getTimeInMillis());
					//					endInterval.add(Calendar.MINUTE, intervalMin);

					beginInterval.setTimeInMillis(currTime.getTimeInMillis());
					endInterval.setTimeInMillis(currTime.getTimeInMillis());
					endInterval.add(Calendar.MINUTE, intervalMin);
				} else {
					System.out.println("changed tweet");
					prevTweet.setNoOfReTweets(reTweetCount);
					retweetsList.add(prevTweet);
					reTweetCount = 1;
					beginInterval.setTimeInMillis(start.getTimeInMillis());
					endInterval.setTimeInMillis(beginInterval.getTimeInMillis());
					endInterval.add(Calendar.MINUTE, intervalMin);
				}
				// Create previous tweet
				prevTweet = new ReTweetInInterval(rs.getString(5), beginInterval, endInterval);
				//				System.out.println("Tweet No: " + i);
				//				System.out.println("TweetID: " + rs.getString(1));
				//				System.out.println("DateTime: " + rs.getDate(2).toString() + " " + rs.getTime(2).toString());
				//				System.out.println("Calendar dateTime: " + currTime.getTime().toString());
				//				System.out.println("Original TweetID: " + rs.getString(5));
				//				System.out.println();
				//				i++;
			}
			prevTweet.setNoOfReTweets(reTweetCount);
			retweetsList.add(prevTweet);
		} catch (SQLException e) {
			e.printStackTrace();
		}


		for (ReTweetInInterval reTweetInInterval: retweetsList) {
			System.out.println(reTweetInInterval.toString());
		} 


		createChart(retweetsList);
	}
	
	public static Tweet[] getLiveFeed() {
		ResultSet rs = QueryDB.getLatestTweets();
		Tweet[] latestTweets = new Tweet[20];
		int i = 0;

		try {
			while (rs.next()) {
				Tweet t = new Tweet();
				t.setUser(rs.getString(1));
				
				t.setDateTime(rs.getTime(2));
				t.setText(rs.getString(3));
				latestTweets[i] = t;
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return latestTweets;
	}
}
