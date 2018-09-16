import java.io.IOException;

import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

class StartingPoint {

	public static void main(String[] args) throws TwitterException, IOException, InterruptedException {
		
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true);
		configurationBuilder.setOAuthConsumerKey("O11cJxqGqYtfPgtCmDqvj9GO2");
		configurationBuilder.setOAuthConsumerSecret("bP0mJt6JS1Hotlzak6ogsLDUB6FWYLlXf2tavGkweZLYA64FFe");
		configurationBuilder.setOAuthAccessToken("847406726231973889-P5Vjgsx4ZuPMzgc702ob1amlBLGMQPt");
		configurationBuilder.setOAuthAccessTokenSecret("wJkZQFkaXr3et5Z5TZPijUPOjPy7upsX1xDYzvjewBTeg");
		Configuration configuration = configurationBuilder.build();
									
		TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();
		twitterStream.addListener(new TweeterListener());
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.track("trump syria,trump russia,trump britain");
		filterQuery.language("en");
		twitterStream.filter(filterQuery);	
	}
}
