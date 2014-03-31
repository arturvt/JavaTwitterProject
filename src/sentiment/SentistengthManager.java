package sentiment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import extra.Util;
import uk.ac.wlv.sentistrength.*;

public class Sentistength {

	private static ArrayList<Integer> result = new ArrayList<Integer>();

	private static void calculator(ArrayList<Integer> result) {

		int total = result.size();
		int pos = 0;
		int neg = 0;
		int neu = 0;

		for (int value : result) {
			if (value < -1)
				neg++;
			else if (value > 1)
				pos++;
			else
				neu++;
		}

		System.out.println("Total: " + total);
		System.out.println("Positive: " + pos);
		System.out.println("Neutral: " + neu);
		System.out.println("Negative: " + neg);
	}

	private static ArrayList<String> classifyFilesFromFolder(String folderLocation) {
		SentiStrength sentiStrength = new SentiStrength();
		String ssthInitialisation[] = { "sentidata", "c:/SentStrength_Data/" };
		sentiStrength.initialise(ssthInitialisation); // Initialise

		ArrayList<String> list = new ArrayList<String>();
		File folder = new File(folderLocation);
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			try {
				for (File file : files) {
					for (String text : Util.parseTXTToArray(file)) {
						String sentiment = sentiStrength.computeSentimentScores(text);
						result.add(Util.classificateSentiment(sentiment));
					}
				}
			} catch (IOException e) {
				System.out.println("Error");
			}
		}
		return list;
	}

	public static void main(String[] args) {

		// Method 1: one-off classification (inefficient for multiple
		// classifications)

		// Create an array of command line parameters, including text or file to
		// process

		// String text = "I'm happy with the environment";
		// text = Util.convertTextToSentistrengthPattern(text);
		//
		// String ssthInitialisationAndText[] = { "sentidata",
		// "C:/SentStrength_Data/", "text", text, "explain" };
		//
		// SentiStrength.main(ssthInitialisationAndText);

		// Method 2: One initialisation and repeated classifications

		SentiStrength sentiStrength = new SentiStrength();

		// Create an array of command line parameters to send (not text or file
		// to process)

		String ssthInitialisation[] = { "sentidata", "c:/SentStrength_Data/" };

		sentiStrength.initialise(ssthInitialisation); // Initialise

		// can now calculate sentiment scores quickly without having to
		// initialise again
		String folderLocation = "C:\\Users\\avt\\Dropbox\\Mestrado\\workspace\\TweetResults\\ENCONTRO COM FATIMA BERNARDES\\English\\";
		classifyFilesFromFolder(folderLocation);
		calculator(result);
//		File file = new File(
//				"C:\\Users\\avt\\Dropbox\\Mestrado\\workspace\\TweetResults\\ENCONTRO COM FATIMA BERNARDES\\English\\20-11-2013_#encontrofatima_filetered_translated.txt");
//		if (file.exists()) {
//			try {
//				for (String text : Util.parseTXTToArray(file)) {
//					String sentiment = sentiStrength.computeSentimentScores(text);
//					result.add(Util.classificateSentiment(sentiment));
//					// System.out.println(Util.classificateSentiment(sentiment)+" - "+text);
//				}
//
//				calculator(result);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		// System.out.println(sentiStrength.computeSentimentScores("I hate frogs."));
		// System.out.println(sentiStrength.computeSentimentScores("I love dogs."));
		// System.out.println(sentiStrength.computeSentimentScores("I'm happy with the environment"));
		// System.out.println(sentiStrength.computeSentimentScores("The current program is boring"));
		// System.out.println(sentiStrength.computeSentimentScores("I wonder when this is going to end!"));
	}
}
