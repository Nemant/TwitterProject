import models.TwitterSearchExample;
import play.*;

public class Global extends GlobalSettings {
	
	public TwitterSearchExample twitter;
	
	@Override
	  public void onStart(Application app) {
		twitter = new TwitterSearchExample();
	    Logger.info("Application has started");
	  }  
	  
	  @Override
	  public void onStop(Application app) {
	    Logger.info("Application shutdown...");
	  } 
}