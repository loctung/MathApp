package model;


import java.util.ArrayList;

public class Question {
	public String question;
	public ArrayList<String> listAnswer;
	public String trueAnswer;

	public Question(String question,ArrayList listAnswer,String trueAnswer) {
		this.question = question;
		this.listAnswer = listAnswer;
		this.trueAnswer = trueAnswer;
	}

}
