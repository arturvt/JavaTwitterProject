package sentiment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import extra.Util;

public class Main {

	private static String folder = "C:\\Users\\avt\\Dropbox\\Mestrado\\workspace\\TweetResults";
	private static String program = "Ratinho";
	private static String pastaEnglish = "English";
	
	public static void main(String[] args) {
		System.out.println("Iniciando classificador.");
		System.out.println("Procurando txts em: "+folder);
		System.out.println("Procurando pelo programa: "+program);
		
		File folderWithTXT = new File(folder+File.separator+program+File.separator+pastaEnglish);
		if (folderWithTXT.exists() && folderWithTXT.isDirectory()) {
			File[] listOfTXT = folderWithTXT.listFiles();
			System.out.println("Encontrado "+listOfTXT.length+" arquivos.");
			
			HashMap<String, ArrayList<String>> tweetsByDate = new HashMap<String, ArrayList<String>>();
			
			
			for (File txtFile:listOfTXT) {
				String data = txtFile.getName().substring(0,txtFile.getName().indexOf("_"));
				
				ArrayList<String> tweetsOfTXT;
				try {
					tweetsOfTXT = Util.parseTXTToArray(txtFile);
					if (!tweetsByDate.containsKey(data)) {
						tweetsByDate.put(data, tweetsOfTXT);
					} else {
						tweetsByDate.get(data).addAll(tweetsOfTXT);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			for (String key:tweetsByDate.keySet()) {
				System.out.println(" ----------------------------------------------------------");
				System.out.println("Data: "+key+" com "+ tweetsByDate.get(key).size()+ " tweets.");
				System.out.println("*** Classificação SWN ***");
				long initial = System.currentTimeMillis();
				try {
					SWNManager swnManager = new SWNManager();
					swnManager.classificarTweets(tweetsByDate.get(key));
				} catch (IOException e) {
					e.printStackTrace();
				}
				long totalTime = System.currentTimeMillis() - initial;
				totalTime/=1000;
				System.out.println("Duração: "+totalTime+ " segundos.");

				System.out.println(" *** Classificação Sentistrength ***");
				initial = System.currentTimeMillis();
				SentistengthManager senti = new SentistengthManager();
				senti.computarSentimentos(tweetsByDate.get(key));
				
				totalTime/=1000;
				System.out.println("Duração: "+totalTime+ " segundos.");
				System.out.println(" ----------------------------------------------------------");
			}
		}
	}
}
