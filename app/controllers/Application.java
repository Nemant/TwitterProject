package controllers;

import play.data.validation.Constraints.Required;
import static play.data.Form.*;
import models.Metrics;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
    public static class TweetID {
        @Required public String name;
    }
    
	public static Result index() {
		return ok(index.render(form(TweetID.class), Metrics.getVolumeOfTweets(), Metrics.getVolumeOfReTweets(), Metrics.getTotalretweetsForTweet(), Metrics.getTotalretweetsForUser(), Metrics.getAvgretweetsForUser()));
	}
	
	public static Result liveFeed() {
	      Metrics.getLiveFeed();
	      return ok(ajax_result.render(answer));
	  }

	public static Result javascriptRoutes() {
	    response().setContentType("text/javascript");
	    return ok(
	      Routes.javascriptRouter("jsRoutes",
	        // Routes
	        controllers.routes.javascript.Application.liveFeed()
	      )
	    );
	  }

}
