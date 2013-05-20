package twitter;


import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class Crawler {

	private static TwitterStream twitterStream;
	private static List<String> wordsToSearch = Util.getWordsSet();
	
	
	static {
		twitterStream = new TwitterStreamFactory(Util.conf).getInstance();
	}
	
	private static boolean filter(String text) {

		for (String word:wordsToSearch) {
			if (text.toLowerCase().contains(word.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	
	
	public static void liveStreamer() {
		FilterQuery filter = new FilterQuery();
		String tracks[] = {"São Paulo", "SaoPaulo"};
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
            		System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                //System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);
        twitterStream.filter(new FilterQuery());
//        twitterStream.sample();
	}
}
