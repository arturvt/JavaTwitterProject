package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.util.PSQLException;

import extra.Constants;

public class DataBaseManager {

	private static Connection con = null;
	private static Statement st = null;
	private static ResultSet rs = null;
	
	public static void startConnection() throws SQLException {

		String url = "jdbc:postgresql://localhost/TwitterDB";
		String user = "postgres";
//		String password = "postgres";
		String password = "261286";
		// Estabelece a conexão
		con = DriverManager.getConnection(url, user, password);
		// Cria a comunicação
		st = con.createStatement();
		// Envio do query
		rs = st.executeQuery("SELECT VERSION()");
		// Se OK, volta uma string com a resposta
		if (rs.next()) {
			System.out.println("Connection established. PostGresql Version:"+rs.getString(1));
		}
	}
	
	public static void closeConnection() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DataBaseManager.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
		}
	}

	public static void insertTweet(Date date, String word, String geoLoc, String tweet) throws SQLException {
        PreparedStatement pst = null;

        String stm = Constants.TWEET_QUERY;
        pst = con.prepareStatement(stm);
        pst.setTimestamp(1, new Timestamp(date.getTime()));
        pst.setString(2, geoLoc);
        pst.setString(3, word);
        pst.setString(4, tweet);
		try {
		     pst.executeUpdate();
		} catch (PSQLException e) {
			System.out.println("-------");
			System.out.println("Could not include current tweet. Msg: "+e.getMessage());
			System.out.println("Tweet content:");
			System.out.println("Tweet: "+tweet);
			System.out.println("Geo: "+geoLoc);
		}
	}
	
	public static void main(String[] args) {
		try {
			startConnection();
//			insertTweet();
			System.out.println("End of program.");
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DataBaseManager.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DataBaseManager.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}
}
