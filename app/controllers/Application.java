package controllers;

import java.sql.SQLException;

import play.*;
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;
import play.data.Form;
import static play.data.Form.*;
import models.Connector;
import models.Metrics;
import models.QueryDB;
import models.TwitterSearchExample;

import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	private static TwitterSearchExample twitter;
	
    public static class TweetID {
        @Required public String name;
    }
    
	public static Result index() {
		
		return ok(index.render(form(TweetID.class), Metrics.getVolumeOfTweets(), Metrics.getVolumeOfReTweets(), Metrics.getTotalretweetsForTweet(), Metrics.getTotalretweetsForUser(), Metrics.getAvgretweetsForUser()));
	}

	public static void generate() {
//		Form<TweetID> form = form(TweetID.class).bindFromRequest();
//
//		if(form.hasErrors()) {
//            return badRequest();
//        } else {
//            TweetID tweetID = form.get();
//            try {
//				twitter.main(tweetID.name);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            return ok(graph.render(tweetID.name));
//        }
	}

}
