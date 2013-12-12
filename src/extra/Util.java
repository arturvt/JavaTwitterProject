package extra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Util {

	public static String convertTextToSentistrengthPattern(String text) {
		// Removes break lines
		String formmated = text.trim();
		formmated = text.replaceAll("\\s+", "+");

		// Removes spaces
		formmated = text.replaceAll(" ", "+");

		return formmated;
	}

	
	public static int classificateSentiment(String received) {
		String[] values = received.split(" ");
		int v1 = Integer.valueOf(values[0]);
		int v2 = Integer.valueOf(values[1]);
		int result = v1+v2;
		return result;
	}
	
	public static ArrayList<String> parseTXTToArray(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-9"));
		ArrayList<String> stringList = new ArrayList<String>();
		String valor;
		while ((valor = reader.readLine()) != null) {
			stringList.add(valor);
		}
		reader.close();
		return stringList;

	}

}
