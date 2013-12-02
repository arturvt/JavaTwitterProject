package sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class SWN {
	private String pathToSWN = "D:\\Artur\\ProjetoMestrado\\SentiWord\\home\\swn\\www\\admin\\dump"
			+ File.separator + "SentiWordNet_3.0.0_20130122.txt";
	private HashMap<String, Double> _dict;

	public SWN() throws IOException {

		_dict = new HashMap<String, Double>();
		HashMap<String, Vector<Double>> _temp = new HashMap<String, Vector<Double>>();
		try {
			BufferedReader csv = new BufferedReader(new FileReader(pathToSWN));
			String line = "";
			while ((line = csv.readLine()) != null) {
				if (!line.startsWith("#")) {
					String[] data = line.split("\t");
					Double score = Double.parseDouble(data[2])
							- Double.parseDouble(data[3]);
					String[] words = data[4].split(" ");
					for (String w : words) {
						String[] w_n = w.split("#");
						w_n[0] += "#" + data[0];
						int index = Integer.parseInt(w_n[1]) - 1;
						if (_temp.containsKey(w_n[0])) {
							Vector<Double> v = _temp.get(w_n[0]);
							if (index > v.size())
								for (int i = v.size(); i < index; i++)
									v.add(0.0);
							v.add(index, score);
							_temp.put(w_n[0], v);
						} else {
							Vector<Double> v = new Vector<Double>();
							for (int i = 0; i < index; i++)
								v.add(0.0);
							v.add(index, score);
							_temp.put(w_n[0], v);
						}
					}
				}
			}
			Set<String> temp = _temp.keySet();
			for (Iterator<String> iterator = temp.iterator(); iterator
					.hasNext();) {
				String word = (String) iterator.next();
				Vector<Double> v = _temp.get(word);
				double score = 0.0;
				double sum = 0.0;
				for (int i = 0; i < v.size(); i++)
					score += ((double) 1 / (double) (i + 1)) * v.get(i);
				for (int i = 1; i <= v.size(); i++)
					sum += (double) 1 / (double) i;
				score /= sum;
				String sent = "";
				if (score >= 0.75)
					sent = "strong_positive";
				else if (score > 0.25 && score <= 0.5)
					sent = "positive";
				else if (score > 0 && score >= 0.25)
					sent = "weak_positive";
				else if (score < 0 && score >= -0.25)
					sent = "weak_negative";
				else if (score < -0.25 && score >= -0.5)
					sent = "negative";
				else if (score <= -0.75)
					sent = "strong_negative";
				_dict.put(word, score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Double extract(String word) {
		Double total = new Double(0);
		if (_dict.get(word + "#n") != null)
			total = _dict.get(word + "#n") + total;
		if (_dict.get(word + "#a") != null)
			total = _dict.get(word + "#a") + total;
		if (_dict.get(word + "#r") != null)
			total = _dict.get(word + "#r") + total;
		if (_dict.get(word + "#v") != null)
			total = _dict.get(word + "#v") + total;
		return total;
	}
	
	private static void detectSentiment(TxtClassified sentence) throws IOException {
		SWN test = new SWN();
		String[] words = sentence.getText().split("\\s+");
		double totalScore = 0;
		for (String word : words) {
			word = word.replaceAll("([^a-zA-Z\\s])", "");
			if (test.extract(word) == null)
				continue;
			totalScore += test.extract(word);
		}
		sentence.setTotalScore(totalScore);
//		System.out.println(totalScore);
//		if (totalScore >= 0.75)
//			System.out.print("strong_positive");
//		else if (totalScore > 0.25 && totalScore <= 0.5)
//			System.out.print("positive");
//		else if (totalScore > 0 && totalScore >= 0.25)
//			System.out.print("weak_positive");
//		else if (totalScore < 0 && totalScore >= -0.25)
//			System.out.print("weak_negative");
//		else if (totalScore < -0.25 && totalScore >= -0.5)
//			System.out.print("negative");
//		else if (totalScore <= -0.75)
//			System.out.print("strong_negative");

	}
	
	private static ArrayList<TxtClassified> readFile(File file) throws IOException {
		 BufferedReader br = new BufferedReader(new FileReader(file));
		 ArrayList<TxtClassified> listTxt = new ArrayList<TxtClassified>();
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
		        while (line != null) {
		        	if (line.startsWith(":")) {
		        		line = line.substring(2,line.length());
		        		if (line.startsWith("RT")) {
			        		line = line.substring(3,line.length());
			        		if (line.startsWith("@")) {
				        		line = line.substring(line.indexOf(":")+1,line.length());
				        	}
			        	}
		        	}
		        	if (line.startsWith(" ")) {
		        		line = line.substring(1,line.length());
		        	}
		        	listTxt.add(new TxtClassified(line));
		            sb.append(line);
		            sb.append('\n');
		            line = br.readLine();
		        }
//		        String everything = sb.toString();
		        System.out.println("***************************");
		        System.out.println("File: "+file.getName());
//		        System.out.println(everything);
		    } finally {
		        br.close();
		    }
	        return listTxt;
	}
	
	
	int weak_negative = 0;
	int negative = 0;
	int none = 0;
	int positive = 0;
	int strong_positive = 0;
	private void metrics() {
		
	}
	
	private static void classifyFilesFromFolder(String folderLocation) {
		File folder = new File(folderLocation);
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File file:files) {
				try {
					ArrayList<TxtClassified> lines = readFile(file);
					for (TxtClassified line:lines) {
						try {
							detectSentiment(line);
							System.out.println(line.getClassification());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {

		String folderLocation = "C:\\Users\\avt\\Dropbox\\Mestrado\\workspace\\TweetResults\\ENCONTRO COM FATIMA BERNARDES\\English\\";
//		String sentence = "Tony Ramos at #MaisVoce an unrivaled sympathy";
		classifyFilesFromFolder(folderLocation);
//		TxtClassified txtClassified = new TxtClassified(sentence);
//		try {
//			detectSentiment(txtClassified);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}

		
