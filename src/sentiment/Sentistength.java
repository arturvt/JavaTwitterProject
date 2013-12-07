package sentiment;
import uk.ac.wlv.sentistrength.*;
public class Sentistength {
	public static void main(String[] args) {
		//Method 1: one-off classification (inefficient for multiple classifications)

		//Create an array of command line parameters, including text or file to process

		String ssthInitialisationAndText[] = {"sentidata", "C:/SentStrength_Data/", "text", 

		"I+hate+frogs+but+love+dogs.", "explain"};

		SentiStrength.main(ssthInitialisationAndText);

		//Method 2: One initialisation and repeated classifications

		SentiStrength sentiStrength = new SentiStrength(); 

		//Create an array of command line parameters to send (not text or file to process)

		String ssthInitialisation[] = {"sentidata", "c:/SentStrength_Data/", "explain"};

		sentiStrength.initialise(ssthInitialisation); //Initialise

		//can now calculate sentiment scores quickly without having to initialise again

		System.out.println(sentiStrength.computeSentimentScores("I hate frogs."));

		System.out.println(sentiStrength.computeSentimentScores("I love dogs."));
	}
}
