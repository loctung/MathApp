package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListQuestion {
    public ArrayList<Question> listQuestion;

    public ListQuestion(String src) {
        listQuestion = readFile(src);  
    }

    private ArrayList<Question> readFile(String src) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(src));
            String line;
            while ((line = reader.readLine()) != null) {
                String questionText = line;
                ArrayList<String> answers = new ArrayList<>();
                for (int i = 0; i < 4; i++) { 
                    answers.add(reader.readLine());
                }
                String trueAnswer = reader.readLine();
                Question question = new Question(questionText, answers, trueAnswer);
                questions.add(question);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static void main(String[] args) {
        ListQuestion list = new ListQuestion("D:/Apphoctoan/AppHocToan/test2.txt");
        for (int i = 0; i < list.listQuestion.size(); i++) {
            System.out.println(list.listQuestion.get(i).question);
            for (String answer : list.listQuestion.get(i).listAnswer) {
                System.out.println(answer);
            }
            System.out.println(list.listQuestion.get(i).trueAnswer);
        }
        System.out.println(list.listQuestion.size());
    }
}