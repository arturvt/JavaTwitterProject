package core;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import db.DataBaseManager;
import twitter.TwitterActions;

public class Main {

	public static void main(String args[]) throws Exception {

		try {
			System.out.println("Stating to fill the DB...");
			DataBaseManager.startConnection();
			TwitterActions.querySearchToSQL("#TheVoiceBrasil");
			TwitterActions.querySearchToSQL("#TheVoiceBR");
			TwitterActions.querySearchToSQL("#VBR");
			
//			TwitterActions.querySearchToSQL("Louro Jos�");
//			TwitterActions.querySearchToSQL("Ana Maria Braga");
			
			// insertTweet();
			System.out.println("End of program...");
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DataBaseManager.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			DataBaseManager.closeConnection();
		}

		// TwitterActions.querySearch("UFC");
		// TwitterActions.querySearch("#UFCNOCOMBATE");

		// Util.saveInTXT("UFC", TwitterActions.querySearch("UFC"));
		// Util.saveInTXT("Jacare", TwitterActions.querySearch("Jacare"));
		// Util.saveInTXT("UfcNoCombate",
		// TwitterActions.querySearch("#ufcnocombate"));
//		Util.saveInTXT("faustao", TwitterActions.querySearchToString("faustao"));

		// Util.saveInTXT("São Paulo", TwitterActions.search("São Paulo"));
		// Util.saveInTXT("Globo", TwitterActions.search("Globo"));
		// Util.saveInTXT("Fox Sports", TwitterActions.search("Fox Sports"));

		// TwitterActions.postStatus("Testeasasae");
		// Crawler.liveStreamer();
		// for(;;){

		// }
		// System.exit(0);
	}
}
