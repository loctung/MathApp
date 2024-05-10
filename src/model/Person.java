package model;

public class Person {
	String userName;
	Double score;
	int testNumber;
	
	public Person(String userName, Double score, int testNumber) {
		
		this.userName = userName;
		this.score = score;
		this.testNumber = testNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public int getTestNumber() {
		return testNumber;
	}

	public void setTestNumber(int testNumber) {
		this.testNumber = testNumber;
	}

	public int compareTo(Person other) {
        return this.score.compareTo(other.getScore());
    }

}
