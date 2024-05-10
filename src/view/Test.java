package view;



import javax.swing.JPanel;
import javax.swing.SwingConstants;

import database.DatabaseHandler;
import model.ListQuestion;
import model.Question;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Test extends JPanel implements ActionListener   {
	private int score = 0;
	JButton btnExit;
	private static final long serialVersionUID = 1L;
	private ArrayList<Question> questionList;
    private JLabel questionLabel;
	private JButton[] answerJButton =  new JButton[4];
	public ArrayList<Question> copyList;
	public String currentAnswer;
	public int  currentQuestion;
	public ArrayList<Boolean> checkComplete ;
	public ArrayList<CircleButton> CircleButtons ;
	public MainScreen frame;
	private CountdownTimer countdownTimer;
	JLabel countdownLB;
	DatabaseHandler connectDB;
	String userName;
	int maxTime = 3600;
	int testNumber;
	public Test(MainScreen frame, String userName, String src, int testNumber)  {
		setLayout(null);
		connectDB = new DatabaseHandler();
		this.frame = frame;
		this.userName = userName;
		this.testNumber = testNumber;
		copyList = questionList = new ListQuestion(src).listQuestion;
		
		questionLabel = new JLabel("Câu hỏi");
		questionLabel.setBounds(181, 236, 594, 77);
		add(questionLabel);
		setLayout(null);

		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 5, 5); 
        JPanel listQuestionPanel = new JPanel(flowLayout);
        listQuestionPanel.setBounds(340, 22, 543, 188); 
        
		JPanel listAnswerPanel = new JPanel();
		listAnswerPanel.setLayout(null);
        listAnswerPanel.setBounds(144, 335, 663, 199);
        add(listAnswerPanel);
		add(listAnswerPanel);
		
		
		answerJButton[0] = new JButton();
		answerJButton[0] = createJButton("a" ,35, 11, 278, 73);
		listAnswerPanel.add(answerJButton[0]);
		answerJButton[1] = new JButton();
		answerJButton[1]=  createJButton("b" ,351, 11, 278, 73);
		listAnswerPanel.add(answerJButton[1]);
		answerJButton[2] = new JButton();
		answerJButton[2] = createJButton("c" ,35, 102, 278, 73);
		listAnswerPanel.add(answerJButton[2]);
		answerJButton[3] = new JButton();
		answerJButton[3]= createJButton("d" ,351, 102, 278, 73);
		answerJButton[3].setBounds(351, 102, 278, 73);
		listAnswerPanel.add(answerJButton[3]);
		checkComplete = new ArrayList<>();
		for(int i = 0;i<30;i++) {
			checkComplete.add(false);
		}
		displayQuestion(0);
		currentAnswer =  questionList.get(0).trueAnswer; 
		CircleButtons = new ArrayList<>();
        for (int i = 1; i <31; i++) {
            CircleButton circleButton = new CircleButton();
            circleButton.setText(Integer.toString(i));
            CircleButtons.add(circleButton);
            int indexQuestion = i - 1;           
            circleButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	displayQuestion(indexQuestion);
                	currentAnswer =  questionList.get(indexQuestion).trueAnswer; 
                    System.out.println("Circle clicked");
                }
            });
            listQuestionPanel.add(CircleButtons.get(i - 1));
}
        add(listQuestionPanel);
       
		
		
		
		JPanel timePanel = new JPanel();
		timePanel.setBounds(170, 74, 169, 51);
		add(timePanel);
		timePanel.setLayout(null);
		
		JLabel timeLabel = new JLabel("Time:");
		timeLabel.setBounds(10, 11, 40, 31);
		timePanel.add(timeLabel);
		
		countdownLB = new JLabel();
		countdownLB.setBounds(44, 11, 125, 31);
		countdownTimer = new CountdownTimer(maxTime, countdownLB, this,true);
		countdownTimer.start();
		timePanel.add(countdownLB);
		
		btnExit= new JButton("Submit");
		btnExit.setBounds(874, 530, 89, 23);
		btnExit.addActionListener(this);
		add(btnExit);
		

	}
	private JButton createJButton(String answer , int x, int  y, int height, int width) {
		JButton JButton  = new JButton(answer);
		JButton.addActionListener(this);
		JButton.setHorizontalAlignment(SwingConstants.CENTER);
		JButton.setBackground(Color.YELLOW);
		JButton.setBounds(x, y, height, width);
		
		return JButton;
		
	}
	private void displayQuestion(int index) {
		 currentQuestion = index;
		 
	      if (index < 0 || index >= questionList.size()) return;
	      if(checkComplete.get(index)  == true) {
	    	  for (JButton btn : answerJButton)
            btn.setEnabled(false);
	      } else {
	    	  for (JButton btn : answerJButton)
	              btn.setEnabled(true);
	      }
	      Question question = copyList.get(index);
	      questionLabel.setText(question.question);
	      
	      for (int i = 0; i < 4; i++) {
	    	  String answerText = "<html>"+"&nbsp;&nbsp;" + question.listAnswer.get(i).replaceAll("\n", "<br>") + "</html>";
	          answerJButton[i].setText(answerText);
	      }
	  }
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean isTrue = false; 
	    for (int i = 0; i < 4; i++) {
	        if (e.getSource() == answerJButton[i]) {
	        	currentAnswer = "<html>"+"&nbsp;&nbsp;" + currentAnswer.replaceAll("\n", "<br>") + "</html>";
	            if(currentAnswer.equals(answerJButton[i].getText())) {	                
	                isTrue = true;	                
	            } 
	            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

	            if (confirm == JOptionPane.YES_OPTION) {
	            	if(isTrue) {
	            		checkComplete.set(currentQuestion, true);
	            		score++;
	            		System.out.println("Correct answer! Score: " + score);
	            	}
	            	for (JButton btn : answerJButton)
                    btn.setEnabled(false);
	            	checkComplete.set(currentQuestion, true);
	            	CircleButtons.get(currentQuestion).setBackground(Color.RED);
	            	if(currentQuestion < questionList.size()-5) {
	            		displayQuestion(currentQuestion+1);
	            	}
            		
	            }
	        }
	    }
	    if(e.getSource()== btnExit) {
	    	 countdownTimer.setRunning(false);
	    	 connectDB.updateUserScore(userName,getPoint(), testNumber);
	    	 int time = countdownTimer.getTimeRemaining();
	    	 double point = getPoint();
	    	 double takeTime = (maxTime - time) / 60.0;
	    	 double roundedTakeTime = Math.ceil(takeTime * 100) / 100;
	    	 String Time = String.valueOf(roundedTakeTime);
	    	 String Point = String.valueOf(point);
	    	 String scores = String.valueOf(score);
	    	 String result ="Số câu làm được: "+ scores +", Tổng điểm là = " + Point + ", Thời gian làm: " + Time; 
	 	    frame.showDialog(result, "Bạn đã hoàn thành bài thi",true);
	    	
	    }
	}
	public double getPoint() {
	    double rawScore = (double) score * 10 / 30;
	    String formattedScore = String.format("%.2f", rawScore); 
	    return Double.parseDouble(formattedScore);
	}

	public void createThread() {
		int time = countdownTimer.getTimeRemaining();
		countdownTimer = new CountdownTimer(time,countdownLB , this, true);
		countdownTimer.start();
		
	}

	public void endTest() {
	    double finalScore = getPoint();
	    int time = countdownTimer.getTimeRemaining();
	   	 double point = getPoint();
	   	 double takeTime = (maxTime - time) / 60.0;
	   	 double roundedTakeTime = Math.ceil(takeTime * 100) / 100;
	   	 String Time = String.valueOf(roundedTakeTime);
	   	 String Point = String.valueOf(point);
	   	 String scores = String.valueOf(score);
	   	 String message ="Số câu làm được: "+ scores +", Tổng điểm là = " + Point + ", Thời gian làm: " + Time; 
	    //String message = "Thời gian đã kết thúc. Điểm của bạn: " + finalScore;
	    JOptionPane.showMessageDialog(null, message, "Kết thúc bài test", JOptionPane.INFORMATION_MESSAGE);  
	    
	    connectDB.updateUserScore(userName,finalScore, testNumber);
	    frame.showDialog(message, "Bạn đã hoàn thành bài thi",true);
	    
	}
}


