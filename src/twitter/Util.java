package twitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Util {
	
	private final static String TXT_FILE_NAME = "storedToken.txt";
	private final static String TXT_WORDS_FILE_NAME = "words.txt";
	private final static String FOLDER_TXT = "txtFiles";
	public static Configuration conf = null;
	
	static {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(false)
		  .setOAuthConsumerKey("6S5z4JRsLlRCTxFQrEbtBA")
		  .setOAuthConsumerSecret("QAFSQK18WaYX9c1PepZj46O0lRGyajfgTb1wJAdvW0")
		  .setOAuthAccessToken("1370188885-an8iXcloxa3VxYy6aVGxi4r8jiVlrLaJPubEpYK")
		  .setOAuthAccessTokenSecret("tDQuev1CvFpHDIDmP1XbWx9WRfzFbUosp6Fn4Enut8");
		conf = cb.build();
	}
	
	
	public static List<String> getWordsSet() {
		File file = new File(FOLDER_TXT+File.separator+TXT_WORDS_FILE_NAME);
		if (file.exists()) {	
			List<String> lines;
			try {
				lines = Files.readAllLines(Paths.get(FOLDER_TXT+File.separator+TXT_WORDS_FILE_NAME), Charset.forName("UTF-8"));
				System.out.println("Gathering tweets with these words: ");
				System.out.println(lines.toString());
				for(String line:lines){
					  System.out.println(line);
				}
				return lines;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return null;
	}
	
	public static void saveInTXT(String fileName, List<String> list) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(FOLDER_TXT+File.separator+fileName));
		for (String value:list) {
			pw.println(value);	
		}
		pw.close();
	}
	
	public static void storeAccessToken(long l, AccessToken accessToken) throws FileNotFoundException {
		System.out.println("UseID: "+ l + " AccesToken:"+accessToken);
		File file = new File(FOLDER_TXT+File.separator+TXT_FILE_NAME);
		
		if (!file.exists()) {
			PrintWriter out = new PrintWriter(file);
			out.println("UserID: "+l);
			out.println("AccessToken: "+accessToken);
			out.close();
		} 
		
	}
	
	public static AccessToken loadAccessToken() {
		return new AccessToken(conf.getOAuthAccessToken(), conf.getOAuthAccessTokenSecret());
	}
}
