package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Calendar;

import play.db.DB;

public class QueryDB {

	private static Connection connection;
	
	public QueryDB() {
		connection = DB.getConnection();
	}
	
	public static ResultSet getDataInInterval(EnumDataSet workingSet, Calendar start, Calendar end) {
		ResultSet rs = null; 
		String query = "SELECT \"TweetID\", \"DateTime\", \"User\" ";
		
		if (workingSet == EnumDataSet.TWEETS) {
			query = query + "FROM \"FinalProject\".\"Tweets\"";
		} else if (workingSet == EnumDataSet.RETWEETS) {
			query = query + "FROM \"FinalProject\".\"ReTweets\"";
		}
		
		query = query + "WHERE \"DateTime\" > '" + start.getTime().toString() + "' AND " +
		 					  "\"DateTime\" < '" + end.getTime().toString() + "' " +
		 				";";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet getAllVolume(EnumDataSet workingSet) {
		Connection connection2 = DB.getConnection();
		ResultSet rs = null; 
		PreparedStatement preparedStatement = null;
		
		try {
			if (workingSet.equals(EnumDataSet.TWEETS)) {
				preparedStatement = connection2.prepareStatement("SELECT \"UniqueTweets\" FROM \"FinalProject\".\"TweetsInInterval\" ORDER BY \"DateTimeFinish\" ASC");
			} else if (workingSet.equals(EnumDataSet.RETWEETS)) {
				preparedStatement = connection2.prepareStatement("SELECT \"UniqueReTweets\" FROM \"FinalProject\".\"ReTweetsInInterval\" ORDER BY \"DateTimeFinish\" ASC");
			}
			
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connection2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public static ResultSet getRetweetsForTweet(){
		Connection connection2 = DB.getConnection();
		ResultSet rs = null; 
		
		try {
			PreparedStatement preparedStatement = connection2.prepareStatement("SELECT \"Total No. of Retweets\" FROM \"FinalProject\".\"total_retweets_for_tweet\" ORDER BY \"Total No. of Retweets\" ASC");
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			connection2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public static ResultSet getRetweetsForUser(){
		Connection connection2 = DB.getConnection();
		ResultSet rs = null; 
		
		try {
			PreparedStatement preparedStatement = connection2.prepareStatement("SELECT \"Total Retweets\"  FROM \"FinalProject\".\"total_retweets_for_user\" ORDER BY \"Total Retweets\" ASC");
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			connection2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public static ResultSet getAvgRetweetsForUser(){
		Connection connection2 = DB.getConnection();
		ResultSet rs = null; 
		
		try {
			PreparedStatement preparedStatement = connection2.prepareStatement("SELECT \"AverageRetweets\"  FROM \"FinalProject\".\"total_retweets_for_user\" ORDER BY \"AverageRetweets\" ASC");
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			connection2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	/**
	 * A = Top 200 rows from total_retweets_for_tweet in ascending order of date
	 * PTS = previous time slice: get first row from A and get the time
	 * TS = time slice: PTS + 10 min
	 * B = All ReTweets that match the ids in table A (use query below)
	 * Start iterating through B
	 * 		Check row is > PTS & < TS
	 *		IF successful then add to LinkedHashMap(ID, count+1) (LHM1)
	 *		ELSE Add LinkedHashMasp(PTS, LHM1) (LHM2); PTS = TS; TS + 10 MIN; LinkedHashMap(ID, count+1) (LHM1) 
	 * DO for 24hrs
	 * 
	 * 
	 * 
	 * 	SELECT * 
		FROM ReTweets
		WHERE OriginalTweetID IN (
    		SELECT TweetID
    		FROM total_retweets_for_tweet
    		ORDER BY
    		 "Total No. of Retweets"  DESC
			LIMIT 200
			)
		ORDER BY DateTime ASC;
		
	 * java hashmap serialisation
	 */
	
	
	public static ResultSet getRetweetsForUsersInInterval(String[] users, Calendar start, Calendar end){
		ResultSet rs = null;
		String query = "SELECT *" +
				"FROM \"FinalProject\".\"ReTweets\"" +
				"WHERE \"OriginalTweetID\" IN (" +
					"SELECT \"TweetID\"" +
					"FROM \"FinalProject\".\"total_retweets_for_tweet\"" +
					"WHERE \"total_retweets_for_tweet\".\"User\" = '" + users[0] + "' ";
		
		for (int i = 1; i < users.length; i++) {
			query = query + "OR \"total_retweets_for_tweet\".\"User\" = '" + users[i] + "' ";
		}
		
		query = query + 
					"ORDER BY " +
					"\"Total No. of Retweets\"  DESC" +
				") AND" +
				"\"ReTweets\".\"DateTime\" > '" + start.getTime().toString() + "' AND " +
				"\"ReTweets\".\"DateTime\" < '" + end.getTime().toString() + "' " +
				"AND \"ReTweets\".\"OriginalTweetID\" = '308231912340721665' " + ///////////////////// 
				"ORDER BY 	\"ReTweets\".\"OriginalTweetID\" ASC," +
				"\"ReTweets\".\"DateTime\" ASC;";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * add the hashmap from metrics to timeline_table
	 * */
	
	public void getTweetsForUserForInterval() throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(
		"SELECT " +
		"  \"total_retweets_for_tweet\".\"TweetID\", " +
		"  \"total_retweets_for_tweet\".\"DateTime\", " +
		"  \"total_retweets_for_tweet\".\"User\", " +
		"  \"total_retweets_for_tweet\".\"Total No. of Retweets\", " +
		"  \"total_retweets_for_tweet\".\"Blog\"" +
		"FROM " +
		"  \"FinalProject\".\"total_retweets_for_tweet\"" +
		"WHERE" + 
		"  \"total_retweets_for_tweet\".\"User\" = '34613288';"
		);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		Map timesAndTweets = new HashMap<Long, Map>(); 
		Map reTweet = new HashMap<String, Integer>();
		
		Calendar startTime = Calendar.getInstance();
		startTime.set(2013, 2, 2, 21, 0);
		Calendar endTime = Calendar.getInstance();
		endTime.set(2013, 2, 3, 21, 0);
		
		String[] ids = new String[25];
		
		int i = 0;
		while (rs.next()) {
			
			ids[i] = rs.getString(1);
			System.out.println("Tweet ID: " + ids[i]);
			i++;
		}
		
		Calendar previousTime = Calendar.getInstance();
		previousTime.setTimeInMillis(startTime.getTimeInMillis());

		while (startTime.before(endTime)) {
			System.out.println(startTime.before(endTime) + " : " + startTime.getTime().toString() + " < " + endTime.getTime().toString());
			System.out.println(previousTime.getTime().toString());
			for (i = 0; i < 25; i++) {
			System.out.println(ids[i]);
				PreparedStatement preparedStatement2 = connection.prepareStatement(
				"SELECT " +
				  "\"ReTweets\".\"TweetID\" " + 
				"FROM " +
				"  \"FinalProject\".\"ReTweets\" " +
				"WHERE " + 
				  "\"ReTweets\".\"OriginalTweetID\" = '" + ids[i] + "' AND " + 
				  "\"ReTweets\".\"DateTime\" > '" + previousTime.getTime().toString() + "' AND " +
				  "\"ReTweets\".\"DateTime\" < '" +  startTime.getTime().toString() + "' ;"
						);
				ResultSet rs2 = preparedStatement2.executeQuery();
				int size = 0;
				while(rs2.next()) {
					size++;
				}
				reTweet.put(ids[i], size);
			}
			
			Map temp = new HashMap(reTweet); 
			timesAndTweets.put(startTime.getTime(), temp);
			
			previousTime.setTimeInMillis(startTime.getTimeInMillis());
			startTime.add(Calendar.MINUTE, 120);
		}
		
		Iterator it = timesAndTweets.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey());
	        
	        Map m = (Map) pairs.getValue();
			Iterator it2 = m.entrySet().iterator();

	        while (it2.hasNext()) {
		        Map.Entry pairs2 = (Map.Entry)it2.next();
		        System.out.println(pairs2.getValue());
		        it2.remove(); // avoids a ConcurrentModificationException

	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }

	}
	
	public static ResultSet getLatestTweets(){
		Connection connection2 = DB.getConnection();
		ResultSet rs = null; 

		try {
			PreparedStatement preparedStatement = connection2.prepareStatement(
					"(" +
						"SELECT \"User\", \"DateTime\", \"Blog\" " +
						"FROM \"FinalProject\".\"ReTweets\" " +
						"ORDER BY \"DateTime\" DESC " +
						"LIMIT 10" +
					")" +
					"UNION" +
					"(" +
						"SELECT \"User\", \"DateTime\", \"Blog\" " +
						"FROM \"FinalProject\".\"Tweets\" " +
						"ORDER BY \"DateTime\" DESC " +
						"LIMIT 10" +
					")" +
					"ORDER BY \"DateTime\" DESC " +
					"LIMIT 20" +
					";");
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
