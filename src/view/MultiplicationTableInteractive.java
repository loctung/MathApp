package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


public class MultiplicationTableInteractive extends JPanel implements ActionListener{
    private JPanel cards; // Panel that uses CardLayout
    private JTextArea displayArea;
    private CardLayout cardLayout;
    private JPanel buttonPanel;
    private JButton buttonLuyenTap;
    private int selectedNumber = 0; // The multiplication table number selected
    private int currentQuestionIndex = 0;
    private JLabel questionLabel;
    private JButton[] answerButtons = new JButton[4];
    JButton btnExit ;
	private int currentFactor;
	MainScreen main;
	

    public MultiplicationTableInteractive(MainScreen main) {
    	 cardLayout = new CardLayout();
         cards = new JPanel(cardLayout);
         cards.setPreferredSize(new Dimension(1000, 600));
         setupMultiplicationPanel();
         setupPracticePanel();

         add(cards);
         this.main=main;
    }

    private void setupMultiplicationPanel() {
        JPanel multiplicationPanel = new JPanel(new GridBagLayout());
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        GridBagConstraints constraints = new GridBagConstraints();

        // Add display area
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 5;
        constraints.weightx = 1;
        constraints.weighty = 1;
        multiplicationPanel.add(scrollPane, constraints);

        // Add buttons
        buttonPanel = new JPanel(new GridLayout(2, 5, 10, 10));  // Layout for buttons
        for (int i = 1; i <= 10; i++) {
            JButton button = new JButton(Integer.toString(i));
            button.setPreferredSize(new Dimension(100, 160)); // Increase these values to make buttons larger
            button.addActionListener(new ButtonActionListener(i, displayArea));
            buttonPanel.add(button);
        }

        // Reset GridBagConstraints and add button panel
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.weightx = 1;
        constraints.weighty = 3; // Increase weighty to make buttonPanel larger
        multiplicationPanel.add(buttonPanel, constraints);


        // Add Luyện tập button
        buttonLuyenTap = new JButton("Luyện tập");
        buttonLuyenTap.addActionListener(e -> {
            if (selectedNumber > 0) {
                currentQuestionIndex = 0;
                cardLayout.show(cards, "Practice");
                showNextQuestion();
                
            } else {
                JOptionPane.showMessageDialog(null, "Hãy chọn một bảng cửu chương trước!");
            }
        });

        // Reset GridBagConstraints and add Luyện tập button
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.weightx = 1;
        multiplicationPanel.add(buttonLuyenTap, constraints);
        cards.add(multiplicationPanel, "Multiplication");
        
        btnExit = new JButton("Exit");
        btnExit.setBackground(Color.red);
        btnExit.addActionListener(this);

        // Tạo một panel mới với FlowLayout để chứa cả hai nút
        JPanel buttonFlowLayout = new JPanel(new FlowLayout());

        // Thêm cả hai nút vào panel mới. Thêm btnExit trước để nó xuất hiện bên trái
        buttonFlowLayout.add(btnExit);
        buttonFlowLayout.add(buttonLuyenTap);

        // Reset GridBagConstraints và thêm panel mới vào multiplicationPanel
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.weightx = 1;
        multiplicationPanel.add(buttonFlowLayout, constraints);
    }

    private void setupPracticePanel() {
    	JPanel practicePanel = new JPanel(new GridLayout(6, 1, 5, 5));
        questionLabel = new JLabel("Câu hỏi ở đây");
        questionLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the text
        practicePanel.add(questionLabel);
        // Setup answer buttons
        
        
        
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JButton("Đáp án " + (i + 1));
            answerButtons[i].setPreferredSize(new Dimension(300, 60)); // Set new size for button
            answerButtons[i].setMargin(new Insets(10,10,10,10)); // Set margins for button
            int finalI = i;
            answerButtons[i].addActionListener(e -> {
                if (checkAnswer(finalI)) {
                    JOptionPane.showMessageDialog(null, "Đúng!");
                } else {
                    JOptionPane.showMessageDialog(null, "Sai!");
                }
                showNextQuestion();
            });
            
            JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            buttonContainer.add(answerButtons[i]);
            practicePanel.add(buttonContainer);
        }
        JButton btnExitPractice = new JButton("Exit");
        btnExitPractice.addActionListener(e -> cardLayout.show(cards, "Multiplication"));
        practicePanel.add(btnExitPractice);
        JPanel exitButtonPanel = new JPanel();
        exitButtonPanel.add(btnExitPractice);
        practicePanel.add(exitButtonPanel);

        cards.add(practicePanel, "Practice");
    }

   
    
    private void showNextQuestion() {
        Random random = new Random();
        currentFactor = random.nextInt(10) + 1;
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        questionLabel.setText(selectedNumber + " x " + currentFactor + " bằng bao nhiêu " + "?");
        int correctAnswer = selectedNumber * currentFactor;
        int correctButtonIndex = random.nextInt(4);
        Set<Integer> usedAnswers = new HashSet<>();  // Tập hợp để lưu các đáp án đã sử dụng
        usedAnswers.add(correctAnswer);  // Thêm đáp án đúng vào tập hợp

        for (int i = 0; i < 4; i++) {
            if (i == correctButtonIndex) {
                answerButtons[i].setText(Integer.toString(correctAnswer));
            } else {
                int wrongAnswer;
                do {
                    wrongAnswer = correctAnswer + random.nextInt(9) + 1;  // Tạo đáp án sai
                } while (usedAnswers.contains(wrongAnswer));  // Đảm bảo đáp án sai không trùng lặp
                usedAnswers.add(wrongAnswer);  // Thêm đáp án sai vào tập hợp
                answerButtons[i].setText(Integer.toString(wrongAnswer));
            }
        }
    }


   
    private boolean checkAnswer(int buttonIndex) {
        String answerText = answerButtons[buttonIndex].getText();
        int answer = Integer.parseInt(answerText);
        int correctAnswer = selectedNumber * currentFactor;  // Sử dụng biến currentFactor
        return answer == correctAnswer;
    }

    class ButtonActionListener implements ActionListener {
        private int number;
        private JTextArea displayArea;

        public ButtonActionListener(int number, JTextArea displayArea) {
            this.number = number;
            this.displayArea = displayArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedNumber = number;
            displayArea.setText("");
            for (int i = 1; i <= 10; i++) {
                displayArea.append(number + " x " + i + " = " + (number * i) + "\n");
            }
        }
    }



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnExit) {
			main.showDialog("Bạn có muốn quay về màn hình chính?", "Warning", false);
		}
	}
}
