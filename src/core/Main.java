package core;


import twitter.TwitterActions;
import twitter.Util;


public class Main {

	 public static void main(String args[]) throws Exception {
		 
//		 Util.getWordsSet();
		 
		 TwitterActions.querySearch("UFC");
		 TwitterActions.querySearch("#UFCNOCOMBATE");
		 
		 
		 Util.saveInTXT("UFC", TwitterActions.querySearch("UFC"));
		 Util.saveInTXT("Jacare", TwitterActions.querySearch("Jacare"));
		 Util.saveInTXT("UfcNoCombate", TwitterActions.querySearch("#ufcnocombate"));
		 		 
		 
		 
//		 Util.saveInTXT("São Paulo", TwitterActions.search("São Paulo"));
//		 Util.saveInTXT("Globo", TwitterActions.search("Globo"));
//		 Util.saveInTXT("Fox Sports", TwitterActions.search("Fox Sports"));
		 
//		 TwitterActions.postStatus("Testeasasae");
//		 Crawler.liveStreamer();
//		 for(;;){
			 
//		 }
//		 System.exit(0);
	 }
}
