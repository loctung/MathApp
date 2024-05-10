package view;

import javax.swing.*;

//import view.MainScreen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame implements ActionListener {

	Test test;
	String userName; 
	
	private static final long serialVersionUID = 1L;
	
	public MainScreen(String userName) {
        setTitle("Ứng Dụng Học Toán");
        setResizable(false);
        setSize(1020, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        
        add(createMainPanel()) ;
        setVisible(true);
        this.userName = userName;
    }
    public JPanel createMainPanel() {
    	JLabel labelBg = new JLabel();
        labelBg.setIcon(resizeImg("/icon/bg.jpg"));
        getContentPane().add(labelBg, BorderLayout.NORTH);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        buttonPanel.add(panel);
        
        panel.setLayout(null);

        JButton btnMultiplicationTable = new JButton("Bảng Cửu Chương");
        btnMultiplicationTable.setBounds(420, 60, 150, 27);
        btnMultiplicationTable.setBackground(Color.CYAN);
        btnMultiplicationTable.addActionListener(this);
        panel.add(btnMultiplicationTable);


        JButton btnTests = new JButton("Đề Kiểm Tra");
       
        btnTests.setBounds( 420, 110, 150, 27);
        btnTests.setBackground(Color.CYAN);
        btnTests.addActionListener(this);
        panel.add(btnTests);

        JButton btnRanking = new JButton("Bảng Xếp Hạng");
        btnRanking.setBounds(420, 160, 150, 27);
        btnRanking.setBackground(Color.CYAN);
        btnRanking.addActionListener(this);
        panel.add(btnRanking);
        
        JButton btnChallenges = new JButton("Exit");
        btnChallenges.setBounds(420, 210, 150, 27);
        btnChallenges.setBackground(Color.CYAN);
        btnChallenges.addActionListener(this);
        panel.add(btnChallenges);

        return panel;
    }
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();
        if (source.getText().equals("Bảng Cửu Chương")) {
        	getContentPane().removeAll();
        	getContentPane().add(new MultiplicationTableInteractive(this), BorderLayout.CENTER);
            revalidate();
            repaint();
  
        	
            System.out.println("Bạn đã chọn Bảng Cửu Chương");
        } else if (source.getText().equals("Exit")) {
            System.exit(0);
        } else if (source.getText().equals("Đề Kiểm Tra")) {
        	
        	createTestSelectionWindow();
           
            System.out.println("Bạn đã chọn Đề Kiểm Tra");
        } else if (source.getText().equals("Bảng Xếp Hạng")) {
        	getContentPane().removeAll();
        	getContentPane().add(new RankPanel(this), BorderLayout.CENTER);
            revalidate();
            repaint();
            System.out.println("Bạn đã chọn Bảng Xếp Hạng");
        }
    }

    public ImageIcon resizeImg(String src) {
        ImageIcon icon = new ImageIcon(getClass().getResource(src));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(this.getWidth(),this.getHeight()/3,java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        return icon;
    }
    public void showDialog(String message,String title, Boolean haveData) {
 
        int select = JOptionPane.showOptionDialog(this, message,title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (select == 0) {
        	getContentPane().removeAll();
            getContentPane().add(createMainPanel(), BorderLayout.CENTER); // Thêm MainScreen mới
            revalidate(); // Làm mới JFrame
            repaint(); // Vẽ lại JFrame
        } else  { 
        	if(haveData) {
        		test.createThread();
        	}
        	
        }
    }
    private void createTestSelectionWindow(){
        JFrame testSelectionFrame = new JFrame();
        testSelectionFrame.setLayout(null);
        testSelectionFrame.setTitle("Chọn Đề Kiểm tra");
        testSelectionFrame.setResizable(false);
        testSelectionFrame.setSize(300, 200);
        testSelectionFrame.setLocationRelativeTo(null);
        //testSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel panel = new JPanel();
        panel.setSize(300, 200);
        panel.setLayout(null);
        

        JButton test1Button = new JButton("Đề 1");
        test1Button.setBounds(100,  20, 100, 30);
        test1Button.addActionListener(e -> {
            System.out.println("tét1");
            testSelectionFrame.setVisible(false);
            getContentPane().removeAll();
        	getContentPane().add(test = new Test(this, userName, "D:/AppHocToan/de1.txt", 1), BorderLayout.CENTER);
            revalidate();
            repaint();
            
        });

        JButton test2Button = new JButton("Đề 2");
        test2Button.setBounds(100,  60, 100, 30);
        test2Button.addActionListener(e -> {
        	System.out.println("tét2");
            testSelectionFrame.setVisible(false);
            getContentPane().removeAll();
        	getContentPane().add(test = new Test(this, userName, "D:/AppHocToan/de2.txt", 2), BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        JButton test3Button = new JButton("Đề 3");
        test3Button.setBounds(100,  100, 100, 30);
        test3Button.addActionListener(e -> {
        	testSelectionFrame.setVisible(false);
            getContentPane().removeAll();
        	getContentPane().add(test = new Test(this, userName, "D:/AppHocToan/de3.txt", 3), BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        panel.add(Box.createVerticalGlue()); // Add space at the top
        panel.add(test1Button);
        panel.add(test2Button);
        panel.add(test3Button);
        panel.add(Box.createVerticalGlue()); // Add space at the bottom

        testSelectionFrame.add(panel);
        testSelectionFrame.setVisible(true);
    }
    
    public static void main(String[] args) {

    	new MainScreen("abc");
    }
}
