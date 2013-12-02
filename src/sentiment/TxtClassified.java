package sentiment;

public class TxtClassified {
	private String text;
	private double totalScore = 0;
	private String classification = "none";
	
	
//	public enum classification() {
//		
//	}
	
	public TxtClassified(String text) {
		this.text = text;
		
	}
	
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
		if (totalScore >= 0.75)
			setClassification("strong_positive");
		else if (totalScore > 0.25 && totalScore <= 0.5)
			setClassification("positive");
		else if (totalScore > 0 && totalScore >= 0.25)
			setClassification("weak_positive");
		else if (totalScore < 0 && totalScore >= -0.25)
			setClassification("weak_negative");
		else if (totalScore < -0.25 && totalScore >= -0.5)
			setClassification("negative");
		else if (totalScore <= -0.75)
			setClassification("strong_negative");

	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
	
}
