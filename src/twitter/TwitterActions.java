package twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import db.DataBaseManager;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterActions {

	private static Twitter twitter;
	private static Query query = new Query();

	static {
		twitter = new TwitterFactory(Util.conf).getInstance();

		query.setCount(100);
		query.setLang("pt");
		query.setResultType("recent");
		query.setGeoCode(new GeoLocation(-10.184444, -48.333611), 10000, "km"); //Lat e Long de Pra�a dos Girassois, Palmas - Tocantins.

	}

	public static List<String> search(String toSearch) throws TwitterException {
		// The factory instance is re-useable and thread safe.
		List<String> stringList = new ArrayList<String>();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Query query = new Query(toSearch);
		QueryResult result = twitter.search(query);
		stringList.add("======== Found: " + result.getTweets().size()
				+ " tweets for key: " + toSearch + " ========");
		System.out.println("======== Found: " + result.getTweets().size()
				+ " tweets for key: " + toSearch + " ========");
		for (Status tweets : result.getTweets()) {
			String time = formatter.format(tweets.getCreatedAt().getTime());
			stringList.add(time + " - @" + tweets.getUser().getScreenName()
					+ ":" + tweets.getText() + ". Location: "
					+ tweets.getGeoLocation());
		}
		return stringList;
	}

	public static void requestNewToken() throws IOException, TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("6S5z4JRsLlRCTxFQrEbtBA",
				"QAFSQK18WaYX9c1PepZj46O0lRGyajfgTb1wJAdvW0");
		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == accessToken) {
			System.out
					.println("Open the following URL and grant access to your account:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out
					.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
			String pin = br.readLine();
			try {
				if (pin.length() > 0) {
					accessToken = twitter
							.getOAuthAccessToken(requestToken, pin);
				} else {
					accessToken = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if (401 == te.getStatusCode()) {
					System.out.println("Unable to get the access token.");
				} else {
					te.printStackTrace();
				}
			}
		}
		// persist to the accessToken for future reference.
		Util.storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
		Status status = twitter.updateStatus("Creating New Auth");
		System.out.println("Successfully updated the status to ["
				+ status.getText() + "].");
	}

	public static void querySearchToSQL(String word) throws TwitterException, SQLException {
		query.setQuery(word);
		QueryResult result = twitter.search(query);
		List<Status> tweets = result.getTweets();
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm a");
		for (Status tweet : tweets) {
			String geloLoc = "NA";
			if (tweet.getGeoLocation() != null) {
				geloLoc =  "lat:"+tweet.getGeoLocation().getLatitude()+", long:"+tweet.getGeoLocation().getLongitude();
			}
			DataBaseManager.insertTweet(tweet.getCreatedAt(), word,geloLoc,tweet.getText());
		}

	}

	public static List<String> querySearchToString(String word) {
		query.setQuery(word);
		List<String> tweetsList = new ArrayList<String>();

		try {
			QueryResult result = twitter.search(query);
			List<Status> tweets = result.getTweets();
			System.out.println("**** Printing results for: " + word + " ****");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm a");
			for (Status tweet : tweets) {
				String date = sdf.format(tweet.getCreatedAt());
				String usrName = tweet.getUser().getName();
				// System.out.println(date+" - "+usrName+" - "+tweet.getText());
				tweetsList
						.add(date + " - " + usrName + " - " + tweet.getText());
			}
			tweetsList.add("**** Total: " + tweets.size() + " ****");

			return tweetsList;
		} catch (TwitterException te) {
			System.out.println("Couldn't connect: " + te.getMessage());
		}
		;
		return tweetsList;
	}

	public static void postStatus(String postMessage) throws TwitterException {
		Status status = twitter.updateStatus(postMessage);
		System.out.println("Successfully updated the status to ["
				+ status.getText() + "].");
	}

}
